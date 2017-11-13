#!/usr/bin/python3

from serial import Serial, SerialException
from subprocess import call
import time
from time import sleep
import os
import sys


class NetmMACtest:

    def __init__(self):
        if os.uname().nodename == 'imx6dl-skyhub':
            self.serialdevice = "/opt/2gig/multiplexerd/helpers/network"
            if not os.path.exists("/opt/2gig/multiplexerd/helpers/network"):
                print("System not ready for testing, wait for multiplexerd to start")
                sys.exit(1)
        else:
            self.serialdevice = "/dev/ttymxc1"

        try:
            self.serialport = Serial(self.serialdevice, 57600, timeout=2)
            self.serialport.flushOutput()
            self.serialport.flushInput()
        except Exception as ex:
            print('Failed to open serial port: {}'.format(ex))
            exit(1)

    def start_netd(self):
        print('starting netd --')
        devnull = open(os.devnull, 'w')
        call(["procman", "start", "netd"], stdout=devnull, stderr=devnull)

    def stop_netd(self):
        print('stopping netd --')
        devnull = open(os.devnull, 'w')
        call(["procman", "stop", "netd"], stdout=devnull, stderr=devnull)

    def display_mac_addr(self):
        for count in range(0, 3):
            resp = b""
            _i = 0
            while self.serialport.inWaiting() > 0:
                resp += self.serialport.read()
            self.serialport.write("netv get macaddrs\n".encode())
            sleep(.5)
            resp = self.serialport.readline()
            print("\nSystem MAC addresses:")
            sleep(.5)
            if resp:
                while resp and (_i < 3):
                    resp = self.serialport.readline().decode('ascii')
                    resp = resp.split('\r\n')
                    print("{}".format(resp[0]))
                    _i = _i+1
                print("")
                break
            break

    def main(self):
        try:
            self.stop_netd()
            # 2.4G, 5G and ethernet MAC addresses
            self.display_mac_addr()
            self.start_netd()

        except Exception as ex:
            print('main: - {}'.format(ex))

driver = NetmMACtest()
driver.main()
