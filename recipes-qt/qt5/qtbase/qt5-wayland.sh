#!/bin/sh

# Disable framebuffer timeout
echo -e '\033[9;0]' > /dev/tty1

export QT_QPA_PLATFORM=wayland
export QT_WAYLAND_DISABLE_WINDOWDECORATION=1
