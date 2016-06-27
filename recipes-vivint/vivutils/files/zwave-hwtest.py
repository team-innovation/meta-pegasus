#!/usr/bin/python3

from serial import Serial, SerialException
import sys
import time

def checksum(data):
    ret = 0xff
    for b in data:
        ret ^= b
    return ret

func_add_node   = 0x4a
func_get_memory = 0x23

mode_add = 0x01
mode_stop = 0x05

callback1 = 0x01
callback2 = 0x02

add_cmd_len = 0x05

sof = 0x01
request = 0x00
response = 0x01

ack = bytearray((0x06,))

add_cmd = bytearray((request, func_add_node, mode_add, callback1, 0)) 
add_cmd.insert(0, len(add_cmd))
cksum = checksum(add_cmd)
add_cmd[-1] = cksum
add_cmd.insert(0, sof)


stop_cmd = bytearray((request, func_add_node, mode_stop, 0, 0)) 
stop_cmd.insert(0, len(stop_cmd))
cksum = checksum(stop_cmd)
stop_cmd[-1] = cksum
stop_cmd.insert(0, sof)

abort_cmd = bytearray((request, func_add_node, mode_stop, callback1, 0)) 
abort_cmd.insert(0, len(abort_cmd))
cksum = checksum(abort_cmd)
abort_cmd[-1] = cksum
abort_cmd.insert(0, sof)

class ZwaveTest:
    """Get information from the zwave device for manufacturing test"""

    # Location of zwave sys class
    _zwavesysclass = '/sys/class/zwave/zwave0/'

    @staticmethod
    def sysclass_open(name, mode='w'):
        """Open zwave sys class to get access to zwave chip."""
        return open(ZwaveTest._zwavesysclass + name, mode=mode)
            
    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_value, traceback):
        self.sysclass_open('uart_enable').write('0')
        self.sysclass_open('access').write('0')

    def __init__(self):
        self.portname = "/dev/" + \
            ZwaveTest.sysclass_open('uartname', mode='r').read()
        self.sysclass_open('access').write('1')
        self.sysclass_open('uart_enable').write('1')
        self.serialport = Serial(self.portname, 115200, 
                                 stopbits = 1, timeout= 2)
        self.serialport.flushOutput()
        self.serialport.flushInput()

    def writep(self, pkt):
        """Write a packet to the chip"""
        self.serialport.write(pkt)

    def readp(self, ackonly=False):
        """Read a packet from the chip"""
        ack = self.serialport.read(1)
        if not ackonly:
            sf = self.serialport.read(1)
            plen = self.serialport.read(1)
            msg = self.serialport.read(plen[0])
            self.serialport.write(ack)
        
        
def main(args):
    with ZwaveTest() as zwave:
        if args[1] == "1":
            zwave.writep(add_cmd)
            zwave.readp()
        else:
            zwave.writep(abort_cmd)
            zwave.readp()
            zwave.writep(stop_cmd)
            zwave.readp(True)

if __name__=='__main__':
    sys.exit(main(sys.argv))
