### 1.7

- Add support for multiple instances using the `instances` option  
  If only one instance is specified, it will be redirected to automatically.  
  Note the breaking change to configuration:
  - `name` option to specify the name of the instance
  - `description` option to specify the description of the instance
  - `server` option to specify the server URL for the backend
  - `path` option to specify the path in the ingress
  - `default_path` optional option to specify the default path in Frigate, example: `review`


### 1.6

- Refresh add-on with updated base image and nginx

### 1.5

- Add `proxy_pass_real_ip` option

### 1.4

- Add support for HTTPS upstream servers
  Note the breaking change to configuration which requires the protocol to be
  specified in the server configuration: `http://frigate.local:5000` instead of
  `frigate.local:5000`.

### 1.3

- Set side panel name to Frigate

### 1.2

- Switch to debian base

### 1.1

- Allow access to side panel for non-admins

### 1.0

- Initial release
