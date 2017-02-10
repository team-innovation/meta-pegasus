SRC_URI="ftp://ftp.netperf.org/netperf/archive/netperf-${PV}.tar.bz2 \
         file://cpu_set.patch \
         file://vfork.patch \
         file://init"

EXTRA_OECONF="--enable-demo"

PR='r0'

