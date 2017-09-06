#!/usr/bin/python3

from serial import Serial, SerialException
from subprocess import call
import time
from time import sleep
import random
import os
import sys
import argparse


class NetmHwtest:

    def __init__(self, ap5g, ap24, mode):
        self.ap_5g = ap5g
        self.ap_24 = ap24
        self.macaddr = ""
        self.signal = "0"
        self.channel = "0"
        self.eth0_2_ip_addr = ""
        self.ssid = ""
        self.test_mode = mode

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

    def enable_minimal_wifi(self):
        self.serialport.write("uci -q set vivint.@globals[0].mode='wireless_wan'\n".encode())
        sleep(.5)
        self.serialport.write("netv set wifi_minimal\n".encode())
        sleep(5)

    def enable_wired_mode(self):
        self.serialport.write("netv set wlan\n".encode())
        sleep(.5)
        self.serialport.write("uci -q set vivint.@globals[0].mode='wireless_wan'\n".encode())
        self.uci_commit()

    def apply_net_changes(self):
        self.serialport.write("/etc/init.d/network reload\n".encode())
        sleep(5)

    def reboot_nm(self):
        self.serialport.write("reboot -f\n".encode())

    def delete_default_config(self):
        self.serialport.write("uci delete wireless.radio1\n".encode())
        sleep(.5)
        self.uci_commit()
        self.serialport.write("uci delete wireless.default_radio1\n".encode())
        sleep(.5)
        self.uci_commit()

    def random_string_generator(self, length=6):
        random_string = ''
        random.seed(time.time())
        randlen = int(length - random.random() % (length / 3))
        char_pool = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                     'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                     'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                     'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                     '1', '2', '3', '4', '5', '6', '7', '8', '9', '0']

        pool_len = len(char_pool)
        for i in range(0, randlen):
            j = (random.random() * 100) % pool_len  # random.random() generates floats
            random_string += char_pool[int(j)]

        return random_string

    def define_wifi_5g_radio(self):
        self.serialport.write("uci -q set wireless.radio1=wifi-device\n".encode())
        self.serialport.write("uci -q set wireless.radio1.type=mac80211\n".encode())
        self.serialport.write("uci -q set wireless.radio1.path='pci0000:00/0000:00:00.0/0000:01:00.0'\n".encode())
        self.serialport.write("uci -q set wireless.radio1.hwmode=11ac\n".encode())
        self.serialport.write("uci -q set wireless.radio1.htmode=VHT80\n".encode())
        self.serialport.write("uci -q set wireless.radio1.disabled=0\n".encode())
        self.serialport.write("uci -q set wireless.radio1.country=US\n".encode())
        self.serialport.write("uci -q set wireless.radio1.txpower=23\n".encode())
        self.uci_commit()

    def define_wifi_5g_ap(self):
        # if the ssid is the same, that will cause issues on the mfg flor
        rand_string = self.random_string_generator()
        unique_ssid = 'vivint_ap_' + rand_string

        self.serialport.write("uci -q add wireless wifi-iface\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].device=radio1\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].network=lan\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].mode=ap\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].ssid={}\n".format(unique_ssid).encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].encryption=psk2\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].key='vivint_mfg_test'\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].ifname='wlan2'\n".encode())
        self.serialport.write("uci -q set wireless.@wifi-iface[-1].disabled=0\n".encode())
        self.uci_commit()

    def uci_commit(self):
        self.serialport.write("uci commit\n".encode())
        sleep(.5)

    def get_mac_address(self):
        for count in range(0, 2):
            resp = b''
            while self.serialport.inWaiting() > 0:
                resp += self.serialport.read()
            self.serialport.write("cat /sys/class/net/{}/address\n".format('wlan1').encode())
            sleep(.5)
            resp = self.serialport.readline()
            sleep(.5)
            resp = self.serialport.readline().decode('ascii')
            self.macaddr = resp.strip()
            if self.macaddr and self.macaddr[0].isdigit():
                break
        print('HWADDR:{}'.format(self.macaddr))

    def perform_site_survey(self, freq, ap):
        channel = signal = ''
        for count in range(0, 4):
            # print('kofi - count:{} freq:{} ap:{}'.format(count,freq,ap))
            resp = b''
            while self.serialport.inWaiting() > 0:
                resp += self.serialport.read()
            self.serialport.write("netv iw scan {} | grep \"{}\"\n".format(freq, ap).encode())
            sleep(.1)
            resp = self.serialport.readline()
            sleep(.1)
            resp = self.serialport.readline().decode('ascii')
            # print(resp)
            fields = resp.split('__dl__')
            if len(fields) > 1 and ap in resp:
                channel = fields[1].strip()
                signal = fields[-1].strip()
                ssid = fields[2].strip()
                if channel.isdigit() and int(channel) > 0:
                    break
        print('ssid:{} channel:{} signal:{}'.format(ap, channel, signal))

    def set_antennas(self, diversity, tx, rx):
        self.serialport.write("uci -q set wireless.radio1.diversity={}\n".format(diversity).encode()); sleep(.1)
        self.serialport.write("uci -q set wireless.radio0.diversity={}\n".format(diversity).encode()); sleep(.1)
        self.serialport.write("uci -q set wireless.radio1.txantenna={}\n".format(tx).encode()); sleep(.1)
        self.serialport.write("uci -q set wireless.radio0.txantenna={}\n".format(tx).encode()); sleep(.1)
        self.serialport.write("uci -q set wireless.radio1.rxantenna={}\n".format(rx).encode()); sleep(.1)
        self.serialport.write("uci -q set wireless.radio0.rxantenna={}\n".format(rx).encode()); sleep(.1)
        self.uci_commit()

    def get_ip_addr(self):
        for count in range(0, 3):
            resp = b""
            while self.serialport.inWaiting() > 0:
                resp += self.serialport.read()
            self.serialport.write("ifconfig eth0.2 | grep 'inet addr:' | cut -d: -f2 | awk '{print $1}'\n".encode())
            sleep(.5)
            resp = self.serialport.readline()
            sleep(.5)
            resp = self.serialport.readline().decode('ascii')
            if resp and len(resp):
                parts = resp.split('.')
                if len(parts) == 4:
                    self.eth0_2_ip_addr = resp
                    break
        print("ETH0.2 IP ADDR: {}".format(self.eth0_2_ip_addr))

    def main(self):
        try:
            if self.test_mode == 'setup':
                print('setting up....')
                self.stop_netd()
                self.enable_wired_mode()
                self.delete_default_config()
                self.define_wifi_5g_radio()
                self.define_wifi_5g_ap()
                self.set_antennas(diversity=0, tx=1, rx=1)
                self.reboot_nm()

            if self.test_mode == 'test1':
                print('running test1: diversity(0) tx_ant(1) rx_ant(1)')
                self.get_ip_addr()
                self.get_mac_address()
                self.perform_site_survey(freq='5', ap=self.ap_5g)
                sleep(5) # allow site survey to finish, or we could just call killall -9 netv
                self.perform_site_survey(freq='2.4', ap=self.ap_24)
                self.set_antennas(diversity=0, tx=2, rx=2)
                self.reboot_nm()

                # Test 2
            if self.test_mode == 'test2':
                print('running test2: diversity(0) tx_ant(2) rx_ant(2)')
                # diversity 0, tx=2 rx=2
                self.perform_site_survey(freq='5', ap=self.ap_5g)
                sleep(5)
                self.perform_site_survey(freq='2.4', ap=self.ap_24)
                self.set_antennas(diversity=1, tx=0, rx=0)
                self.reboot_nm()

                # Test 3
            if self.test_mode == 'test3':
                print('running test3: diversity(1) tx_ant(0) rx_ant(0)')
                # diversity 1, tx=0, rx=0
                self.perform_site_survey(freq='5', ap=self.ap_5g)
                sleep(5)
                self.perform_site_survey(freq='2.4', ap=self.ap_24)
 
            if self.test_mode == 'finish':
                print('restarting netd....')
                self.start_netd()

        except Exception as ex:
            print('main: - {}'.format(ex))

parser = argparse.ArgumentParser(description='Test the network module for basic functionality')
parser.add_argument('ap24', help='2.4GHz AP Name')
parser.add_argument('ap5', help='5GHz AP Name')
parser.add_argument('cmd', help='cmd - setup|test1|test2|test3|finish')
args = parser.parse_args()

driver = NetmHwtest(ap5g=args.ap5, ap24=args.ap24, mode=args.cmd)
driver.main()
