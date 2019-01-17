#!/usr/bin/python3

import os, time, sys, glob, subprocess
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
        if not self.serial_gpio_is_enabled():
            self.serial_gpio_enable(False)
            self.serial_gpio_enable(True)
            return True
        return False

    def open_serial_port(self, serial_port="/dev/ttyACM0", baud_rate=115200, timeout=5):
        if self._serial_port:
            return

        #print("Opening serial port {}...".format(serial_port))
        # make sure serial port is ready (file link exists) - give it up to 10 seconds
        for n in range(0, 20):
            if os.path.exists(serial_port):
                break
            else:
                time.sleep(0.5)

        if os.path.exists(serial_port):
            self._serial_port = Serial(port=serial_port, baudrate=baud_rate, timeout=timeout)
            return True

        return False

    def close_serial_port(self):
        #print("Closing serial port...")
        if self._serial_port:
            self._serial_port.close()
            self._serial_port = None

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
                return read_result(retry=False)

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

class SierraHL7588:

    def __init__(self, device):
        self._device = None
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
            if self._serial_port is not None:
                self._device.write_command("AT+CGMR")
                buffer = self.read_result()

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
            else:
                print("Error: Serial port is not open")

        except Exception as exception:
            print("Error reading version: {}".format(exception))

        return ""


    def get_imei(self):
        try:
            if self._serial_port is not None:
                self._device.write_command("AT+CGSN")
                buffer = self.read_result()

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
            else:
                print("Error: Serial port is not open")

        except Exception as exception:
            print("Error reading IMEI: {}".format(exception))

        return ""


    def get_sim(self):
        try:
            if self._serial_port is not None:
                self._device.write_command("AT+KSIMSEL?")
                buffer = self.read_result()

                tuples = buffer.split("\r\n")
                for tuple in tuples:
                    if "+KSIMSEL: " in tuple:
                        return tuple[10:].split(",")[0]

                # strip the command down to just the returned version
                tuples = buffer.decode("utf-8").split("\r\n")

                if "OK" in tuples:
                    for tuple in tuples:
                        if "+CCID: " in tuple:
                            return tuple[7:]
            else:
                print("Error reading sim selection")

        except Exception as exception:
            print("Error reading SIM {}: {}".format(sim_number, exception))

        return ""

    def select_sim(self, sim_number, wait=True):
        try:
            if self._serial_port is not None:
                #print("Selecting SIM {}...".format(sim_number))
                self._device.write_command("AT+KSIMSEL={}".format(sim_number))
                buffer = self.read_result()

                if "OK" in buffer and wait:
                    self.wait_for_ready()
            else:
                print("Error: Serial port is not open")

        except Exception as exception:
            print("Error selecting SIM {}: {}".format(sim_number, exception))

    def get_iccid(self, sim_number):
        #print("Getting ICCID for sim {}...".format(sim_number))
        for n in range(3):
            try:
                if self._serial_port:
                    self._device.write_command("AT+CCID")
                    buffer = self.read_result()

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

                            #print("SIM {} CCID: {}".format(sim_number, tuple[7:]))
                            return ccid
                else:
                    print("Error: Serial port is not open")

            except Exception as exception:
                print("Error reading SIM {}: {}".format(sim_number, exception))
                self.reset_hard()

        return ""

    def get_rx_power(self):
        try:
            self._device.write_command("AT+WMRXPOWER=1,2,600")
            buffer = self.read_result()

            tuples = buffer.split("\r\n")
            for tuple in tuples:
                if "+WMRXPOWER: " in tuple:
                    return tuple[12:]
        except:
            pass

        return ""

    def reset(self, connect=True):
        try:
            if self._serial_port is not None:
                #print("Resetting modem...")
                self._device.write_command("AT+CFUN=1,1")
                self.read_result()
                self.close_serial_port()
                if connect:
                    time.sleep(5)
                    if self.open_serial_port("/dev/ttyACM0"):
                        self.wait_for_nvbackup()
                        return True
                    else:
                        print("Modem did not respond - exiting")
                        return False
                else:
                    return True
            else:
                print ("Serial port is not open - using hard reset")
                return self.reset_hard()
        except:
            pass

        return False

    def reset_hard(self):
        self.close_serial_port()
        self.serial_gpio_access()
        self.serial_gpio_reset(False)
        time.sleep(1)
        self.serial_gpio_reset(True)
        time.sleep(5)
        if self.open_serial_port("/dev/ttyACM0"):
            self.wait_for_nvbackup()
            return True

        return False

    def power_down(self):
        self._device.write_command("AT+CPWROFF")
        self.read_result()
        self.close_serial_port()

    def wait_for_nvbackup(self):
        # try up to 10 times waiting 1 second between tries
        for n in range(10):
            self._device.write_command("AT+NVBU?")
            result = self.read_result()

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

    def reflash_modem(self, carrier, force_flash=True, current_version=None):
        file_list = []
        if carrier == CARRIER_ATT:
            file_list = glob.glob("/var/lib/firmware/Sierra/*.A.*.fls")
        elif carrier == CARRIER_VERIZON:
            file_list = glob.glob("/var/lib/firmware/Sierra/*-VC*.fls")
            if not file_list:
                file_list = glob.glob("/var/lib/firmware/Sierra/*.V.*.fls")

        firmware_file = None
        if file_list:
            # sort the list so if we have multiple files, we'll get the latest at the end of the list
            file_list.sort()
            firmware_file = file_list[-1]
        else:
            print("Error reflashing modem - invalid carrier specified, or no firmware files found in /var/lib/firmware/Sierra")
            return False

        if firmware_file:
            flash_proc = subprocess.Popen(["/usr/bin/swdltool", firmware_file], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
            time.sleep(5)

            # reset the modem and close the serial port
            self._device.write_command("AT+CFUN=1,1")
            self.read_result()
            self.close_serial_port()

            try:
                stdout, stderr = flash_proc.communicate(timeout=60)

                # check stdout for completion string
                if stdout:
                    stdout = stdout.decode("utf-8")

                    #print(stdout)

                    # check result
                    if "failed" in stdout:
                        print("Flash operation failed!")
                        print(stdout)

                    elif "Firmware download successful" in stdout:
                        # flash has completed
                        print("Success flashing file {}".format(firmware_file))
                        if self.open_serial_port("/dev/ttyACM0"):
                            self.wait_for_nvbackup()
                            return True
                else:
                    print("Flash operation completed with no output")
                    print(stdout)
                    if self.open_serial_port("/dev/ttyACM0"):
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

    def check_reg_state(self):
        try:
            if self._serial_port:
                self._device.write_command("AT+COPS?")
                buffer = self.read_result()

                if "+COPS: 0" in buffer:
                    # nothing more to do - we are in automatic registration state
                    print("Modem is configured for automatic registration")
                    return

                self._device.write_command("AT+COPS=0")
                self.read_result(timeout=30)
                print("Modem changed to automatic registration")
            else:
                print("Error: Serial port is not open")

        except Exception as exception:
            print("Error reading COPS state: {}".format(exception))
            self.reset_hard()

def is_upgrade_needed(current_version):
    file_version = ""
    file_list = []
    if ".A." in current_version:
        file_list = glob.glob("/var/lib/firmware/Sierra/*.A.*.fls")
    elif ".V." in current_version or "-VC" in current_version:
        file_list = glob.glob("/var/lib/firmware/Sierra/*-VC*.fls")
        if not file_list:
            file_list = glob.glob("/var/lib/firmware/Sierra/*.V.*.fls")

    if len(file_list) > 0:
        file_list.sort()
        file_version = file_list[-1]
        # the first element in the file list should be the newest
        if current_version in file_version:
            return False
    else:
        return False

    if file_version:
        print("Modem firmware update detected")
        return True

    return False

if __name__ == "__main__":
    MODEMID_FILENAME = "/media/extra/conf/modemids"
    modem = None
    flash_modem = True
    update_firmware = False

    # open the device and determine modem type
    device = ModemDevice()

    # check device id
    id = device.serial_gpio_get_id()
    if id > 1:
        print("Modem ID: {}, no flash check needed.".format(id))
        quit()

    print("Turning modem on...")
    powered_up = device.turn_on()
    if not device.open_serial_port("/dev/ttyACM0"):
        # reset and try again
        if not device.reset_hard():
            print("Error: Modem is not responding")
            device.turn_off()
            quit()

    # get the modem type
    device.write_command("AT+CGMM")
    result = device.read_result()
    if "HL7588" in result:
        modem = SierraHL7588(device)
    else:
        print("Modem not supported")
        device.turn_off()
        quit()

    # get the firmware version currently on the modem to check for an upgrade
    firmware_version = modem.get_firmware_version()

    # if the command line contains ""--force" or "-f", then skip the file check
    if "--force" in argv or "-f" in argv:
        print("Resetting modem to default configuration...")
    else:
        # check for existence of id file - if it's not there or doesn't have SIM1 then reflash the modem
        if os.path.exists(MODEMID_FILENAME):
            flash_modem = False
            id_file = open(MODEMID_FILENAME, "r")
            line = id_file.readline()
            id_file.close()
            tuples = line.split(',')

            # check to see if a firmware update is available
            if tuples and tuples[0]:
                update_firmware = is_upgrade_needed(firmware_version)
                if not update_firmware:
                    # if no update, then we can exit if sims have been read
                    # if we have at least one CCID, then the file is good - exit
                    if len(tuples) >= 4 and tuples[2]:
                        print("Modemids file found with SIM Id's - exiting.")
                        modem.power_down()
                        quit(0)

    # try up to 3 times to get iccid's
    firmware_version = None
    imei = None
    sim1 = None
    sim2 = None

    if powered_up:
        modem.wait_for_nvbackup()

    firmware_version = modem.get_firmware_version()
    imei = modem.get_imei()

    print("Current firmware version is {}".format(firmware_version))

    if flash_modem:
        print("Flashing modem with new boot loader...")
        # Flash the new boot loader (AT&T version)
        if not modem.reflash_modem(CARRIER_ATT):
            print("Error flashing new AT&T boot loader version - exiting")
            quit()
    elif update_firmware:
        result = True
        if ".A." in firmware_version:
            result = modem.reflash_modem(CARRIER_ATT)
        elif firmware_version in [".V.", "-VC"]:
            result = modem.reflash_modem(CARRIER_VERIZON)
        else:
            print("Unknown firmware version {} - can't update".format(firmware_version))
            modem.power_down()
            quit()

        if not result:
            print("Error updating firmware - exiting")
            modem.power_down()
            quit()

    # Resetting the NVRam can cause more problems than it fixes - so don't do it for now!
    firmware_version = modem.get_firmware_version()
    #    print("Resetting NVRAM returned False - resetting module")

    for n in range(3):
        print("Changing to SIM 1 and resetting...")
        # change to SIM 1 and reset modem
        modem.select_sim(1, wait=False)
        if not modem.reset():
            print("Error: Modem is not responding")
            quit()

        # read SIM 1
        sim1 = modem.get_iccid(1)
        print("Read SIM 1 ICCID: {}".format(sim1))

        # select SIM 2
        modem.select_sim(2)

        # read SIM 2
        sim2 = modem.get_iccid(2)
        print("Read SIM 2 ICCID: {}".format(sim2))

        if sim1 and sim1 != sim2:
            # if we have a SIM1 value (SIM2 can be empty) and they are not the same, then break out of the loop
            break

        print("SIM1 and SIM2 have same ID {}, resetting and trying again...".format(sim1))
        modem.reset()

    # Check for COPS deregistration
    modem.check_reg_state()

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
    cfgfile = open("/media/extra/conf/modemids", "w")
    cfgfile.write("{},{},{},{}\n".format(firmware_version, imei, sim1, sim2))
    cfgfile.close()

    modem.power_down()
