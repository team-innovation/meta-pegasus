do_compile_prepend() {
	grep -v "CONFIG_NICE is not" .config > ._config
	mv ._config .config
	grep -q "CONFIG_NICE=yes" .config && return
	echo "CONFIG_NICE=yes" >> .config
}
