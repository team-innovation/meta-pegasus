#!/usr/bin/python3

import cgi
import cgitb
import sys
import os
import time
from taurine.rpc.rpc_client import RpcClient
from taurine.async_io.event_loop import EventLoop
from taurine.async_io.delayed_call import DelayedCall

cgitb.enable()

sys.path.insert(0, "/opt/2gig/procmand/proxies/python")
sys.path.insert(0, "/opt/2gig/procmand")


class RestoreHelper(EventLoop):
    def __init__(self):
        super().__init__(self.on_exit)
        self.procmand = None
        self.form = cgi.FieldStorage()
        self.ssid = self.form.getvalue("reset", "")

    def setup(self):
        self.__client = RpcClient("procmand")
        self.__client.connect('localhost', 7016, self.on_connected, auto_reconnect=False)

    def run(self):
        DelayedCall(0, self.setup)
        super().run()

    def on_connected(self, error):
        if not error:
            self.procmand = self.__client.get_service_with_name("RestoreService")
            self.connect_to_panel()

    def connect_to_panel(self):
        print("Content-type: text/html")
        print("<html><head><title>Reseting</title></head><body><Connecting></body></html>")
        sys.stdout.flush()
        os.close(sys.stdout.fileno())

        try:
            pid = os.fork()
            if pid:
                sys.exit(0)
            
            time.sleep(1)
            os.setsid()

            self.procmand.reset_to_factory_defaults()
            os._exit(0)
        except OSError as e:
            sys.exit(1)

    def on_exit(self):
        pass

rh = RestoreHelper()
rh.run()
