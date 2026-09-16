server {
    listen 5000 default_server;
    absolute_redirect off;

    include /etc/nginx/includes/server_params.conf;

    {{- $server := .server }}
    {{- $name := default "Main" .name }}
    {{- $description := default "" .description }}
    {{- $path := default "/" .path }}
    {{- if and .additional_instances (eq $path "/") }}
        {{- $path = "/main" }}
    {{- end }}
    {{- $default_path := .default_path }}
    {{- $proxy_pass_host := .proxy_pass_host }}
    {{- $proxy_pass_real_ip := .proxy_pass_real_ip }}
    {{- $entry := .entry }}

    {{- $instances := list }}
    {{- if .server }}
        {{- $instances = append $instances (dict "name" $name "description" $description "server" .server "path" $path "default_path" .default_path "proxy_pass_host" .proxy_pass_host "proxy_pass_real_ip" .proxy_pass_real_ip) }}
    {{- end }}
    {{- range .additional_instances }}
        {{- $instances = append $instances . }}
    {{- end }}

    # Handle root path for landing page
    # Only render this block if we actually need to redirect or show a landing page.
    # If we have a single instance at root with no default path, we want to let the main proxy block handle it.
    {{- $render_root := true }}
    {{- if eq (len $instances) 1 }}
        {{- $target := index $instances 0 }}
        {{- if and (eq $target.path "/") (not $target.default_path) }}
             {{- $render_root = false }}
        {{- end }}
    {{- end }}

    {{- if $render_root }}
    location = / {
        add_header Cache-Control "no-store, no-cache, must-revalidate, proxy-revalidate, max-age=0";
        add_header Pragma "no-cache";
        add_header Expires "0";

        root /etc/nginx/html;

        {{- if eq (len $instances) 1 }}
            {{- $target := index $instances 0 }}
             return 302 {{ $entry }}{{ if ne $target.path "/" }}{{ $target.path }}/{{ end }}{{ if $target.default_path }}{{ $target.default_path }}{{ end }};
        {{- else }}
            try_files /landing.html /index.html =404;
        {{- end }}
    }
    {{- end }}

    location = /index.html {
        root /etc/nginx/html;
        rewrite ^ /landing.html break;
    }

    # Handle each Frigate instance
    {{- range $instances }}
    location {{ if eq .path "/" }}/{{ else }}{{ .path }}/{{ end }} {
        allow   172.30.32.2;
        deny    all;
        
        proxy_pass {{ .server }}/;
        proxy_set_header X-Ingress-Path {{ $entry }}{{ if ne .path "/" }}{{ .path }}{{ end }};

        include /etc/nginx/includes/proxy_params.conf;

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
