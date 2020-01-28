#!/usr/bin/python3

import os, time, sys, glob, subprocess, shutil
from serial import Serial
from sys import argv

CARRIER_ATT = 1
CARRIER_VERIZON = 2

SIM_ATT_PREFIX = "8901"
SIM_VERIZON_PREFIX = "8914"
SIM_ROGERS_PREFIX = "8930"
SIM_TELUS_PREFIX = "8912"

class ModemDevice:
    def __init__(self):
        self._serial_port_name = ""
        self._serial_port = None
        self._last_command = None

    def serial_gpio_access(self):
        if os.path.exists("/sys/class/gsm/gsm0"):
            with open("/sys/class/gsm/gsm0/access", "w") as f:
                f.write('1')
                time.sleep(1)
        elif os.path.exists("/sys/class/cell/cell0"):
            with open("/sys/class/cell/cell0/access", "w") as f:
                f.write('1')
                time.sleep(1)

    def serial_gpio_enable(self, enable=True):
        if os.path.exists("/sys/class/gsm/gsm0"):
            with open("/sys/class/gsm/gsm0/enable", "w") as f:
                f.write('1' if enable else '0')
                time.sleep(1)
        elif os.path.exists("/sys/class/cell/cell0"):
            with open("/sys/class/cell/cell0/enable", "w") as f:
                f.write('1' if enable else '0')
                time.sleep(1)

    def serial_gpio_is_enabled(self):
        value = None
        if os.path.exists("/sys/class/gsm/gsm0"):
            with open("/sys/class/gsm/gsm0/enable", "r") as f:
                value = f.read()
        elif os.path.exists("/sys/class/cell/cell0"):
            with open("/sys/class/cell/cell0/enable", "r") as f:
                value = f.read()

        if value:
            if '0' in value:
                return False
            if '1' in value:
                return True

        return False

    def serial_gpio_reset(self, value=True):
        write_value = '1' if value else '0'
        if os.path.exists("/sys/class/gsm/gsm0"):
            with open("/sys/class/gsm/gsm0/reset", "w") as f:
                f.write(write_value)
                time.sleep(1)
        elif os.path.exists("/sys/class/cell/cell0"):
            with open("/sys/class/cell/cell0/reset", "w") as f:
                f.write(write_value)
                time.sleep(1)

    def serial_gpio_get_id(self):
        value = None
        if os.path.exists("/sys/class/gsm/gsm0"):
            with open("/sys/class/gsm/gsm0/id", "r") as f:
                value = f.read()
        elif os.path.exists("/sys/class/cell/cell0"):
            with open("/sys/class/cell/cell0/id", "r") as f:
                value = f.read()
        return int(value)

    def turn_on(self):
        self.serial_gpio_access()
        self.serial_gpio_enable(False)
        self.serial_gpio_enable(True)
        return True

    def turn_off(self):
        self.serial_gpio_access()
        self.serial_gpio_enable(False)
        return True

    def set_serial_port(self, port_name):
        self._serial_port_name = port_name

    def open_serial_port(self):
        if self._serial_port:
            return True

        if not self._serial_port_name:
            print("Error: Trying to open serial port with no serial port name configured.")
            return False

        #print("Opening serial port {}...".format(serial_port))
        # make sure serial port is ready (file link exists) - give it up to 30 seconds
        for n in range(0, 60):
            if os.path.exists(self._serial_port_name):
                break
            else:
                time.sleep(0.5)

        if os.path.exists(self._serial_port_name):
            self._serial_port = Serial(port=self._serial_port_name, baudrate=115200, timeout=5)
            return True

        return False

    def close_serial_port(self):
        #print("Closing serial port...")
        if self._serial_port:
            self._serial_port.close()
            self._serial_port = None

    def wait_for_port_closed(self, timeout=30):
        time_expired = time.monotonic() + timeout
        while os.path.exists(self._serial_port_name) and time.monotonic() < time_expired:
            time.sleep(1)

        if os.path.exists(self._serial_port_name):
            return False

        return True

    def write_command(self, command):
        if not self._serial_port:
            print("Serial port is not open - unable to write!")
            return

        self._serial_port.flushInput()
        self._serial_port.flushOutput()
        command = bytes(command, "utf-8")
        self._last_command = command
        self._serial_port.write(command)
        self._serial_port.write(b"\r")
        self._serial_port.flush()

    def read_result(self, retry=True, timeout=5):
        try:
            command_buffer = self._serial_port.read(len(self._last_command) + 1)

            if len(command_buffer) == 0:
                # print("Failed to echo command")
                return ""
            # else:
            #    print("ECHO command {}".format(command_buffer))

            # now try another read - if we don't get anything wait up to timeout seconds
            now = time.time()
            result_buffer = b""
            while len(result_buffer) == 0 and (time.time() - now) < timeout:

                while self._serial_port.inWaiting() > 0:
                    result_buffer += self._serial_port.read()
                if len(result_buffer) == 0:
                    time.sleep(0.5)

            # print("Command {} Result: {}".format(command_buffer, result_buffer))
            return command_buffer.decode("utf-8") + result_buffer.decode("utf-8")
        except Exception as exception:
            self.close_serial_port()
            self.open_serial_port()
            if retry:
                return self.read_result(retry=False)

    def monitor(self, timeout=10):
        orig_timeout = self._serial_port.timeout
        self._serial_port.timeout = timeout
        result_buffer = b""
        try:
            while self._serial_port.inWaiting() > 0:
                result_buffer += self._serial_port.read()
        except:
            pass

        self._serial_port.timeout = orig_timeout
        return result_buffer.decode("utf-8")

    def reset_hard(self):
        self.close_serial_port()
        self.serial_gpio_access()
        self.serial_gpio_reset(False)
        time.sleep(1)
        self.serial_gpio_reset(True)
        time.sleep(5)
        if self.open_serial_port():
            time.sleep(5)
            return True

        return False


# ----------------------------------------------------------------------------------------------------------------------
class SierraHL7588:

    def __init__(self, device):
        self._device = device
        self._last_command = None

    def wait_for_ready(self):
        #print("Waiting for +PBREADY...")
        wait_time = 10
        now = time.time()
        result_buffer = self._device.monitor(timeout=10)

        if result_buffer and "+PBREADY" in result_buffer:
            return True

        #print("Modem did not respond with +PBREADY")
        return False

    def get_firmware_version(self):
        try:
            self._device.write_command("AT+CGMR")
            buffer = self._device.read_result()

            if len(buffer) == 0:
                return ""

            # strip the command down to just the returned version
            tuples = buffer.split("\r\n")

            tuple_num = 0
            for tuple in tuples:
                if tuple == "OK":
                    break
                else:
                    tuple_num += 1

            if tuple_num > 1:
                return tuples[tuple_num - 2]

        except Exception as exception:
            print("Error reading version: {}".format(exception))

        return ""

    def read_ids(self):
        # try upto 3 times to get device IDs
        imei = self.get_imei()
        sim1 = None
        sim2 = None

        current_sim = self.get_sim()
        if current_sim == "0":
            # select SIM1 and restart modem
            self._device.write_command("AT+KSIMSEL=1")
            self._device.write_command("AT+CFUN=1,1")
            if not self.reset():
                return imei, sim1, sim2

            current_sim = self.get_sim()

        if current_sim == "1":
            sim1 = self.get_iccid()
            self.select_sim(2, wait=True)
        else:
            sim2 = self.get_iccid()
            self.select_sim(1, wait=True)

        current_sim = self.get_sim()
        if current_sim == "1":
            sim1 = self.get_iccid()
        else:
            sim2 = self.get_iccid()

        return imei, sim1, sim2

    def get_imei(self):
        try:
            self._device.write_command("AT+CGSN")
            buffer = self._device.read_result()

            if len(buffer) == 0:
                return ""

            # strip the command down to just the returned version
            tuples = buffer.split("\r\n")

            tuple_num = 0
            for tuple in tuples:
                if tuple == "OK":
                    break
                else:
                    tuple_num += 1

            if tuple_num > 1:
                return tuples[tuple_num - 2]

        except Exception as exception:
            print("Error reading IMEI: {}".format(exception))

        return ""

    def get_sim(self):
        try:
            self._device.write_command("AT+KSIMSEL?")
            buffer = self._device.read_result()
            tuples = buffer.split("\r\n")
            for tuple in tuples:
                if "+KSIMSEL: " in tuple:
                    return tuple[10:].split(",")[0]
        except Exception as exception:
            print("Error reading SIM {}: {}".format(sim_number, exception))

        return ""

    def select_sim(self, sim_number, wait=True):
        try:
            print("Switching to SIM{}...".format(sim_number))
            self._device.write_command("AT+KSIMSEL={}".format(sim_number))
            buffer = self._device.read_result()

            if "OK" in buffer and wait:
                self.wait_for_ready()

        except Exception as exception:
            print("Error selecting SIM {}: {}".format(sim_number, exception))

    def get_iccid(self):
        for n in range(3):
            try:
                self._device.write_command("AT+CCID")
                buffer = self._device.read_result()

                if "SIM NOT INSERTED" in buffer:
                    return None

                if "ERROR" in buffer:
                    # wait a few seconds and try again
                    time.sleep(5)
                    continue

                # strip the command down to just the returned version
                tuples = buffer.split("\r\n")

                for tuple in tuples:
                    if "+CCID: " in tuple:
                        ccid = tuple[7:]

                        # we get all zeros, it just didn't read correctly, try again
                        if ccid == "00000000000000000000":
                            time.sleep(5)
                            continue

                        return ccid

            except Exception as exception:
                print("Error reading SIM: {}".format(exception))
                self.reset_hard()

        return ""

    def get_rx_power(self):
        try:
            self._device.write_command("AT+WMRXPOWER=1,2,600")
            buffer = self._device.read_result()

            tuples = buffer.split("\r\n")
            for tuple in tuples:
                if "+WMRXPOWER: " in tuple:
                    return tuple[12:]
        except:
            pass

        return ""

    def reset(self, connect=True):
        try:
            #print("Resetting modem...")
            self._device.write_command("AT+CFUN=1,1")
            self._device.read_result()
            self._device.close_serial_port()
            if connect:
                time.sleep(7)
                if self._device.open_serial_port():
                    self.wait_for_nvbackup()
                    return True
                else:
                    print("Modem did not respond - using hard reset")
                    return self.reset_hard()
            else:
                return True
        except:
            pass

        return False

    def reset_hard(self):
        self._device.close_serial_port()
        self._device.serial_gpio_access()
        self._device.serial_gpio_reset(False)
        time.sleep(1)
        self._device.serial_gpio_reset(True)
        time.sleep(5)
        if self._device.open_serial_port():
            self.wait_for_nvbackup()
            return True

        return False

    def power_down(self):
        self._device.write_command("AT+CPWROFF")
        self._device.read_result()
        self._device.close_serial_port()

    def wait_for_nvbackup(self):
        # try up to 10 times waiting 1 second between tries
        for n in range(10):
            self._device.write_command("AT+NVBU?")
            result = self._device.read_result()

            if "NVBU: 0," in result and "NVBU: 1," in result and "NVBU: 2," in result:
                # split the output
                output = result.split("\r\n")
                version = None
                for line in output:
                    if "+NVBU" in line:
                        tuples = line.split(",")
                        if len(tuples) >= 3:
                            if not version:
                                # get the version of NVBU: 0
                                version = tuples[2]
                            # check NVBU 1, and NVBU 2 to make sure it's the same version as 0
                            elif tuples[2] != version:
                                time.sleep(1)
                                break
                        else:
                            # malformed line
                            time.sleep(1)
                            break
                    else:
                        # ignore line not containing +NVBU
                        pass
            else:
                time.sleep(1)
                continue

            return True

        print("Failed to verify NVRAM backup completion")
        return False

    def reflash_modem(self, firmware_file, force_flash=True, current_version=None):
        if firmware_file:
            flash_proc = subprocess.Popen(["/usr/bin/swdltool", firmware_file], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
            time.sleep(5)

            # reset the modem and close the serial port
            self._device.write_command("AT+CFUN=1,1")
            self._device.read_result()
            self._device.close_serial_port()

            try:
                stdout, stderr = flash_proc.communicate(timeout=60)

                # check stdout for completion string
                if stdout:
                    stdout = stdout.decode("utf-8")

                    # check result
                    if "failed" in stdout:
                        print("Flash operation failed!")
                        print(stdout)

                    elif "Firmware download successful" in stdout:
                        # flash has completed
                        print("Success flashing file {}".format(firmware_file))
                        if self._device.open_serial_port():
                            self.wait_for_nvbackup()
                            return True
                else:
                    print("Flash operation completed with no output")
                    print(stdout)
                    if self._device.open_serial_port():
                        self.wait_for_nvbackup()
                        return True
            except subprocess.TimeoutExpired:
                # otherwise poll to see if the app is done
                result = flash_proc.poll()
                if result is not None:
                    print("Flash program complete - no stdout")
        else:
            print("Error reflashing modem - no firmware files found in /var/lib/firmware/Sierra")
            return False

        return False


# ----------------------------------------------------------------------------------------------------------------------
class QuectelEG91:

    def __init__(self, device):
        self._device = device
        self._last_command = None
        self._orig_sim = 1

    def wait_for_ready(self):
        result_buffer = self._device.monitor(timeout=10)
        if result_buffer and "+QIND: PB DONE" in result_buffer:
            return True

        return False

    def get_firmware_version(self):
        def _get_output(output, command, prefix=None):
            lines = output.split("\r\n")
            for line in lines:
                if len(line) > 0:
                    if line == command:
                        continue
                    elif prefix:
                        if line.startswith(prefix):
                            return line[len(prefix):]
                    elif line == "OK":
                        break
                    else:
                        return line
            return ""

        try:
            # first get sim selection, so we know what SIM to return to if we reflash
            self._orig_sim = self.get_sim()
            print("Module is on SIM{}".format(self._orig_sim))

            # Get version and sub-version
            self._device.write_command("AT+QGMR")
            version = self._device.read_result()
            version = _get_output(version, "AT+QGMR\r")

            self._device.write_command("AT+CSUB")
            csub = self._device.read_result()
            csub = _get_output(csub, "AT+CSUB\r", prefix="SubEdition: ")

            if len(version) == 0 or len(csub) == 0:
                return ""
            return "{}-{}".format(version, csub)

        except Exception as exception:
            print("Error reading version: {}".format(exception))

        return ""

    def read_ids(self):
        imei = self.get_imei()
        sim1 = None
        sim2 = None

        current_sim = self.get_sim()

        # Take module out of auto mode so we won't try registering on Verizon
        self._device.write_command('AT+QMBNCFG="AutoSel",0')
        self._device.read_result()
        self._device.write_command('AT+QMBNCFG="Select","VoLTE-ATT"')
        self._device.read_result()

        if current_sim in ["0", 0]:
            sim1 = self.get_iccid()
            print("SIM0 ICCID: {}, Switching to SIM1...".format(sim1))
            self.select_sim(1)
            current_sim = "1"
            sim2 = self.get_iccid()
            print("SIM1 ICCID: {}".format(sim2))
        else:
            sim2 = self.get_iccid()
            print("SIM1 ICCID: {}, Switching to SIM0...".format(sim2))
            self.select_sim(0)
            current_sim = "0"
            sim1 = self.get_iccid()
            print("SIM0 ICCID: {}".format(sim1))

        print("Restoring auto select mode...")
        self._device.write_command('AT+QMBNCFG="AutoSel",1')
        self._device.read_result()

        if int(current_sim) != int(self._orig_sim):
            print("Restoring SIM selection to SIM{}...".format(self._orig_sim))
            self.select_sim(self._orig_sim, reboot=False)

        return imei, sim1, sim2

    def get_imei(self):
        try:
            self._device.write_command("AT+CGSN")
            buffer = self._device.read_result()

            if len(buffer) == 0:
                return ""

            # strip the command down to just the returned version
            tuples = buffer.split("\r\n")

            tuple_num = 0
            for tuple in tuples:
                if tuple == "OK":
                    break
                else:
                    tuple_num += 1

            if tuple_num > 1:
                return tuples[tuple_num - 2]

        except Exception as exception:
            print("Error reading IMEI: {}".format(exception))

        return ""

    def get_sim(self):
        try:
            self._device.write_command("AT+QDSIM?")
            buffer = self._device.read_result()
            tuples = buffer.split("\r\n")
            for tuple in tuples:
                if "+QDSIM: " in tuple:
                    return tuple[8:]
        except Exception as exception:
            print("Error reading SIM {}: {}".format(sim_number, exception))

        return ""

    def select_sim(self, sim_number, reboot=True):
        try:
            self._device.write_command("AT+QDSIM={}".format(sim_number))
            buffer = self._device.read_result()

            if "OK" in buffer:
                if reboot:
                    self.reset(connect=True)
        except Exception as exception:
            print("Error selecting SIM {}: {}".format(sim_number, exception))

    def get_iccid(self):
        # make sure sim is ready
        ready = False
        for n in range(3):
            self._device.write_command("AT+CPIN?")
            buffer = self._device.read_result()
            if "+CPIN: READY" in buffer:
                ready = True
                break
            else:
                time.sleep(5)

        if ready:
            self._device.write_command("AT+CCID")
            buffer = self._device.read_result()

            if "ERROR" in buffer:
                # wait a few seconds and try again
                time.sleep(5)
                return ""

            # strip the command down to just the returned version
            tuples = buffer.split("\r\n")

            for tuple in tuples:
                if "+CCID: " in tuple:
                    ccid = tuple[7:]
                    return ccid
        else:
            print("Error: SIM not ready")

        return ""

    def reset(self, connect=True):
        try:
            #print("Resetting modem...")
            self._device.write_command("AT+CFUN=1,1")
            self._device.read_result()
            self._device.close_serial_port()
            if connect:
                time.sleep(25)
                if self._device.open_serial_port():
                    self.wait_for_ready()
                    return True
                else:
                    print("Modem did not respond - using hard reset")
                    return self.reset_hard()
            else:
                return True
        except:
            pass

        return False

    def reset_hard(self):
        self._device.close_serial_port()
        self._device.serial_gpio_access()
        self._device.serial_gpio_reset(False)
        time.sleep(1)
        self._device.serial_gpio_reset(True)
        time.sleep(5)
        if self._device.open_serial_port():
            return True

        return False

    def power_down(self):
        print("Powering modem off...")
        try:
            for n in range(5):
                self._device.write_command("AT+QPOWD")
                buffer = self._device.read_result()
                if "OK" in buffer:
                    break
                else:
                    time.sleep(2)
            buffer = self._device.read_result(timeout=12)
            if "POWERED DOWN" in buffer:
                print("Device powered down")
        except:
            print("Power off request failed - forcing power off")

        self._device.turn_off()

    def reflash_modem(self, firmware_file, force_flash=True, current_version=None):
        if firmware_file:
            # close the serial port
            self._device.close_serial_port()

            flash_proc = subprocess.Popen(["/usr/local/bin/qfotatool", "-p", "/dev/ttyUSB4", "-f", "/var/lib/firmware/Quectel/"+firmware_file], bufsize=-1)
            time.sleep(10)
            result = False

            try:
                # wait up to 12 minutes for the flash to complete.
                flash_proc.communicate(timeout=720)
                if flash_proc.returncode == 0:
                    # flash has completed
                    print("Success flashing file {}".format(firmware_file))
                    if self._device.open_serial_port():
                        result = True
                else:
                    print("Flash operation failed with error {}".format(flash_proc.returncode))
            except Exception as exception:
                # otherwise poll to see if the app is done
                print("Exception running flash program: {}".format(exception))

            if result:
                return True

            # if there was an error upgrading the modem, then the modem may reboot up to 5 times.  We won't be able to
            # send "AT and get OK until it's done.
            for n in range(5):
                try:
                    if self._device.wait_for_port_closed():
                        if self._device.open_serial_port():
                            # try sending AT
                            self._device.write_command("AT")
                            buffer = self._device.read_result(timeout=1)
                            if buffer and "OK" in buffer:
                                return False
                            else:
                                self._device.close_serial_port()
                except:
                    self._device.close_serial_port()
        else:
            print("Error reflashing modem - no firmware files found in /var/lib/firmware/Quectel")
            return False

        return False


# ----------------------------------------------------------------------------------------------------------------------
def is_upgrade_needed(current_version, device_id):
    file_version = ""
    file_list = []
    if device_id is 1:  #Sierra
        if "SWIMCB71XX.A." in current_version or "SWIMCB71XX-AIM" in current_version:
            file_list = glob.glob("/var/lib/firmware/Sierra/*SWIMCB71XX-AIM*.fls")
            if not file_list:
                file_list = glob.glob("/var/lib/firmware/Sierra/RHL75xx.A.*.fls")
        elif "SWIMCB71XX.V." in current_version or "SWIMCB71XX-VC" in current_version:
            file_list = glob.glob("/var/lib/firmware/Sierra/*SWIMCB71XX-VC*.fls")
            if not file_list:
                file_list = glob.glob("/var/lib/firmware/Sierra/*SWIMCB71XX.V.*.fls")
        if len(file_list) > 0:
            file_list.sort()
            file_version = file_list[-1]
            print("Sierra HL7588 firmware update detected from {} to {}".format(current_version, file_version))
            return True, file_version
        else:
            return False, None
    elif device_id is 3:    #Quectel
        if os.path.exists('/var/lib/firmware/Quectel/version'):
            upgrade_file = ""
            with open("/var/lib/firmware/Quectel/version", "r") as content_file:
                version_text = content_file.read().strip("\n")
                version_elements = version_text.split(",")
                ver_from = version_elements[0]
                file_version = version_elements[1]
                upgrade_file = version_elements[2]
            if current_version == file_version:
                return False, None
            elif current_version == ver_from:
                print("Quectel EG91 firmware update detected from {} to {}".format(current_version, file_version))
                return True, upgrade_file

    return False, None


if __name__ == "__main__":
    MODEM_IDS_FILE = "/media/extra/conf/modemids"
    AT_PORT_HL7588 = "/dev/ttyACM0"
    AT_PORT_EG91 = "/dev/ttyUSB4"
    FIRMWARE_FOLDER = "/var/lib/firmware"
    modem = None
    update_firmware = False

    # open the device and determine modem type
    device = ModemDevice()

    print("Turning modem on...")
    powered_up = device.turn_on()

    # get device id
    device_id = device.serial_gpio_get_id()
    if device_id == 3:
        device.set_serial_port(AT_PORT_EG91)
        is_quectel = True
        # delete sierra firmware files
        try:
            shutil.rmtree(FIRMWARE_FOLDER + "/Sierra")
        except:
            pass
    else:
        device.set_serial_port(AT_PORT_HL7588)
        # delete quectel firmware files
        try:
            shutil.rmtree(FIRMWARE_FOLDER + "/Quectel")
        except:
            pass

    if not device.open_serial_port():
        # reset and try again
        if not device.reset_hard():
            print("Error: Modem is not responding")
            device.turn_off()
            quit()

    # check for modem response
    print("Checking for modem response...")
    found_ok = False
    for n in range(5):
        try:
            device.write_command("AT")
            result = device.read_result()
            if result and "OK" in result:
                found_ok = True
                break
            else:
                time.sleep(2)
        except:
            print("Modem may have rebooted - trying to open serial again...")
            device.close_serial_port()
            time.sleep(20)
            if device.open_serial_port():
                print("serial port open - trying again")
            else:
                print("Modem still not responding - resetting and trying again")
                device.reset_hard()

    if not found_ok:
        print("Modem is not responding")
        device.turn_off()
        quit()

    if device_id == 1:
        print("Found Sierra HL7588 modem")
        modem = SierraHL7588(device)
    elif device_id == 3:
        print("Found Quectel EG91 modem")
        modem = QuectelEG91(device)
    else:
        print("Modem not supported")
        device.turn_off()
        quit()

    # get the firmware version currently on the modem to check for an upgrade
    firmware_version = modem.get_firmware_version()
    print("Current firmware version is {}".format(firmware_version))

    update_firmware, update_file = is_upgrade_needed(firmware_version, device_id)
    if update_firmware:
        if device_id is 1:      # Sierra
            if "SWIMCB71XX.A." in firmware_version or "SWIMCB71XX-AIM" in firmware_version:
                result = modem.reflash_modem(update_file)
            elif "SWIMCB71XX.V." in firmware_version or "SWIMCB71XX-VC" in firmware_version:
                result = modem.reflash_modem(update_file)
            else:
                print("Unknown firmware version {} - can't update".format(firmware_version))
                modem.power_down()
                quit()
        elif device_id is 3:    # Quectel
            result = modem.reflash_modem(update_file)

        if result:
            print("Modem firmware update successful")
        else:
            print("Error updating firmware")
            # modem may be rebooting - wait for a bit before trying to open it
            device.close_serial_port()
            time.sleep(15)
            if not device.open_serial_port():
                if not device.reset_hard():
                    print("Unable to open serial port - exiting")
                    quit()
    else:
        print("Modem firmware is up to date")

    if os.path.exists(MODEM_IDS_FILE):
        print("Modemids file found, exiting.")
    else:
        imei = ""
        sim1 = ""
        sim2 = ""
        for n in range(3):
            try:
                print("Reading modem serial number and SIM IDs...")
                # read SIM ICCIDs
                imei, sim1, sim2 = modem.read_ids()
                break
            except:
                print("Modem may have rebooted - trying to open serial again...")
                device.close_serial_port()
                time.sleep(20)
                if device.open_serial_port():
                    print("serial port open - trying again")
                else:
                    print("Modem still not responding - resetting and trying again")
                    device.reset_hard()

        # set values to blank instead of None
        if not imei:
            imei = ""
        if not sim1:
            sim1 = ""
        if not sim2:
            sim2 = ""

        print("Firmware: {}".format(firmware_version))
        print("IMEI: {}".format(imei))
        print("SIM1: {}".format(sim1))
        print("SIM2: {}".format(sim2))

        # write results to /media/extra/conf/modemids file
        cfgfile = open(MODEM_IDS_FILE, "w")
        cfgfile.write("{},{},{},{}\n".format(firmware_version, imei, sim1, sim2))
        cfgfile.close()

    modem.power_down()
    quit()
