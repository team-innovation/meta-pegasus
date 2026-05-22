# The Synaptics base image hard-requires ModemManager, which conflicts with ofono in Debian/APT.
# Since we are keeping the '3g' DISTRO_FEATURE for parity, we must remove the conflicting
# ofono dependency from the base 3G packagegroup.

RDEPENDS:packagegroup-base-3g:remove = "ofono"
