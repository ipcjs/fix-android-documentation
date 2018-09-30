package com.github.ipcjs.fad;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.lang.documentation.ExternalDocumentationProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.android.AndroidDocumentationProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @see org.jetbrains.android.AndroidDocumentationProvider#fetchExternalDocumentation(Project, PsiElement, List)
 */
public class FixAndroidDocumentationProvider2 extends AndroidDocumentationProvider {
    @Override
    public String fetchExternalDocumentation(Project project, PsiElement element, List<String> docUrls) {
        return "fuck:" + super.fetchExternalDocumentation(project, element, docUrls);
    }
}