# Instructions
## Flash with save/restore serial numbers (preferred)
```
# <here> in Yocto is under the deploy deirectory, examples:
#     * build-gallus/tmp/deploy/images/imx8mm-gallus/uuu/
#     * build-nene/tmp/deploy/images/imx8mn-nene/uuu/
# <here> in general - a directory with the zip archive for flashing and the uuu binary.
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
