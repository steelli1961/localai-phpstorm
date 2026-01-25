package com.ollama.plugin.ui;

import com.intellij.openapi.project.Project;
import com.ollama.plugin.client.OllamaClient;
import com.ollama.plugin.client.OllamaModel;
import com.ollama.plugin.client.OllamaResponse;
import com.ollama.plugin.settings.OllamaSettingsService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OllamaChatPanel {

    private final Project project;
    private final OllamaClient ollamaClient;
    private final OllamaSettingsService settingsService;

    private JPanel mainPanel;
    private JComboBox<OllamaModel> modelSelector;
    private JButton refreshModelsButton;
    private JButton connectButton;
    private JLabel connectionStatusLabel;

    private JTextArea promptInput;
    private JButton sendButton;

    private JTextArea resultOutput;
    private JLabel metricsLabel;

    private JProgressBar progressBar;

    public OllamaChatPanel(Project project) {
        this.project = project;
        this.ollamaClient = OllamaClient.getInstance();
        this.settingsService = OllamaSettingsService.getInstance();
        createUI();
        initializeModels();
    }

    private void createUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));

        // Top panel: Connection and model selection
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Middle panel: Prompt input
        JPanel promptPanel = createPromptPanel();
        mainPanel.add(promptPanel, BorderLayout.CENTER);

        // Bottom panel: Results and metrics
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Connection & Model Selection"));

        // Connection status row
        JPanel statusRow = new JPanel();
        statusRow.setLayout(new BoxLayout(statusRow, BoxLayout.X_AXIS));
        statusRow.add(new JLabel("Status:"));
        connectionStatusLabel = new JLabel("Disconnected");
        connectionStatusLabel.setForeground(Color.RED);
        statusRow.add(Box.createHorizontalStrut(5));
        statusRow.add(connectionStatusLabel);
        statusRow.add(Box.createHorizontalStrut(10));
        
        connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> connectToOllama());
        statusRow.add(connectButton);
        statusRow.add(Box.createHorizontalGlue());
        panel.add(statusRow);

        panel.add(Box.createVerticalStrut(5));

        // Model selection row
        JPanel modelRow = new JPanel();
        modelRow.setLayout(new BoxLayout(modelRow, BoxLayout.X_AXIS));
        modelRow.add(new JLabel("Model:"));
        modelSelector = new JComboBox<>();
        modelRow.add(Box.createHorizontalStrut(5));
        modelRow.add(modelSelector);
        modelRow.add(Box.createHorizontalStrut(5));
        
        refreshModelsButton = new JButton("Refresh Models");
        refreshModelsButton.addActionListener(e -> refreshModels());
        modelRow.add(refreshModelsButton);
        modelRow.add(Box.createHorizontalGlue());
        panel.add(modelRow);

        return panel;
    }

    private JPanel createPromptPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Prompt"));

        promptInput = new JTextArea(5, 40);
        promptInput.setLineWrap(true);
        promptInput.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(promptInput);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        buttonPanel.add(progressBar);
        
        buttonPanel.add(Box.createHorizontalGlue());
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendPrompt());
        buttonPanel.add(sendButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));

        // Results panel
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout(5, 5));
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));
        
        resultOutput = new JTextArea(8, 40);
        resultOutput.setEditable(false);
        resultOutput.setLineWrap(true);
        resultOutput.setWrapStyleWord(true);
        JScrollPane resultsScrollPane = new JScrollPane(resultOutput);
        resultsPanel.add(resultsScrollPane, BorderLayout.CENTER);

        panel.add(resultsPanel, BorderLayout.CENTER);

        // Metrics panel
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BoxLayout(metricsPanel, BoxLayout.X_AXIS));
        metricsPanel.setBorder(BorderFactory.createTitledBorder("Metrics"));
        
        metricsLabel = new JLabel("Memory: - | CPU: - | Tokens/sec: -");
        metricsPanel.add(metricsLabel);
        metricsPanel.add(Box.createHorizontalGlue());
        
        panel.add(metricsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void connectToOllama() {
        String baseUrl = settingsService.getBaseUrl();
        progressBar.setVisible(true);
        
        ollamaClient.isConnected(baseUrl).thenAccept(connected -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setVisible(false);
                if (connected) {
                    connectionStatusLabel.setText("Connected");
                    connectionStatusLabel.setForeground(new Color(0, 128, 0));
                    refreshModels();
                } else {
                    connectionStatusLabel.setText("Failed to connect");
                    connectionStatusLabel.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(mainPanel, 
                        "Failed to connect to Ollama at " + baseUrl,
                        "Connection Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setVisible(false);
                connectionStatusLabel.setText("Error");
                connectionStatusLabel.setForeground(Color.RED);
            });
            return null;
        });
    }

    private void refreshModels() {
        String baseUrl = settingsService.getBaseUrl();
        progressBar.setVisible(true);
        
        ollamaClient.fetchModels(baseUrl).thenAccept(models -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setVisible(false);
                modelSelector.removeAllItems();
                for (OllamaModel model : models) {
                    modelSelector.addItem(model);
                }
                if (models.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel,
                        "No models found. Please download a model in Ollama first.",
                        "No Models",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setVisible(false);
                JOptionPane.showMessageDialog(mainPanel,
                    "Failed to fetch models: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            });
            return null;
        });
    }

    private void sendPrompt() {
        if (modelSelector.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(mainPanel,
                "Please select a model first",
                "No Model Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String prompt = promptInput.getText().trim();
        if (prompt.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel,
                "Please enter a prompt",
                "Empty Prompt",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        OllamaModel selectedModel = (OllamaModel) modelSelector.getSelectedItem();
        String baseUrl = settingsService.getBaseUrl();
        
        progressBar.setVisible(true);
        sendButton.setEnabled(false);
        resultOutput.setText("Generating response...");

        ollamaClient.sendPrompt(baseUrl, selectedModel.getName(), prompt, 
            new OllamaClient.OllamaResponseCallback() {
                @Override
                public void onSuccess(OllamaResponse response) {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setVisible(false);
                        sendButton.setEnabled(true);
                        resultOutput.setText(response.getResponse());
                        updateMetrics(response);
                    });
                }

                @Override
                public void onError(String error) {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setVisible(false);
                        sendButton.setEnabled(true);
                        resultOutput.setText("Error: " + error);
                        metricsLabel.setText("Memory: - | CPU: - | Tokens/sec: -");
                    });
                }
            }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setVisible(false);
                sendButton.setEnabled(true);
                resultOutput.setText("Exception: " + ex.getMessage());
            });
            return null;
        });
    }

    private void updateMetrics(OllamaResponse response) {
        String tokensPerSec = String.format("%.2f", response.getTokensPerSecond());
        metricsLabel.setText(String.format(
            "Tokens: %d | Tokens/sec: %s | Duration: %.2fs",
            response.getTokenCount(),
            tokensPerSec,
            response.getTotalDuration() / 1_000_000_000.0
        ));
    }

    private void initializeModels() {
        // Try to auto-connect if enabled
        if (settingsService.autoConnect) {
            connectToOllama();
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
