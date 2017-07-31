SRCREV = "${AUTOREV}"
GIT_UBOOT_SERVER ?= "${GIT_SERVER}"
GIT_UBOOT_BRANCH ?= "develop"
SRC_URI = "git://${GIT_UBOOT_SERVER}/uboot-imx;protocol=ssh;branch=${GIT_UBOOT_BRANCH}"
