SRCREV = "${AUTOREV}"
GIT_SERVER_UBOOT ?= "${GIT_SERVER}"
GIT_UBOOT_BRANCH ?= "develop"
SRC_URI = "git://${GIT_SERVER_UBOOT}/uboot-imx;protocol=ssh;branch=${GIT_UBOOT_BRANCH}"
