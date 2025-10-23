do_install:append() {
    cd ${D}/${KERNEL_IMAGEDEST}/ 
    md5sum ${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${S}/${KERNEL_IMAGETYPE}.md5sum
    cd -
    install ${S}/${KERNEL_IMAGETYPE}.md5sum ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum
}

FILES:${KERNEL_PACKAGE_NAME}-base += "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum"
