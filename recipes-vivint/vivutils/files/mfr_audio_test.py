#!/usr/bin/env python3
#
# Copyright 2014 Vivint Innovation
#
# Used by pa_platform.py when starting to play audio.  Similar to the audio_thread useind in alsa_platform.py
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
except ImportError:
    print('ERROR: numpy library not installed')

__author__ = 'mark'

# frequency bins for 450, 900, 1800 and 3600 Hz given a 4096 pt FFT @ 8 kHz
freq_bin = [230, 461, 922, 1843]
frequencies = [450, 900, 1800, 3600]
wave_files = ['wave_450_hz.wav', 'wave_900_hz.wav', 'wave_1800_hz.wav', 'wave_3600_hz.wav']

class AudioTest:
    def __init__(self, audioFile, idx):
        #print('Testing at frequency {}'.format(frequencies[idx]))
        self.idx = idx
        self.audioFile = audioFile
        self.playing = False
        self.play_stream = None
        self.recording = False
        self.rec_stream = None
        self.frames = 0
        self.fftfreq = np.fft.fftfreq(4096) * 8000
        self.fftfreq = self.fftfreq[0:2048]
        #pprint(self.fftfreq)
        #pprint(np.amax(self.fftfreq))
        #pprint(np.searchsorted(self.fftfreq, 2, side='left'))
        self.playThread = threading.Thread(name='PlayThread', target=self._start_playback)
        self.recordThread = threading.Thread(name='RecordThread', target=self._start_record)
        self.recordThread.start()
        self.playThread.start()
        self.playThread.join()
        self.recordThread.join()

    def _create_playback_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = 8000
        self.play_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_PLAYBACK, None, b'playback', ss, None, None, None)

    def _create_record_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = 8000
        self.rec_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_RECORD, None, b'playback', ss, None, None, None)

    def _start_playback(self):
        self._create_playback_stream()
        file = wave.open(self.audioFile, 'rb')
        self.frames = file.getnframes()
        data = file.readframes(self.frames)

        self.playing = True
        self._play_buffer(data)

    def _start_record(self):
        self._create_record_stream()
        freq = frequencies[self.idx]

        # wait for playback to start
        while self.frames == 0:
            pass

        rec_data = (c_short * self.frames)()
        self.recording = True
        self._record_buffer(rec_data)

        x = np.ctypeslib.as_array((ctypes.c_short * self.frames).from_address(ctypes.addressof(rec_data)))
        x = x / 32768
        y = np.fft.rfft(x, 4096)/self.frames

        y = np.absolute(y)

        if self.idx == 0:
            self.thd = self._compute_thd(y, freq)
            fi = self._find_nearest_index(freq)
            self.base_power = 20 * np.log10(y[fi] + y[fi+1])

        y = np.log10(y)
        y = 20 * y
        if self.idx == 0:
            #print('THD at {} Hz: {}% ...{} {} {} {} {}'.format(frequencies[0], self.thd, self._find_nearest(y, freq), self._find_nearest(y, freq*2), self._find_nearest(y, freq*3), self._find_nearest(y, freq*4), self._find_nearest(y, freq*5)))
            print('THD at {} Hz: {}%'.format(frequencies[0], self.thd))
            print('Power at {} Hz: {} dB'.format(freq, self.base_power))
        else:
            print('Power at {} Hz: {} dB'.format(freq, self._find_nearest(y, freq)))


    def _play_buffer(self, buffer):
        error = c_int()
        pa_simple_write(self.play_stream, buffer, len(buffer), error)

    def _record_buffer(self, buffer):
        error = c_int()
        pa_simple_read(self.rec_stream, buffer, self.frames, error)

    def _find_nearest(self, array, value):
        idx = np.searchsorted(self.fftfreq, value, side="left")
        #print('find nearest {} {}'.format(value, idx))
        if math.fabs(value - self.fftfreq[idx-1]) < math.fabs(value - self.fftfreq[idx]):
            return array[idx-1]
        else:
            return array[idx]

    def _find_nearest_index(self,  value):
        idx = np.searchsorted(self.fftfreq, value, side="left")
        #print('find nearest {} {}'.format(value, idx))
        if math.fabs(value - self.fftfreq[idx-1]) < math.fabs(value - self.fftfreq[idx]):
            return idx-1
        else:
            return idx

    def _compute_thd(self, x, freq, n_harmonics=7):
        sum_of_squares = 0
        for i in range(2, n_harmonics+1):
            sum_of_squares += self._find_nearest(x, freq * i) ** 2
        fi = self._find_nearest_index(freq)
        base_power = x[fi] + x[fi+1]
        return math.sqrt(sum_of_squares) / base_power * 100


def setup_gains():
    call("amixer sset 'DSP Max Mic Gain' 64", shell=True)
    call("amixer sset 'Mic Gain' 64", shell=True)
    call("amixer sset 'Playback Gain' 65", shell=True)

if __name__ == "__main__":
    setup_gains()
    for i in range(0, len(wave_files)):
    #for i in range(0, 1):
        at = AudioTest(wave_files[i], i)
        time.sleep(0.5)





