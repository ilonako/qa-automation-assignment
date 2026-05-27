package com.flamingo.qa.pages;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public abstract class BasePage {

    private static final String REPORT_DIR = "target/playwright-report/screenshots";

    protected final Page page;
    protected final ConfigProvider config;
    protected final UIComponents components;

    protected BasePage(Page page, ConfigProvider config, UIComponents components) {
        this.page = page;
        this.config = config;
        this.components = components;
    }

    protected abstract String getPath();

    public void navigate() {
        String url = config.ui().getBaseUrl() + getPath();
        log.debug("Navigating to {}", url);
        page.navigate(url);
    }

    public String getTitle() {
        return page.title();
    }

    public void takeScreenshot(String testName) {
        Path screenshotPath = Paths.get(REPORT_DIR, testName + ".png");
        try {
            Files.createDirectories(screenshotPath.getParent());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create screenshot directory", e);
        }
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(screenshotPath)
                .setType(ScreenshotType.PNG)
                .setFullPage(true));
        log.info("Screenshot saved: {}", screenshotPath);
    }
}
