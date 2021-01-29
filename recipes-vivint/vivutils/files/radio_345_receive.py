#
# Python3 script to read 345 data
#
# python3 radio_345_receive.py [TXID] [Duration]
#
# TXID is a 4 hex byte TXID, 1111 = default
#
# Duration is the length of time this scripts listens for packets, 0 = default and infinite
#
# To listen for sensor packets with a TXID of 12a4
# python3 radio_345_receive.py 12a4 0
# 
# To listen for sensor packets with a TXID of be82 for 4 seconds
# python 3 radio_345_receive.py be82 4
#
# Print readout reports count #, received TXID, selected TXID, packet, and RSSI
# If the received TXID and selected TXID match, the counter is incremented
# count: 114  TXID: b'1111'  actual: b'1111'  _hexData: b'55ae804111113137'  RSSI: b'31' 
#


import argparse
import binascii
import time
from serial import Serial, SerialException
import sys

def _write_bytes(bytes):
    _serial.write(bytes)

def _read_bytes():
    _waiting = True
    _receive_count = 0
    while _waiting:
        # get the current time
        _current_time = time.clock()
        # compute the elapsed time from the current time and start time
        _elapsed_time = _current_time - _start_time
        # has the elapsed time exceeded the selected duration? 
        # AND is the application not running forever?
        if _elapsed_time > _selectDuration and _selectDuration != 0:
            _waiting = False
        # has any data arrived at the serial port?
        if _serial.inWaiting():
            # collect the initial bytes
            _data = _serial.read()
            while _serial.inWaiting():
                # collect the remaining bytes
                _data += _serial.read()
            # is this an 8 byte legacy packet?
            if len(_data) == 8:
                # extract the 2 byte TXID
                _payload = _data[4:6]
                _rssi = _data[6:7]
            else:
                _payload = _data
                _rssi = binascii.unhexlify('00')
            # convert the selected 2 byte TXID into a 4 byte hex string TXID
            _hexTxid = binascii.hexlify(_txid)
            # convert the received 2 byte TXID into a 4 byte hex string TXID
            _hexPayload = binascii.hexlify(_payload)
            # convert the received 8 byte packet into a 16 byte hex string packet
            _hexData = binascii.hexlify(_data)
            # convert the received 1 byte RSSI into a 2 byte hex string RSSI
            _hexRssi = binascii.hexlify(_rssi)
            # does the selected TXID equal the received TXID?
            if _hexTxid == _hexPayload:
                # increment
                _receive_count += 1
            print('count:', _receive_count, ' TXID:', _hexPayload, ' selected:', _hexTxid, ' _hexData:', _hexData, ' RSSI:', _hexRssi)

if __name__=='__main__':
    print('Starting radio_345_receive.py')
    # is there a selected TXID?
    if len(sys.argv) >= 2:
        _txid = sys.argv[1]
        # convert the 4 byte hex string TXID into a 2 byte hex TXID
        _txid = binascii.unhexlify(_txid)
    else:
        # default 2 byte hex TXID is 0x1111
        _txid = b'\x11\x11'
    # is there a selected duration?
    if len(sys.argv) >= 3:
        _selectDuration = float(sys.argv[2])
    else:
        # default select duration is 4 seconds
        _selectDuration = 0
    _serialname = "/dev/ttymxc4"
    _sysclass = "/sys/class/zwave/zwave0/"
    _serial = Serial(_serialname, 115200, timeout=None)

    # start time
    _start_time = time.clock()

    _sleep_delay = 0.05
    _write_delay = 0.2
    time.sleep(0.04)
    # read the incoming serial data
    _read_bytes()
    time.sleep(_write_delay)

    _serial.flush()
    _serial.close()
    print('Done')

