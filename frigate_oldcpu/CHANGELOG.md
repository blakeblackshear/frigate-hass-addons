### 1.13.1
- Update to 0.8.4

### 1.12.1
- Use new tmpfs config option
- Update to 0.8.3
- Update device config to avoid protection mode to be disabled for Coral access
- Add additional devices in hopes that protection mode can be enabled on RPi with hwaccel
- Update to avoid deprecation warning for new devices config
- Fixes for supervisor 2021.02.5
- Configuration moved to homeassistant config directory as `frigate.yml`

### 1.9.7

- Alter bazel compilation options to build libedgetpu.so for target platform

### 1.9.6

- Downgrade bazel from 4.0.0 to 3.20 to build libedgetpu.so

### 1.9.5

- Actually rebuild libedgetpu.so on target platform

### 1.9.4

- Update devices to new format to remove deprecation warning

### 1.9.3

- Pin numpy to the same 1.19.4 version that frigate pins, see https://github.com/blakeblackshear/frigate/blob/v0.8.1/docker/Dockerfile.wheels#L27

### 1.9.2

- Move ARG BUILD_ARCH=amd64 to top of Dockerfile

### 1.9.1

- Dynamically build tensorflow tflite wheel on target platform and use official blakeblackshear/frigate:0.8.1 as base image

### 1.2

- 0.8.0 Beta 3

### 1.1

- 0.8.0 Beta 2

### 1.0

- 0.8.0 Beta 1
