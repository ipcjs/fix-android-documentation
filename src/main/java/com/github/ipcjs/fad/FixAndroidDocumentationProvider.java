package com.github.ipcjs.fad;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.lang.documentation.ExternalDocumentationProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @see org.jetbrains.android.AndroidDocumentationProvider#fetchExternalDocumentation(Project, PsiElement, List)
 */
public class FixAndroidDocumentationProvider extends AbstractDocumentationProvider implements ExternalDocumentationProvider {
    private static final Logger LOG = Logger.getInstance(FixAndroidDocumentationProvider.class);

    @Nullable
    @Override
    public String fetchExternalDocumentation(Project project, PsiElement element, List<String> docUrls) {
        return null;
    }

    @Override
    public boolean hasDocumentationFor(PsiElement element, PsiElement originalElement) {
        return false;
    }

    @Override
    public boolean canPromptToConfigureDocumentation(PsiElement element) {
        return false;
    }

    @Override
    public void promptToConfigureDocumentation(PsiElement element) {

    }
}
