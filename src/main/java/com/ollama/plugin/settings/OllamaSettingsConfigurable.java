package com.ollama.plugin.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class OllamaSettingsConfigurable implements Configurable {

    private OllamaSettingsPanel settingsPanel;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Ollama Configuration";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsPanel = new OllamaSettingsPanel();
        return settingsPanel.getPanel();
    }

    @Override
    public boolean isModified() {
        return settingsPanel != null && settingsPanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        if (settingsPanel != null) {
            settingsPanel.apply();
        }
    }

    @Override
    public void reset() {
        if (settingsPanel != null) {
            settingsPanel.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        settingsPanel = null;
    }
}
