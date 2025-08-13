server {
    listen 5000 default_server;

    include /etc/nginx/includes/server_params.conf;

    # Handle root path for landing page
    location = / {
        allow   172.30.32.2;
        deny    all;
        alias /etc/nginx/html/landing.html;
    }

    # Handle each Frigate instance
    {{- range .instances }}
    location {{ .path }}/ {
        allow   172.30.32.2;
        deny    all;
        proxy_pass {{ .server }}/;
        include /etc/nginx/includes/proxy_params.conf;

        # WebSocket support
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection $connection_upgrade;
    }

    location {{ .path }}/api/ {
        allow   172.30.32.2;
        deny    all;
        proxy_pass {{ .server }}/api/;
        include /etc/nginx/includes/proxy_params.conf;
    }
    {{- end }}
}

        {{- if .proxy_pass_host }}
        proxy_set_header Host $http_host;
        {{- end }}
        {{- if .proxy_pass_real_ip }}
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Real-IP $remote_addr;
        {{- end }}

        include /etc/nginx/includes/proxy_params.conf;
    }
    {{- end }}
}
