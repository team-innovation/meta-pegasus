# Memory address to load/boot kernel
# setenv loadaddr 0x40400000
# setenv fdt_addr 0x43000000

# t1 Test from check_update
setenv t1 'if test ${test_state} = test_update; then
	echo "Test Update..." ;
	
	setenv bootcount 1 ;
	setenv test_state ;	
else	
	echo "Normal Boot..." ;
	
	setenv bootcount 0 ;
fi'

# t2 Test from check_update
setenv t2 'if test ${bootcount} = 1 ; then
	echo "Failed Test Boot..." ;
	setenv bootcount 0 ;
	setenv test_state "test_restored" ;
	if test ${bootnum} = 2; then
		echo "Boot number reset from "${bootnum}" to 1" ;
		setenv bootnum 1 ;
	else
		echo "Boot number reset from "${bootnum}" to 2" ;
		setenv bootnum 2 ;
	fi ;
	setenv msg 't2:Failed:'${bootnum}':'${bootcount} ;
else
	echo "What? We should never get here!!!!, we assume a Normal Boot..." ;
	setenv bootcount 0 ;
	setenv msg 't2:Never:'${bootnum}':'${bootcount} ;
fi'

# check_update
setenv check_update 'if test -z ${bootcount} || test ${bootcount} = 0; then
	echo "Check for test_update..."
	run t1
else
	echo "Check for test_update failed..."
	run t2
fi;'

# clean up env check scripts
setenv check_cleanup 'setenv check_update ; setenv t1 ; setenv t2; setenv check_cleanup; saveenv'

# Run test scripts and clean up
#setenv bootdelay '-2'
run check_update
run check_cleanup

# Setup script to select boot partition based on bootnum variable, default is the first partition
# partition 2 is the first partition, 3 is the second.
setenv setup_boot 'if test ${bootnum} = 2; then
	setenv mmcroot /dev/mmcblk2p3 rootwait rw loglevel=3 vt.global_cursor_default=0 ${extra_console_parameters};
	setenv mmclinuxpart 3 ;
else
	setenv mmcroot /dev/mmcblk2p2 rootwait rw loglevel=3 vt.global_cursor_default=0 ${extra_console_parameters};
	setenv mmclinuxpart 2 ;
fi;'

# Run setup boot script
run setup_boot

# Setup bootargs
run mmcargs

setenv image Image
setenv fdt_file imx8mn-evk.dtb

# Load kernel and device-tree from mmc card and boot
ext4load mmc ${mmcdev}:${mmclinuxpart} ${loadaddr} /boot/${image}
ext4load mmc ${mmcdev}:${mmclinuxpart} ${fdt_addr} /boot/${fdt_file}
booti ${loadaddr} - ${fdt_addr};
