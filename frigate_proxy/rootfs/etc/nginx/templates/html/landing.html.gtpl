<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Frigate Instances</title>
    <style>
        :root {
            --primary-color: #03a9f4;
            --bg-color: #fafafa;
            --card-bg: #ffffff;
            --text-color: #212121;
            --border-color: #ddd;
        }
        @media (prefers-color-scheme: dark) {
            :root {
                --primary-color: #039be5;
                --bg-color: var(--primary-background-color, #111111);
                --card-bg: var(--card-background-color, #1c1c1c);
                --text-color: var(--primary-text-color, #e1e1e1);
                --border-color: var(--divider-color, #444);
            }
        }
        body {
            font-family: Roboto, -apple-system, BlinkMacSystemFont, "Segoe UI", "Helvetica Neue", Arial, sans-serif;
            max-width: 800px;
            margin: 2em auto;
            padding: 0 1em;
            background-color: var(--bg-color);
            color: var(--text-color);
        }
        .instance {
            border: 1px solid var(--border-color);
            background-color: var(--card-bg);
            padding: 1.5em;
            margin: 1em 0;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .instance h2 {
            margin-top: 0;
            margin-bottom: 0.5em;
        }
        .instance p {
            color: var(--text-color);
            opacity: 0.8;
            margin-bottom: 1.5em;
        }
        .instance a {
            display: inline-block;
            padding: 0.6em 1.2em;
            background: var(--primary-color);
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-weight: 500;
            transition: opacity 0.2s;
        }
        .instance a:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <h1>Frigate Instances</h1>
    {{- $entry := .entry }}
    {{- range .instances }}
    <div class="instance">
        <h2>{{ .name }}</h2>
        <p>{{ .description }}</p>
        <a href="{{ $entry }}{{ .path }}/{{ if .default_path }}{{ .default_path }}{{ end }}" target="_self">Open Instance</a>
    </div>
    {{- end }}
</body>
</html>
