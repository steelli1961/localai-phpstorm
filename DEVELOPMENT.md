# Ollama PhpStorm Plugin - Development Guide

## Project Structure

```
ollama-plugin/
├── src/main/java/com/ollama/plugin/
│   ├── OllamaPluginConstants.java          # Plugin configuration constants
│   ├── client/
│   │   ├── OllamaClient.java               # HTTP client for Ollama API
│   │   ├── OllamaModel.java                # Model data class
│   │   ├── OllamaResponse.java             # Response data class
│   │   └── OllamaMetrics.java              # Metrics data class
│   ├── settings/
│   │   ├── OllamaSettingsService.java      # Settings persistence service
│   │   ├── OllamaSettingsConfigurable.java # Settings UI controller
│   │   └── OllamaSettingsPanel.java        # Settings UI panel
│   └── ui/
│       ├── OllamaChatToolWindowFactory.java # Tool window factory
│       └── OllamaChatPanel.java            # Main chat UI panel
├── src/main/resources/
│   └── META-INF/
│       └── plugin.xml                      # Plugin descriptor
├── build.gradle                            # Gradle build configuration
└── README.md                               # User documentation
```

## Building the Plugin

### Prerequisites

- Java 11 or higher
- Gradle 7.0 or higher (or use the provided gradlew)

### Build Steps

```bash
# Clone or navigate to the project directory
cd /Users/user/code/localai-phpstorm

# Build the plugin
./gradlew build

# The plugin JAR will be created at:
# build/libs/ollama-plugin-1.0.0.jar
```

### Development Build

```bash
# Run plugin in development mode
./gradlew runIde

# This will launch PhpStorm with the plugin loaded
```

## Installation Methods

### Method 1: From Disk (Recommended for Users)

1. Build the plugin: `./gradlew build`
2. Open PhpStorm Settings/Preferences
3. Navigate to Plugins → Install Plugin from Disk
4. Select `build/libs/ollama-plugin-1.0.0.jar`
5. Restart PhpStorm

### Method 2: Manual Installation

1. Place the JAR file in:
   - macOS: `~/Library/Application Support/JetBrains/PhpStorm2023.3/plugins/`
   - Linux: `~/.config/JetBrains/PhpStorm2023.3/plugins/`
   - Windows: `%APPDATA%\JetBrains\PhpStorm2023.3\plugins\`
2. Restart PhpStorm

## Configuration

### Initial Setup

1. **Open Settings**
   - macOS: PhpStorm → Preferences (Cmd+,)
   - Windows/Linux: File → Settings (Ctrl+Alt+S)

2. **Navigate to Ollama Configuration**
   - Settings → Tools → Ollama Configuration

3. **Configure Connection**
   - Host: Usually `localhost` (or your Ollama server IP)
   - Port: Usually `11434` (default Ollama port)
   - Enable "Auto-connect on startup" (optional)

4. **Test Connection**
   - Click "Test Connection" button to verify

## Usage Guide

### Opening the Chat Tool

View → Tool Windows → Ollama Chat (or click the Ollama Chat tab on the right)

### Workflow

1. **Connect to Ollama**
   - Click "Connect" button
   - Status will change to "Connected" (green) when successful

2. **Load Models**
   - Click "Refresh Models" to list all downloaded models
   - Select desired model from dropdown

3. **Send Prompt**
   - Type your prompt in the text area
   - Click "Send"
   - Wait for AI response

4. **Monitor Performance**
   - View metrics below results:
     - Token count
     - Tokens per second
     - Generation duration

## API Reference

### OllamaClient Methods

```java
// Check connection
CompletableFuture<Boolean> isConnected(String baseUrl)

// Get available models
CompletableFuture<List<OllamaModel>> fetchModels(String baseUrl)

// Send prompt and get response
CompletableFuture<OllamaResponse> sendPrompt(
    String baseUrl, 
    String model, 
    String prompt, 
    OllamaResponseCallback callback
)

// Get metrics
CompletableFuture<OllamaMetrics> fetchMetrics(String baseUrl)
```

## Extending the Plugin

### Adding Custom Commands

1. Create a new action in `src/main/java/com/ollama/plugin/actions/`
2. Register in `plugin.xml`:
```xml
<actions>
    <action id="ollama.myAction" class="com.ollama.plugin.actions.MyAction" text="My Action">
        <add-to-group group-id="ToolsMenu" anchor="last"/>
    </action>
</actions>
```

### Adding New Metrics

1. Extend `OllamaMetrics.java`
2. Update `OllamaClient.fetchMetrics()` method
3. Update UI in `OllamaChatPanel.updateMetrics()`

## Troubleshooting

### Issue: Plugin Won't Load

**Solution**: Check that PhpStorm version matches (2023.3+) and Java 11+ is installed

### Issue: Connection Refused

**Solution**: 
- Verify Ollama is running: `ollama serve`
- Check host/port in settings
- Firewall may be blocking connection

### Issue: No Models Listed

**Solution**:
- Download a model: `ollama pull llama2`
- Refresh models in plugin
- Ensure Ollama is fully started

### Issue: Slow Response Times

**Solution**:
- Use a smaller model (e.g., neural-chat vs llama2-13b)
- Check system resources
- Increase Ollama memory allocation

## Performance Optimization

### Recommended Models for IDE Integration

- `neural-chat` - Fast, good for code suggestions
- `mistral` - Fast, balanced quality
- `llama2-7b` - Medium speed, quality
- `ollama-chat` - Optimized for conversations

### System Requirements

- **Minimum**: 4GB RAM, 8GB disk
- **Recommended**: 8GB+ RAM, 20GB disk
- **Optimal**: 16GB+ RAM, 50GB disk

## Contributing

To contribute improvements:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

MIT License - See LICENSE file for details

## Support

For issues and feature requests:
- Check existing issues in the repository
- Provide detailed error messages and steps to reproduce
- Include your PhpStorm version and Ollama version
