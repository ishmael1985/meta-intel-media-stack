SUMMARY = "Intel Media Driver for VAAPI"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

SECTION = "x11"
LICENSE = "Intel"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0"

SRC_URI = "git://github.com/intel/media-driver.git"
SRCREV = "2eab2e248c5787ceaebd76be791e60e28e56fbf3"

SRCREV_mediadriver ?= "2eab2e248c5787ceaebd76be791e60e28e56fbf3"
SRCREV_gmmlib ?= "b1451bbe4c506f17ddc819f12b4b448fc08698c5"

SRC_URI = "git://github.com/intel/media-driver.git;name=mediadriver;destsuffix=media-driver \
           git://github.com/intel/gmmlib.git;name=gmmlib;destsuffix=gmmlib"

SRC_URI += "file://0001-media-driver-disable-tests.patch"
SRC_URI += "file://0002-gmmlib-disable-tests.patch"

S = "${WORKDIR}/media-driver"

DEPENDS += "libva libpciaccess"

inherit cmake pkgconfig

# OECMAKE_C_FLAGS = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} ${CFLAGS} -i/home/pknopf/git/x3/abrarecipes/build/tmp/work/corei7-64-poky-linux/media-driver/git-r0/recipe-sysroot/usr/include"
# OECMAKE_CXX_FLAGS = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} ${CXXFLAGS} -i/home/pknopf/git/x3/abrarecipes/build/tmp/work/corei7-64-poky-linux/media-driver/git-r0/recipe-sysroot/usr/include"

EXTRA_OECMAKE += " \
	  -DMEDIA_VERSION=2.0.0 \
      -DBUILD_ALONG_WITH_CMRTLIB=1 \
      -DBS_DIR_GMMLIB=`pwd`/../gmmlib/Source/GmmLib/ \
      -DBS_DIR_COMMON=`pwd`/../gmmlib/Source/Common/ \
      -DBS_DIR_INC=`pwd`/../gmmlib/Source/inc/ \
      -DBS_DIR_MEDIA=`pwd`/../media-driver \
        "

do_patch() {
    cd media-driver
    patch -p1 < ${WORKDIR}/0001-media-driver-disable-tests.patch
    cd ../gmmlib
    patch -p1 < ${WORKDIR}/0002-gmmlib-disable-tests.patch
}

FILES_${PN} += "${libdir} ${libdir}/dir"

INSANE_SKIP_${PN} = "ldflags package_qa_hash_style already-stripped"