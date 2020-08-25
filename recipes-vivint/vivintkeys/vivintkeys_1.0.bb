DESCRIPTION = "Deploys gpg keys and build the database"
LICENSE = "CLOSED"

SRC_URI += " \
    file://buildergpg.asc \
"

DEPENDS += "gnupg-native"
RDEPENDS_${PN} += "gnupg"

do_configure[noexec] = "1"

do_compile(){
    mkdir -p ${WORKDIR}/build
    
    # Import the public key
    gpg --homedir ${WORKDIR}/build --batch --import ${WORKDIR}/buildergpg.asc
    
    # Set the trust or the encryption function will report the key as unusable
    (echo 5; echo y; echo save) | gpg --homedir ${WORKDIR}/build --command-fd 0 --no-tty --no-greeting -q --edit-key \
        "$(gpg --list-packets ${WORKDIR}/buildergpg.asc | awk '$1=="keyid:"{print$2;exit}')" trust
}

do_install(){
    install -d ${D}/home/    
    install -d ${D}/home/vivint/
    install -d -m 0700 ${D}/home/vivint/.gnupg/
    install -m 0644 ${WORKDIR}/build/pubring.kbx ${D}/home/vivint/.gnupg/
    install -m 0600 ${WORKDIR}/build/trustdb.gpg ${D}/home/vivint/.gnupg/
}

FILES_${PN} += " \
    /home/vivint/.gnupg/* \
"
