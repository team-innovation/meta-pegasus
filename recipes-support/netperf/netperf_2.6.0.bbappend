SRC_URI="http://${UPDATESENG}/downloads/netperf-${PV}.tar.bz2 \
         file://cpu_set.patch \
         file://vfork.patch \
         file://init"

EXTRA_OECONF="--enable-demo"

PR='r1'

