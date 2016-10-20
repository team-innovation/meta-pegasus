PROVIDES_remove_mx6sx = "virtual/libgl"

PACKAGECONFIG[x11] = "--enable-glx,--disable-glx,${X11_DEPS}"

do_install_append_mx6 () {
        rm -f ${D}${libdir}/libGL.*
        rm -rf ${D}${includedir}/GL/gl.h
        rm -rf ${D}${includedir}/GL/glext.h
}
