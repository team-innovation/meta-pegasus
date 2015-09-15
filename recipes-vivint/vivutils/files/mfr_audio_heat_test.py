#!/usr/bin/env python3
#
# Copyright 2014 Vivint Innovation
#
#
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
    import ctypes
    from subprocess import call
except ImportError:
    print('ERROR: numpy library not installed')

__author__ = 'mark'

#wave_files = ['wave_1000_hz.wav', 'wave_900_hz.wav', 'wave_1800_hz.wav', 'wave_3600_hz.wav']

class AudioTest:
    def __init__(self, audioFile):
        #print('Testing at frequency {}'.format(frequencies[idx]))
        self.audioFile = audioFile
        self.play_stream = None
        self.frames = 0
        self.playThread = threading.Thread(name='PlayThread', target=self._start_playback)
        self.playThread.start()
        self.playThread.join()

    def _create_playback_stream(self):
        ss = pa_sample_spec()
        ss.format = PA_SAMPLE_S16LE
        ss.channels = 1
        ss.rate = 8000
        self.play_stream = pa_simple_new(None, b'AudioTest', PA_STREAM_PLAYBACK, None, b'playback', ss, None, None, None)

    def _start_playback(self):
        self._create_playback_stream()
        file = wave.open(self.audioFile, 'rb')
        self.frames = file.getnframes()
        data = file.readframes(self.frames)

        while True:
            self._play_buffer(data)

    def _play_buffer(self, buffer):
        error = c_int()
        pa_simple_write(self.play_stream, buffer, len(buffer), error)

def setup_gains():
    call("amixer sset 'DSP Max Mic Gain' 64", shell=True)
    call("amixer sset 'Mic Gain' 64", shell=True)
    call("amixer sset 'Playback Gain' 65", shell=True)

if __name__ == "__main__":
    setup_gains()
    at = AudioTest('wave_1000_hz_half_mag.wav')





