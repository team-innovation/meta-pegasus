# Instructions
## Flash with save/restore serial numbers (preferred)
```
cd <here>
# put the target into the fastboot mode
sudo ./uuu <archive-name>.zip
```
*Note*: **savedfiles.tar** will be downloaded from the target. It contains an archived /media/bootscript/ folder (serial numbers). Use for manual recovery if needed.
## Flash without backup
```
cd <here>
unzip <archive-name>.zip
# put the target into the fastboot mode
sudo ./uuu imx-boot image.wic.bz2
```
