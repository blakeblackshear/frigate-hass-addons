#!/usr/bin/with-contenv bashio
# ==============================================================================
# Configures NGINX for use with this add-on.
# ==============================================================================
declare server

bashio::var.json \
    entry "$(bashio::addon.ingress_entry)" \
    | tempio \
        -template /etc/nginx/templates/ingress.gtpl \
        -out /etc/nginx/servers/ingress.conf

server=$(bashio::config 'server')

echo '{"server":"'"$server"'"}' \
    | tempio \
        -template /etc/nginx/templates/upstream.gtpl \
        -out /etc/nginx/includes/upstream.conf

declare proxy_url

proxy_url=$(bashio::config 'proxy_url')

echo '{"proxy_url":"'"$proxy_url"'"}' \
    | tempio \
        -template /etc/nginx/templates/server_params.gtpl \
        -out /etc/nginx/includes/server_params.conf