This addon creates a proxy to a Frigate server run separately from Home Assistant so that you can have the benefit of access in the sidebar without running Frigate as an addon.

Note that this addon does not run Frigate itself.

## Configuration

### Option: `server`

The `server` option sets the address of the Frigate server.

This must be in the format `host:port`. The following are valid examples:

- `frigate.local:5000`
- `192.168.0.101:5000`

## Required Dependencies

- Network access to running Frigate server

## Support

Please [open an issue](https://github.com/blakeblackshear/frigate/issues/new/choose) if you need support.
