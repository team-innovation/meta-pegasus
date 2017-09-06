require qt5.inc

DEPENDS += "qtscript qtsvg qtxmlpatterns"

PACKAGECONFIG ??= "webkit"
PACKAGECONFIG[webkit] = ",,qtwebkit"

EXTRA_QMAKEVARS_PRE += "${@bb.utils.contains('PACKAGECONFIG', 'webkit', '', 'CONFIG+=noqtwebkit', d)}"

do_configure_prepend() {
    sed -i 's#^qtHaveModule(webkitwidgets):#qtHaveModule(webkitwidgets):!contains(CONFIG, noqtwebkit):#g' ${S}/src/imports/imports.pro
}
# qttools

# There are no LGPLv3-only licensed files in this component.
# There are no GPLv2 licensed files in this component.
# Note that some files are LGPL-2.1 only without Digia-Qt-LGPL-Exception-1.1.
LIC_FILES_CHKSUM += "file://LICENSE.GPLv2;md5=e782f55badfa137e5e59c330f12cc8ed"
