#!/bin/sh

# Disable framebuffer console cursor
echo -e '\033[?17;0;0c' > /dev/tty0

# Disable framebuffer timeout
echo -e '\033[9;0]' > /dev/tty1

export QT_QPA_EGLFS_PHYSICAL_HEIGHT=90
export QT_QPA_EGLFS_PHYSICAL_WIDTH=153
export QT_QPA_EGLFS_WIDTH=1024
export QT_QPA_EGLFS_HEIGHT=600
export QT_QPA_EGLFS_DEPTH=32
export QT_QPA_GENERIC_PLUGINS=tslib:/dev/input/event0
export QT_QPA_EGLFS_HIDECURSOR=1
export QT_QPA_PLATFORM=eglfs
