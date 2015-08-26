#!/bin/sh -x
LED_SYS="/sys/bus/i2c/devices/3-0034/leds"

LED_RESTORE()
{
    cd ${LED_SYS}
    cat /tmp/temp_blink_off > l_primary_blink_off_time
    cat /tmp/temp_blink_on > l_primary_blink_on_time
    cat /tmp/temp_led_levels > l_primary_levels
    cat /tmp/temp_led > l_primary_led
    cat /tmp/temp_led_control > l_led_control
    cat /tmp/temp_led_mode > l_led_mode
}

killall wps.sh
killall wpa_supplicant
LED_RESTORE

exit 0
