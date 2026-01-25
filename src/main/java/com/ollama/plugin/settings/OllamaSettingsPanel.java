package com.ollama.plugin.settings;

import javax.swing.*;

public class OllamaSettingsPanel {

    private JPanel mainPanel;
    private JTextField hostField;
    private JSpinner portSpinner;
    private JButton testConnectionButton;
    private JLabel connectionStatusLabel;
    private JCheckBox autoConnectCheckBox;

    public OllamaSettingsPanel() {
        createUI();
        reset();
    }

    private void createUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Host configuration
        JPanel hostPanel = new JPanel();
        hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.X_AXIS));
        hostPanel.add(new JLabel("Ollama Host:"));
        hostField = new JTextField(20);
        hostPanel.add(hostField);
        mainPanel.add(hostPanel);

        mainPanel.add(Box.createVerticalStrut(10));

        // Port configuration
        JPanel portPanel = new JPanel();
        portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
        portPanel.add(new JLabel("Port:"));
        portSpinner = new JSpinner(new SpinnerNumberModel(11434, 1, 65535, 1));
        portPanel.add(portSpinner);
        mainPanel.add(portPanel);

        mainPanel.add(Box.createVerticalStrut(10));

        // Auto-connect checkbox
        autoConnectCheckBox = new JCheckBox("Auto-connect on startup");
        mainPanel.add(autoConnectCheckBox);

        mainPanel.add(Box.createVerticalStrut(10));

        // Test connection button
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.X_AXIS));
        testConnectionButton = new JButton("Test Connection");
        testConnectionButton.addActionListener(e -> testConnection());
        connectionPanel.add(testConnectionButton);
        connectionStatusLabel = new JLabel("Not connected");
        connectionPanel.add(Box.createHorizontalStrut(10));
        connectionPanel.add(connectionStatusLabel);
        mainPanel.add(connectionPanel);

        mainPanel.add(Box.createVerticalGlue());
    }

    private void testConnection() {
        String host = hostField.getText();
        int port = (Integer) portSpinner.getValue();
        
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel,
                "Please enter a host",
                "Empty Host",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        connectionStatusLabel.setText("Testing...");
        com.ollama.plugin.ui.SettingsConnectionTest.testConnection(mainPanel, host, port);
    }

    public JComponent getPanel() {
        return mainPanel;
    }

    public boolean isModified() {
        OllamaSettingsService settings = OllamaSettingsService.getInstance();
        return !hostField.getText().equals(settings.host) ||
                !portSpinner.getValue().equals(settings.port) ||
                autoConnectCheckBox.isSelected() != settings.autoConnect;
    }

    public void apply() {
        OllamaSettingsService settings = OllamaSettingsService.getInstance();
        settings.host = hostField.getText();
        settings.port = (Integer) portSpinner.getValue();
        settings.autoConnect = autoConnectCheckBox.isSelected();
    }

    public void reset() {
        OllamaSettingsService settings = OllamaSettingsService.getInstance();
        hostField.setText(settings.host);
        portSpinner.setValue(settings.port);
        autoConnectCheckBox.setSelected(settings.autoConnect);
        connectionStatusLabel.setText("Not connected");
    }
}
