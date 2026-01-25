package com.ollama.plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(
    name = "OllamaSettings",
    storages = @Storage("ollama_settings.xml")
)
public class OllamaSettingsService implements PersistentStateComponent<OllamaSettingsService> {

    public String host = "localhost";
    public int port = 11434;
    public String selectedModel = "";
    public boolean autoConnect = false;

    public static OllamaSettingsService getInstance() {
        return ApplicationManager.getApplication().getService(OllamaSettingsService.class);
    }

    @Nullable
    @Override
    public OllamaSettingsService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull OllamaSettingsService state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getBaseUrl() {
        return String.format("http://%s:%d", host, port);
    }
}
