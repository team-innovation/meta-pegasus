include mfgkit.inc
DESCRIPTION = "Build zip files for manufacturing"

inherit meta

platform = "slimline"
rsyncexclude = "--exclude '*~' --exclude '*sly*.vbs' --exclude '*sly*.sh'"
