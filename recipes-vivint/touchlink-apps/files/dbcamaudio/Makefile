.PHONY: clean
CC ?= ${CC}
GST_DIR ?= /usr
GST_INCLUDE_DIR = ${GST_DIR}/include/gstreamer-0.10
LIBXML_INCLUDE  = ${GST_DIR}/include/libxml2
GLIB2_INC_DIR   = ${GST_DIR}/include/glib-2.0
GLIB2_LIB_DIR   = ${GST_DIR}/lib/glib-2.0/include
SYSTEM ?= TOUCHLINK

CCOMPILER = $(CC) -fPIC -Wall -Wno-unused 
LINK = $(CC) 

EXECUTABLE =libaudio.so
OBJECTS = audio.o

LIBS = -L${GST_DIR}/lib -L ./
SYSLIBRARIES =-lgstreamer-0.10 -lgstrtp-0.10 -lgstrtsp-0.10 -lgstsdp-0.10 -lgobject-2.0 -lgthread-2.0 -lgmodule-2.0 -lglib-2.0 -lgstapp-0.10 -lgstbase-0.10

ifeq "$(SYSTEM)" "SLIMLINE" 
	OPTIONS = -c -Wall -march=armv7-a -mthumb-interwork -mfloat-abi=hard -mfpu=neon -mtune=cortex-a9 -MD -MP -fno-exceptions -MMD 
else
	OPTIONS = -c -Wall -march=armv7-a -mtune=cortex-a8 -mfpu=neon -mfloat-abi=softfp -MD -MP -fno-exceptions -MMD 
endif

CCFLAGS = $(CFLAGS) \
		-I${GLIB2_INC_DIR} \
		-I${GLIB2_LIB_DIR} \
		-I${GST_INCLUDE_DIR} \
		-I${LIBXML_INCLUDE}

OBJECTS = audio.o

default: $(EXECUTABLE)

# builds the executable itself
libaudio.so: $(OBJECTS)
	$(LINK) -shared $(OBJECTS) -o $@ $(LIBS) $(SYSLIBRARIES)

%.o: %.c
	$(CCOMPILER) $(OPTIONS) $(CCFLAGS) -D$(SYSTEM) -c $< -o $@ $(LIBS) $(SYSLIBRARIES) 

clean:
	rm -f $(OBJECTS) $(EXECUTABLE) *.d
