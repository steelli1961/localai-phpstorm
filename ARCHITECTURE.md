# Plugin Architecture

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PhpStorm IDE                              │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         Ollama Chat Tool Window                       │   │
│  │  ┌────────────────────────────────────────────────┐  │   │
│  │  │  OllamaChatPanel                               │  │   │
│  │  │  - Model Selection                             │  │   │
│  │  │  - Prompt Input                                │  │   │
│  │  │  - Results Display                             │  │   │
│  │  │  - Metrics Display                             │  │   │
│  │  └────────────────────────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────┘   │
│                           ▲                                   │
│                           │ uses                              │
│                           ▼                                   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         Settings & Configuration                     │   │
│  │  ┌────────────────────────────────────────────────┐  │   │
│  │  │  OllamaSettingsService                         │  │   │
│  │  │  - host: String                               │  │   │
│  │  │  - port: int                                  │  │   │
│  │  │  - selectedModel: String                      │  │   │
│  │  │  - autoConnect: boolean                       │  │   │
│  │  └────────────────────────────────────────────────┘  │   │
│  │  ┌────────────────────────────────────────────────┐  │   │
│  │  │  OllamaSettingsConfigurable                   │  │   │
│  │  │  - Settings UI Controller                     │  │   │
│  │  └────────────────────────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                           │
                           │ REST API calls (HTTP)
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              OllamaClient (HTTP Layer)                       │
│                                                               │
│  - isConnected(baseUrl): CompletableFuture<Boolean>         │
│  - fetchModels(baseUrl): CompletableFuture<List<Model>>    │
│  - sendPrompt(...): CompletableFuture<OllamaResponse>      │
│  - fetchMetrics(baseUrl): CompletableFuture<Metrics>       │
│                                                               │
│  Uses: OkHttp3, Gson                                        │
└─────────────────────────────────────────────────────────────┘
                           │
                           │ HTTP Requests
                           ▼
┌─────────────────────────────────────────────────────────────┐
│         Local Ollama Server                                  │
│         (http://localhost:11434)                             │
│                                                               │
│  API Endpoints:                                              │
│  - GET /api/tags (list models)                              │
│  - POST /api/generate (send prompt)                         │
│  - GET /api/ps (process status)                             │
└─────────────────────────────────────────────────────────────┘
```

## Component Interaction Flow

```
┌─────────────────────────────────────────────────────────────┐
│                    User Action                               │
└─────────────────────┬───────────────────────────────────────┘
                      │
    ┌─────────────────┼─────────────────┬──────────────────┐
    │                 │                 │                  │
    ▼                 ▼                 ▼                  ▼
┌─────────┐      ┌──────────┐      ┌────────┐      ┌────────────┐
│ Connect │      │ Refresh  │      │ Select │      │ Send       │
│         │      │ Models   │      │ Model  │      │ Prompt     │
└────┬────┘      └────┬─────┘      └────┬───┘      └─────┬──────┘
     │                │                 │               │
     │                │                 │               │
     ▼                ▼                 ▼               ▼
  ┌────────────────────────────────────────────────────────┐
  │         OllamaChatPanel (UI Controller)                 │
  └────────────────────┬───────────────────────────────────┘
                       │
                       │ calls
                       ▼
  ┌────────────────────────────────────────────────────────┐
  │         OllamaClient (API Client)                       │
  │                                                          │
  │  isConnected() ────┐                                    │
  │  fetchModels()  ───┼──→ Build HTTP Request             │
  │  sendPrompt()   ───┤    Execute Request (OkHttp3)      │
  │  fetchMetrics() ───┘    Parse Response (Gson)          │
  └────────────────────┬───────────────────────────────────┘
                       │
                       │ HTTP
                       ▼
  ┌────────────────────────────────────────────────────────┐
  │         Ollama REST API Server                          │
  └────────────────────┬───────────────────────────────────┘
                       │
                       │ Response
                       ▼
  ┌────────────────────────────────────────────────────────┐
  │         OllamaResponse / OllamaMetrics Objects          │
  │         (Data Transfer Objects)                         │
  └────────────────────┬───────────────────────────────────┘
                       │
                       │ return to
                       ▼
  ┌────────────────────────────────────────────────────────┐
  │         OllamaChatPanel (Update UI)                     │
  │                                                          │
  │  - Update result display                               │
  │  - Update metrics                                       │
  │  - Update status labels                                │
  │  - Handle errors                                       │
  └────────────────────────────────────────────────────────┘
```

## Class Diagram

```
OllamaClient
├── httpClient: OkHttpClient
├── gson: Gson
├── isConnected(baseUrl): CompletableFuture<Boolean>
├── fetchModels(baseUrl): CompletableFuture<List<OllamaModel>>
├── sendPrompt(...): CompletableFuture<OllamaResponse>
└── fetchMetrics(baseUrl): CompletableFuture<OllamaMetrics>

OllamaModel
├── name: String
├── size: long
├── getName(): String
├── getSize(): long
└── getSizeFormatted(): String

OllamaResponse
├── response: String
├── tokenCount: long
├── evalDuration: long
├── totalDuration: long
├── getResponse(): String
├── getTokenCount(): long
├── getTokensPerSecond(): double
└── getTotalDuration(): long

OllamaMetrics
├── memoryUsage: long
├── cpuUsage: double
├── tokensPerSecond: double
├── getMemoryUsage(): long
├── getMemoryUsageFormatted(): String
├── getCpuUsage(): double
└── getTokensPerSecond(): double

OllamaSettingsService (implements PersistentStateComponent)
├── host: String
├── port: int
├── selectedModel: String
├── autoConnect: boolean
├── getInstance(): OllamaSettingsService
├── getState(): OllamaSettingsService
├── loadState(state): void
└── getBaseUrl(): String

OllamaChatPanel
├── modelSelector: JComboBox<OllamaModel>
├── promptInput: JTextArea
├── resultOutput: JTextArea
├── metricsLabel: JLabel
├── connectToOllama(): void
├── refreshModels(): void
├── sendPrompt(): void
├── updateMetrics(response): void
└── getPanel(): JPanel

OllamaSettingsPanel
├── hostField: JTextField
├── portSpinner: JSpinner
├── autoConnectCheckBox: JCheckBox
├── testConnectionButton: JButton
├── isModified(): boolean
├── apply(): void
├── reset(): void
└── getPanel(): JComponent
```

## Data Flow Diagram

```
User Opens IDE
    │
    ├─→ Load OllamaSettingsService
    │       └─→ Read saved host, port, model
    │
    ├─→ Create OllamaChatPanel
    │
    └─→ Auto-connect if enabled
            └─→ OllamaClient.isConnected()
                    └─→ GET /api/tags
                        └─→ Check response status
                            └─→ Update UI (Connected/Disconnected)

User Clicks "Refresh Models"
    │
    └─→ OllamaClient.fetchModels()
            │
            ├─→ Build GET /api/tags request
            │
            ├─→ Execute HTTP request (async)
            │
            ├─→ Parse JSON response with Gson
            │
            └─→ Create OllamaModel objects
                    └─→ Update JComboBox in UI

User Selects Model & Types Prompt
    │
    └─→ User Clicks "Send"
            │
            ├─→ Validate input
            │
            ├─→ Build POST /api/generate request
            │   ├─→ model: selected model name
            │   ├─→ prompt: user input
            │   └─→ stream: false
            │
            ├─→ Show progress bar
            │
            ├─→ Execute HTTP request (async)
            │
            ├─→ Parse JSON response
            │
            ├─→ Create OllamaResponse object
            │   ├─→ response: generated text
            │   ├─→ tokenCount: from response
            │   ├─→ evalDuration: from response
            │   └─→ totalDuration: from response
            │
            └─→ Update UI on main thread
                    ├─→ Display response text
                    ├─→ Display metrics
                    └─→ Hide progress bar
```

## Module Dependencies

```
com.ollama.plugin
├── client/
│   ├── OllamaClient (main HTTP client)
│   ├── OllamaModel (data class)
│   ├── OllamaResponse (data class)
│   └── OllamaMetrics (data class)
│
├── settings/
│   ├── OllamaSettingsService (persistence service)
│   ├── OllamaSettingsConfigurable (settings UI controller)
│   └── OllamaSettingsPanel (settings UI panel)
│
├── ui/
│   ├── OllamaChatToolWindowFactory (factory for tool window)
│   ├── OllamaChatPanel (main chat UI)
│   └── SettingsConnectionTest (connection testing utility)
│
└── OllamaPluginConstants (shared constants)

External Dependencies:
├── IntelliJ Platform SDK
├── OkHttp3 (HTTP client library)
└── Gson (JSON processing library)
```

## Threading Model

```
Main Thread (UI Thread)
├── Handle user interactions
├── Update JComponents
└── Event listeners
    │
    └─→ Spawn async tasks
            │
            └─→ CompletableFuture threads
                    │
                    ├─→ Execute HTTP requests (blocking)
                    │
                    ├─→ Parse responses
                    │
                    └─→ Post results back to Main Thread
                            │
                            └─→ SwingUtilities.invokeLater()
                                    │
                                    └─→ Update UI safely
```

## Error Handling Flow

```
User Action
    │
    └─→ Try-Catch block
            │
            ├─→ CompletableFuture.exceptionally()
            │
            ├─→ Network error
            │   └─→ Show error dialog
            │
            ├─→ JSON parse error
            │   └─→ Log & show error
            │
            ├─→ Timeout
            │   └─→ Show timeout error
            │
            └─→ Other exceptions
                └─→ Show generic error message
```

This architecture ensures:
- **Separation of Concerns**: UI, API, Settings are separate
- **Thread Safety**: Async operations with proper thread coordination
- **Maintainability**: Clear module structure
- **Extensibility**: Easy to add new features
- **Reliability**: Comprehensive error handling
