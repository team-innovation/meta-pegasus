# we have our own config/init method so remove the stock initscript
# and put it in its own package
FILES_${PN}_remove  = "${sysconfdir}/init.d/ntpd"
PACKAGES =+ "${PN}-initscript"
FILES_${PN}-initscript = "${sysconfdir}/init.d/ntpd"
