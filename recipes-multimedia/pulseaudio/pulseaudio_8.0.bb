require pulseaudio-8.0.inc

SRC_URI = "http://freedesktop.org/software/pulseaudio/releases/${BP}.tar.xz \
           file://0001-padsp-Make-it-compile-on-musl.patch \
           file://0001-client-conf-Add-allow-autospawn-for-root.patch \
           file://volatiles.04_pulse \
           file://0002-alsa-bluetooth-fail-if-user-requested-profile-doesn-.patch \
"

SRC_URI[md5sum] = "8678442ba0bb4b4c33ac6f62542962df"
SRC_URI[sha256sum] = "690eefe28633466cfd1ab9d85ebfa9376f6b622deec6bfee5091ac9737cd1989"

do_compile_prepend() {
    mkdir -p ${S}/libltdl
    cp ${STAGING_LIBDIR}/libltdl* ${S}/libltdl
}

