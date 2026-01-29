# Ollama PhpStorm Plugin

A powerful PhpStorm plugin for integrating Ollama AI models directly into your IDE.

## Features

- **Local Ollama Connection**: Connect to your local Ollama instance with custom host and port configuration
- **Model Auto-Detection**: Automatically discovers and lists all downloaded models
- **Interactive Chat Interface**: Send prompts and receive AI-generated responses in real-time
- **Performance Metrics**: Monitor tokens per second, memory usage, and generation time
- **Auto-Connection**: Optional automatic connection on IDE startup
- **Settings Persistence**: All configurations are saved across IDE sessions

### Code Editing & Refactoring

- **Refactor Code**: Get AI-powered suggestions to improve code quality, readability, and performance
- **Explain Code**: Get detailed explanations of what selected code does
- **Optimize Code**: Receive performance optimization suggestions
- **Generate Tests**: Automatically generate unit tests for your code
- **Add Type Hints**: Auto-add type annotations to your code
- **Language Support**: Works with Java, PHP, Python, JavaScript, TypeScript, Go, Rust, C++
- **Direct Editor Integration**: Insert or replace code directly in your editor

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

### Chat Interface

1. Open the Ollama Chat tool window from View → Tool Windows → Ollama Chat
2. Click "Connect" to establish connection with Ollama
3. Click "Refresh Models" to load available models
4. Select a model from the dropdown
5. Enter your prompt in the text area
6. Click "Send" to generate a response
7. View the result and performance metrics below

### Code Refactoring & Analysis

Select any code in your editor and use the following keyboard shortcuts or IDE actions:

#### Keyboard Shortcuts

| Feature | Shortcut |
|---------|----------|
| Refactor Code | `Ctrl+Alt+Shift+R` |
| Explain Code | `Ctrl+Alt+Shift+E` |
| Optimize Code | `Ctrl+Alt+Shift+O` |
| Generate Tests | `Ctrl+Alt+Shift+T` |

#### Usage Steps

1. **Select code** in your editor
2. **Press the keyboard shortcut** for the desired action
3. Ollama analyzes the code and provides suggestions
4. A dialog appears with the result:
   - **Refactor/Optimize**: Click "Replace" to update the code, or "Cancel" to dismiss
   - **Explain**: View the explanation in an info dialog
   - **Generate Tests**: Click "Insert" to add tests after your code, or "Cancel" to dismiss

#### Supported Languages

- Java
- PHP
- Python
- JavaScript
- TypeScript
- Go
- Rust
- C/C++

#### Examples

**Refactor Code**: Select a function with poor naming/structure → `Ctrl+Alt+Shift+R` → Get suggestions → Replace

**Explain Code**: Select a complex algorithm → `Ctrl+Alt+Shift+E` → Understand what it does

**Optimize**: Select performance-critical code → `Ctrl+Alt+Shift+O` → Get optimization tips

**Generate Tests**: Select a function → `Ctrl+Alt+Shift+T` → Get unit tests → Insert into file

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

## Architecture

The plugin consists of several key components:

- **OllamaClient**: Handles communication with Ollama API
- **OllamaSettingsService**: Manages user configuration (host, port, auto-connect)
- **CodeAnalysisService**: Provides code refactoring, explanation, optimization, and test generation
- **OllamaChatPanel**: UI for interactive chat interface
- **Action Classes**: IDE commands for refactoring operations (Refactor, Explain, Optimize, GenerateTests)

## Development

### Building from Source

```bash
# Build the plugin
./gradlew build

# Clean build
./gradlew clean build

# Skip searchable options task (optional)
./gradlew build -x buildSearchableOptions
```

### Project Structure

```
src/main/
├── java/com/ollama/plugin/
│   ├── client/          # API client and models
│   ├── settings/        # Configuration UI and storage
│   ├── ui/              # UI components
│   ├── service/         # CodeAnalysisService
│   └── action/          # IDE actions for refactoring
└── resources/
    └── META-INF/
        └── plugin.xml   # Plugin descriptor
```

## License

MIT License
