# Not an interactive shell?
[[ $- == *i* ]] || return 0

if [ -x /usr/local/bin/resize ]; then
	eval `/usr/local/bin/resize`
fi
