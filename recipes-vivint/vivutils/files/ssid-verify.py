#!/usr/bin/python3

from serial import Serial, SerialException
from subprocess import call
import time
from time import sleep
import os
import sys

class MAC_tests:

    def __init__(self):
        self.macaddr = ""
        self.saved_ssid = ""
        self.generated_ssid = ""

        self.serialdevice = "/dev/ttymxc1"
        self.interface = 'wlan1'
        self.platform = 'wallsly'

        try:
            self.serialport = Serial(self.serialdevice, 57600, timeout=2)
            self.serialport.flushOutput()
            self.serialport.flushInput()
        except Exception as ex:
            print('Failed to open serial port: {}'.format(ex))
            sys.exit(1)

    def reboot_nm(self):
        self.serialport.write("reboot -f\n".encode())
        sleep(45)

    def get_mac_address(self):
        for count in range(0, 2):
            result = b''
            while self.serialport.inWaiting() > 0:
                result += self.serialport.read()
            self.serialport.write("cat /sys/class/net/{}/address\n".format(self.interface).encode())
            sleep(.5)
            result = self.serialport.readline()
            sleep(.5)
            result = self.serialport.readline().decode('ascii')
            self.macaddr = result.strip()
            if self.macaddr and self.macaddr[0].isdigit():
                break

        print('MACADDR:\t{}'.format(self.macaddr))

    def get_expected_ssid(self):
        if self.macaddr and self.macaddr[0].isdigit():
            result=self.macaddr.split(':')
            self.generated_ssid='tl' + result[3] + result[4] + result[5]

        print('Expected SSID:\t{}'.format(self.generated_ssid))

    def get_actual_ssid(self):
        for count in range(0, 2):
            result = b''
            while self.serialport.inWaiting() > 0:
                result += self.serialport.read()
            self.serialport.write("netv get ap ssid\n".encode())
            sleep(.5)
            result = self.serialport.readline()
            sleep(.5)
            result = self.serialport.readline().decode('ascii')
            self.saved_ssid = result.strip()
            if self.saved_ssid and self.macaddr[0].isdigit():
                break

        print('Actual SSID:\t{}'.format(self.saved_ssid))

    def main(self):
        try:
            for count in range(0, 2):
                self.get_mac_address()
                self.get_expected_ssid()
                self.get_actual_ssid()
                if (self.saved_ssid == self.generated_ssid):
                    sys.exit(0)
                else:
                    print(':::::::::::: ERROR: SSID did not match expected SSID :::::::::::::')
                    sys.exit(1)
        except Exception as ex:
            print('main SSID function error: - {}'.format(ex))
            sys.exit(1)

driver = MAC_tests()
driver.main()
