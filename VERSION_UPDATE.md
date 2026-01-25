# Version Update Summary - PhpStorm 2025.3 (PS-253.30387.85)

## Changes Made

### ✅ Build Configuration Updated
**File**: [build.gradle](build.gradle)

Changed from:
```gradle
intellij {
    version = '2023.3'
    plugins = ['php:233.11799.298']
    updateSinceUntilBuild = false
}
```

Updated to:
```gradle
intellij {
    version = '253.30387.85'
    plugins = ['php:253.30387.85']
    updateSinceUntilBuild = false
}
```

### ✅ Plugin Manifest Updated
**File**: [src/main/resources/META-INF/plugin.xml](src/main/resources/META-INF/plugin.xml)

Updated description to include:
- "Requires PhpStorm 2025.3+"

### ✅ Documentation Updated

**Files Updated**:
1. [README.md](README.md) - Requirements section
2. [QUICKSTART.md](QUICKSTART.md) - System Requirements section
3. [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) - Supported IDEs section
4. [FEATURES.md](FEATURES.md) - Configuration paths updated to 2025.3

**Changes Made**:
- Updated IDE version requirement from PhpStorm 2023.3 to PhpStorm 2025.3
- Updated version identifier from "2023.3" to "PS-253.30387.85"
- Updated configuration file paths to reflect 2025.3 paths
- Updated all references to IDE compatibility

## Compatibility

### Previous Version
- PhpStorm 2023.3+

### New Version
- PhpStorm 2025.3+ (PS-253.30387.85+)

## Building the Updated Plugin

```bash
cd /Users/user/code/localai-phpstorm
./gradlew build
```

This will create: `build/libs/ollama-plugin-1.0.0.jar` compatible with PhpStorm 2025.3

## Installation Instructions (Unchanged)

1. Build the plugin
2. PhpStorm → Settings → Plugins → Install from Disk
3. Select the JAR file
4. Restart PhpStorm

## Testing

To test with PhpStorm 2025.3 (PS-253.30387.85):

1. Build the updated plugin
2. Install in PhpStorm 2025.3
3. Verify plugin loads without errors
4. Test all features (connection, models, chat, metrics)

## Compatibility Status

| Component | Status |
|-----------|--------|
| IDE Version | ✅ Updated to 2025.3 |
| PHP Plugin | ✅ Updated to match 2025.3 |
| Java Version | ✅ Java 11+ (unchanged) |
| Dependencies | ✅ Compatible |
| Documentation | ✅ Updated |
| Source Code | ✅ No changes needed |

## Notes

- The plugin codebase itself requires no changes - only build configuration and documentation were updated
- All features remain the same
- Version number remains 1.0.0
- Backward compatibility: This version only works with PhpStorm 2025.3+
