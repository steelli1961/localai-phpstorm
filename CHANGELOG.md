# Changelog

All notable changes to the Ollama PhpStorm Plugin will be documented in this file.

## [1.0.0] - 2024-01-25

### Added
- Initial release of Ollama PhpStorm Plugin
- Connection management with custom host and port configuration
- Automatic model detection from local Ollama instance
- Interactive chat interface for sending prompts
- Real-time response display
- Performance metrics display:
  - Token count
  - Tokens per second
  - Generation duration
- Settings persistence across IDE sessions
- Auto-connect feature for faster startup
- Connection status indicator
- Model refresh functionality
- Test connection button in settings

### Features
- Tool window integration with PhpStorm
- Asynchronous API calls to prevent UI blocking
- Error handling and user feedback
- Memory-efficient async processing
- Formatted model size display
- Progress indicator during operations

### Technical Details
- Built with IntelliJ Platform SDK
- OkHttp3 for HTTP communication
- Gson for JSON processing
- CompletableFuture for async operations
- Gradle build system

## Planned Features

### [1.1.0] - Upcoming
- [ ] Prompt history
- [ ] Save/load conversations
- [ ] Multiple model comparison
- [ ] Custom system prompts
- [ ] Temperature and parameter control
- [ ] Export responses to file
- [ ] Syntax highlighting in responses
- [ ] Code block execution context

### [1.2.0] - Future
- [ ] Local model fine-tuning
- [ ] Prompt templates
- [ ] Integration with code inspection
- [ ] AI-assisted code completion
- [ ] Document summarization
- [ ] Code explanation feature
- [ ] Advanced metrics dashboard
- [ ] Performance profiling

### [2.0.0] - Major Update
- [ ] Multi-language support
- [ ] Custom API endpoint support
- [ ] Cloud Ollama connection
- [ ] Model downloading from UI
- [ ] Advanced caching
- [ ] Batch processing
- [ ] Plugin marketplace integration

## Known Limitations

- Ollama must be running locally (no remote connection yet)
- Streaming responses not yet supported (full response only)
- No model management from UI (must use CLI)
- CPU metrics require Ollama 0.1.5+
- Memory calculation is approximate

## Compatibility

### Supported IDEs
- PhpStorm 2023.3 and later
- Other JetBrains IDEs with PHP plugin (WebStorm, etc.)

### Supported Models
- llama2 (any size)
- neural-chat
- mistral
- ollama-chat
- dolphin-mixtral
- Any model available on Ollama repository

### System Requirements
- Java 11 or higher
- Ollama 0.1.0 or later
- macOS 10.12+, Windows 10+, or Linux with glibc 2.23+

## Migration Guide

### From 0.9.0 to 1.0.0 (First Release)
N/A - This is the initial release

## Support

For issues, questions, or feature requests:
1. Check the [README.md](README.md)
2. Review [DEVELOPMENT.md](DEVELOPMENT.md)
3. Check existing GitHub issues
4. Create a new issue with:
   - PhpStorm version
   - Ollama version
   - Steps to reproduce
   - Error logs if applicable
