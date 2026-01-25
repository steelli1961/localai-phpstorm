package com.ollama.plugin.ui;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.ollama.plugin.client.OllamaClient;
import com.ollama.plugin.settings.OllamaSettingsService;

import javax.swing.*;
import java.awt.*;

public class SettingsConnectionTest {

    public static void testConnection(Component parent, String host, int port) {
        String baseUrl = String.format("http://%s:%d", host, port);
        
        // Show loading dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Testing Connection");
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(parent);
        
        JLabel statusLabel = new JLabel("Testing connection to " + baseUrl + "...");
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        
        dialog.setContentPane(panel);
        dialog.setVisible(true);
        
        // Test connection asynchronously
        OllamaClient.getInstance().isConnected(baseUrl).thenAccept(connected -> {
            dialog.dispose();
            if (connected) {
                JOptionPane.showMessageDialog(parent,
                    "Successfully connected to Ollama at " + baseUrl,
                    "Connection Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "Failed to connect to Ollama at " + baseUrl + "\n\n" +
                    "Please ensure:\n" +
                    "1. Ollama is running\n" +
                    "2. Host is correct: " + host + "\n" +
                    "3. Port is correct: " + port,
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }).exceptionally(ex -> {
            dialog.dispose();
            JOptionPane.showMessageDialog(parent,
                "Error testing connection:\n" + ex.getMessage(),
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }
}
