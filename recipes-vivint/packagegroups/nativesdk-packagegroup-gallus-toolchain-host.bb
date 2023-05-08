# Copyright (C) 2020 Vivint, Inc.
DESCRIPTION = "packages for slimline native sdk" 
LICENSE = "MIT"

PR = "r3"
inherit nativesdk packagegroup

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS_${PN} += " \
	nativesdk-packagegroup-sdk-host \
	nativesdk-python3-pkgconfig \
	nativesdk-python3-compile \
	nativesdk-python3-setuptools \
	nativesdk-breakpad-dev \
	nativesdk-breakpad-staticdev \
	nativesdk-variant-lite-dev \
	nativesdk-taocpp-json-dev \
	nativesdk-rapidjson-dev \
	nativesdk-python3-setuptools \
	nativesdk-python3-aiodns \
	nativesdk-python3-aioconsole \
	nativesdk-python3-aiohttp \
	nativesdk-python3-async-generator \
	nativesdk-python3-async-timeout \
	nativesdk-python3-atomicwrites \
	nativesdk-python3-attrs \
	nativesdk-python3-bcrypt \
	nativesdk-python3-bson \
	nativesdk-python3-cachetools \
	nativesdk-python3-cchardet \
	nativesdk-python3-cherrypy \
	nativesdk-python3-cstruct \
	nativesdk-python3-coverage \
	nativesdk-python3-certifi \
	nativesdk-python3-cython \
	nativesdk-python3-dateutil \
	nativesdk-python3-distutils \
	nativesdk-python3-gnupg \
	nativesdk-python3-grpcio \
	nativesdk-python3-pycares \
	nativesdk-python3-multidict \
	nativesdk-python3-terminaltables \
	nativesdk-python3-idna \
	nativesdk-python3-idna-ssl \
	nativesdk-python3-grpcio-tools \
	nativesdk-python3-googleapis-common-protos \
	nativesdk-python3-intelhex \
	nativesdk-python3-jaracocollections \
	nativesdk-python3-jinja2 \
	nativesdk-python3-jsonschema \
	nativesdk-python3-markupsafe \
	nativesdk-python3-mixpanel \
	nativesdk-python3-more-itertools \
	nativesdk-python3-msgpack \
	nativesdk-python3 \
	nativesdk-python3-pathlib2 \
	nativesdk-python3-paho-mqtt \
	nativesdk-python3-pexpect \
	nativesdk-python3-phue \
	nativesdk-python3-pluggy \
	nativesdk-python3-portend \
	nativesdk-python3-protobuf \
	nativesdk-protobuf-compiler \
	nativesdk-python3-ptyprocess \
	nativesdk-python3-psutil \
	nativesdk-python3-pyalsaaudio \
	nativesdk-python3-pycrypto \
	nativesdk-python3-pyftpdlib \
	nativesdk-python3-pytest \
	nativesdk-python3-pytest-asyncio \
	nativesdk-python3-pytest-cov \
	nativesdk-python3-pyinotify \
	nativesdk-python3-pyserial \
	nativesdk-python3-pysodium \
	nativesdk-python3-py \
	nativesdk-python3-pytz \
	nativesdk-python3-requests \
	nativesdk-python3-requests-toolbelt \
	nativesdk-python3-easydict \
	nativesdk-python3-six \
	nativesdk-python3-setproctitle \
	nativesdk-python3-soco \
	nativesdk-python3-sparsedict \
	nativesdk-python3-sentry-sdk \
	nativesdk-python3-toolz \
	nativesdk-python3-transitions \
	nativesdk-python3-uvloop \
	nativesdk-python3-wheel \
	nativesdk-python3-urllib3 \
	nativesdk-python3-xmltodict \
	nativesdk-python3-yarl \
	nativesdk-python3-pyopenssl \
	nativesdk-python3-cryptography \
	nativesdk-python3-cffi \
	nativesdk-python3-asn1crypto \
	nativesdk-python3-pycparser \
	nativesdk-qtdeclarative-tools \
"
