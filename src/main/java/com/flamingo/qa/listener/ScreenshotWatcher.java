package com.flamingo.qa.listener;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import lombok.NonNull;
import lombok.Setter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Setter
public class ScreenshotWatcher implements TestWatcher {

    private BrowserContext context;

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
            context.close();
            context = null;
        }
    }

    @Override
    public void testSuccessful(@NonNull ExtensionContext extensionContext) {
        closeQuietly();
    }

    @Override
    public void testAborted(@NonNull ExtensionContext extensionContext, Throwable cause) {
        closeQuietly();
    }

    @Override
    public void testDisabled(@NonNull ExtensionContext extensionContext, @NonNull Optional<String> reason) {
        closeQuietly();
    }

    private void closeQuietly() {
        if (context != null) {
            try {
                context.tracing().stop();
                context.close();
            } finally {
                context = null;
            }
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
