server {
    listen 5000 default_server;

    include /etc/nginx/includes/server_params.conf;

    # Serve landing page at root and for unmatched paths
    location / {
        allow   172.30.32.2;
        deny    all;
        root /etc/nginx/html;
        try_files /landing.html =404;
    }

    # Handle each Frigate instance
    {{- range .instances }}
    location {{ .path }}/ {
        allow   172.30.32.2;
        deny    all;
        rewrite ^{{ .path }}/(.*) /$1 break;
        proxy_pass {{ .server }};
        proxy_set_header X-Ingress-Path {{ $.entry }}{{ .path }};

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
