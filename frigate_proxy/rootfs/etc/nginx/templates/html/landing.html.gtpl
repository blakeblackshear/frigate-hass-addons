<!DOCTYPE html>
<html>
<head>
    <title>Frigate Instances</title>
    <style>
        body {
            font-family: sans-serif;
            max-width: 800px;
            margin: 2em auto;
            padding: 0 1em;
        }
        .instance {
            border: 1px solid #ddd;
            padding: 1em;
            margin: 1em 0;
            border-radius: 4px;
        }
        .instance h2 {
            margin-top: 0;
        }
        .instance a {
            display: inline-block;
            padding: 0.5em 1em;
            background: #03a9f4;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Frigate Instances</h1>
    {{ range $key, $instance := .instances }}
    <div class="instance">
        <h2>{{ $instance.name }}</h2>
        <p>{{ $instance.description }}</p>
        <a href="{{ $.entry }}{{ $instance.path }}">Open Instance</a>
    </div>
    {{ end }}
</body>
</html>
