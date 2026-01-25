# Quick Start Guide

## Installation (2 minutes)

### Step 1: Build the Plugin
```bash
cd /Users/user/code/localai-phpstorm
./gradlew build
```

### Step 2: Install in PhpStorm
1. Open PhpStorm
2. Go to **Settings/Preferences** (Cmd+, on macOS)
3. Navigate to **Plugins** → **Install Plugin from Disk**
4. Select `build/libs/ollama-plugin-1.0.0.jar`
5. Restart PhpStorm

## Configuration (1 minute)

### Step 1: Open Settings
- **macOS**: PhpStorm → Preferences (Cmd+,)
- **Windows/Linux**: File → Settings (Ctrl+Alt+S)

### Step 2: Configure Ollama
1. Go to **Tools** → **Ollama Configuration**
2. Set **Host**: `localhost` (or your Ollama server IP)
3. Set **Port**: `11434` (default Ollama port)
4. Click **Test Connection**
5. Check the "Auto-connect on startup" box (optional)

### Step 3: Verify Connection
- Status should show "Connected" in green
- If failed, ensure Ollama is running: `ollama serve`

## First Use (3 minutes)

### Step 1: Open Chat Tool
- Click **View** → **Tool Windows** → **Ollama Chat**
- Or look for the Ollama Chat tab on the right side

### Step 2: Download a Model
If no models appear, download one:
```bash
ollama pull llama2
# or try a faster model:
ollama pull neural-chat
```

### Step 3: Connect and Load Models
1. Click the **Connect** button
2. Click **Refresh Models**
3. Select a model from the dropdown

### Step 4: Send Your First Prompt
1. Type in the prompt field: "Hello! Who are you?"
2. Click **Send**
3. Wait for the response
4. View metrics below the result

## Common Prompts to Try

### Code Generation
```
Write a PHP function to calculate factorial of a number
```

### Code Explanation
```
Explain this PHP code:
function fibonacci($n) {
    if ($n <= 1) return $n;
    return fibonacci($n-1) + fibonacci($n-2);
}
```

### Quick Questions
```
What is the difference between abstract classes and interfaces in PHP?
```

### Debugging Help
```
Why am I getting "Call to undefined method" error?
[paste your error and code]
```

## Best Models for Different Tasks

### For Speed (Recommended for IDE)
- **neural-chat**: Fast (5-15 tokens/sec), good quality
- **mistral**: Fast (3-8 tokens/sec), excellent quality

### For Quality
- **llama2**: Slow (2-5 tokens/sec), very good quality
- **dolphin-mixtral**: Balanced speed and quality

### For Coding Tasks
1. Try **neural-chat** first (fastest)
2. If not good enough, try **mistral**
3. For complex tasks, use **llama2**

## Troubleshooting Quick Fixes

| Problem | Solution |
|---------|----------|
| Connection failed | Run `ollama serve` in terminal |
| No models listed | Run `ollama pull llama2` or `ollama pull neural-chat` |
| Very slow response | Try a smaller model like neural-chat |
| High memory usage | Use a quantized model (e.g., mistral instead of llama2-13b) |
| Plugin won't load | Ensure you have PhpStorm 2023.3+ and Java 11+ |

## Keyboard Tips

- **Open Settings**: Cmd+, (macOS) or Ctrl+Alt+S (Windows/Linux)
- **Open Chat Tool**: View → Tool Windows → Ollama Chat
- **Send Prompt**: Press Enter in the prompt field
- **Focus Chat**: Click the Ollama Chat tab

## Next Steps

1. **Learn More**: Read [README.md](README.md)
2. **Advanced Features**: Check [FEATURES.md](FEATURES.md)
3. **Development**: See [DEVELOPMENT.md](DEVELOPMENT.md)
4. **Configuration**: See [CHANGELOG.md](CHANGELOG.md) for updates

## Getting Help

### Check These Resources First
1. [README.md](README.md) - General information
2. [FEATURES.md](FEATURES.md) - Feature details
3. [DEVELOPMENT.md](DEVELOPMENT.md) - Technical guide

### Verification Checklist
- [ ] Ollama is running (`ollama serve`)
- [ ] PhpStorm version is 2023.3+
- [ ] Java 11+ is installed
- [ ] Plugin is installed and enabled
- [ ] Host/Port are correct in settings
- [ ] Connection test passes
- [ ] At least one model is downloaded

### If Issues Persist
1. Check Ollama logs: Look at the terminal running `ollama serve`
2. Check PhpStorm logs: Help → Show Log in Finder/Explorer
3. Verify settings file: Check ollama_settings.xml location
4. Try reinstalling the plugin

## Performance Tips

### For Faster Responses
1. Use **neural-chat** model
2. Close unused IDE projects
3. Disable other heavy plugins
4. Ensure Ollama is the only process using GPU (if applicable)

### For Better Answers
1. Use **llama2** or **mistral** models
2. Be specific in prompts
3. Provide context (code snippets, errors)
4. Ask follow-up questions for clarification

### For Lower Memory Usage
1. Use quantized models when available
2. Close completed conversations
3. Restart IDE if memory grows over time
4. Limit number of open IDE projects

## System Requirements

- **OS**: macOS 10.12+, Windows 10+, or Linux
- **Memory**: 4GB minimum (8GB recommended)
- **Java**: 11 or higher
- **IDE**: PhpStorm 2025.3 or later (PS-253.30387.85+)
- **Ollama**: 0.1.0 or later

## What's Next?

Once you're comfortable with basic usage:

1. **Try Different Models**: Experiment with various models to see which works best for you
2. **Explore Advanced Features**: Check settings for additional options
3. **Build Workflows**: Create your own prompt patterns for common tasks
4. **Provide Feedback**: Share what works well and what could be improved

## Support Resources

- **Official Ollama**: https://ollama.ai
- **Plugin Repository**: [GitHub URL when available]
- **Model Zoo**: https://ollama.ai/library
- **Community**: Ollama Discord community

---

**Ready to start?** Open PhpStorm and begin with Step 1 above!
