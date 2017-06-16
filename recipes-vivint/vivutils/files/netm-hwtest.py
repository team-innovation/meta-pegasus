#!/usr/bin/python3

from serial import Serial, SerialException
from subprocess import call
from time import sleep
import os
import sys
import argparse 


if os.uname().nodename == 'imx6dl-skyhub':
    serialdevice = "/opt/2gig/multiplexerd/helpers/network"
    defaultiface = 'wlan0'
    if not os.path.exists("/opt/2gig/multiplexerd/helpers/network"):
        print("System not ready for testing, wait for multiplexerd to start") 
        sys.exit(1)
else:
    serialdevice = "/dev/ttymxc1"
    defaultiface = 'wlan2'

parser = argparse.ArgumentParser(description='Test the network module for basic functionality')
parser.add_argument('ap', help='accesspoint name')
parser.add_argument('-i', nargs='?', default=defaultiface, dest="iface", 
    help='interface to use, default apcli0')
args = parser.parse_args()

# We need to stop netd or it messes with the network module
devnull = open(os.devnull, 'w')
call(["procman", "stop", "netd"], stdout=devnull, stderr=devnull)

try:
    serialport = Serial(serialdevice, 57600, 
                        timeout = 2)
    serialport.flushOutput()
    serialport.flushInput()

    # Get MAC address
    resp = b''
    while serialport.inWaiting() > 0:
        resp += serialport.read()
    serialport.write("cat /sys/class/net/{}/address\n".format(args.iface).encode())
    sleep(.1)
    resp = serialport.readline()
    sleep(.1)
    resp = serialport.readline().decode('ascii')
    macaddr = resp.strip()

    serialport.write('wifi up\n'.encode())
    sleep(1)
    resp = serialport.readline()
    while serialport.inWaiting() > 0:
        resp += serialport.read()

    # Connect to ap
    channel = "0"
    for count in range(0,10):
        resp = b''
        while serialport.inWaiting() > 0:
            resp += serialport.read()
        serialport.write("netv iw scan | grep \"{}\"\n".format(args.ap).encode())
        sleep(.1)
        resp = serialport.readline()
        sleep(.1)
        resp = serialport.readline().decode('ascii')
        fields = resp.split('__dl__')
        if len(fields) > 1:
            channel = fields[1].strip()
            signal = fields[-1].strip()
            ssid = fields[2].strip()
            if channel.isdigit() and int(channel) > 0:
                break
    else:
        print("Could not find {}".format(args.ap))
        sys.exit(1)

    print("HWADDR:{}".format(macaddr))
    print("Signal:{}".format(signal))
    print("SSID:{}".format(ssid))

finally:
    # Startup netd again so that the system is functional
    call(["procman", "start", "netd"], stdout=devnull, stderr=devnull)

