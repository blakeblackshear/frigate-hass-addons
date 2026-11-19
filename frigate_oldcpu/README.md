# Home Assistant Add-on: Frigate (For old x86_64 CPUs)

Please reference the [release notes](https://github.com/blakeblackshear/frigate/releases) for breaking changes.

![Supports amd64 Architecture][amd64-shield]

NVR with realtime local object detection for IP cameras.

This add-on uses Frigate 0.16.4, the release recommended for CPUs without AVX in
the [Frigate support discussion](https://github.com/blakeblackshear/frigate/discussions/22381).
Hardware compatibility depends on the detector and CPU. The previous add-on
built custom TensorFlow Lite and Coral libraries for older CPUs.

You must create a config file as `config.yml` in your add-on configuration folder.

[Documentation](https://docs.frigate.video)

[amd64-shield]: https://img.shields.io/badge/amd64-yes-green.svg
