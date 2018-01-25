#!/usr/bin/python3

import os, time, sys, glob, subprocess
from serial import Serial
from sys import argv

class SierraHL7588:

    CARRIER_ATT = 1
    CARRIER_VERIZON = 2

    SIM_ATT_PREFIX = "8901"
    SIM_VERIZON_PREFIX = "8914"
    SIM_ROGERS_PREFIX = "8930"
    SIM_TELUS_PREFIX = "8912"

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

    def turn_on(self):
        self.serial_gpio_access()
        if not self.serial_gpio_is_enabled():
            self.serial_gpio_enable(False)
            self.serial_gpio_enable(True)
            return True
        return False

    def write_command(self, command):
        if not self._serial_port:
            print("Serial port is not open - unable to write!")
            return

        self._serial_port.flushInput()
        self._serial_port.flushOutput()
        self._last_command = command
        self._serial_port.write(command)
        self._serial_port.write(b"\r")
        self._serial_port.flush()

    def read_result(self, retry=True):
        try:
            command_buffer = self._serial_port.read(len(self._last_command) + 1)

            if len(command_buffer) == 0:
                #print("Failed to echo command")
                return b""
            #else:
            #    print("ECHO command {}".format(command_buffer))

            # now try another read - if we don't get anything wait up to 5 seconds
            now = time.time()
            result_buffer = b""
            while len(result_buffer) == 0 and (time.time() - now) < 5:

                while self._serial_port.inWaiting() > 0:
                    result_buffer += self._serial_port.read()
                if len(result_buffer) == 0:
                    time.sleep(0.5)

            #print("Command {} Result: {}".format(command_buffer, result_buffer))
            return command_buffer + result_buffer
        except Exception as exception:
            self.close_serial_port()
            self.open_serial_port()
            if retry:
                return read_result(retry=False)


    def wait_for_ready(self):
        #print("Waiting for +PBREADY...")
        wait_time = 30
        now = time.time()
        result_buffer = b""
        while (time.time() - now) < wait_time and len(result_buffer) < 8:
            try:
                while self._serial_port.inWaiting() > 0:
                    result_buffer += self._serial_port.read()
                if len(result_buffer) == 0:
                    time.sleep(0.5)
            except:
                time.sleep(1)

        if result_buffer and b"+PBREADY" in result_buffer:
            return True

        print("Modem did not respond with +PBREADY")
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

    def get_firmware_version(self):
        try:
            if self._serial_port is not None:
                self.write_command(b"AT+CGMR")
                buffer = self.read_result()

                if len(buffer) == 0:
                    return ""

                # strip the command down to just the returned version
                tuples = buffer.decode("utf-8").split("\r\n")

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
                self.write_command(b"AT+CGSN")
                buffer = self.read_result()

                if len(buffer) == 0:
                    return ""

                # strip the command down to just the returned version
                tuples = buffer.decode("utf-8").split("\r\n")

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
                self.write_command(b"AT+KSIMSEL?")
                buffer = self.read_result()

                tuples = buffer.decode("utf-8").split("\r\n")
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
                self.write_command(bytes("AT+KSIMSEL={}".format(sim_number), "utf-8"))
                buffer = self.read_result()

                if b"OK" in buffer and wait:
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
                    self.write_command(b"AT+CCID")
                    buffer = self.read_result()

                    if b"SIM NOT INSERTED" in buffer:
                        return None

                    if b"ERROR" in buffer:
                        # wait a few seconds and try again
                        time.sleep(5)
                        continue

                    # strip the command down to just the returned version
                    tuples = buffer.decode("utf-8").split("\r\n")

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

        return ""

    def get_rx_power(self):
        try:
            self.write_command(b"AT+WMRXPOWER=1,2,600")
            buffer = self.read_result()

            tuples = buffer.decode("utf-8").split("\r\n")
            for tuple in tuples:
                if "+WMRXPOWER: " in tuple:
                    return tuple[12:]
        except:
            pass

        return ""

    def reset_nvram(self):
        try:
            if self._serial_port is not None:
                #print("Resetting modem...")
                self.write_command(b"AT+NVRST=2")
                self.read_result()
                self.close_serial_port()
                time.sleep(5)
                if self.open_serial_port("/dev/ttyACM0"):
                    self.wait_for_nvbackup()
                    return True
            else:
                print ("Serial port is not open - using hard reset")
                return False
        except:
            pass

        return False

    def reset(self, connect=True):
        try:
            if self._serial_port is not None:
                #print("Resetting modem...")
                self.write_command(b"AT+CFUN=1,1")
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
        self.write_command(b"AT+CPWROFF")
        self.read_result()
        self.close_serial_port()

    def wait_for_nvbackup(self):
        # try up to 10 times waiting 1 second between tries
        for n in range(10):
            self.write_command(b"AT+NVBU?")
            result = self.read_result()
            result = result.decode('utf-8')

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

    def reflash_modem(self, carrier):
        file_list = []
        if carrier == self.CARRIER_ATT:
            file_list = glob.glob("/var/lib/firmware/Sierra/*.A.*.fls")
        elif carrier == self.CARRIER_VERIZON:
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
            self.write_command(b"AT+CFUN=1,1")
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
                            return self.wait_for_nvbackup()
                else:
                    print("Flash operation completed with no output")
                    print(stdout)
                    if self.open_serial_port("/dev/ttyACM0"):
                        return self.wait_for_nvbackup()
            except subprocess.TimeoutExpired:
                # otherwise poll to see if the app is done
                result = flash_proc.poll()
                if result is not None:
                    print("Flash program complete - no stdout")
        else:
            print("Error reflashing modem - no firmware files found in /var/lib/firmware/Sierra")
            return False

        return False

if __name__ == "__main__":
    # if the command line contains ""--force" or "-f", then skip the file check
    if "--force" in argv or "-f" in argv:
        print("Resetting modem to default configuration...")
    else:
        # check for existence of id file - we only need to do it if it isn't there
        if os.path.exists("/media/extra/conf/modemids"):
            print("Modemids file found - exiting.")
            quit(0)

    sierra_modem = SierraHL7588()

    # try up to 3 times to get iccid's
    firmware_version = None
    imei = None
    sim1 = None
    sim2 = None
    rxpower = None

    print("Turning modem on...")
    powered_up = sierra_modem.turn_on()
    if not sierra_modem.open_serial_port("/dev/ttyACM0"):
        # reset and try again
        if not sierra_modem.reset_hard():
            print("Error: Modem is not responding")
            quit()

    if powered_up:
        sierra_modem.wait_for_nvbackup()

    firmware_version = sierra_modem.get_firmware_version()
    imei = sierra_modem.get_imei()

    print("Current firmware version is {}".format(firmware_version))

    # Flash the new boot loader (AT&T version)
    print("Reflashing modem with new boot loader...")
    if not sierra_modem.reflash_modem(sierra_modem.CARRIER_ATT):
        print("Error flashing new AT&T boot loader version - exiting")
        quit()

    # Resetting the NVRam can cause more problems than it fixes - so don't do it for now!
    # Reset NVRam (modem will reboot) - this clears the time, so nv backup times won't be correct
    #if not sierra_modem.reset_nvram():
    #    print("Resetting NVRAM returned False - resetting module")
    #    if not sierra_modem.reset_hard():
    #        print("Error: Modem is not responding")
    #        quit()

    for n in range(3):
        print("Changing to SIM 1 and resetting...")
        # change to SIM 1 and reset modem
        sierra_modem.select_sim(1, wait=False)
        if not sierra_modem.reset():
            print("Error: Modem is not responding")
            quit()

        # read SIM 1
        sim1 = sierra_modem.get_iccid(1)
        print("Read SIM 1 ICCID: {}".format(sim1))

        # select SIM 2
        sierra_modem.select_sim(2)

        # read SIM 2
        sim2 = sierra_modem.get_iccid(2)
        print("Read SIM 2 ICCID: {}".format(sim2))

        if sim1 and sim1 != sim2:
            # if we have a SIM1 value (SIM2 can be empty) and they are not the same, then break out of the loop
            break

        print("SIM1 and SIM2 have same ID {}, resetting and trying again...".format(sim1))

    # Current default for 2018 is AT&T.  When we want to change the default to Verizon, uncomment
    # the following lines:
    #if sim1 and sim1.startswith(sierra_modem.SIM_VERIZON_PREFIX):
    #    print("Reflashing modem to select Verizon...")
    #    if not sierra_modem.reflash_modem(sierra_modem.CARRIER_VERIZON):
    #        print("Error flashing Verizon firmware - exiting")
    #        quit()
    #    sierra_modem.wait_for_nvbackup()
    #    sierra_modem.select_sim(1, wait=False)
    #    if not sierra_modem.reset():
    #        print("Error: Modem is not responding")
    #        quit()
    #    sierra_modem.wait_for_nvbackup()

    # clear the read buffer and wait a few seconds
    sierra_modem.read_result()

    # try an extra AT command to make sure we're ready for receive test
    sierra_modem.write_command(b"AT")
    result = sierra_modem.read_result()

    # Do the receive test
    try_count = 3
    while try_count > 0:
        try_count -= 1
        rxpower = sierra_modem.get_rx_power()
        if rxpower:
            break

        print("Modem did not respond to rx power test, resetting...")
        # reset modem and try again
        sierra_modem.close_serial_port()
        if not sierra_modem.reset_hard():
            print("Error: Modem is not responding")
            quit()

    print("Firmware: {}".format(firmware_version))
    print("IMEI: {}".format(imei))
    print("SIM1: {}".format(sim1))
    print("SIM2: {}".format(sim2))
    print("RXPower: {}".format(rxpower))

    # write results to /media/extra/conf/modemids file
    cfgfile = open("/media/extra/conf/modemids", "w")
    cfgfile.write("{},{},{},{},{}\n".format(firmware_version, imei, sim1, sim2, rxpower))
    cfgfile.close()

    sierra_modem.power_down()
