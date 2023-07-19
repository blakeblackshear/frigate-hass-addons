#!/usr/bin/with-contenv bashio
# ==============================================================================
# Configures NGINX for use with this add-on.
# ==============================================================================

# Note the ^ at the beginning of the proxy_pass_host value
# This stops bashio:var.json from passing the value as a string

bashio::var.json \
    entry "$(bashio::addon.ingress_entry)" \
    server "$(bashio::config 'server')" \
    proxy_pass_host "^$(bashio::config 'proxy_pass_host')" \
    | tempio \
        -template /etc/nginx/templates/ingress.gtpl \
        -out /etc/nginx/servers/ingress.conf
