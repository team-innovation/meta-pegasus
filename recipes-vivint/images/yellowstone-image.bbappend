# Remove 64-bit opengl package to run yellowstone image as 32-bit
PACKAGECONFIG:remove:pn-lib32-gstreamer1.0-plugins-base = "opengl egl glx gles2"
