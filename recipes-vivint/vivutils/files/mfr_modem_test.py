#!/usr/bin/python3

import os, time, sys
from serial import Serial

class SierraHL7588:

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
        self._serial_port.flushInput()
        self._serial_port.flushOutput()
        self._last_command = command
        self._serial_port.write(command)
        self._serial_port.write(b"\r")
        self._serial_port.flush()

    def read_result(self):
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

    def wait_for_ready(self):
        wait_time = 7
        now = time.time()
        result_buffer = b""
        while (time.time() - now) < wait_time and len(result_buffer) == 0:
            while self._serial_port.inWaiting() > 0:
                result_buffer += self._serial_port.read()
            if len(result_buffer) == 0:
                time.sleep(0.1)

        if result_buffer and b"PBREADY" in result_buffer:
            #print("Modem ready in {} seconds".format(time.time() - now))
            return True

        return False

    def open_serial_port(self, serial_port, baud_rate=115200, timeout=5):
        # make sure serial port is ready (file link exists) - give it up to 10 seconds
        for n in range(0, 20):
            if os.path.exists(serial_port):
                break
            else:
                time.sleep(0.5)

        if os.path.exists(serial_port):
            self._serial_port = Serial(port=serial_port, baudrate=baud_rate, timeout=timeout)
            self._serial_port.flushInput()
            self._serial_port.flushOutput()
            return True

        return False

    def close_serial_port(self):
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
                print("Serial port is not open")

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
                print("Serial port is not open")

        except Exception as exception:
            print("Error reading IMEI: {}".format(exception))

        return ""


    def get_sim(self):
        try:
            if self._serial_port is not None:
                #print("Selecting SIM {}...".format(sim_number))
                self.write_command(b"AT+KSIMSEL?")
                buffer = self.read_result()

                tuples = buffer.decode("utf-8").split("\r\n")
                for tuple in tuples:
                    if "+KSIMSEL: " in tuple:
                        return tuple[10:].split(",")[0]

                # strip the command down to just the returned version
                tuples = buffer.decode("utf-8").split("\r\n")

                #print(tuples)

                if "OK" in tuples:
                    for tuple in tuples:
                        if "+CCID: " in tuple:
                            return tuple[7:]
            else:
                print("Error reading sim selection")

        except Exception as exception:
            print("Error reading SIM {}: {}".format(sim_number, exception))

        return ""

    def select_sim(self, sim_number):
        try:
            if self._serial_port is not None:
                #print("Selecting SIM {}...".format(sim_number))
                self.write_command(bytes("AT+KSIMSEL={}".format(sim_number), "utf-8"))
                buffer = self.read_result()

                if b"OK" in buffer:
                    self.wait_for_ready()
            else:
                print("Serial port is not open")

        except Exception as exception:
            print("Error selecting SIM {}: {}".format(sim_number, exception))

    def get_iccid(self, sim_number):
        for n in range(3):
            try:
                if self._serial_port:
                    self.write_command(b"AT+CCID")
                    buffer = self.read_result()

                    # strip the command down to just the returned version
                    tuples = buffer.decode("utf-8").split("\r\n")

                    for tuple in tuples:
                        if "+CCID: " in tuple:
                            return tuple[7:]
                else:
                    print("Serial port is not open")

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

    def reset(self):
        try:
            if self._serial_port is not None:
                #print("Resetting modem...")
                self.write_command(b"AT+CFUN=1,1")
                self.read_result()
            else:
                print("Serial port is not open")
        except:
            pass

    def reset_hard(self):
        #print("Resetting modem...")
        self.serial_gpio_access()
        self.serial_gpio_reset(False)
        time.sleep(1)
        self.serial_gpio_reset(True)

if __name__ == "__main__":
    sierra_modem = SierraHL7588()

    # try up to 3 times to get iccid's
    firmware_version = None
    imei = None
    sim1 = None
    sim2 = None
    rxpower = None

    powered_up = sierra_modem.turn_on()
    if not sierra_modem.open_serial_port("/dev/ttyACM0"):
        # reset and try again
        sierra_modem.reset_hard()
        time.sleep(5)
        if sierra_modem.open_serial_port("/dev/ttyACM0"):
            sierra_modem.wait_for_ready()
        else:
            print("Modem did not respond - exiting")
            quit()

    if powered_up:
        sierra_modem.wait_for_ready()

    firmware_version = sierra_modem.get_firmware_version()
    imei = sierra_modem.get_imei()

    # Do the receive test
    try_count = 3
    while try_count > 0:
        rxpower = sierra_modem.get_rx_power()
        if rxpower:
            break
        # reset modem and try again
        sierra_modem.close_serial_port()
        sierra_modem.reset_hard()
        time.sleep(5)
        if sierra_modem.open_serial_port("/dev/ttyACM0"):
            sierra_modem.wait_for_ready()
        else:
            print("Modem did not respond - exiting")
            quit()

    # check current SIM - if 0 then we need to select another sim and reset
    sim_number = sierra_modem.get_sim()
    if sim_number == '0':
        # select SIM 1 and reset
        sierra_modem.select_sim(1)
        try:
            sierra_modem.reset()
            sierra_modem.close_serial_port()
        except:
            sierra_modem.close_serial_port()
        time.sleep(7)
        sierra_modem.open_serial_port("/dev/ttyACM0")
        sierra_modem.wait_for_ready()
        sim_number = sierra_modem.get_sim()

    # get SIM IDs
    try_count = 3
    while try_count > 0:
        for n in range(2):
            if sim_number == '1':
                sim1 = sierra_modem.get_iccid(1)
                sim_number = '2'
            elif sim_number == '2':
                sim2 = sierra_modem.get_iccid(2)
                sim_number = '1'

            if n == 0:
                # switch SIMs
                sierra_modem.select_sim(int(sim_number))

        if sim1 and sim2:
            if sim1 == sim2:
                print("Identical sims, resetting and trying again...")
                sim1 = None
                sim2 = None
            else:
                # we have sims and they are not the same - we're done
                break

        # Reset the modem and try getting the sims again
        try_count -= 1
        sierra_modem.close_serial_port()
        sierra_modem.reset_hard()
        time.sleep(5)
        if sierra_modem.open_serial_port("/dev/ttyACM0"):
            sierra_modem.wait_for_ready()
        else:
            print("Modem did not respond - exiting")
            quit()

    print("Firmware: {}".format(firmware_version))
    print("IMEI: {}".format(imei))
    print("SIM1: {}".format(sim1))
    print("SIM2: {}".format(sim2))
    print("RXPower: {}".format(rxpower))

    sierra_modem.reset()
    sierra_modem.close_serial_port()

    # write results to /var/log/modemcfg.log file
    cfgfile = open("/var/log/modemcfg.log", "w")
    cfgfile.write("{},{},{},{},{}\n".format(firmware_version, imei, sim1, sim2, rxpower))
    cfgfile.close()