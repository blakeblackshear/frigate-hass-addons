server {
    listen 5000 default_server;

    include /etc/nginx/includes/server_params.conf;

    # Handle each Frigate instance first
    {{ range $key, $instance := .instances }}
    location {{ $instance.path }}/ {
        allow   172.30.32.2;
        deny    all;
        rewrite ^{{ $instance.path }}/(.*) /$1 break;
        proxy_pass {{ $instance.server }};
        proxy_set_header X-Ingress-Path {{ $.entry }}{{ $instance.path }};

        {{ if $instance.proxy_pass_host }}
        proxy_set_header Host $http_host;
        {{ end }}
        {{ if $instance.proxy_pass_real_ip }}
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Real-IP $remote_addr;
        {{ end }}

        include /etc/nginx/includes/proxy_params.conf;
    }
    {{ end }}

    # Serve landing page at root and for unmatched paths
    location / {
        allow   172.30.32.2;
        deny    all;
        root /etc/nginx/html;
        try_files /landing.html =404;
    }
        allow   172.30.32.2;
        deny    all;

        proxy_pass {{ $instance.server }};
        proxy_set_header X-Ingress-Path {{ $.entry }}{{ $instance.path }};

        {{ if $instance.proxy_pass_host }}
        proxy_set_header Host $http_host;
        {{ end }}
        {{ if $instance.proxy_pass_real_ip }}
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Real-IP $remote_addr;
        {{ end }}

        include /etc/nginx/includes/proxy_params.conf;
    }
    {{ end }}
}
