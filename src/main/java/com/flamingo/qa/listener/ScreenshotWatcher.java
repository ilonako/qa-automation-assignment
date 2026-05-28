package com.flamingo.qa.listener;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import lombok.NonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ScreenshotWatcher implements TestWatcher {

    private BrowserContext context;
    private Playwright playwright;

    public void setUp(BrowserContext context, Playwright playwright) {
        this.context = context;
        this.playwright = playwright;
    }

    @Override
    public void testFailed(@NonNull ExtensionContext extensionContext, Throwable cause) {
        if (context == null) return;
        try {
            String name = buildArtifactName(extensionContext);
            Path screenshotDir = Paths.get("target/screenshots");
            Path traceDir = Paths.get("target/traces");
            createDirectories(screenshotDir, traceDir);

            context.pages().stream().findFirst().ifPresent(page ->
                    page.screenshot(new Page.ScreenshotOptions()
                            .setPath(screenshotDir.resolve(name + ".png"))
                            .setFullPage(true)));
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(traceDir.resolve(name + ".zip")));
        } finally {
            closeResources();
        }
    }

    @Override
    public void testSuccessful(@NonNull ExtensionContext extensionContext) {
        closeResources();
    }

    @Override
    public void testAborted(@NonNull ExtensionContext extensionContext, Throwable cause) {
        closeResources();
    }

    @Override
    public void testDisabled(@NonNull ExtensionContext extensionContext, @NonNull Optional<String> reason) {
        closeResources();
    }

    private void closeResources() {
        try {
            if (context != null) {
                context.tracing().stop();
                context.close();
            }
        } finally {
            if (playwright != null) playwright.close();
            context = null;
            playwright = null;
        }
    }

    /**
     * The method builds a filesystem-safe filename for screenshots and traces
     * by combining the test class name and test display name.
     *
     * @return class name + method name + data
     */
    private String buildArtifactName(ExtensionContext extensionContext) {
        String className = extensionContext.getTestClass().map(Class::getSimpleName).orElse("unknown");
        String method = extensionContext.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        return className + "_" + method;
    }

    private void createDirectories(Path... dirs) {
        for (Path dir : dirs) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + dir, e);
            }
        }
    }
}