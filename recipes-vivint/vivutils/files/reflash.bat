sb_loader.exe -nojump -f u-boot.imx
sb_loader.exe -trans 0x11000000 -f usb-reflash-boot.scr
sb_loader.exe -trans 0x12000000 -f uImage
sb_loader.exe -trans 0x12C00000 -f core-image-mfg-usbboot-imx6dl-slimline.ext4.gz.u-boot 
sb_loader.exe -trans 0x18000000 -f imx6dl-slimline-ldo-nofec.dtb
sb_loader.exe -exec  0x17800000 
