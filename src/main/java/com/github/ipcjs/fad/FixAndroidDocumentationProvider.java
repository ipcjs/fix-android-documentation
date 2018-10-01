package com.github.ipcjs.fad;

import com.android.annotations.VisibleForTesting;
import com.intellij.codeInsight.javadoc.JavaDocExternalFilter;
import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.ExternalDocumentationProvider;
import com.intellij.lang.java.JavaDocumentationProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see org.jetbrains.android.AndroidDocumentationProvider#fetchExternalDocumentation(Project, PsiElement, List)
 */
public class FixAndroidDocumentationProvider extends AbstractDocumentationProvider implements ExternalDocumentationProvider {
    private static final Logger LOG = Logger.getInstance(FixAndroidDocumentationProvider.class);


    @Override
    public String fetchExternalDocumentation(final Project project, final PsiElement element, final List<String> docUrls) {
        LOG.debug("fetchExternalDocumentation", docUrls);
        return JavaDocumentationProvider.fetchExternalJavadoc(element, docUrls, new MyDocExternalFilter(project));
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

    @VisibleForTesting
    static class MyDocExternalFilter extends JavaDocExternalFilter {
        MyDocExternalFilter(Project project) {
            super(project);
        }

        private static final Pattern ourClassDataStartPattern = Pattern.compile("START OF CLASS DATA", Pattern.CASE_INSENSITIVE);
        private static final Pattern ourClassDataEndPattern = Pattern.compile("SUMMARY ========", Pattern.CASE_INSENSITIVE);
        //        private static final Pattern ourNonClassDataEndPattern = Pattern.compile("<A (NAME|ID)=", Pattern.CASE_INSENSITIVE);
        private static final Pattern ourNonClassDataEndPattern = Pattern.compile("<h3 class=\"api-name\" id=\"", Pattern.CASE_INSENSITIVE);

        @NotNull
        @Override
        protected ParseSettings getParseSettings(@NotNull String url) {
            return url.endsWith(JavaDocumentationProvider.PACKAGE_SUMMARY_FILE) ? super.getParseSettings(url) : getParseSettingsFix(url);
        }

        @NotNull
        protected ParseSettings getParseSettingsFix(@NotNull String url) {
            Pattern startSection = ourClassDataStartPattern;
            Pattern endSection = ourClassDataEndPattern;
            boolean anchorPresent = false;

            Matcher anchorMatcher = ourAnchorSuffix.matcher(url);
            if (anchorMatcher.find()) {
                anchorPresent = true;
                startSection = Pattern.compile("<h3 class=\"api-name\" id=\"" + Pattern.quote(anchorMatcher.group(1).replace(" ", "%20")) + "\"", Pattern.CASE_INSENSITIVE);
                endSection = ourNonClassDataEndPattern;
            }
            return new ParseSettings(startSection, endSection, !anchorPresent, anchorPresent);
        }
    }
}
