SUMMARY = "Secure Socket Layer"
DESCRIPTION = "Secure Socket Layer (SSL) binary and related cryptographic tools."
HOMEPAGE = "http://www.openssl.org/"
BUGTRACKER = "http://www.openssl.org/news/vulnerabilities.html"
SECTION = "libs/network"

# "openssl | SSLeay" dual license
LICENSE = "openssl"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f9a8f968107345e0b75aa8c2ecaa7ec8"

SRC_URI = "http://www.openssl.org/source/openssl-${PV}.tar.gz \
          "

S = "${WORKDIR}/openssl-${PV}"

PACKAGECONFIG[perl] = ",,,"
inherit lib_package multilib_header ptest

EXTRA_OECONF_append_aarch64 = " no-afalgeng"
EXTRA_OECONF_append_libc-musl = " -DOPENSSL_NO_ASYNC"

do_configure () {
	os=${HOST_OS}
	case $os in
	linux-gnueabi |\
	linux-gnuspe |\
	linux-musleabi |\
	linux-muslspe |\
	linux-musl )
		os=linux
		;;
	*)
		;;
	esac
	target="$os-${HOST_ARCH}"
	case $target in
	linux-arm)
		target=linux-armv4
		;;
	linux-armeb)
		target=linux-armv4
		;;
	linux-aarch64*)
		target=linux-aarch64
		;;
	linux-sh3)
		target=linux-generic32
		;;
	linux-sh4)
		target=linux-generic32
		;;
	linux-i486)
		target=linux-elf
		;;
	linux-i586 | linux-viac3)
		target=linux-elf
		;;
	linux-i686)
		target=linux-elf
		;;
	linux-gnux32-x86_64 | linux-muslx32-x86_64 )
		target=linux-x32
		;;
	linux-gnu64-x86_64)
		target=linux-x86_64
		;;
	linux-mips)
		# specifying TARGET_CC_ARCH prevents openssl from (incorrectly) adding target architecture flags
		target="linux-mips32 ${TARGET_CC_ARCH}"
		;;
	linux-mipsel)
		target="linux-mips32 ${TARGET_CC_ARCH}"
		;;
	linux-gnun32-mips*)
		target=linux-mips64
		;;
	linux-*-mips64 | linux-mips64)
		target=linux64-mips64
		;;
	linux-*-mips64el | linux-mips64el)
		target=linux64-mips64
		;;
	linux-microblaze*|linux-nios2*)
		target=linux-generic32
		;;
	linux-powerpc)
		target=linux-ppc
		;;
	linux-powerpc64)
		target=linux-ppc64
		;;
	linux-riscv32)
		target=linux-generic32
		;;
	linux-riscv64)
		target=linux-generic64
		;;
	linux-supersparc)
		target=linux-sparcv9
		;;
	linux-sparc)
		target=linux-sparcv9
		;;
	darwin-i386)
		target=darwin-i386-cc
		;;
	esac

	useprefix=/usr/lib/openssl11
	if [ "x$useprefix" = "x" ]; then
		useprefix=/
	fi
	libdirleaf="$(echo /usr/lib/openssl11/lib | sed s:$useprefix::)"
	perl ./Configure ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS} --prefix=$useprefix --openssldir=/usr/lib/openssl11/lib/ssl-1.1 --libdir=$libdirleaf $target
}


do_compile () {
	oe_runmake depend
	oe_runmake
}

do_install () {
	oe_runmake DESTDIR="${D}" MANDIR="${mandir}" MANSUFFIX=ssl install
	oe_multilib_header openssl/opensslconf.h
}


do_install_ptest() {
	cp -r * ${D}${PTEST_PATH}

	# Putting .so files in ptest package will mess up the dependencies of the main openssl package
	# so we rename them to .so.ptest and patch the test accordingly
	mv ${D}${PTEST_PATH}/libcrypto.so ${D}${PTEST_PATH}/libcrypto.so.ptest
	mv ${D}${PTEST_PATH}/libssl.so ${D}${PTEST_PATH}/libssl.so.ptest
	sed -i 's/$target{shared_extension_simple}/".so.ptest"/' ${D}${PTEST_PATH}/test/recipes/90-test_shlibload.t
}

pkg_postinst_${PN}() {
#!/bin/sh -e
#
# We need to run ldconfig otherwise we cannot find the 
# GL library when try to use it
if [ x"$D" = "x" ]; then
    echo /usr/lib/openssl11/lib >> /etc/ld.so.conf
    echo "Running ldconfig"
    ldconfig
else
    exit 1
fi
}

PACKAGES =+ "${PN}-engines" 

FILES_${PN} += "/usr/lib/openssl11/*"
FILES_${PN}-engines = "/usr/lib/openssl11/lib/engines-1.1"
FILES_${PN}-engines-dbg = "/usr/lib/openssl11/engines-1.1/.debug"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

RDEPENDS_${PN}-ptest += "perl-module-file-spec-functions bash python"

# For target side versions of openssl enable support for OCF Linux driver
# if they are available.
BBCLASSEXTEND = "native nativesdk"
DEPENDS += "cryptodev-linux"

PR="r3"

CFLAG += "-DHAVE_CRYPTODEV -DUSE_CRYPTODEV_DIGESTS"

LIC_FILES_CHKSUM = "file://LICENSE;md5=d57d511030c9d66ef5f5966bee5a7eff"

SRC_URI += "file://find.pl;subdir=${BP}/util/ \
            file://run-ptest \
            file://openssl-c_rehash.sh \
	    file://0001-Take-linking-flags-from-LDFLAGS-env-var.patch \
           "
SRC_URI[md5sum] = "9495126aafd2659d357ea66a969c3fe1"
SRC_URI[sha256sum] = "ebbfc844a8c8cc0ea5dc10b86c9ce97f401837f3fa08c17b2cdadc118253cf99"

# The crypto_use_bigint patch means that perl's bignum module needs to be
# installed, but some distributions (for example Fedora 23) don't ship it by
# default.  As the resulting error is very misleading check for bignum before
# building.
do_configure_prepend() {
	if ! perl -Mbigint -e true; then
		bbfatal "The perl module 'bignum' was not found but this is required to build openssl.  Please install this module (often packaged as perl-bignum) and re-run bitbake."
	fi
}

INSANE_SKIP_${PN} = "dev-so staticdev"
