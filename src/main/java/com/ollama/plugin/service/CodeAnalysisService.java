package com.ollama.plugin.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.ollama.plugin.client.OllamaClient;
import com.ollama.plugin.client.OllamaResponse;
import com.ollama.plugin.settings.OllamaSettingsService;

/**
 * Service for code analysis, refactoring, and suggestions using Ollama
 */
public class CodeAnalysisService {
    private final OllamaClient ollamaClient;
    private final OllamaSettingsService settingsService;

    public CodeAnalysisService(OllamaClient ollamaClient) {
        this.ollamaClient = ollamaClient;
        this.settingsService = OllamaSettingsService.getInstance();
    }

    /**
     * Get code suggestions for the selected text with callback
     */
    public void suggestRefactoring(String selectedCode, String language, RefactoringCallback callback) {
        String prompt = String.format(
                "Analyze this %s code and suggest improvements for readability, performance, and best practices:\n\n```%s\n%s\n```\n\nProvide a refactored version with explanation.",
                language, language, selectedCode);
        performAnalysis(prompt, callback);
    }

    /**
     * Get explanation for selected code
     */
    public void explainCode(String selectedCode, String language, RefactoringCallback callback) {
        String prompt = String.format(
                "Explain this %s code in simple terms:\n\n```%s\n%s\n```",
                language, language, selectedCode);
        performAnalysis(prompt, callback);
    }

    /**
     * Generate unit tests for the code
     */
    public void generateTests(String selectedCode, String language, RefactoringCallback callback) {
        String prompt = String.format(
                "Generate comprehensive unit tests for this %s code:\n\n```%s\n%s\n```\n\nProvide tests using common testing frameworks.",
                language, language, selectedCode);
        performAnalysis(prompt, callback);
    }

    /**
     * Add type hints/annotations to code
     */
    public void addTypeHints(String selectedCode, String language, RefactoringCallback callback) {
        String prompt = String.format(
                "Add type hints and annotations to this %s code:\n\n```%s\n%s\n```\n\nProvide the annotated version.",
                language, language, selectedCode);
        performAnalysis(prompt, callback);
    }

    /**
     * Optimize code for performance
     */
    public void optimizeCode(String selectedCode, String language, RefactoringCallback callback) {
        String prompt = String.format(
                "Optimize this %s code for better performance:\n\n```%s\n%s\n```\n\nProvide the optimized version with explanation of changes.",
                language, language, selectedCode);
        performAnalysis(prompt, callback);
    }

    private void performAnalysis(String prompt, RefactoringCallback callback) {
        OllamaClient.OllamaResponseCallback wrappedCallback = new OllamaClient.OllamaResponseCallback() {
            @Override
            public void onSuccess(OllamaResponse response) {
                if (callback != null) {
                    callback.onSuccess(response.getResponse());
                }
            }

            @Override
            public void onError(String error) {
                if (callback != null) {
                    callback.onError(error);
                }
            }
        };

        // Use llama2 as default model
        ollamaClient.sendPrompt(settingsService.getBaseUrl(), "llama2", prompt, wrappedCallback);
    }

    /**
     * Insert code at cursor position
     */
    public void insertCodeAtCursor(Editor editor, String code) {
        ApplicationManager.getApplication().invokeLater(() -> {
            CommandProcessor.getInstance().executeCommand(
                    editor.getProject(),
                    () -> {
                        Document document = editor.getDocument();
                        int offset = editor.getCaretModel().getOffset();
                        ApplicationManager.getApplication().runWriteAction(() -> {
                            document.insertString(offset, code);
                        });
                    },
                    "Insert Code from Ollama",
                    null);
        });
    }

    /**
     * Replace selected text with new code
     */
    public void replaceSelectedCode(Editor editor, String newCode) {
        ApplicationManager.getApplication().invokeLater(() -> {
            CommandProcessor.getInstance().executeCommand(
                    editor.getProject(),
                    () -> {
                        Document document = editor.getDocument();
                        int start = editor.getSelectionModel().getSelectionStart();
                        int end = editor.getSelectionModel().getSelectionEnd();

                        if (start < end) {
                            ApplicationManager.getApplication().runWriteAction(() -> {
                                document.replaceString(start, end, newCode);
                            });
                        }
                    },
                    "Replace Code with Ollama Suggestion",
                    null);
        });
    }

    /**
     * Get the selected code from editor
     */
    public String getSelectedCode(Editor editor) {
        String selectedText = editor.getSelectionModel().getSelectedText();
        return selectedText != null && !selectedText.isEmpty() ? selectedText : null;
    }

    /**
     * Detect code language
     */
    public String detectLanguage(Editor editor) {
        String fileName = editor.getVirtualFile() != null ? editor.getVirtualFile().getName() : "";

        if (fileName.endsWith(".java"))
            return "java";
        if (fileName.endsWith(".php"))
            return "php";
        if (fileName.endsWith(".py"))
            return "python";
        if (fileName.endsWith(".js"))
            return "javascript";
        if (fileName.endsWith(".ts"))
            return "typescript";
        if (fileName.endsWith(".go"))
            return "go";
        if (fileName.endsWith(".rs"))
            return "rust";
        if (fileName.endsWith(".cpp") || fileName.endsWith(".cc"))
            return "cpp";

        return "unknown";
    }

    /**
     * Callback interface for refactoring results
     */
    public interface RefactoringCallback {
        void onSuccess(String result);

        void onError(String error);
    }
}
