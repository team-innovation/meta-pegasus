# Alias: Tell BitBake to build this when we type 'bitbake pegasus-image'
# PROVIDES += "pegasus-image"

# Rename: Force the final output files to be 'pegasus-image-'
# IMAGE_BASENAME = "pegasus-image"

IMAGE_FEATURES += " \
    package-management \
    splash \
    hwcodecs \
    ssh-server-openssh \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-full-cmdline \
    packagegroup-tools-bluetooth \
    packagegroup-pegasus \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'weston-xwayland xterm', '', d)} \
"

# Override the default 3G dependencies to remove ofono
# This prevents the APT conflict with Synaptics' default ModemManager

RDEPENDS:packagegroup-base-3g = "\
    kernel-module-cdc-acm \
    kernel-module-cdc-wdm \
"

# =========================================================================
# Ported NXP Packages -> Synaptics Equivalent (Target Image Packages)
# =========================================================================

# System & Utilities
IMAGE_INSTALL:append = " \
    alsa-tools \
    psplash \
    keyutils \
    lvm2 \
    cryptodev-linux \
    cryptodev-module \
    libaio \
    dbus-broker \
    dpkg \
"

# Graphics, UI, and Qt5 Elements
IMAGE_INSTALL:append = " \
    gtk+3 \
    qtquick3d \
    qtsvg \
    adwaita-icon-theme \
    hicolor-icon-theme \
    spirv-tools \
    p11-kit \
    libepoxy \
    at-spi2-core \
"

# =========================================================================
# Remaining Ported Packages (Rows 493-569 ONLY - Filtered)
# =========================================================================
IMAGE_INSTALL:append = " \
    python3-cryptography \
    python3-pyopenssl \
    python3-pretend \
    python3-hypothesis \
    python3-bcrypt \
    python3-pytz \
    python3-iso8601 \
    python3-pytest-subtests \
    python3-psutil \
    python3-cryptography-vectors \
    python3-cffi \
"

# =========================================================================
# Final Pending List (Filtered for valid target packages)
# =========================================================================
IMAGE_INSTALL:append = " \
    python3-mako \
    python3-requests \
    python3-pygments \
    python3-markupsafe \
    wireless-tools \
    python3-certifi \
    python3-chardet \
    python3-idna \
    python3-urllib3 \
    python3-pysocks \
"

######################################
DEV_PACKAGES = " \
    python3-pip \
    gstreamer1.0-python \
    python3-venv \
    packagegroup-core-buildessential \
    cmake \
    git \
    libgomp \
    libgomp-dev \
    sqlite3 \
    python3-sqlite3 \
"

IMAGE_INSTALL:append:sl2619 = " \
    ${DEV_PACKAGES} \
    python3 \
    python3-numpy \
    python3-ensurepip \
    python3-pyqt5 \
    python3-pexpect \
"
