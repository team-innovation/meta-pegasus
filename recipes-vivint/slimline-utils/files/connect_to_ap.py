#!/usr/bin/python3

import cgi
import cgitb
import sys
import os
from taurine.rpc.rpc_client import RpcClient
from taurine.async_io.event_loop import EventLoop
from taurine.async_io.delayed_call import DelayedCall

cgitb.enable()

sys.path.insert(0, "/opt/2gig/netd/proxies/python")
sys.path.insert(0, "/opt/2gig/netd")

import services.singletons.slim_line.scripts.wps


class NetworkHelper(EventLoop):
    def __init__(self):
        super().__init__(self.on_exit)
        self.netd = None
        self.form = cgi.FieldStorage()
        self.ssid = self.form.getvalue("ssid", "")
        self.password = self.form.getvalue("password","")
        self.mac = ""

    def setup(self):
        self.__client = RpcClient("netd")
        self.__client.connect('localhost', 7170, self.on_connected, auto_reconnect=False)

    def run(self):
        DelayedCall(0, self.setup)
        super().run()

    def on_connected(self, error):
        if not error:
            self.netd = self.__client.get_service_with_name("NetworkService")
            self.connect_to_panel()

    def connect_to_panel(self):
        self.netd.start_wifi_connect(self.ssid, self.password)
        print("Content-type: text/html")
        print("<html><head><title>Connecting</title></head><body><Connecting></body></html>")
        sys.exit(0)

    def on_exit(self):
        pass

nh = NetworkHelper()
nh.run()
