# Ollama PhpStorm Plugin - Features Overview

## Core Features

### 1. Connection Management
- **Custom Configuration**: Set host and port for your Ollama instance
- **Connection Status**: Real-time indicator showing connection state
- **Test Connection**: Verify Ollama accessibility before use
- **Auto-Connect**: Optional automatic connection on IDE startup
- **Settings Persistence**: All configurations saved between sessions

### 2. Model Management
- **Auto-Detection**: Automatically discovers all downloaded models
- **Model Browser**: Drop-down selector for easy model switching
- **Model Information**: Display model size and metadata
- **Refresh**: Update model list with a single click
- **Model Compatibility**: Works with any Ollama-supported model

### 3. Interactive Chat Interface
- **Prompt Input**: Multi-line text area for entering prompts
- **Response Display**: Full response output with text wrapping
- **Real-time Feedback**: Loading indicators during processing
- **Error Handling**: Clear error messages for troubleshooting
- **Context Preservation**: Maintains conversation context

### 4. Performance Metrics
- **Token Counting**: Track total tokens generated
- **Speed Metrics**: Tokens per second calculation
- **Duration Tracking**: Total generation time display
- **Real-time Updates**: Metrics update with each response
- **Performance Insights**: Identify slow models or issues

### 5. User Interface
- **Tool Window Integration**: Seamless PhpStorm integration
- **Responsive Design**: Works with various window sizes
- **Keyboard Shortcuts**: Fast access to common operations
- **Visual Feedback**: Progress indicators and status updates
- **Modern Layout**: Clean, organized interface

## Advanced Features

### Settings Panel
- Host configuration (IP or hostname)
- Port configuration (1-65535)
- Auto-connect toggle
- Test connection button
- Visual status indicator

### Chat Panel Features
- Model selector with size information
- Multi-line prompt input with scroll support
- Send button with keyboard shortcut (Ctrl+Enter)
- Real-time response streaming
- Formatted metrics display
- Error message display

### Async Processing
- Non-blocking API calls
- Background task execution
- Proper thread management
- Exception handling
- Progress notifications

## Use Cases

### 1. Code Generation
```
Prompt: "Write a PHP function that validates email addresses"
Model: neural-chat
Result: Complete, working PHP code
```

### 2. Code Explanation
```
Prompt: "Explain this code: [paste code]"
Model: mistral
Result: Detailed explanation with line-by-line breakdown
```

### 3. Bug Fixing
```
Prompt: "Why is this code giving an error? [code]"
Model: llama2
Result: Root cause analysis and fix suggestions
```

### 4. Documentation
```
Prompt: "Write documentation for this function: [code]"
Model: neural-chat
Result: Professional documentation
```

### 5. Quick Answers
```
Prompt: "How do I use Laravel migrations?"
Model: mistral
Result: Concise, practical answer
```

## Performance Characteristics

### Token Generation Speed
- **neural-chat**: 5-15 tokens/sec
- **mistral**: 3-8 tokens/sec
- **llama2**: 2-5 tokens/sec

### Memory Usage
- **neural-chat**: 4-6GB
- **mistral**: 7-10GB
- **llama2-7b**: 6-8GB
- **llama2-13b**: 10-16GB

### Recommended System Specs

#### Minimum (Slow)
- 4GB RAM
- CPU: 2+ cores @ 2GHz+
- SSD: 20GB free

#### Recommended (Good)
- 8GB RAM
- CPU: 4+ cores @ 2.5GHz+
- SSD: 50GB free

#### Optimal (Excellent)
- 16GB+ RAM
- CPU: 8+ cores @ 3GHz+
- SSD: 100GB free
- GPU: Optional (CUDA/Metal support in Ollama)

## Settings Reference

### Configuration File Location
- **macOS**: `~/Library/Application Support/JetBrains/PhpStorm2025.3/options/ollama_settings.xml`
- **Windows**: `%APPDATA%\JetBrains\PhpStorm2025.3\options\ollama_settings.xml`
- **Linux**: `~/.config/JetBrains/PhpStorm2025.3/options/ollama_settings.xml`

### Configuration Options
```xml
<OllamaSettings>
    <host>localhost</host>
    <port>11434</port>
    <selectedModel>llama2</selectedModel>
    <autoConnect>false</autoConnect>
</OllamaSettings>
```

## Integration Examples

### With Code Inspection
Future feature: AI-powered code suggestions while you type

### With Git
Future feature: Commit message generation, changelog creation

### With Documentation
Future feature: Auto-generate docs from code analysis

### With Tests
Future feature: Generate test cases from code

## Troubleshooting Guide

### Common Issues

**1. Connection Refused**
- Check Ollama is running: `ollama serve`
- Verify host/port settings
- Check firewall rules

**2. No Models Found**
- Download models: `ollama pull llama2`
- Wait for download to complete
- Refresh in plugin

**3. Slow Responses**
- Use smaller model (neural-chat vs llama2-13b)
- Close other applications
- Check Ollama logs

**4. High Memory Usage**
- Use quantized models
- Close unused models: `ollama rm model-name`
- Reduce system load

**5. Connection Timeout**
- Check network connectivity
- Increase timeout in code if needed
- Verify Ollama port is accessible

## API Endpoints Used

### Models List
```
GET /api/tags
```

### Generate Response
```
POST /api/generate
Body: {
  "model": "llama2",
  "prompt": "Your prompt",
  "stream": false
}
```

### Process Status
```
GET /api/ps
```

## Keyboard Shortcuts

- **Open Ollama Chat**: View → Tool Windows → Ollama Chat
- **Send Prompt**: Enter (in prompt field)
- **Focus Chat**: F12 or Tab
- **Settings**: Cmd+, (macOS) / Ctrl+Alt+S (Windows/Linux)

## Best Practices

### For Optimal Performance
1. Use appropriate model size for your hardware
2. Refresh models list after downloading new ones
3. Monitor metrics to identify slow responses
4. Close unused IDE windows to save resources

### For Better Results
1. Be specific in your prompts
2. Provide context (code snippets, error messages)
3. Use model-specific optimizations
4. Test different models for your use case

### For Security
1. Keep Ollama updated
2. Use firewall rules
3. Don't expose Ollama to untrusted networks
4. Regularly check Ollama logs

## Future Roadmap

### Version 1.1
- [ ] Conversation history
- [ ] Save/export conversations
- [ ] Prompt templates
- [ ] Multiple model comparison

### Version 1.2
- [ ] Advanced parameters (temperature, top-p)
- [ ] Streaming responses
- [ ] Code syntax highlighting
- [ ] Performance profiling

### Version 2.0
- [ ] Remote Ollama connection
- [ ] Model management UI
- [ ] Fine-tuning support
- [ ] Plugin marketplace integration
