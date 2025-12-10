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

        # Clear Ingress headers to prevent Frigate from auto-detecting the ingress path
        # ignoring this allows us to fully control the rewriting via sub_filter
        proxy_set_header X-Ingress-Path "";
        proxy_set_header X-Forwarded-Prefix "";

        # Rewrite paths for Ingress
        sub_filter_once off;
        sub_filter_types *;
        sub_filter 'href="/' 'href="{{ $entry }}{{ .path }}/';
        sub_filter 'src="/' 'src="{{ $entry }}{{ .path }}/';
        sub_filter 'action="/' 'action="{{ $entry }}{{ .path }}/';
        
        # Javascript imports (Vite/ESM)
        sub_filter 'from "/' 'from "{{ $entry }}{{ .path }}/';
        
        # CSS URL rewrites
        sub_filter 'url("/' 'url("{{ $entry }}{{ .path }}/';
        sub_filter "url('/" "url('{{ $entry }}{{ .path }}/";

        # API & App rewrites (rewriting strings in JS bundles)
        sub_filter '"/api' '"{{ $entry }}{{ .path }}/api';
        sub_filter '"/ws' '"{{ $entry }}{{ .path }}/ws';
        sub_filter '"/live' '"{{ $entry }}{{ .path }}/live';
        sub_filter '"/vod' '"{{ $entry }}{{ .path }}/vod';
        sub_filter '"/clips' '"{{ $entry }}{{ .path }}/clips';
        sub_filter '"/recordings' '"{{ $entry }}{{ .path }}/recordings';
        sub_filter '"/events' '"{{ $entry }}{{ .path }}/events';
        sub_filter '"/export' '"{{ $entry }}{{ .path }}/export';
        sub_filter '"/login' '"{{ $entry }}{{ .path }}/login';

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
