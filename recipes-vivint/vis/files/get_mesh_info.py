#!/usr/bin/env python3
# The plan with this script is to use it to fix up the issues around lost BB AP connections
# Copyright Vivint Innovation 2017
#
# python3 fix_networking.py -f -y -p /dev/ttyO5
##
#

import os
import io
import platform
import subprocess
import time
import serial
import pexpect
import sys
from pexpect import fdpexpect
from pexpect import pxssh
from pexpect import *

try:
    from pprint import pprint
except ImportError:
    print('Must be a 3.7 or earlier panel build')
    pprint = print

def on_touchlink():
    uname = platform.uname()
    is_linux = "linux" in sys.platform
    return is_linux and not "x86" in uname[4] and not "Ubuntu" in uname[3]

if not on_touchlink():
    base = os.getcwd()
    indx = base.find('meta-vivint')
    if indx > -1:
        os.chdir(base[0:indx]+'embedded-apps/code')
        base = os.getcwd()
    sun_prox = os.path.abspath(base + "/sundance/proxies/python")
    sys.path.insert(0, sun_prox)
    sys.path.insert(0, base + "/sundance")
    sys.path.insert(0, base + "/framework")
else:
    sys.path.insert(0, "/opt/2gig/sundance/proxies/python")
#    sys.path.insert(0, "/opt/2gig/sundance/proxies/python/sundance_proxies/singletons")


try:
    from taurine.utils.ssh_network_module import SSHToNetworkModule, ExceptionPasswordRefused
except ImportError as ex:
    print('No taurine {}'.format(ex))
    exit(-1)

from taurine.async_io.event_loop import EventLoop
from taurine.async_io.delayed_call import DelayedCall
from taurine.rpc.rpc_client import RpcClient

from sundance_proxies.devices.camera_device import CameraDevice
from sundance_proxies.devices.slim_line_device import SlimLineDevice
from sundance_proxies.devices.yofi_device import YofiDevice
from sundance_proxies.devices.lgit_poe_wifi_device import LGITPoEWifiDevice


# Bump this number when we change stuff in this script
VERSION = "1.0.0.0"


class MyStringIO(io.StringIO):
    def fileno(self):
        return -1


class ExceptionPxssh(pxssh.ExceptionPxssh):
    """
    # Exception classes used by this module.
    Raised for pxssh exceptions.
    """


class CustomBaseSSH(pxssh.pxssh):
    KNOWN_HOSTS_FILE = "/home/root/.ssh/known_hosts"

    def __init__(self, timeout=30, maxread=2000, searchwindowsize=None, logfile=None, cwd=None, env=None):
        if pexpect.__version__ == '2.5.1':
            super().__init__(timeout, maxread, searchwindowsize, logfile, cwd, env)
        else:
            super().__init__(timeout, maxread, searchwindowsize, logfile, cwd, env, encoding='utf-8')
        self._server = None
        self._server_mac = None
        self._uptime = None
        # Clean up the known hosts so we don't get weird failures
        if os.path.exists(CustomSSH.KNOWN_HOSTS_FILE):
            os.unlink(CustomSSH.KNOWN_HOSTS_FILE)

    def execute_cmd(self, cmd, strip_cmd=True, timeout=None):
        raise Exception("Must override")

    def login_slimline(self, server, username, password='', login_timeout=10):
        # Force to slimline login
        original_prompt = 'root@imx6dl-slimline:~#'
        self._server = server
        ret = self.login(server=server, username=username, password=password, original_prompt=original_prompt,
                         login_timeout=login_timeout)
        if ret:
            self._server_mac = self.execute_cmd("ifconfig wlan0 | grep HWaddr | awk '{print $5}'")
            self._uptime = self.execute_cmd('uptime')

        return ret


if pexpect.__version__ == '2.5.1':
    # Custom pxssh class so it will work on Glance
    class CustomSSH(CustomBaseSSH):
        KNOWN_HOSTS_FILE = "/home/root/.ssh/known_hosts"

        def __init__(self, timeout=30, maxread=2000, searchwindowsize=None, logfile=None, cwd=None, env=None):
            super().__init__(timeout, maxread, searchwindowsize, logfile, cwd, env)

        def sync_original_prompt(self):
            self.sendline()

            if self.before:
                self.expect(r'.+')

            time.sleep(0.1)

            return True
else:
    # Custom pxssh class so it will work on Glance
    class CustomSSH(CustomBaseSSH):
        KNOWN_HOSTS_FILE = "/home/root/.ssh/known_hosts"

        def __init__(self, timeout=30, maxread=2000, searchwindowsize=None, logfile=None, cwd=None, env=None):
            super().__init__(timeout, maxread, searchwindowsize, logfile, cwd, env)

        def sync_original_prompt(self, sync_multiplier=1.0):
            self.sendline()

            if self.before:
                self.expect(r'.+')

            time.sleep(0.1)

            return True


class SSHIntoPanel(SSHToNetworkModule):
    def __init__(self, timeout=30, maxread=2000, searchwindowsize=None, logfile=None, cwd=None, env=None):
        super().__init__(timeout, maxread, searchwindowsize, logfile, cwd, env)
        self._in_db = None
        self._sql_prompt = [TIMEOUT, 'sqlite>']

    def login_network_module(self, server, port=None):
        owrt_prompt = 'root@imx6dl-wallsly:~#'
        self._server = server
        ret = False
        try:
            ret = self.login(server=server, port=port, username='root', password='ap3x!', original_prompt=owrt_prompt,
                             login_timeout=20)
            if ret:
                self._server_mac = self.execute_cmd("ifconfig eth0 | grep HWaddr | awk '{print $5}'").strip()
                self._uptime = self.execute_cmd('uptime').strip()
        except pexpect.pxssh.ExceptionPxssh:
            print('Do we have issue logging in to the panel {}?  Try at commandline! {}'.format(server, self.before))
            if self.can_ping(server):
                subprocess.check_output('ssh-keygen -f "/home/craig/.ssh/known_hosts" -R "{}"'.format(server),
                                        shell=True)
        except pexpect.EOF:
            print('Do we have issue logging in to the panel {}?  Try at commandline! {}'.format(server, self.before))
        return ret

    def login_into_sql(self, db_name):
        ret = False
        if self._uptime is not None:
            self.sendline('/usr/bin/sqlite3 /media/extra/db/{}'.format(db_name))
            ret = self.expect(self._sql_prompt)
            if ret:
                self._in_db = db_name

        return ret

    def close_sql(self):
        if self._in_db:
            self.sendline('commit;')
            self.expect(self._sql_prompt)
            self.sendline('.quit')
            self.expect(self._sql_prompt)
            self._in_db = None

    def close(self):
        self.close_sql()
        super().close()

    def execute_db_cmd(self, cmd):
        result = None

        if self.before:
            self.before = ''
        self.sendline(cmd)
        ret = self.expect(self._sql_prompt)
        if ret == 1:
            result = self.before

            import re
            try:
                r = re.sub(r'^[^\n]*\n', '', result)
            except TypeError:
                r = re.sub(r'^[^\n]*\n', '', result.decode())
            result = r.strip()

        return result


############################################################################
class TelnetCamera:
    PING = 1
    VIVOTEK = 2

    def __init__(self):
        self.telnet_conn = None
        self._server = None
        self.camera_type = None  # 1 is Ping, 2 is Vivotek
        self.pwds = [None, 'admin', 'adcvideo']
        self.prompts = [TIMEOUT, '#', '~ #']
        self.login_prompts = [TIMEOUT, 'CS-6022 login:', 'Network-Camera login:']

    def prompt(self, timeout=-1):
        self.telnet_conn.expect(self.prompts, timeout=timeout)

    def sendline(self, cmd):
        if self.telnet_conn is None:
            return None

        return self.telnet_conn.sendline(cmd)

    def sendcontrol(self, char):

        """This sends a control character to the child such as Ctrl-C or
        Ctrl-D. For example, to send a Ctrl-G (ASCII 7)::

            child.sendcontrol('g')

        See also, sendintr() and sendeof().
        """

        char = char.lower()
        a = ord(char)
        if a >= 97 and a <= 122:
            a = a - ord('a') + 1
            return self.telnet_conn.send(chr(a))
        d = {'@': 0, '`': 0,
             '[': 27, '{': 27,
             '\\': 28, '|': 28,
             ']': 29, '}': 29,
             '^': 30, '~': 30,
             '_': 31,
             '?': 127}
        if char not in d:
            return 0
        return self.telnet_conn.send(chr(d[char]))

    @property
    def before(self):
        if self.telnet_conn is None:
            return None
        buf = self.telnet_conn.before
        self.telnet_conn.before = ''
        return buf

    def close(self):
        if self.telnet_conn is None:
            return None

        self.telnet_conn.close()

    def execute_cmd(self, cmd, strip_cmd=True, timeout=None):
        if self.telnet_conn is None:
            return None

        self.sendline(cmd)
        self.prompt(10 if timeout is None else timeout)
        result = self.before
        if strip_cmd:
            import re
            try:
                r = re.sub(r'^[^\n]*\n', '', result)
            except TypeError:
                r = re.sub(r'^[^\n]*\n', '', result.decode())
            result = r
            # result = result.strip(cmd+'\r\n')
        return result

    def start_cmd(self, cmd):
        return self.sendline(cmd)

    def end_cmd(self, cmd, strip_cmd=True, timeout=None):
        self.prompt(10 if timeout is None else timeout)
        result = self.before
        if strip_cmd:
            import re
            try:
                r = re.sub(r'^[^\n]*\n', '', result)
            except TypeError:
                r = re.sub(r'^[^\n]*\n', '', result.decode())
            result = r

        return result

    def wait(self, timeout=None):
        if self.telnet_conn is None:
            self.prompt(timeout if timeout is not None else -1)

    def login_camera(self, server):
        self._server = server
        if self.telnet_conn is None:
            self.telnet_conn = pexpect.spawn('telnet {}'.format(server))
            self.camera_type = self.telnet_conn.expect(self.login_prompts)
            if self.camera_type > 0:
                self.telnet_conn.sendline('root')
                self.telnet_conn.expect('Password:')
                self.telnet_conn.sendline(self.pwds[self.camera_type])
                self.prompt(10)


############################################################################

# Class to open up a serial port to the network module and keep it open to send commands.
# Used on virtual serial port /dev/serialNet0 can be used on /dev/ttyO5 (NM), make sure netd
# is not running or is STOPPED.
class NMCmdConsoleClass:
    # Default prompt from the NM serial port (openWRT)
    PROMPT = '/ #'
    # Busybox message from console NM
    BUSYBOX = 'BusyBox v1.23.2 (2017-04-26 14:11:54 MDT) built-in shell (ash)'
    # Message from virtual serial when a thread is processing and using the serial port.
    WAIT_SERIAL_NET = 'Wait for thread to complete, please wait...'

    def __init__(self, dev_name='/dev/serialNet0', rate=None, wait_time=30, command_prompt='/ #'):
        NMCmdConsoleClass.PROMPT = command_prompt

        self.dev_name = dev_name
        self.rate = rate
        self.wait_time = wait_time
        self.global_debug = 0

        self._open_serial_port()

    def enable_debug(self):
        self.global_debug = 1

    def disable_debug(self):
        self.global_debug = 0

    def _open_serial_port(self):
        if self.rate is not None:
            self._serial_port = serial.Serial(self.dev_name, self.rate)
        else:
            self._serial_port = serial.Serial(self.dev_name)

        self._serial_port.open()
        self._serial_port.flushInput()
        self._serial_port.flushOutput()

        self.write_control_c()

        self._wrt_cmd = fdpexpect.fdspawn(self._serial_port.fileno(), encoding='utf-8', timeout=self.wait_time)
        # self._wrt_cmd.linesep = '\n'
        ret = self._wrt_cmd.expect(
            [TIMEOUT, NMCmdConsoleClass.PROMPT, NMCmdConsoleClass.BUSYBOX, NMCmdConsoleClass.WAIT_SERIAL_NET])
        if ret == 3:
            self._wrt_cmd.send(os.linesep)
            ret = self._wrt_cmd.expect([TIMEOUT, NMCmdConsoleClass.PROMPT])

    def close(self):
        self.write_control_c()
        self._wrt_cmd.close()
        self._wrt_cmd = None

    def flush_cmd(self):
        # self._wrt_cmd.flush()
        if self._wrt_cmd and self._wrt_cmd.before:
            self._wrt_cmd.expect(r'.+')

    def wait_for_prompt(self):
        self._wrt_cmd.sendline('\r\n')
        ret = self._wrt_cmd.expect([TIMEOUT, NMCmdConsoleClass.PROMPT], timeout=60 * 4)
        if ret == 1:
            # print('BEFORE: {}'.format(self._wrt_cmd.before))
            self.flush_cmd()
            return True
        return False

    def data_from_wrt_cmd(self, cmd):
        try:
            result = self._wrt_cmd.before.strip(cmd).strip()
            return result
        except TypeError:
            if self.global_debug:
                print('FAILED TO GET BUFFER! buffer({}), cmd({})'.format(self._wrt_cmd.before, cmd))
            result = self._wrt_cmd.before.decode().strip(cmd).strip()
            return result

    def execute_command(self, cmd, wait_time=30, debug=0):
        result = None
        if self.global_debug:
            debug = self.global_debug

        if self._wrt_cmd is not None:
            if debug:
                print('DEBUG: cmd={}'.format(cmd))
            try:
                self._wrt_cmd.sendline(cmd)
                if self.dev_name.startswith('/dev/serialNet0'):
                    time.sleep(0.3)
                ret = self._wrt_cmd.expect([TIMEOUT, NMCmdConsoleClass.PROMPT], timeout=wait_time)
                if ret == 1:
                    if debug:
                        print('DEBUG: "PROMPT found" before={}'.format(self._wrt_cmd.before).encode())
                    result = self.data_from_wrt_cmd(cmd)
                    if debug:
                        print('DEBUG: stripped result={}'.format(result))
                    self.flush_cmd()

                elif ret == 0:
                    if debug:
                        print('DEBUG: TIMEOUT found before={}'.format(self._wrt_cmd.before))

                    self.flush_cmd()

                    result = self.data_from_wrt_cmd(cmd)
                    print('TIMEOUT waiting for result from cmd {}, result={}'.format(cmd, result))
                    print('In buffer: {}'.format(self._wrt_cmd.before))
                    os.write(self._wrt_cmd.child_fd, b"\x03")  # self.write_control_c()
                    os.write(self._wrt_cmd.child_fd, b"\x0d")  # self.write_CR()
                    ret = self._wrt_cmd.expect([TIMEOUT, NMCmdConsoleClass.PROMPT])
                    if ret == 0:
                        print('TIMEOUT: in execute_command on cmd = {}'.format(cmd))
                    raise TimeoutError("Command {} timed out".format(cmd))

            except UnicodeDecodeError:
                if debug:
                    print('DEBUG: Unicode exception')

                print('ERROR: Power Cycle NM, in bad state!')
                # In a bad state, just power cycle the module
                #### TODO - PanelInfoClass.power_cycle_network_module()
                raise TimeoutError("Command {} timed out".format(cmd))

        return result

    def write_CR(self):
        print("CR Network Module")
        try:
            self._serial_port.write(b"\x0d")  # \r
            self._serial_port.flush()
        except Exception as ex:
            print("Serial failed write CR - re-opening...".format(ex))
            self._open_serial_port()

    def write_LF(self):
        print("LF Network Module")
        try:
            self._serial_port.write(b"\x0a")  # \n
            self._serial_port.flush()
        except Exception as ex:
            print("Serial failed write LF - re-opening...".format(ex))
            self._open_serial_port()

    def write_control_c(self):
        """Write a control-c to attempt to put the serial port in a good state"""
        print("Control-C Network Module")
        try:
            self._serial_port.write(b"\x03")
            self._serial_port.flush()
        except Exception as ex:
            print("Serial failed cntl c - re-opening...".format(ex))
            self._open_serial_port()

    def write_control_d(self):
        """Write a control-d to attempt to get out of a bad mode"""
        print("Control-D Network Module")
        try:
            self._serial_port.write(b"\x04")
            self._serial_port.flush()
        except Exception as ex:
            print("Serial failed cntl d - re-opening...".format(ex))
            self._open_serial_port()


###########################################################
'''
MPP  wlan0 Panel: 88:6A:E3:D7:49:37(38) 172.16.10.254 * (Kofi)
MAP1 wlan0 Panel: 88:6A:E3:E9:05:49(48) 172.16.10.141, panel 172.16.10.100 (Craig)
MAP2 wlan0 Panel: 88:6A:E3:E8:F3:77(76) 172.16.10.145 (Black antenna, QA area near Arvin)
MAP3 wlan0 Panel: 88:6A:E3:E9:00:BD(BC) 172.16.10.162 (HW Area Jim cube front)
MAP4 wlan0 Panel: 88:6A:E3:D6:99:15(16) 172.16.10.186 (Above Jeffie on HW side)
MAP5 wlan0 Panel: 88:6A:E3:E8:FC:E1(E0) 172.16.10.142 (Was in Conf room (Farnsworth), close to Natallia Office?)
MAP6 wlan0 Panel: 88:6A:E3:E8:FD:25(24) 172.16.10.152 (Next to Kofi and I)
MAP7 wlan0  Node: 00:0c:43:76:20:70 172.16.10.131 (Hall Node)
MAP8 wlan0 Panel: 88:6a:e3:e8:f1:2f 172.16.10.114 (Marked Bad MAC, SE Window by stairs)
'''


class NetworkModuleInfo:
    @staticmethod
    def find_nodes(use_ssdp=False, password_list=None):
        node_list = []
        if use_ssdp:
            try:
                import sys
                from taurine.async_io.protocols.ssdp import SsdpServer
                from taurine.async_io.event_loop import EventLoop
                from taurine.async_io.delayed_call import DelayedCall

                yofi_nodes = []
                ssdp = None

                search_target = "urn:schemas-upnp-org:device:VivintMesh-YOFI0001:1"

                def ssdp_callback(headers, address, multicast):
                    val = headers['ST']
                    if val == search_target:
                        if not address in yofi_nodes:
                            print("%83s" % headers.get("USN"), "%20s" % address,
                                  "%20s" % "multicast" if multicast else "unicast")
                            yofi_nodes.append(address)

                def exit_now():
                    sys.exit(0)

                def on_exit_callback():
                    for i in yofi_nodes:
                        node_ip = i.split('.')[-1]
                        if node_ip not in node_list:
                            node_list.append(node_ip)

                loop = EventLoop(on_exit_callback)
                ssdp = SsdpServer(ssdp_callback)
                if not on_touchlink():
                    ssdp.SSDP_UUID_PATH = '/tmp/ssdp-uuid'

                ssdp.search(search_target)
                DelayedCall(2, ssdp.search, search_target)
                DelayedCall(2, ssdp.search, search_target)
                DelayedCall(2, ssdp.search, search_target)
                DelayedCall(10, exit_now)
                loop.run()

            except ImportError:
                # node_list = [254, 141, 144, 162, 186, 142, 152]
                node_list = [254, 135, 196, 103, 148, 104]
        else:
            addr = '172.16.10.254'
            s = SSHToNetworkModule()
            if not s.can_ping(addr):
                # Try panel NM
                addr = '172.16.10.253'
            s.get_password_from_db = False
            print('Attempt login to address: {}'.format(addr))
            try:
                if addr not in password_list:
                    raise ExceptionPasswordRefused('Password not found use default')
                ret = s.login_network_module(addr, password=password_list[addr])
            except ExceptionPasswordRefused:
                s.close()
                s = SSHToNetworkModule()
                ret = s.login_network_module(addr)
            if ret is False:
                s.close()
                s = SSHToNetworkModule()
                s.get_password_from_db = False
                try:
                    if addr not in password_list:
                        raise ExceptionPasswordRefused('Password not found use default')
                    ret = s.login_network_module(addr, 2020, password=password_list[addr])
                except ExceptionPasswordRefused:
                    s.close()
                    s = SSHToNetworkModule()
                    ret = s.login_network_module(addr, 2020)
            if ret:
                import re
                regex = r'^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$'

                uptime = s.execute_cmd('uptime')
                print(uptime)
                mode = s.execute_cmd('uci -q get vivint.@globals[0].mode')
                if mode == 'mesh':
                    node_health = s.execute_cmd('netv mhealth')
                    print(node_health)
                    lines = node_health.split('\r\n')
                    node_health = s.execute_cmd('netv get mchan24')
                    print(node_health)
                else:
                    try:
                        mac = s.execute_cmd("netv get macaddrs | grep '5.0G' | awk '{print $1}'")
                        if not mac:
                            mac = s.execute_cmd("netv get macaddrs | grep Ethernet | awk '{print $1}'")
                    except :
                        print('Failed to find a mac on a non mesh setup')
                        mac = '00:00:00:00:00:00'
                    node_health = "node    {}       172.16.10.254".format(mac)
                lines += node_health.split('\r\n')
                for line in lines:
                    if line.startswith('node'):
                        a = line.split()
                        mac = a[1]
                        if re.match(regex, mac) and len(a) <= 2:
                            # found an IP not a mac, there is a bug in yofid that it will not report
                            # a mac, just [node IP] not [node MAC IP]
                            ip = mac
                        elif len(a) == 3:
                            ip = a[2]
                        else:
                            continue

                        # If we get here we think we found a IP
                        ip_last = ip.split('.')[-1]
                        if not ip_last in node_list:
                            node_list.append(ip_last)
            s.close()

        return node_list

    def __init__(self, list_of_nm=None, password_list=None):
        import logging

        self.logger = logging.getLogger('{}'.format(self.__class__))
        self.dhcpdump = None
        self.password_list = password_list
        # logging.basicConfig(level=logging.DEBUG)
        logging.basicConfig(format='%(asctime)s %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p', level=logging.DEBUG)
        if list_of_nm is None:
            print('Search for nodes')
            self.list_of_nm = NetworkModuleInfo.find_nodes(use_ssdp=False, password_list=password_list)
        else:
            self.list_of_nm = list_of_nm

    def _parse_dhcp_dump(self, result):
        # Copied from above, should make a static function
        rows = []
        if result is not None and len(result) > 0:
            lines = result.split('\n')
            self.logger.debug('LINES(netv dhcpdump): {}\n'.format(lines))
            for line in lines:
                data = {}
                try:
                    items = line.split('|')  # 0 = name, 1 = mac, 2 = ip, 3 = lease
                    data['name'] = items[0].strip().strip()
                    data['mac'] = items[1].strip().strip()
                    data['ip'] = items[2].strip().strip()
                    data['lease'] = items[3].strip().strip()
                    rows.append(data)
                except IndexError:
                    pass
        return rows

    def _parse_station_info(self, result, dhcpdump=None, arp=None):
        stations = []
        if result is not None and len(result) > 0:
            lines = result.split('\n')
            self.logger.debug('LINES(iw IFACE station dump): {}\n'.format(lines))
            station_info = {}
            for line in lines:
                if line.startswith('Station'):
                    if station_info:
                        # Done with last station, save start next
                        stations.append(station_info)
                        station_info = {}
                    try:
                        # Split the line with the Station and MAC
                        sta_mac_info = line.split()
                        # Store the MAC from the second item in the list
                        station_info['mac'] = sta_mac_info[1]
                        station_info['iface'] = sta_mac_info[3].strip().strip()[:-1]
                        # If we provide the dhcpdump table we try to look up the IP and save it
                        if dhcpdump:
                            for d_entry in dhcpdump:
                                if d_entry['mac'] == station_info['mac']:
                                    station_info['ip'] = d_entry['ip']
                                    station_info['hostname'] = d_entry['name']
                                    break

                        if 'ip' not in station_info:
                            if arp:
                                for a_entry in arp:
                                    if a_entry['mac'] == station_info['mac']:
                                        station_info['ip'] = a_entry['ip']
                                        if a_entry['hostname'] != '?':
                                            station_info['hostname'] = a_entry['hostname']
                                        else:
                                            station_info['hostname'] = a_entry['mac']
                                        break

                        # TODO - Use arp if we do not find it in the dhcpdump
                    except IndexError:
                        pass
                elif station_info and line:
                    # Past Station line
                    row = line.strip()
                    key, val = row.split(':')
                    station_info[key] = val.strip()
            if station_info:
                stations.append(station_info)
                # pprint(stations)
        return stations

    def _parse_iwinfo(self, result):
        ifaces = []
        if result is not None and len(result) > 0:
            lines = result.split('\n')
            self.logger.debug('LINES(iwinfo): {}\n'.format(lines))
            wlan_info = {}
            for line in lines:
                try:
                    if line.startswith('wlan'):
                        if wlan_info:
                            # Done with last station, save start next
                            ifaces.append(wlan_info)
                            wlan_info = {}
                        try:
                            # Split the line with the Station and MAC
                            iface_info = line.split()
                            # Store the MAC from the second item in the list
                            wlan_info['essid'] = iface_info[2]
                            wlan_info['iface'] = iface_info[0].split()[0]
                        except IndexError:
                            pass
                    elif wlan_info and line:
                        # Past Station line
                        row = line.strip()
                        items = row.split(': ')
                        if len(items) > 2:
                            # We have more than two items on a line
                            if items[0].startswith('Mode') and items[1].endswith('Channel'):
                                key = items[0]
                                val = items[1].split()[0] + ' ' + items[1].split()[1]
                                wlan_info[key] = val.strip()
                                # if items[1].endswith('Channel')
                                key = 'Channel'
                                val = items[2].split()[0]
                                # wlan_info[key] = val.strip()
                            elif items[0].startswith('Tx-Power') and items[1].endswith('Link Quality'):
                                key = items[0]
                                val = items[1].split()[0]
                                wlan_info[key] = val.strip()
                                # if items[1].endswith('Link Quality')
                                key = 'Link Quality'
                                val = items[2].split()[0]
                                # wlan_info[key] = val.strip()
                            elif items[0].startswith('Signal') and items[1].endswith('Noise'):
                                key = items[0]
                                val = items[1].split()[0]
                                wlan_info[key] = val.strip()
                                # if items[1].endswith('Noise')
                                key = 'Noise'
                                val = items[2].split()[0]
                                # wlan_info[key] = val.strip()
                            elif items[0].startswith('Type') and items[1].endswith('HW Mode(s)'):
                                key = items[0]
                                val = items[1].split()[0]
                                wlan_info[key] = val.strip()
                                # if items[1].endswith('HW Mode(s)')
                                key = 'HW Mode(s)'  # items[1][items[1].find('HW Mode(s)'):]
                                val = items[2].split()[0]
                                # wlan_info[key] = val.strip()
                            else:
                                print('Do not know how to parse this: {}'.format(items))
                        elif len(items) == 2:
                            # Just a tuple of data
                            key, val = items
                        else:
                            print('Nothing to parse!')

                        wlan_info[key] = val.strip()
                except Exception as ex:
                    print('EXCEPTION: _parse_iwinfo: {}'.format(ex))

            # Append the lst bit of info
            if wlan_info:
                ifaces.append(wlan_info)
        return ifaces

    def _parse_iwconfig(self, result):
        rows = []
        if result is not None and len(result) > 0:
            lines = result.split('\n')
            self.logger.debug('LINES(iwconfig wlan0): {}\n'.format(lines))
            import re
            data = {}
            for line in lines:
                data = {}
                ret = re.split('[ \t]+', line)
                if ret[0] == 'wlan0':
                    key = ret[0]
                    val = ret[1:3]
                    data[key] = val
                    key, val = ret[3].split(':')
                    data[key] = val
                    key, val = ret[4].split(':')
                    data[key] = val
                elif ret[0].startswith('Mode:'):
                    # Mode
                    key, val = ret[0].split(':')
                    data[key] = val
                    # Freq
                    key, val = ret[1].split(':')
                    data[key] = val + ' ' + ret[2]
                    # AP MAC
                    key = ret[3] + ' ' + ret[4:-1]
                    val = ret[5]
                    data[key] = val
                elif ret[0] == 'Bit' and ret[1].startswith('Rate:'):
                    k, val = ret[1].split(':')
                    key = ret[0] + ' ' + k
                    data[key] = val + ' ' + ret[2]
                    key, val = ret[3].split(':')
                    data[key] = val
                elif ret[0] == 'Link':
                    # Link Quality
                    k, val = ret[1].split('=')
                    key = ret[0] + ' ' + k
                    data[key] = val
                    # Signal level
                    k, val = ret[3].split('=')
                    key = ret[2] + ' ' + k
                    data[key] = val + ' ' + ret[4]
                    # Noise level
                    k, val = ret[6].split('=')
                    key = ret[5] + ' ' + k
                    data[key] = val + ' ' + ret[7]
            rows.append(data)
        return rows

    def _parse_mesh_path(self, result):
        # Copied from above, should make a static function
        rows = []
        if result is not None and len(result) > 0:
            lines = result.split('\n')
            self.logger.debug('LINES(iw wlan0 mpath dump): {}\n'.format(lines))
            for line in lines:
                data = {}
                if not line.startswith('DEST ADDR'):
                    try:
                        line = line.replace('\t', ' ').strip()
                        items = line.split(' ')  # 0 = dest, 1 = next_hop, 2 = iface,
                        # 3 = seq_num, 4 = link_metric, 5 = q_len
                        # 6 = exp_time, 7 = disc_timeout, 8 = disc_retry
                        # 9 = flags
                        data['dest_addr'] = items[0].strip().strip()
                        data['next_hop'] = items[1].strip().strip()
                        data['iface'] = items[2].strip().strip()
                        data['seq_num'] = items[3].strip().strip()
                        data['link_metric'] = items[4].strip().strip()
                        data['q_len'] = items[5].strip().strip()
                        data['exp_time'] = items[6].strip().strip()
                        data['disc_timeout'] = items[7].strip().strip()
                        data['disc_retry'] = items[8].strip().strip()
                        data['flags'] = items[9].strip().strip()
                        rows.append(data)
                    except IndexError:
                        pass
        return rows

    def _parse_arp_info(self, result):
        rows = []
        if result is not None and len(result) > 0:
            lines = result.split('\n')
            self.logger.debug('LINES(arp): {}\n'.format(lines))
            for line in lines:
                data = {}
                try:
                    items = line.split(' ')  # 0 = hostname, 1 = ip, 2 = at, 3 = mac, 4 = [ether], 5 = on, 6 = br-lan
                    ip = items[1].strip().strip().strip('(').strip(')')
                    mac = items[3].strip().strip()
                    data['hostname'] = items[0].strip().strip()
                    data['ip'] = ip
                    data['mac'] = mac
                    rows.append(data)
                except IndexError:
                    pass
        return rows

    def get_arp_info(self, s):
        self.logger.info('get_arp_info: {}'.format(s._server))
        result = s.execute_cmd('arp -n | grep br-lan')
        wlan_mac = s._server_mac
        self.logger.debug('ARP {} - {}'.format(s._server, wlan_mac))
        rows = self._parse_arp_info(result)
        self.logger.debug(rows)
        return rows

    def get_ifconfig(self, s):
        import re
        rows = []

        wlan_mac = s._server_mac
        self.logger.debug('IFCONFIG on NM {} - {}'.format(s._server, wlan_mac))
        for iface in ['br-lan', 'wlan0', 'wlan1', 'wlan1-1']:
            data = {}
            try:
                data['iface'] = iface
                result = s.execute_cmd("ifconfig {} | grep 'inet addr:172.16.10'".format(iface))
                items = result.strip().split(':')
                if items and items[0] != '':
                    data['ip'] = items[1].split(' ')[0]
                result = s.execute_cmd("ifconfig {} | grep 'HWaddr'".format(iface))
                items = re.split('[ \t]+', result.strip())
                if items:
                    data['mac'] = items[4]
            except Exception as ex:
                print('Failed in get_ifconfig: {}'.format(ex))
            if data:
                rows.append(data)
        self.logger.debug(rows)
        return rows

    def get_iw_info(self, s):
        '''root@OpenWrt:~# iw wlan1 info
Interface wlan1
	ifindex 34
	wdev 0x100000008
	addr 88:6a:e3:e9:05:48
	ssid tle90548
	type AP
	wiphy 1
	channel 9 (2452 MHz), width: 20 MHz, center1: 2452 MHz
	txpower 20.00 dBm
'''

        def _parse_iw_info(result):
            rows = {}
            if result is not None and len(result) > 0:
                lines = result.split('\n')
                self.logger.debug('LINES(iw wlan1 info): {}\n'.format(lines))
                for line in lines:
                    data = line.strip().split(' ')
                    if len(data) == 2:
                        key, val = data
                        rows[key.strip()] = val.strip()
                    elif data[0] == 'channel':
                        key = data[0]
                        val = data[1] + ' ' + ' '.join(data[2:]).split(',')[0]
                        rows[key.strip()] = val.strip()
                        lines2 = line.strip().split(',')
                        for line2 in lines2:
                            if ':' in line2:
                                key, val = line2.split(':')
                                rows[key.strip()] = val.strip()
                    elif data[0] == 'txpower':
                        key = data[0]
                        val = data[1] + ' ' + ' '.join(data[2:])
                        rows[key] = val
                    elif len(line) > 0:
                        print('Failed to parse: {}'.format(line))
                        print('FAILED cmd: {}'.format(s._cmd))
            return rows

        result = s.execute_cmd('iw wlan1 info')
        rows = _parse_iw_info(result)
        return rows

    def get_ap_station_info(self, s, dhcpdump=None, arp=None):
        result = s.execute_cmd('iw wlan1 station dump', strip_cmd=False)
        wlan_mac = s._server_mac
        self.logger.debug('STA ON AP {} - {}'.format(s._server, wlan_mac))
        rows = self._parse_station_info(result, dhcpdump, arp)
        result = s.execute_cmd('ifconfig wlan1-1', strip_cmd=False)
        if 'Device not found' not in result:
            result = s.execute_cmd('iw wlan1-1 station dump', strip_cmd=False)
            rows0 = self._parse_station_info(result, dhcpdump, arp)
            for r in rows0:
                rows.append(r)

        self.logger.debug(rows)
        if len(rows) > 0:
            rows2 = self.get_iw_info(s)
            self.logger.debug(rows2)
            try:
                # Update the found STA with the info from the AP theyt are connected too
                for r in rows:
                    r['channel'] = rows2['channel']
                    r['channel_center'] = rows2['center1']
                    r['channel_width'] = rows2['width']
                    r['ap_txpower'] = rows2['txpower']
                    r['ap_addr'] = rows2['addr']
            except KeyError:
                pass

        return rows

    def get_iwinfo(self, s):
        result = s.execute_cmd('iwinfo', strip_cmd=False)
        wlan_mac = s._server_mac
        self.logger.debug('IW on NM {} - {}'.format(s._server, wlan_mac))
        rows = self._parse_iwinfo(result)
        self.logger.debug(rows)
        return rows

    def get_mesh_node_neighbors(self, s):
        result = s.execute_cmd('iw wlan0 station dump', strip_cmd=False)
        wlan_mac = s._server_mac
        self.logger.debug('MESH NODES ON {} - {}'.format(s._server, wlan_mac))
        rows = self._parse_station_info(result)
        self.logger.debug(rows)
        return rows

    def get_mesh_path_info(self, s):
        result = s.execute_cmd('iw wlan0 mpath dump')
        wlan_mac = s._server_mac
        self.logger.debug('MESH PATH {} - {}'.format(s._server, wlan_mac))
        rows = self._parse_mesh_path(result)
        self.logger.debug(rows)
        return rows

    def get_camera_info(self, ip):
        rows = []
        tel = TelnetCamera()
        tel.login_camera(ip)
        result = tel.execute_cmd('iwconfig wlan0')
        if tel.camera_type == TelnetCamera.VIVOTEK:
            rows = self._parse_iwconfig(result)
        return rows

    def test_camera_info(self):
        tel = TelnetCamera()
        tel.login_camera('172.16.10.169')
        ret = tel.execute_cmd('ifconfig wlan0')
        print(ret)
        ret = tel.execute_cmd('iwconfig wlan0')
        print(ret)
        ret = tel.execute_cmd('iwlist wlan0 scan')
        print(ret)
        ret = tel.execute_cmd('iwlist wlan0 scanning | grep -A 6 Cell')
        print(ret)

    def single_iperf_test_nm_to_nm(self, s, s2, to_ip):
        #        cmd2 = 'iperf -i 2 -u -s'
        cmd2 = 'iperf3 -s'
        s2.start_cmd(cmd2)
        cmd = 'iperf3 -u -b 50M -t 10 -c {}'.format(to_ip)
        # result = s.execute_cmd(cmd)
        s.linesep = s.crlf
        s2.linesep = s2.crlf
        s.sendline(cmd)
        ret = s.expect(["Connecting to host", TIMEOUT])
        ret = s.expect(["iperf Done.", TIMEOUT])
        result = s.before.split("\r\n")

        self.logger.debug('CLIENT: {}'.format(s._server))
        self.logger.debug(result[2])
        self.logger.debug(result[-4])
        s.sendcontrol('c')
        ret = s2.expect(["-----------------------------------------------------------", TIMEOUT])
        s2.prompt(10)
        # outs = s2.end_cmd(cmd2)
        outs = s2.before.split("\r\n")
        self.logger.debug('SERVER: {}'.format(s2._server))
        self.logger.debug(outs[-6])
        self.logger.debug(outs[-5])
        s2.sendcontrol('c')

        # tmp = []
        # tmp.append(result[2])
        # tmp.append(result[-4])
        # tmp.append(outs[-6])
        # tmp.append(outs[-5])
        # ret = '\r\n'.join(tmp)
        ret = result[2] + "\n" + result[-4] + "\n" + outs[-6] + "\n" + outs[-5] + "\n"

        return ret

    def single_iperf_test_nm_to_laptop(self, s, local_ip):
        import shlex, tempfile

        log_file = tempfile.TemporaryFile()
        cmds = shlex.split('iperf -i 2 -u -s')
        process = subprocess.Popen(cmds, stdout=log_file)
        result = s.execute_cmd('iperf -u -e -b 100M -t 10 -c {}'.format(local_ip))
        self.logger.info(result)
        process.kill()
        log_file.seek(0)
        outs = log_file.read().decode()
        log_file.close()
        self.logger.info(outs)

    def single_iperf_test_laptop_to_nm(self, s, addr):
        # Iperf laptop to servers (nm)
        result = s.execute_cmd('killall iperf ; iperf -s -u')
        self.logger.debug(result)
        result = subprocess.check_output('iperf -u -e -i 1 -b 100M -c {}'.format(addr), shell=True)
        self.logger.debug(result.decode())
        self.logger.debug(result)

    def netv_dhcpdump(self, s):
        msg = 'DHCPDUMP {} - {}'.format(s._server, s._server_mac)
        line = "-" * len(msg)
        #self.logger.debug('\n{}\n{}'.format(msg, line))
        result = s.execute_cmd('netv dhcpdump')
        rows = self._parse_dhcp_dump(result)
        #self.logger.debug(rows)
        return rows

    def get_platform(self, s):
        result = s.execute_cmd('uci -q get vivint.@globals[0].platform').strip()
        return result

    def get_version(self, s):
        result = s.execute_cmd('cat /etc/os-release  | grep LEDE_RELEASE').strip()
        key, val = result.split('=')
        result = val.strip('"')
        return result

    def get_uuid(self, s):
        result = s.execute_cmd('netv get uuid').strip()
        return result

    def get_wan_address(self, s):
        address = ''
        try:
            address = s.execute_cmd("ifconfig eth0.2 | grep 'inet addr' | awk '{print $2}' | cut -d':' -f2").strip()
            self.logger.info('get_wan_address: ip={}, result={}'.format(s._server, address))
        except :
            self.logger.exception('Failed to get wan_address')
        return address

    def get_wan_address_mac(self, s):
        address = ''
        try:
            address = s.execute_cmd("ifconfig -a eth0.2 | grep 'HWaddr' | awk '{print $5}'").strip()
            self.logger.info('get_wan_address: mac={}, result={}'.format(s._server, address))
        except :
            self.logger.exception('Failed to get wan_address')
        return address

    def get_node_info_from_sundance_database(self):
        result = {}
        # addr2 = '172.16.10.100'
        # self.logger.info('Attempting to log into {}'.format(addr2))
        # try:
        #     s1 = SSHIntoPanel(timeout=5)
        #     ret = s1.login_network_module(addr2)
        #     if ret:
        #         self.logger.info('Logged into panel {}'.format(addr2))
        #         ret = s1.login_into_sql('sundance.db')
        #         if ret == 1:  # Got prompt
        #             ret = s1.execute_db_cmd("select id from service where class='YofiDevice';")
        #             # above returns the list of service id's
        #             for id in ret.split():
        #                 name = s1.execute_db_cmd("select value from property where service_id={}} and name='name' ;".format(id))
        #                 uuid = s1.execute_db_cmd("select value from property where service_id={}} and name='uuid' ;".format(id))
        #                 result[uuid] = name
        #         s1.close()
        # except Exception :
        #     self.logger.exception('Failed on access of panel db')

        p = PanelSystemInfo()
        p.run()
        yofi_devices = p.get_yofi_info()
        for node in yofi_devices:
            # print()
            # print(node['class'])
            # print(node['properties']['name'])
            # print(node['id'])
            # print(node['properties']['address'])
            # print(node['properties']['mac_address'])
            #
            uuid = node['properties']['uuid']
            result[uuid] = node['properties']['name']

        return result

    def mesh_node_info_map(self):
        mesh_map = {}

        list_of_node_names = self.get_node_info_from_sundance_database()
        if self.dhcpdump is None:
            # Try to get the dhcpdump info first from the MPP
            addr2 = '172.16.10.254'
            s1 = SSHToNetworkModule()
            if not s1.can_ping(addr2):
                # Try panel NM
                addr2 = '172.16.10.253'

            try:
                if addr2 not in self.password_list:
                    raise ExceptionPasswordRefused('Password not found use default')
                ret = s1.login_network_module(addr2, password=self.password_list[addr2])
            except ExceptionPasswordRefused:
                s1.close()
                s1 = SSHToNetworkModule()
                ret = s1.login_network_module(addr2)
            if ret is False:
                s1.close()
                s1 = SSHToNetworkModule()
                try:
                    if addr2 not in self.password_list:
                        raise ExceptionPasswordRefused('Password not found use default')
                    ret = s1.login_network_module(addr2, 2020, password=self.password_list[addr2])
                except ExceptionPasswordRefused:
                    s1.close()
                    s1 = SSHToNetworkModule()
                    ret = s1.login_network_module(addr2, 2020)
            if ret:
                self.logger.info('Logged into {}'.format(addr2))
                self.dhcpdump = self.netv_dhcpdump(s1)

        for j in self.list_of_nm:
            addr2 = '172.16.10.{}'.format(j)
            try:
                s1 = SSHToNetworkModule()
                try:
                    if addr2 not in self.password_list:
                        raise ExceptionPasswordRefused('Password not found use default')
                    ret = s1.login_network_module(addr2, password=self.password_list[addr2])
                except ExceptionPasswordRefused:
                    s1.close()
                    s1 = SSHToNetworkModule()
                    ret = s1.login_network_module(addr2)
                if ret is False:
                    s1.close()
                    time.sleep(1)
                    s1 = SSHToNetworkModule()
                    try:
                        if addr2 not in self.password_list:
                            raise ExceptionPasswordRefused('Password not found use default')
                        ret = s1.login_network_module(addr2, 2020, password=self.password_list[addr2])
                    except ExceptionPasswordRefused:
                        s1.close()
                        s1 = SSHToNetworkModule()
                        ret = s1.login_network_module(addr2, 2020)

                if ret:
                    self.logger.info('Logged into {}'.format(addr2))
                    wlan_mac = s1._server_mac
                    mesh_map[wlan_mac] = {}
                    mesh_map[wlan_mac]['platform'] = self.get_platform(s1)
                    try:
                        mesh_map[wlan_mac]['version'] = self.get_version(s1)
                    except :
                        mesh_map[wlan_mac]['version'] = ''

                    uuid = self.get_uuid(s1)
                    if uuid:
                        mesh_map[wlan_mac]['uuid'] = uuid
                        try:
                            mesh_map[wlan_mac]['name'] = list_of_node_names[uuid]
                        except :
                            self.logger.exception('Failed with UUID and NAME setup')
                            mesh_map[wlan_mac]['name'] = 'Unknown Name'
                    else:
                        mesh_map[wlan_mac]['uuid'] = ''
                        mesh_map[wlan_mac]['name'] = ''

                    mesh_map[wlan_mac]['uptime'] = s1._uptime
                    if int(j) == 254:
                        if self.dhcpdump is None:
                            self.dhcpdump = self.netv_dhcpdump(s1)
                        mesh_map[wlan_mac]['wan_address'] = self.get_wan_address(s1)
                        mesh_map[wlan_mac]['wan_address_mac'] = self.get_wan_address_mac(s1)
                    else:
                        mesh_map[wlan_mac]['wan_address'] = ''
                        mesh_map[wlan_mac]['wan_address_mac'] = ''
                    if self.dhcpdump:
                        mesh_map[wlan_mac]['dhcpdump'] = self.dhcpdump
                    msg = 'Getting MESH NODE INFO {} - {}'.format(s1._server, wlan_mac)
                    line = "-" * len(msg)
                    self.logger.debug('\n{}\n{}'.format(msg, line))
                    # Get arp info from node
                    try:
                        arp = self.get_arp_info(s1)
                        mesh_map[wlan_mac]['arp'] = arp
                    except Exception as ex:
                        self.logger.error('No arp entries can be found')
                        arp = None

                    # Get Mesh node STA info from AP
                    try:
                        sta = self.get_ap_station_info(s1, self.dhcpdump, arp)
                        if len(sta):
                            self.logger.info('\tFound {} STA'.format(len(sta)))
                        mesh_map[wlan_mac]['sta_on_ap'] = sta
                    except Exception:
                        self.logger.error('AP disabled?, can not determine if we have any sta_on_ap')

                    try:
                        # Get Mesh node neighboring MESH nodes
                        nodes = self.get_mesh_node_neighbors(s1)
                        if len(nodes):
                            self.logger.info('\tFound {} NEIGHBORING MESH NODES'.format(len(nodes)))
                        mesh_map[wlan_mac]['mesh_neighbors'] = nodes
                        # Get Mesh node path
                        mpath = self.get_mesh_path_info(s1)
                        mesh_map[wlan_mac]['mesh_path'] = mpath
                    except Exception :
                        pass

                    # Get iwinfo for both radios
                    iw = self.get_iwinfo(s1)
                    mesh_map[wlan_mac]['iwinfo'] = iw
                    # Get Mesh node AP info
                    ap_node_info = self.get_iw_info(s1)
                    mesh_map[wlan_mac]['ap_info'] = ap_node_info
                    ifcfg = self.get_ifconfig(s1)
                    mesh_map[wlan_mac]['ifconfig'] = ifcfg
                else:
                    self.logger.warning('FAILED TO LOGIN INTO {}'.format(addr2))
                s1.close()
            except Exception as ex:
                self.logger.exception(ex)
                import traceback
                traceback.print_exc()

                self.logger.warning('FAILED TO LOGIN INTO {}'.format(addr2))

        # pprint(mesh_map)
        return mesh_map

    def test_all_iperf(self):
        result = ''
        for i in self.list_of_nm:
            try:
                addr = '172.16.10.{}'.format(i)
                s = SSHToNetworkModule()
                s.password = self.password_list[addr]
                s.get_password_from_db = False
                ret = s.login_network_module(addr)
                if ret is False:
                    s.close()
                    s = SSHToNetworkModule()
                    s.password = self.password_list[addr]
                    ret = s.login_network_module(addr, 2020)

                if ret:
                    for j in self.list_of_nm:
                        if i != j:
                            try:
                                addr2 = '172.16.10.{}'.format(j)
                                s1 = SSHToNetworkModule()
                                s1.password = self.password_list[addr2]
                                s1.get_password_from_db = False
                                ret = s1.login_network_module(addr2)
                                if ret is False:
                                    s1.close()
                                    s1 = SSHToNetworkModule()
                                    s1.password = self.password_list[addr2]
                                    s1.get_password_from_db = False
                                    ret = s1.login_network_module(addr2, 2020)

                                if ret:
                                    wlan_mac = s._server_mac
                                    wlan_mac1 = s1._server_mac
                                    msg = 'Testing IPERF {} - {} to {} - {}'.format(s._server, wlan_mac, s1._server,
                                                                                    wlan_mac1)
                                    line = "-" * len(msg)
                                    print('\n{}\n{}'.format(msg, line))
                                    result += msg + "\n"
                                    result += self.single_iperf_test_nm_to_nm(s, s1, addr2)
                                s1.close()
                            except Exception:
                                pass
                    s.close()
            except Exception:
                pass

        return result

    def iperf_from_cameras_to_laptop(self, m, bw='20M', interval=1, timelimit=30):
        import shlex, tempfile
        local_ip = subprocess.check_output("ifconfig | grep 'inet 172.16.10'  | awk '{print $2}'",
                                           shell=True).strip().decode()

        self.logger.info('------------ IPERF cameras to laptop({}) ---------------------'.format(local_ip))

        sta = []
        for key in m:
            sta.extend(m[key]['sta_on_ap'])

        log_file = tempfile.TemporaryFile()

        # iperf command on laptop (server)
        cmds = shlex.split('iperf -u -s -i {}'.format(interval))
        process = subprocess.Popen(cmds, stdout=log_file)

        for cam in sta:
            # iperf command on camera (client to server)
            tel = TelnetCamera()
            try:
                tel.login_camera(cam['ip'])
                cmd2 = 'iperf -i {} -u -c {} -b {} -t {}'.format(interval, local_ip, bw, timelimit)
                result = tel.execute_cmd(cmd2)
                self.logger.info(result)
            except:
                pass
            tel.close()
        process.kill()
        log_file.seek(0)
        outs = log_file.read().decode()
        log_file.close()
        self.logger.info('======= Result from server on laptop =============')
        self.logger.info(outs)

    def iperf_from_laptop_to_cameras(self, m, bw='20M', interval=1, timelimit=30):
        import shlex, tempfile

        local_ip = subprocess.check_output("ifconfig | grep 'inet 172.16.10'  | awk '{print $2}'",
                                           shell=True).strip().decode()

        self.logger.info('------------ IPERF laptop({}) to camera ---------------------'.format(local_ip))

        sta = []
        for key in m:
            sta.extend(m[key]['sta_on_ap'])

        for cam in sta:
            process = None
            result = None
            cmds = None
            log_file = tempfile.TemporaryFile()
            # iperf command on camera
            tel = TelnetCamera()
            try:
                tel.login_camera(cam['ip'])
                tel.execute_cmd('killall iperf')
                cmd2 = 'iperf -u -s -i {}'.format(interval)
                ret = tel.start_cmd(cmd2)

                # iperf command on laptop
                cmds = shlex.split('iperf -i {} -u -c {} -b {} -t {}'.format(interval, cam['ip'], bw, timelimit))
                process = subprocess.Popen(cmds, stdout=log_file)

                result = tel.end_cmd(cmd2, timeout=timelimit + 5)
                tel.sendcontrol('c')
            except:
                pass
            tel.close()
            try:
                process.wait(timeout=timelimit + 5)
            except subprocess.TimeoutExpired:
                self.logger.warning('Waiting command {} and timed out'.format(' '.join(cmds)))
            self.logger.info('======= Result from server on camera {} ============='.format(cam['ip']))
            self.logger.info(result)

            log_file.seek(0)
            outs = log_file.read().decode()
            log_file.close()

            self.logger.info('======= Result from client on laptop {} ============='.format(local_ip))
            self.logger.info(outs)

    def can_ping(self, address):
        if address:
            import subprocess
            ret_code = subprocess.call(['ping', '-c', '1', address])
            # If ret_code == 0 we suceeded
            return (ret_code == 0)
        return False  # Failed to Ping

    def force_udhcpc_renew(self, m):
        import tempfile

        # must already have telnet and ipfilter disabled for each camera
        self.logger.info('------------ renew cameras lease ---------------------')

        sta = []
        for key in m:
            sta.extend(m[key]['sta_on_ap'])

        log_file = tempfile.TemporaryFile()

        for cam in sta:
            # iperf command on camera (client to server)
            tel = TelnetCamera()
            try:
                if not self.can_ping(cam['ip']):
                    self.logger.warning('Failed to ping {}'.format(cam['ip']))
                    tel.close()
                    continue
                self.logger.info('Telnet into {}'.format(cam['ip']))
                tel.login_camera(cam['ip'])
                cmd2 = 'killall -SIGUSR1 udhcpc'
                result = tel.execute_cmd(cmd2)
                self.logger.info(result)
            except:
                self.logger.exception('Failed with exception:')
            tel.close()
        log_file.seek(0)
        outs = log_file.read().decode()
        log_file.close()
        self.logger.info('======= Result from udhcpc renew =============')
        self.logger.info(outs)

    def test_1(self):
        for i in self.list_of_nm:
            addr = '172.16.10.{}'.format(i)
            s = SSHToNetworkModule()
            s.password = self.password_list[addr]
            s.get_password_from_db = False
            print('')
            print('Attempt login to address: {}'.format(addr))
            try:
                ret = s.login_network_module(addr)
                if ret is False:
                    s.close()
                    s = SSHToNetworkModule()
                    s.password = self.password_list[addr]
                    s.get_password_from_db = False
                    ret = s.login_network_module(addr, 2020)

                if ret:
                    uptime = s.execute_cmd('uptime')
                    print(uptime)
                    wlan_mac = s.execute_cmd("ifconfig wlan0 | grep HWaddr | awk '{print $5}'")
                    print('MAC: {}'.format(wlan_mac))
                    mpath_dump = s.execute_cmd('iw wlan0 mpath dump')
                    print(mpath_dump)

                    local_ip = subprocess.check_output("ifconfig | grep 'inet 172.16.10'  | awk '{print $2}'",
                                                       shell=True).strip().decode()
                    print('My IP: {}'.format(local_ip))

                    # test_iperf_1(s, addr)

                    self.single_iperf_test_nm_to_laptop(s, local_ip)

                    # test_netv_dhcpdump(s)

                    s.close()
            except Exception as ex:
                pass

    def get_module_local_ip(self):
        try:
            con_cmd = NMCmdConsoleClass(rate=57600)
            module_ip = con_cmd.execute_command("ifconfig br-lan | grep Bcast | awk '{print $2}'|cut -d':' -f2")
            con_cmd.close()
            return module_ip
        except Exception :
            print('Failed to get IP')

        return ''

    def fix_up_netd_database(self, list_of_panels):
        for j in list_of_panels:
            addr2 = '172.16.10.{}'.format(j)
            self.logger.info('Attempting to log into {}'.format(addr2))
            try:
                s1 = SSHIntoPanel(timeout=5)
                ret = s1.login_network_module(addr2)
                if ret:
                    self.logger.info('Logged into panel {}'.format(addr2))
                    ret = s1.login_into_sql('netd.db')
                    if ret == 1:  # Got prompt
                        ret = s1.execute_db_cmd("select value from property where name = 'mesh_id';")
                        if ret is not None:
                            result = ret
                            new_mesh_id = 'tle90548-vivintmesh-886ae3e8f377'
                            ret = s1.execute_db_cmd(
                                "update property set value = '\"{}\"' where name = 'mesh_id';".format(new_mesh_id))
                            print(result)

                        ret = s1.execute_db_cmd("select value from property where name = 'operating_mode';")
                        if ret is not None:
                            result = ret
                            ret = s1.execute_db_cmd(
                                "update property set value = '{}' where name = 'operating_mode';".format(7))
                            print(result)

                        ret = s1.execute_db_cmd("select value from property where name = 'network_operating_mode';")
                        if ret is not None:
                            result = ret
                            ret = s1.execute_db_cmd(
                                "update property set value = '{}' where name = 'network_operating_mode';".format(7))
                            print(result)

                        s1.close_sql()

                    s1.close()
            except Exception as ex:
                pass

class PanelSystemInfo(EventLoop):
    def __init__(self, address='172.16.10.100'):
        super().__init__(self.on_exit)
        self.address = address
        import logging
        self.logger = logging.getLogger(self.__class__.__name__)

        if not on_touchlink() and address.startswith('172.16.10.100'):
            self.address = '10.42.1.79'

        self.port = 7080

        self._cam_list = []

    def setup(self):
        self.__client = RpcClient('sundance')
        self.__client.connect(self.address, self.port, self.on_connected, disconnected_callback=self.on_disconnected,auto_reconnect=False)

    def on_exit(self):
        if self.__client:
            self.__client.disconnect()

    def on_connected(self, error):
        if not error:
            self.logger.info("CONNECTED CALLED.........STARTING TEST")
            self.cameras = []
            self.lgs = []
            self.yofi_nodes = []
            self.panels = []
            for device in CameraDevice.list():
                dev = device.get_all_properties()
                mac = "00:00:00:00:00:00"
                if device.camera_mac_address:
                    mac = device.camera_mac_address.lower()
                elif device.mac_address:
                    mac = device.mac_address.lower()

                if ':' not in mac:
                    mac = ':'.join([mac[i: i + 2] for i in range(0, len(mac), 2)])
                dev['properties']['camera_mac_address'] = mac
                if 'motion_detection_windows' in dev['collections']:
                    from taurine.decorators.service_object_decorator import service_object
                    mdw_list = dev['collections']['motion_detection_windows']
                    new_mdw_list = []
                    try:
                        for mdw in mdw_list:
                            new_mdw_list.append(mdw.as_dict())
                        dev['collections']['motion_detection_windows'] = new_mdw_list
                    except:
                        dev['collections']['motion_detection_windows'] = []
                self.cameras.append(dev)

            for device in LGITPoEWifiDevice.list():
                dev = device.get_all_properties()
                mac = "00:00:00:00:00:00"
                if device.mac_address:
                    mac = device.mac_address.lower()
                if ':' not in mac:
                    mac = ':'.join([mac[i: i + 2] for i in range(0, len(mac), 2)])
                dev['properties']['camera_mac_address'] = mac
                dev['properties']['connected_ssid'] = ''
                dev['properties']['wireless_link_quality'] = ''
                try:
                    dev['properties']['wireless_signal_level'] = dev['properties']['rssi']
                    # Match up the camera name to the bridge so we can also show the IP of the camera in the name.
                    for cam in self.cameras:
                        if dev['properties']['name'].startswith(cam['properties']['name']):
                            dev['properties']['name'] = '{} [{}]'.format(cam['properties']['name'], cam['properties']['camera_ip_address'])
                            break
                except:
                    pass

                self.lgs.append(dev)

            self.cameras.extend(self.lgs)

            for device in YofiDevice.list():
                dev = device.get_all_properties()
                mac = "00:00:00:00:00:00"
                if device.mac_address:
                    mac = device.mac_address.lower()
                if ':' not in mac:
                    mac = ':'.join([mac[i: i + 2] for i in range(0, len(mac), 2)])
                dev['properties']['mac_address'] = mac
                self.yofi_nodes.append(dev)

            for device in SlimLineDevice.list():
                dev = device.get_all_properties()
                mac = "00:00:00:00:00:00"
                if device.panel_mac_address:
                    mac = device.panel_mac_address.lower()
                elif device.mac_address:
                    mac = device.mac_address.lower()

                if ':' not in mac:
                    mac = ':'.join([mac[i: i + 2] for i in range(0, len(mac), 2)])
                dev['properties']['panel_mac_address'] = mac
                self.panels.append(dev)

            sys.exit(0)

    def on_disconnected(self):
        self.logger.info('Goodbye!:')

    def run(self):
        DelayedCall(0,self.setup)
        super().run()

    def retrieve_nodes_password(self):
        self.logger.info("Retrieving node password...")
        yofi_devices = self.get_yofi_info()

        list_of_password = {}

        for node in yofi_devices:
            if node['properties']['password']:
                _password = node['properties']['password']
            else:
                _password = 'ap3x!0wrt'

            list_of_password[node['properties']['address']] = _password

        # self.logger.info("DEBUG: passord list: {}".format(list_of_password))
        return list_of_password

    def get_camera_info(self):
        return self.cameras

    def get_yofi_info(self):
        return self.yofi_nodes

    def get_slim_line_info(self):
        return self.panels


def on_touchlink():
    uname = platform.uname()
    is_linux = "linux" in sys.platform
    return is_linux and not "x86" in uname[4] and not "Ubuntu" in uname[3]

def main(use_ssdp=True):
    node_list = []
    node_password_list = {}
    p = PanelSystemInfo(address='10.42.1.79')
    p.run()
    camera_info = p.get_camera_info()
    panel_info = p.get_slim_line_info()
    node_password_list = p.retrieve_nodes_password()

    # pprint(j)
    #
    # for cam in j:
    #     print()
    #     print(cam['class'])
    #     print(cam['properties']['name'])
    #     print(cam['id'])
    #     print(cam['properties']['camera_ip_address'])  # cam['properties']['address']
    #     print(cam['properties']['camera_mac_address'])

    if not node_password_list:
        print("Failed to get password list for nodes. exit now")
        sys.exit(1)

    node_list = NetworkModuleInfo.find_nodes(use_ssdp, password_list=node_password_list)

    nm = NetworkModuleInfo(node_list, password_list=node_password_list)

    result = nm.get_module_local_ip()
    with open('/tmp/module_ip.txt', 'w') as f:
        f.write(result)

    # nm.fix_up_netd_database([100])

    # nm.get_camera_info()
    data = nm.mesh_node_info_map()
    # data = nm.test_all_iperf()

    print("------------------------------------------------------------------------------")
    pprint(data)
    print("------------------------------------------------------------------------------")
    dest_dir = '/srv/www/network' if on_touchlink() else '/tmp'
    print('Dest Dir: {}'.format(dest_dir))
    with open('{}/tmp_cam.dat'.format(dest_dir), 'w') as f:
        pprint(camera_info, f)

    with open('{}/tmp_panel.dat'.format(dest_dir), 'w') as f:
        pprint(panel_info, f)

    with open('{}/tmp.dat'.format(dest_dir), 'w') as f:
        pprint(data, f)


if __name__ == '__main__':
    main(use_ssdp=False)
