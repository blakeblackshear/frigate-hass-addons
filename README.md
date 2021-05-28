# Frigate Add-ons: The official repository

## Installing
In Hass.io, navigate to Supervisor > Add-on Store > Repositories and add `https://github.com/blakeblackshear/frigate-hass-addons`.

## Add-ons provided by this repository
- [Frigate NVR](frigate/README.md)
- [Frigate NVR Beta](frigate_beta/README.md)

  NVR with realtime local object detection for IP cameras
  
  
  Hass.io Media Folder from remote Unraid Server
 
Configuration.yaml should contain the following, The folder /media can be replaced by /mnt/media in both instances if you have other integrations that rely  on media.
The media source is not required if the "defualt_config:" is raised in the configuration.yaml.
 
```
homeassistant:
    media_dirs:
      media: /media

media_source:

shell_command:
  mount_cctv_media_folder: mkdir -p /media;mount -t cifs -o username=USER,password=PASSWORD,domain=WORKGROUP //tower.local/Media /mnt/media
```     
    
    
 
