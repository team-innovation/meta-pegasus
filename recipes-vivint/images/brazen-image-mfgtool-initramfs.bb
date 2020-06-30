# Based on
#   fsl-image-mfgtool-initramfs.bb
#   Copyright (C) 2014 O.S. Systems Software LTDA.

DESCRIPTION = "Small image to be used with Manufacturing Tool \
(mfg-tool) in a production environment."

LICENSE = "MIT"

inherit mfgtool-initramfs-image

# regular u-boot should work fine

DEPENDS_remove = "u-boot-mfgtool"
RDEPENDS_${PN} += "u-boot-slimline u-boot-brazen u-boot-script-slimline"
