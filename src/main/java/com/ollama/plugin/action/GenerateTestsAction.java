package com.ollama.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.ollama.plugin.client.OllamaClient;
import com.ollama.plugin.service.CodeAnalysisService;
import org.jetbrains.annotations.NotNull;

/**
 * Generate tests using Ollama
 */
public class GenerateTestsAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (project == null || editor == null) {
            return;
        }

        CodeAnalysisService analysisService = new CodeAnalysisService(OllamaClient.getInstance());
        String selectedCode = analysisService.getSelectedCode(editor);

        if (selectedCode == null || selectedCode.isEmpty()) {
            Messages.showWarningDialog(project, "Please select code first", "No Selection");
            return;
        }

        String language = analysisService.detectLanguage(editor);
        analysisService.generateTests(selectedCode, language, new CodeAnalysisService.RefactoringCallback() {
            @Override
            public void onSuccess(String result) {
                int option = Messages.showOkCancelDialog(project,
                        result,
                        "Generated Tests",
                        "Insert", "Cancel",
                        Messages.getInformationIcon());

                if (option == Messages.OK) {
                    analysisService.insertCodeAtCursor(editor, result);
                }
            }

            @Override
            public void onError(String error) {
                Messages.showErrorDialog(project, "Error: " + error, "Test Generation Failed");
            }
        });
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        event.getPresentation().setEnabled(editor != null && editor.getSelectionModel().hasSelection());
    }
}
