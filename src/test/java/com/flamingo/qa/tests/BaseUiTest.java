package com.flamingo.qa.tests;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.flamingo.qa.listener.ScreenshotWatcher;
import com.microsoft.playwright.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
public abstract class BaseUiTest {

    protected final Faker faker = new Faker();
    @Autowired
    protected ConfigProvider config;
    @Autowired
    protected UIComponents uiComponents;
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @RegisterExtension
    protected final ScreenshotWatcher screenshotWatcher = new ScreenshotWatcher();

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(config.ui().isHeadless())
                .setSlowMo(config.ui().getSlowMo());
        browser = playwright.chromium().launch(options);
    }

    @BeforeEach
    void openPage() {
        BrowserContext context = browser.newContext();
        context.setDefaultTimeout(config.ui().getTimeout());
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        page = context.newPage();
        screenshotWatcher.setContext(context);
    }

    @AfterAll
    void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
