Frigate brings realtime object detection to any camera video feed supported by ffmpeg. More detailed docs are maintained [here](https://github.com/blakeblackshear/frigate).

## Required Dependencies
- MQTT: Frigate communicates via MQTT

## Recommended Configuration
- **Google Coral**: It is strongly recommended to use a Google Coral, but Frigate will fall back to CPU in the event one is not found. Offloading tensorflow to the Google Coral is an order of magnitude faster and will reduce your CPU load dramatically.
- **Resolution**: Choose a camera resolution where the smallest object you want to detect barely fits inside a 300x300px square. The model used by Frigate is trained on 300x300px images, so you will get worse performance and no improvement in accuracy by using a larger resolution since Frigate resizes the area where it is looking for objects to 300x300 anyway.
- **FPS**: 5 frames per second should be adequate. Higher frame rates will require more CPU usage without improving detections or accuracy.
- **Hardware Acceleration**: Make sure you configure the `hwaccel_args` for your hardware. They provide a significant reduction in CPU usage if they are available.

## Hardware Acceleration
Frigate works on Raspberry Pi 3b/4 and x86 machines. It is recommended to update your configuration to enable hardware accelerated decoding in ffmpeg. Depending on your system, these parameters may not be compatible.

Raspberry Pi 3/4 (32-bit OS):
```yaml
ffmpeg:
  hwaccel_args:
    - -c:v
    - h264_mmal
```

Raspberry Pi 3/4 (64-bit OS)
```yaml
ffmpeg:
  hwaccel_args:
    - -c:v
    - h264_v4l2m2m
```

Intel-based CPUs (<10th Generation) via Quicksync (https://trac.ffmpeg.org/wiki/Hardware/QuickSync)
```yaml
ffmpeg:
  hwaccel_args:
    - -hwaccel
    - vaapi
    - -hwaccel_device
    - /dev/dri/renderD128
    - -hwaccel_output_format
    - yuv420p
```

Intel-based CPUs (>=10th Generation) via Quicksync (https://trac.ffmpeg.org/wiki/Hardware/QuickSync)
**Note:** You also need to set `LIBVA_DRIVER_NAME=iHD` as an environment variable on the container.
```yaml
ffmpeg:
  hwaccel_args:
    - -hwaccel
    - vaapi
    - -hwaccel_device
    - /dev/dri/renderD128
```

## Support
Please [open an issue](https://github.com/blakeblackshear/frigate/issues/new/choose) if you need support.