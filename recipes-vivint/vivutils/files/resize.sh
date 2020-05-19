# Not an interactive shell?
[[ $- == *i* ]] || return 0

if [ -x /usr/bin/resize ]; then
	eval `/usr/bin/resize`
fi
