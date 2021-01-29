#
# Python3 script to read 345 data (optimized for sensitivity testing with RIGOL instrument and chosen script)
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

_receive_count = 0
period_counter = 0
period_start = 0
total_fail_count = 0
data_received = True
live_missed_packet_count = True

def _write_bytes(bytes):
    _serial.write(bytes)
    
def interpret_packet(data):
    global _receive_count
    global period_counter
    global period_start
    global total_fail_count
    global data_received
    global live_missed_packet_count
    
    print("3we are here!")
    
    data_received = True
    
    # extract the 2 byte TXID
    _payload = data[4:6]
    _rssi = data[6:7]
    # convert the selected 2 byte TXID into a 4 byte hex string TXID
    _hexTxid = binascii.hexlify(_txid)
    # convert the received 2 byte TXID into a 4 byte hex string TXID
    _hexPayload = binascii.hexlify(_payload)
    # convert the received 8 byte packet into a 16 byte hex string packet
    _hexData = binascii.hexlify(data)
    # convert the received 1 byte RSSI into a 2 byte hex string RSSI
    _hexRssi = binascii.hexlify(_rssi)
    # does the selected TXID equal the received TXID?
    if _hexTxid == _hexPayload:
        # increment
        # *****
        #print("reset period_start")
        period_counter += 1
        period_start = time.clock()
        # *****
        _receive_count += 1
    time1 = time.clock()
    # print("+")
    percent_success = (_receive_count/(_receive_count + total_fail_count))*100
    if live_missed_packet_count is True:
        print('[', "{:.3f}".format(time1), ']', ' misses:', total_fail_count, ' rx count:', _receive_count, 'pct:', "{:.3f}".format(percent_success), 'TXID:', _hexPayload)
    else:
        print('[', "{:.3f}".format(time1), ']', ' count:', _receive_count, 'TXID:', _hexPayload)

def _read_bytes():
    _waiting = True
    global _receive_count
    global period_counter
    global period_start
    global total_fail_count
    global data_received
    global live_missed_packet_count
    # ********
    live_missed_packet_count = True
    data_received = True
    wait_period = 0.600 #s *** a quiet period between 3-packet bursts
    wait_period_active = 1 # ***
    test_period = 60 # seconds of test period
    
    period_start = time.clock()
    
    max_packets_in_one_minute = 172 # 3-packet burst period is just greater than 1 second on average
    # ********
    while _waiting:
        
        # get the current time
        _current_time = time.clock()
        if _current_time >= test_period:
                if _receive_count + total_fail_count < 1:
                    percent_success = 0.0
                else:
                    percent_success = (_receive_count/(_receive_count + total_fail_count))*100
                print("time:", "{:.3f}".format(_current_time),  ' misses:', total_fail_count, ' rx count:', _receive_count,"pct:", percent_success)
                exit()
        
        if total_fail_count + _receive_count > max_packets_in_one_minute and live_missed_packet_count is False:
            total_fail_count = max_packets_in_one_minute - _receive_count

        # compute the elapsed time from the current time and start time
        _elapsed_time = _current_time - _start_time
        # has the elapsed time exceeded the selected duration?
        # AND is the application not running forever?
        if _elapsed_time > _selectDuration and _selectDuration != 0:
            _waiting = False
        # has any data arrived at the serial port?

        # *****
        # check if in period between packet bursts
        if _current_time - period_start > wait_period:
            # if data_received is False:
                # if live_missed_packet_count is True:
                    # print("**Low sensitivity detected; switching off inaccurate live missed packet count and percentage reporting**")
                    # print("(Missed packet count and successful packet percentage will be reported at the end of the test period)")
                # live_missed_packet_count = False
            # add any failures to fail counter
            total_fail_count += 3 - period_counter
            # reset period_counter
            period_counter = 0

            print("*")
            #print("current_time:", _current_time, " period_start:", period_start, " period end")
            
            data_received = False
            period_start = time.clock()
        # *****
        if _serial.inWaiting():
            print("1we are here!")
            # collect the initial bytes
            _data = _serial.read()
            while _serial.inWaiting():
                # collect the remaining bytes
                _data += _serial.read()
            print("2we are here!")
            # is this an 8 byte legacy packet?
            if len(_data) == 8:
                interpret_packet(_data)
            else:
                #string_data = str(_data)
                #print("string_data is:", string_data)
                packets = _data.split(b'U\xae')
                #print("packets is:", packets)
                #print("range(len(packets)) is:", range(len(packets)))
                for idx in range(len(packets)):
                    #print("idx is:", idx)
                    #print("raw packets[idx] is:", packets[idx])
                    if len(packets[idx]) < 6:
                        #packets.pop(idx)
                        continue
                    packets[idx] = packets[idx][:12]
                    packets[idx] = b'U\xae' + packets[idx]
                    interpret_packet(packets[idx])
                    
if __name__=='__main__':
    print('Starting radio_345_receive.py')
    
    serial_name = "/dev/ttymxc4"  #***for Sky Ctrl change to ttyO2
    # is there a selected TXID?
    if len(sys.argv) >= 2:
        _txid = sys.argv[1]
        # convert the 4 byte hex string TXID into a 2 byte hex TXID
        _txid = binascii.unhexlify(_txid)
        if len(sys.argv) >= 3:
            serial_name = sys.argv[2]
    else:
        # default 2 byte hex TXID is 0x1111
        # _txid = b'\x11\x11'
        print("No ID selected.\n")
        exit()
    # is there a selected duration?
    # if len(sys.argv) >= 3:
        # _selectDuration = float(sys.argv[2])
    # else:
        # # default select duration is 4 seconds
        # _selectDuration = 0
    _selectDuration = 0
    _serialname = serial_name
    _sysclass = "/sys/class/zwave/zwave0/"
    if "COM" not in _serialname:
        _serial = Serial(_serialname, 115200, timeout=None) #***for Sky Ctrl change to 19200
    else:
        _serial = Serial('COM27')

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
