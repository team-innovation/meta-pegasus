include mfgkit.inc
DESCRIPTION = "Build zip files for manufacturing"

inherit meta

platform = "sly"
rsyncexclude = "--exclude '*~' --exclude 'slimline*.vbs'"
