This addon creates a proxy to a Frigate server run separately from Home Assistant so that you can have the benefit of access in the sidebar without running Frigate as an addon.

Note that this addon does not run Frigate itself.

## Configuration

### Option: `server`

The `server` option sets the address of the Frigate server.

This must be in the format `http[s]://host:port`. The following are valid examples:

- `http://frigate.local:5000`
- `http://192.168.0.101:5000`
- `https://192.168.0.101:443`

### Option: `name` (Optional)

Optional name for the main instance (e.g. "Main Frigate") (used for landing page when using multiple instances)

### Option: `description` (Optional)

Optional description for the main instance.  (used for landing page when using multiple instances)

### Option: `default_path` (Optional)

Optional path to redirect to when accessing the main instance's root. For example, setting this to `review` will redirect `/` to `/review`.

### Option: `proxy_pass_host`

Determines whether we should pass the host we're running on (for example,
`homeassistant.local`) to the server we're proxying to. In general, you probably
want this to be set to `true`.

Set to `false` if the server needs to receive the host of the frigate instance
(not the host Home Assistant or this addon are running on). This might be the case
if your frigate instance is behind an SSL proxy (like Traefik or Caddy), which
needs to receive the frigate host in order to route the request correctly.

### Option: `proxy_pass_real_ip`

Determines whether we should pass the client's real IP address to the server we're proxying to. In general, you probably
want this to be set to `true`.

Set to `false` if you need to know the request is coming from the HA IP. This might be the case if your frigate instance is behind a proxy which only allows specific IPs to connect.

### Option: `additional_instances` (Optional)

A list of additional Frigate instances to proxy. Each instance requires:
- `name`: Display name
- `server`: The server URL (e.g. `http://192.168.1.11:5000`)
- `path`: The subpath to access this instance (e.g. `/second`, avoid using `/main` since it's used by the main instance).
- `default_path`: (Optional) path to redirect to (e.g. `/review`).
- `proxy_pass_host`: (Optional, default true)
- `proxy_pass_real_ip`: (Optional, default true)

## Required Dependencies

- Network access to running Frigate server

## Support

Please [open an issue](https://github.com/blakeblackshear/frigate/issues/new/choose) if you need support.
