#!/usr/bin/python3

import cgi
import cgitb
import sys
import os
import time
from taurine.rpc.rpc_client import RpcClient
from taurine.async_io.event_loop import EventLoop

cgitb.enable()

sys.path.insert(0, "/opt/2gig/procmand/proxies/python")
sys.path.insert(0, "/opt/2gig/procmand")

class RestoreHelper(EventLoop):
    def __init__(self):
        super().__init__()
        self.procmand = None
        self.form = cgi.FieldStorage()
        self.reset = self.form.getvalue("reset", "")

    async def setup(self):
        self.__client = RpcClient("procmand")
        self.__client.connect('localhost', 7016, self.on_connected, auto_reconnect=False)

    def run(self):
        self.create_task(self.setup())
        super().run()

    def on_connected(self, error):
        if not error:
            self.procmand = self.__client.get_service_with_name("RestoreService")
            self.connect_to_panel()

    def connect_to_panel(self):
        self.procmand.reset_to_factory_defaults()
        self.shutdown()

try:
    pid = os.fork()
    if pid:
        print("Content-type: text/html")
        print("")
        print("<html><head><title>Reseting</title></head><body>Reseting</body></html>")
        sys.exit(0)
except OSError:
    sys.exit(1)

os.setsid()
sys.stdout.flush()
os.close(sys.stdout.fileno())

rh = RestoreHelper()
rh.run()
