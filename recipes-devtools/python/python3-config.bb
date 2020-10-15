DESCRIPTION = "Get compile information for python cross build"
LICENSE = "CLOSED"

inherit setuptools3

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() { 
	install -d ${D}${bindir}
	cat <<- EOF > ${D}${bindir}/python3-config-baked
		#/bin/sh
		case "\$1" in
			--includes) echo "$(python3-config --includes)" ;;
			--extension-suffix) echo "$(python3-config --extension-suffix)" ;;
			--ldflags) echo "$(python3-config --ldflags)" ;;
			--cflags) echo "$(python3-config --cflags)" ;;
			--libs) echo "$(python3-config --libs)" ;;
			--prefix) echo "$(python3-config --prefix)" ;;
			--exec-prefix) echo "$(python3-config --exec-prefix)" ;;
			--abiflags) echo "$(python3-config --abiflags)" ;;
			--configdir) echo "$(python3-config --configdir)" ;;
			*) echo "Bad args \$1"; exit 1 ;;
		esac

		exit 0
EOF
	sed -i -e "s:${WORKDIR}/recipe-sysroot-native::g" ${D}${bindir}/python3-config-baked
	sed -i -e "s:${WORKDIR}/recipe-sysroot:\$SDKTARGETSYSROOT:g" ${D}${bindir}/python3-config-baked
	sed -i -e "s:=\$SDKTARGETSYSROOT=:==:g" ${D}${bindir}/python3-config-baked
	sed -i -e "s:${TMPDIR}/work/${MULTIMACH_TARGET_SYS}::g" ${D}${bindir}/python3-config-baked
	chmod +x ${D}${bindir}/python3-config-baked
}

FILES_${PN} = " ${bindir}/python3-config-baked"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
