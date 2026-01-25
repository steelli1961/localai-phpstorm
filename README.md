# Ollama PhpStorm Plugin

A powerful PhpStorm plugin for integrating Ollama AI models directly into your IDE.

## Features

- **Local Ollama Connection**: Connect to your local Ollama instance with custom host and port configuration
- **Model Auto-Detection**: Automatically discovers and lists all downloaded models
- **Interactive Chat Interface**: Send prompts and receive AI-generated responses in real-time
- **Performance Metrics**: Monitor tokens per second, memory usage, and generation time
- **Auto-Connection**: Optional automatic connection on IDE startup
- **Settings Persistence**: All configurations are saved across IDE sessions

## Installation

1. Build the plugin:
```bash
./gradlew build
```

2. The plugin JAR will be available at `build/libs/ollama-plugin-1.0.0.jar`

3. Install in PhpStorm:
   - Go to Settings/Preferences → Plugins
   - Click the gear icon and select "Install Plugin from Disk"
   - Select the generated JAR file
   - Restart PhpStorm

## Configuration

1. Open Settings/Preferences (Cmd+, on macOS, Ctrl+Alt+S on Windows/Linux)
2. Navigate to Tools → Ollama Configuration
3. Set your Ollama host (default: localhost)
4. Set your Ollama port (default: 11434)
5. Enable "Auto-connect on startup" if desired
6. Click "Test Connection" to verify connectivity

## Usage

1. Open the Ollama Chat tool window from View → Tool Windows → Ollama Chat
2. Click "Connect" to establish connection with Ollama
3. Click "Refresh Models" to load available models
4. Select a model from the dropdown
5. Enter your prompt in the text area
6. Click "Send" to generate a response
7. View the result and performance metrics below

## Requirements

- PhpStorm 2025.3 or later (PS-253.30387.85+)
- Ollama running locally (download from https://ollama.ai)
- Java 11+

## API Compatibility

- Ollama API v1.0+
- Tested with Ollama models: llama2, neural-chat, mistral, etc.

## Troubleshooting

**Connection Failed**: Ensure Ollama is running and accessible at the configured host:port

**No Models**: Download a model using `ollama pull <model-name>` in your terminal

**Slow Performance**: Check your system resources and consider using a smaller model

## License

MIT License
