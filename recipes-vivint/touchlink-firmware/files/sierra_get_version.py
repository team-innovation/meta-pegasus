#!/usr/bin/python3

import os, time, sys
from serial import Serial

class SierraHL7588:

    def __init__(self):
        self._serial_port = None

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

    def close_serial_port(self):
        if self._serial_port:
            self._serial_port.close()
            self._serial_port = None

    def get_firmware_version(self):
        try:
            self.open_serial_port("/dev/ttyACM0")

            if self._serial_port is not None:
                self._serial_port.flushInput()
                self._serial_port.flushOutput()
                self._serial_port.write(b"AT+CGMR\r")
                self._serial_port.flush()

                buffer = self._serial_port.read(len("AT+CGMR\r"))

                if len(buffer) == 0:
                    return ""

                buffer = self._serial_port.read()
                while self._serial_port.inWaiting() > 0:
                    buffer += self._serial_port.read()

                # strip the command down to just the returned version
                tuples = buffer.decode("utf-8").split("\r\n")

                if "OK" in tuples:
                    return tuples[1]
            else:
                print("Error opening serial port")

        except Exception as exception:
            print("Exception opening serial port: {}".format(exception))

        return ""

if __name__ == "__main__":
    sierra_modem = SierraHL7588()

    version = sierra_modem.get_firmware_version()
    sierra_modem.close_serial_port()
    print(version)
