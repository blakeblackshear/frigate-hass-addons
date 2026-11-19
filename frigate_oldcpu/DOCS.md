For a fresh install, create `config.yml` in this add-on's configuration
directory, `/addon_configs/ccab4aaf_frigate-oldcpu` in the official repository.

Before updating from 0.14.1, back up your Frigate configuration and database.
Frigate moves them to the add-on's configuration directory on first start.
Review the [0.16 upgrade notes](https://github.com/blakeblackshear/frigate/releases/tag/v0.16.0)
for changes to configuration, paths, and database compatibility.

Frigate brings realtime object detection to any camera video feed supported by FFmpeg. More detailed docs are maintained [here](https://docs.frigate.video).

## Required Dependencies

- MQTT: Frigate communicates with Home Assistant via MQTT

## Support

Please [open an issue](https://github.com/blakeblackshear/frigate/issues/new/choose) if you need support.
