#MACHINE_HWCODECS_remove += "va-intel gstreamer1.0-vaapi"
MACHINE_HWCODECS_append += "media-driver msdk gstreamer-msdk"

PACKAGECONFIG_append_pn-ffmpeg += "vaapi"