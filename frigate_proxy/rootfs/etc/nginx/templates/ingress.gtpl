server {
    listen 5000 default_server;

    include /etc/nginx/includes/server_params.conf;

    # Handle root path for landing page
    location = / {
        root /etc/nginx/html;
        try_files /landing.html /index.html =404;
    }

    location = /index.html {
        root /etc/nginx/html;
        rewrite ^ /landing.html break;
    }

    # Handle each Frigate instance
    {{- $entry := .entry }}
    {{- range .instances }}
    location {{ .path }}/ {
        allow   172.30.32.2;
        deny    all;
        proxy_pass {{ .server }}/;
        include /etc/nginx/includes/proxy_params.conf;

        # Rewrite paths for Ingress
        sub_filter_once off;
        sub_filter_types *;
        sub_filter 'href="/' 'href="{{ $entry }}{{ .path }}/';
        sub_filter 'src="/' 'src="{{ $entry }}{{ .path }}/';
        sub_filter 'action="/' 'action="{{ $entry }}{{ .path }}/';

        {{- if .proxy_pass_host }}
        proxy_set_header Host $http_host;
        {{- end }}
        {{- if .proxy_pass_real_ip }}
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Real-IP $remote_addr;
        {{- end }}
    }
    {{- end }}
    include /etc/nginx/includes/proxy_params.conf;

}
