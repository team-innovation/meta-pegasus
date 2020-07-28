include mfgkit.inc
DESCRIPTION = "Build zip files for manufacturing"

inherit meta

DEPENDS += "brazen-image-mfgtool-initramfs brazen-mfg-image"

platform = "brazen"
rsyncexclude = "--exclude '*~' --exclude 'slimline*.vbs' --exclude 'slimline-reflash*.sh' --exclude '*sly*.vbs'"
