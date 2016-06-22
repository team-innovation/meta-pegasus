#!/usr/bin/python3

from serial import Serial, SerialException
from subprocess import call
from time import sleep
import os
import sys
import argparse 

parser = argparse.ArgumentParser(description='Test the network module for basic functionality')
parser.add_argument('ap', help='accesspoint name')
parser.add_argument('-i', nargs='?', default='apcli0', dest="iface", 
    help='interface to use, default apcli0')
args = parser.parse_args()

if not os.path.exists("/opt/2gig/multiplexerd/helpers/network"):
    print("System not ready for testing, wait for multiplexerd to start")
    sys.exit(1)

# We need to stop netd or it messes with the network module
devnull = open(os.devnull, 'w')
call(["procman", "stop", "netd"], stdout=devnull, stderr=devnull)

try:
    serialport = Serial("/opt/2gig/multiplexerd/helpers/network", 57600, 
                        timeout = 10)
    serialport.flushOutput()
    serialport.flushInput()

    # Get MAC address
    serialport.write("cat /sys/class/net/{}/address\n".format(args.iface).encode())
    resp = serialport.readline() # Read command back
    resp = serialport.readline() # Read response
    macaddr = resp.decode().strip()

    # Connect to ap
    channel = "0"
    count = 0
    while count < 10:
        serialport.write("iwpriv {} set SiteSurvey=1\n".format(args.iface).encode())
        resp = serialport.readline() # Read command back
        serialport.write("iwpriv {} get_site_survey | grep {}\n".format(args.iface, args.ap).encode())
        resp = serialport.readline() # Read command back
        resp = serialport.readline().decode() # Read response
        channel = resp.split(' ')[0]
        if channel.isdigit() and int(channel) > 0:
            break
    else:
        print("Could not find {}".format(args.ap))
        sys.exit(1)

    serialport.write("iwpriv {} set ApCliEnable=0\n".format(args.iface).encode())
    resp = serialport.readline() # Read command back
    serialport.write("iwpriv {} set ApCliSsid={}\n".format(args.iface, args.ap).encode())
    resp = serialport.readline() # Read command back
    serialport.write("iwpriv {} set Channel={}\n".format(args.iface, channel).encode())
    resp = serialport.readline() # Read command back
    serialport.write("iwpriv {} set ApCliAuthMode=OPEN,ApCliEncrypType=NONE\n".format(args.iface).encode())
    resp = serialport.readline() # Read command back
    serialport.write("iwpriv {} set ApCliEnable=1\n".format(args.iface).encode())
    resp = serialport.readline() # Read command back

    # Wait until connection is established
    resp = ""
    count = 500
    while not args.ap in resp:
        serialport.write("iwgetid {}\n".format(args.iface).encode())
        resp = serialport.readline() # Read command back
        resp = serialport.readline().decode() # Read response
        count -= 1
        if count == 0: 
            print("Failed to connect to {}".format(args.ap))
            sys.exit(1)

    ssid = resp.strip().split(":")[-1][1:-1]

    # Calculate signal strength from RSS
    serialport.write("iwpriv {} stat | grep RSSI*\n".format(args.iface).encode())
    resp = serialport.readline() # Read command back
    resp = serialport.readline().decode()
    # Check what module type this is and format response
    if 'RSSI-A' in resp: # Old module format
        rssia = resp.split("=")[-1].strip()
        resp = serialport.readline().decode()
        rssib = resp.split("=")[-1].strip()
        resp = serialport.readline().decode()
        rssic = resp.split("=")[-1].strip()
        rssi = rssia + ' ' + rssib + ' ' + rssic
    else: # New module format
        rssi = resp.split("=")[-1].strip()

    rssv = rssi.split()
    sv = [int(i) for i in rssv[0:2]]

    signal = max(min(2*(max(sv)+100),100),0)

    print("HWADDR:{}".format(macaddr))
    print("Signal:{}".format(signal))
    print("SSID:{}".format(ssid))

finally:
    # Startup netd again so that the system is functional
    call(["procman", "start", "netd"], stdout=devnull, stderr=devnull)

