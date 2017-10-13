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

        if os.uname().nodename == 'imx6dl-wallsly':
            self.serialdevice = "/dev/ttymxc1"
            self.interface = 'wlan1'
            self.platform = 'wallsly'
        else: #SkyControl
            self.serialdevice = "/dev/ttyO5"
            self.interface = 'wlan0'
            self.platform = 'skycontrol'

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

        print('MACADDR: {}'.format(self.macaddr))

    def get_expected_ssid(self):
        if self.macaddr and self.macaddr[0].isdigit():
            result=self.macaddr.split(':')
            self.generated_ssid='tl' + result[3] + result[4] + result[5]

        print('Expected SSID: {}'.format(self.generated_ssid))

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

        print('Actual   SSID: {}'.format(self.saved_ssid))

    def main(self):
        try:
            for count in range(0, 2):
                self.get_mac_address()
                self.get_expected_ssid()
                self.get_actual_ssid()
                if (self.saved_ssid == self.generated_ssid):
                    sys.exit(0)
                else:
                    if self.platform != 'skycontrol': # Clear network module nvram on WallSly and later
                        try:
                            os.remove('/media/extra/db/netd.db')
                        except OSError:
                            pass
                        try:
                            os.remove('/var/lib/firmware/old_nvram_settings')
                        except OSError:
                            pass
                        with open('/var/lib/firmware/old_nvram_settings', 'w') as new_file:
                            new_file.write('Sending command to Network interface.\n')
                            new_file.write('Command: \'ralink_init show 2860\'\n')
                            new_file.write('OperationMode = 1\n')
                            new_file.write('SSID1 = \n')
                            new_file.write('WPAPSK1 = \n')
                            new_file.write('OperationMode = 3\n')
                        os.sync()
                        self.serialport.write("netv set ap_ssid {}\n".format(self.generated_ssid).encode())
                        print('The SSIDs did not match - Reset network module SSID and reboot - wait 40 seconds')
                        self.reboot_nm()
                    else:
                        sys.exit(1)
            print(':::::::::::: ERROR: SSID in NVRAM does not match MAC SSID :::::::::::::')
            sys.exit(1)
        except Exception as ex:
            print('main SSID function error: - {}'.format(ex))
            sys.exit(1)

driver = MAC_tests()
driver.main()
