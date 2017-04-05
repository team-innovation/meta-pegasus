#!/usr/bin/env python3
#
# Copyright 2017 Vivint Innovation
#
# mfr_audio_test.py for Wallsly with z8380tw
#
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

frequencies = [220, 330, 440, 500, 600, 700, 880, 1000, 1100, 1200, 1760, 3520, 7040]
#frequencies = [1760, 3520, 7040]
#Array not used: wave_files = ['wave_220_hz.wav', 'wave_440_hz.wav', 'wave_880_hz.wav', 'wave_1760_hz.wav', 'wave_3520_hz.wav', 'wave_7040_hz.wav']

FFT_SIZE = 16384
FSREC = 16000
FSPB  = 16000

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
        print('Testing at frequency {}'.format(frequencies[idx]))

        self._enable_wallsly_audio_amp()

        self.costabsize = 8192
        self.costabmask = int(8192 - 1)
        self.costab = (c_short * self.costabsize)()
        for i in range(0, self.costabsize):
            self.costab[i] = np.int16(28000. * np.cos((2. * 3.14159265359 * float(i)) / float(self.costabsize)))

        self.idx = idx
        self.audioFile = audioFile
        self.playing = False
        self.play_stream = None
        self.recording = False
        self.rec_stream = None
        self.pblength = FSPB * 4
        self.reclength = FSPB * 2
        self.fftfreq = np.fft.fftfreq(FFT_SIZE) * FSREC
        self.fftfreq = self.fftfreq[0:FFT_SIZE/2]
        #pprint(self.fftfreq)
        #pprint(np.amax(self.fftfreq))
        #pprint(np.searchsorted(self.fftfreq, 2, side='left'))
        self._create_wave(frequencies[idx])

        self._create_playback_stream()
        self._create_record_stream()

        #file = wave.open(self.audioFile, 'rb')
        #self.pblength = file.getnframes()
        #self.data = file.readframes(self.pblength)
        #file.close()

        #print(audioFile)
        self.playThread = threading.Thread(name='PlayThread', target=self._start_playback)
        self.recordThread = threading.Thread(name='RecordThread', target=self._start_record)
        self.playThread.start()
        time.sleep(0.5)
        self.recordThread.start()
        self.playThread.join()
        self.recordThread.join()

    def _create_wave(self, freq):
        self.data = (c_short * self.pblength)()
        step = self.costabsize * freq / FSPB;
        for i in range(0, self.pblength):
            self.data[i] = self.costab[int(step * i) & self.costabmask]
#            self.data[i] = np.int16(28000. * np.cos((2. * 3.14159265359 * float(freq) * float(i)) / float(FSPB)))
        #print('What is the type of create_vave self.data?')
        #print(type(self.data[0]))
        fname = 'in_' + str(freq) + '.wav'
        file = wave.open(fname, 'wb')
        file.setnchannels(1)
        file.setsampwidth(2)
        file.setframerate(FSPB)
        file.writeframes(self.data)
        file.close()

    def _enable_wallsly_audio_amp(self):
        try:
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_ACCESS_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_SS_FF_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_SD_BOOST_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_SD_AMP_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_FB_SEL_FILE, '1')
            SecioIO.f_write(self.AUDIO_AMP_PATH, self.AUDIO_AMP_ACCESS_FILE, '0')
        except Exception as why:
            print('Enable amp failed: {}'.format(why))
            self.logger.warn('Enable amp failed: {}'.format(why))

    def _create_playback_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = FSPB
        self.play_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_PLAYBACK, None, b'playback', ss, None, None, None)

    def _create_record_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = FSREC
        self.rec_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_RECORD, None, b'record', ss, None, None, None)

    def _start_playback(self):
        self.playing = True
        self._play_buffer(self.data)

    def _start_record(self):
        freq = frequencies[self.idx]
        rec_data = (c_short * self.reclength)()
        self.recording = True

        while self.playing is False:
            pass

        self._record_buffer(rec_data)

        fname = 'tmp' + str(freq) + '.wav'
        file = wave.open(fname, 'wb')
        file.setnchannels(1)
        file.setsampwidth(2)
        file.setframerate(FSREC)
        file.writeframes(rec_data)
        file.close()

        #print(rec_data[1:self.reclength+1])
        #d = np.ctypeslib.as_array((ctypes.c_short * self.reclength).from_address(ctypes.addressof(self.data)))
        #d = unpack_from('<4000h', self.data)
        #x = np.ctypeslib.as_array((ctypes.c_short * self.reclength).from_address(ctypes.addressof(rec_data)))
        x = np.array(rec_data, dtype='d')
        #print('Ignore the above warning. Fixed in later python releases according to google search.')
        # convert from two's-complement to floats in the range of 1.0 to -1.0
        x = x / 32768
        # y = np.fft.rfft(x, FFT_SIZE, "ortho")   not supported in this version of NumPy
        # compute a normalized real dft
        y = np.fft.rfft(x, FFT_SIZE)/self.reclength
        y = np.absolute(y)

        upper_harmonics = int(np.floor(np.log2((FSREC / 2) / freq)))
        #print('Number of harmonics above the fundamental {} '.format(upper_harmonics))
        self.thd = self._compute_thd(y, freq, upper_harmonics)
        fni = self._find_nearest_index(freq)
        self.fundamental_power = 20 * np.log10(y[fni])
        y = np.log10(y)
        y = 20 * y
        if upper_harmonics > 0:
            #print('THD at {} Hz: {}% ...{} {} {} {} {}'.format(frequencies[0], self.thd, self.fundamental_power, self._find_nearest(y, freq*2), self._find_nearest(y, freq*3), self._find_nearest(y, freq*4), self._find_nearest(y, freq*5)))
            print('THDf {0:5d} Hz: {1:7.3f}%,  Number of upper harmonics: {2:2d}'.format(freq, self.thd, upper_harmonics))
            #print('Power fundamental {0:4d} Hz: {1:7.3f} dB'.format(freq, self.fundamental_power))
        #print('second try');
        idx = np.searchsorted(self.fftfreq, freq, side="left")
        #print('find nearest index {0:5d} {1:7.2f} {2:7.2f} {3:2d} ===  {4:7.2f} {5:7.2f} {6:7.2f} {7:7.2f} {8:7.2f} {9:7.2f} {10:7.2f} {11:7.2f} {12:7.2f} {13:7.2f}'.format(freq, self.fftfreq[idx-1], self.fftfreq[idx], idx, y[idx-6], y[idx-5], y[idx-4], y[idx-3], y[idx-2], y[idx-1], y[idx], y[idx+1], y[idx+2], y[idx+3] ))
        nearest = self._find_nearest(y, freq)
        print('Fundamental {0:5d} Hz: {1:7.3f} dB'.format(freq, nearest))

    def _play_buffer(self, buffer):
        error = c_int()
        pa_simple_flush(self.play_stream, error)
        pa_simple_write(self.play_stream, buffer, len(buffer), error)
        pa_simple_free(self.play_stream)

    def _record_buffer(self, buffer):
        error = c_int()
        pa_simple_flush(self.rec_stream, error)
        pa_simple_read(self.rec_stream, buffer, self.reclength*2, error)
        pa_simple_free(self.rec_stream)

    def _find_nearest(self, array, value):
        idx = np.searchsorted(self.fftfreq, value, side="left")
        #print('find nearest {} {}'.format(value, idx))
        if math.fabs(value - self.fftfreq[idx-1]) < math.fabs(value - self.fftfreq[idx]):
            return array[idx-1]
        else:
            return array[idx]

    def _find_nearest_index(self, value):
        idx = np.searchsorted(self.fftfreq, value, side="left")
        #print('find nearest index {} {}'.format(value, idx))
        if math.fabs(value - self.fftfreq[idx-1]) < math.fabs(value - self.fftfreq[idx]):
            #print('nearest frequency bin {0:5d} {1:7.3f}'.format(idx-1, self.fftfreq[idx-1]))
            return idx-1
        else:
            #print('nearest frequency bin {0:5d} {1:7.3f}'.format(idx, self.fftfreq[idx]))
            return idx

    def _compute_thd(self, x, freq, n_harmonics):
        #The industry accepted formula, with V1 voltage from a single frequency bin:
        #THDf(%) = 100 * SQRT[(V2^2 + V3^2 + V4^2 + ... + Vn^2)] / V1
        sum_of_squares = 0
        for i in range(2, n_harmonics+2):
            tmp = self._find_nearest(x, freq * i) ** 2
            sum_of_squares += tmp
            print('Harmonic: {0:2d}, mag: {1:9.7f}, sum: {2:9.7f}'.format(i, tmp, sum_of_squares))
        fi = self._find_nearest_index(freq)
        fundamental_power = x[fi]
        #print('base power: {0:9.7f}  {1:9.7f}'.format(fundamental_power, sum_of_squares ** .5))
        return ((sum_of_squares ** .5) / fundamental_power) * 100

def setup_gains():
    call("amixer sset 'Sin' 16", shell=True)
    call("amixer sset 'Sout' 8", shell=True)
    call("amixer sset 'Mic' 4", shell=True)
    call("amixer sset 'Master' 78", shell=True)
    call("amixer sset 'Rout' 64", shell=True)
    call("amixer sset 'Wave' 78", shell=True)
    call("amixer sset 'Sineq' 0", shell=True)
#FIXIT zl380 disable AEC, anti-howling, Sin EQ, etc.


if __name__ == "__main__":
    setup_gains()
    for i in range(0, len(frequencies)):
        wfn = 'wave_' + str(frequencies[i]) + '_hz.wav'
        at = AudioTest(wfn, i)
        time.sleep(0.5)

