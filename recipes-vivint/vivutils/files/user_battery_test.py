#!/usr/bin/python3
import sys

from taurine.rpc.rpc_client import RpcClient
from taurine.async_io.event_loop import EventLoop
from taurine.async_io.delayed_call import DelayedCall

sys.path.insert(0, "/opt/2gig/iod/proxies/python")
sys.path.insert(0, "/opt/2gig/iod")

class IodHelper(EventLoop):
    def __init__(self):
        super().__init__(self.on_exit)
        self.iod = None

    def setup(self):
        self.__client = RpcClient("iod")
        self.__client.connect('localhost', 7150, self.on_connected, auto_reconnect=False)

    def run(self):
        DelayedCall(0, self.setup)
        super().run()

    def on_connected(self, error):
        if not error:
            self.iod = self.__client.get_service_with_name("PowerService")
            self.connect_to_panel()

    def connect_to_panel(self):
        with open('/var/tmp/battery_raw', 'w') as f:
            f.write('3800')
        with open('/var/tmp/temp_raw', 'w') as f:
            f.write('1965')
        with open('/var/tmp/temp1_input', 'w') as f:
            f.write('25000')
        with open('/var/tmp/battery_no_fault', 'w') as f:
            f.write('1')
        with open('/var/tmp/battery_not_charging', 'w') as f:
            f.write('0')
        with open('/var/tmp/v_batch_i', 'w') as f:
            f.write('0')
        with open('/var/tmp/battery_no_fault', 'w') as f:
            f.write('1')
        with open('/var/tmp/battery_not_charging', 'w') as f:
            f.write('0')
        with open('/var/tmp/ch_battery_charge_enable', 'w') as f:
            f.write('2')
        with open('/var/tmp/ch_battery_not_charged', 'w') as f:
            f.write('3')
        with open('/var/tmp/ch_battery_warn_indicator', 'w') as f:
            f.write('0')

        if len(sys.argv) == 1:
            enable = True
        else:
            if sys.argv[1] == '1':
                enable = True
            else:
                enable = False
            
        self.iod.use_test_directories(enable, '/var/tmp')

        sys.exit(0)

    def on_exit(self):
        pass

ioh = IodHelper()
ioh.run()
