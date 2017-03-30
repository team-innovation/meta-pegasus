#!/usr/bin/env python3
#
# Copyright 2017 Vivint Innovation
#
# mfr_audio_test.py for Wallsly with z8380tw
#
# -
import logging
import os
import wave
import threading
import time

try:
    from pulseaudio.lib_pulseaudio import *
except ImportError:
    print('ERROR: pulseaudio library not installed')
try:
    from ctypes import c_int, c_short
except ImportError:
    print('ERROR: ctypes library not installed')
try:
    import numpy as np
    import ctypes
    from time import sleep
    import math
    from pprint import pprint
    from subprocess import call
    from struct import *
except ImportError:
    print('ERROR: numpy library not installed')

__author__ = 'bob'

frequencies = [220, 440, 880, 1760, 3520, 7040]
wave_files = ['wave_220_hz.wav', 'wave_440_hz.wav', 'wave_880_hz.wav', 'wave_1760_hz.wav', 'wave_3520_hz.wav', 'wave_7040_hz.wav']
FFT_SIZE = 4092
FS = 16000

class SecioIO:

    @staticmethod
    def f_write(sys_path, sys_file, value):
        with open(os.path.join(sys_path, sys_file), 'w') as f:
            f.write(value)

    @staticmethod
    def f_read(sys_path, sys_file, typ=None):
        val = None
        with open(os.path.join(sys_path, sys_file), 'r') as f:
            val = f.read()
            if typ:
                val = typ(val)

        return val

class AudioTest:

    AUDIO_AMP_PATH = "/sys/class/lm48511/lm485110"
    AUDIO_AMP_ACCESS_FILE = "access"
    AUDIO_AMP_FB_SEL_FILE = "fb_sel"
    AUDIO_AMP_SD_AMP_FILE = "sd_amp"
    AUDIO_AMP_SD_BOOST_FILE = "sd_boost"
    AUDIO_AMP_SS_FF_FILE = "ss_ff"

    def __init__(self, audioFile, idx):
        # print('Testing at frequency {}'.format(frequencies[idx]))

#FIXIT z8380 disable AEC, anti-howling, etc.        
        # force off noise reduction and echo cancellation
#        os.system("echo 0x117a 0x8 > /sys/class/cx20704/cx20704_controls/regwrite")
#        os.system("echo 1 > /sys/class/cx20704/cx20704_controls/newc")

	# This was put in to patch pulse 8.0. We need to figure out what's wrong with 8.0
        # restart pulseaudio to get a good connection
        #os.system("/etc/init.d/pulseaudio restart  > /dev/null")
        #time.sleep(1)
        
        self._enable_sly_audio_amp()

        self.idx = idx
        self.audioFile = audioFile
        self.playing = False
        self.play_stream = None
        self.recording = False
        self.rec_stream = None
        self.frames = 0
        self.fftfreq = np.fft.fftfreq(FFT_SIZE) * FS
        self.fftfreq = self.fftfreq[0:FFT_SIZE/2]
        #pprint(self.fftfreq)
        #pprint(np.amax(self.fftfreq))
        #pprint(np.searchsorted(self.fftfreq, 2, side='left'))
        self._create_playback_stream()
        self._create_record_stream()
        file = wave.open(self.audioFile, 'rb')
        self.frames = file.getnframes()
        self.data = file.readframes(self.frames)
        file.close()

        self.playThread = threading.Thread(name='PlayThread', target=self._start_playback)
        self.recordThread = threading.Thread(name='RecordThread', target=self._start_record)
        self.recordThread.start()
        self.playThread.start()
        self.playThread.join()
        self.recordThread.join()

    def _enable_sly_audio_amp(self):
        try:
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_ACCESS_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_SS_FF_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_SD_BOOST_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_SD_AMP_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_FB_SEL_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_ACCESS_FILE, '0')
        except Exception as why:
            self.logger.warn('Enable amp failed: {}'.format(why))

    def _create_playback_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = FS
        self.play_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_PLAYBACK, None, b'playback', ss, None, None, None)

    def _create_record_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = FS
        self.rec_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_RECORD, None, b'record', ss, None, None, None)

    def _start_playback(self):
        data = self.data + self.data

        self.playing = True
        self._play_buffer(data)

    def _start_record(self):
        #self._create_record_stream()
        freq = frequencies[self.idx]

        rec_data = (c_short * self.frames)()
        self.recording = True

        while self.playing is False:
            pass

        self._record_buffer(rec_data)

        file = wave.open('tmp.wav', 'wb')
        file.setnchannels(1)
        file.setsampwidth(2)
        file.setframerate(8000)
        file.writeframes(rec_data)
        file.close()

        #print(rec_data[1:self.frames+1])

        #d = np.ctypeslib.as_array((ctypes.c_short * self.frames).from_address(ctypes.addressof(self.data)))
        #d = unpack_from('<4000h', self.data)
        x = np.ctypeslib.as_array((ctypes.c_short * self.frames).from_address(ctypes.addressof(rec_data)))
        x = x / 32768
        y = np.fft.rfft(x, FFT_SIZE)/self.frames        

        y = np.absolute(y)

        if self.idx == 0:
            self.thd = self._compute_thd(y, freq)
            fi = self._find_nearest_index(freq)
            self.base_power = 20 * np.log10(y[fi] + y[fi+1])

        y = np.log10(y)
        y = 20 * y
        if self.idx == 0:
            #print('THD at {} Hz: {}% ...{} {} {} {} {}'.format(frequencies[0], self.thd, self.base_power, self._find_nearest(y, freq*2), self._find_nearest(y, freq*3), self._find_nearest(y, freq*4), self._find_nearest(y, freq*5)))
            print('THD at {} Hz: {}%'.format(frequencies[0], self.thd))
            print('Power at {} Hz: {} dB'.format(freq, self.base_power))
        else:
            print('Power at {} Hz: {} dB'.format(freq, self._find_nearest(y, freq)))


    def _play_buffer(self, buffer):
        error = c_int()
        pa_simple_flush(self.play_stream, error)
        pa_simple_write(self.play_stream, buffer, len(buffer), error)
        pa_simple_free(self.play_stream)

    def _record_buffer(self, buffer):
        error = c_int()
        pa_simple_flush(self.rec_stream, error)
        pa_simple_read(self.rec_stream, buffer, self.frames*2, error)
        pa_simple_free(self.rec_stream)

    def _find_nearest(self, array, value):
        idx = np.searchsorted(self.fftfreq, value, side="left")
        #print('find nearest {} {}'.format(value, idx))
        if math.fabs(value - self.fftfreq[idx-1]) < math.fabs(value - self.fftfreq[idx]):
            return array[idx-1]
        else:
            return array[idx]

    def _find_nearest_index(self,  value):
        idx = np.searchsorted(self.fftfreq, value, side="left")
        #print('find nearest index {} {}'.format(value, idx))
        if math.fabs(value - self.fftfreq[idx-1]) < math.fabs(value - self.fftfreq[idx]):
            return idx-1
        else:
            return idx

    def _compute_thd(self, x, freq, n_harmonics=5):
        sum_of_squares = 0
        for i in range(2, n_harmonics+1):
            tmp = self._find_nearest(x, freq * i) ** 2
            sum_of_squares += tmp
            #print('i: {} mag: {} sum: {}'.format(i, tmp, sum_of_squares))
        fi = self._find_nearest_index(freq)
        base_power = (x[fi] + x[fi+1]) ** 2
        print('base power: {}'.format(base_power))
        return (sum_of_squares / base_power) * 100

def setup_gains():
    call("amixer sset 'Sin' 16", shell=True)
    call("amixer sset 'Sout' 8", shell=True)
    call("amixer sset 'Mic' 4", shell=True)
    call("amixer sset 'Master' 78", shell=True)
    call("amixer sset 'Rout' 64", shell=True)
    call("amixer sset 'Wave' 90", shell=True)
#FIXIT set zl380 sampling rate to 16khz

if __name__ == "__main__":
    setup_gains()
    for i in range(0, len(wave_files)):
        at = AudioTest(wave_files[i], i)
        time.sleep(0.5)





