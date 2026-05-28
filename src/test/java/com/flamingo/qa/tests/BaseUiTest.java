package com.flamingo.qa.tests;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.flamingo.qa.listener.ScreenshotWatcher;
import com.microsoft.playwright.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Tag("ui")
@SpringBootTest
public abstract class BaseUiTest {

    protected final Faker faker = new Faker();
    @RegisterExtension
    protected final ScreenshotWatcher screenshotWatcher = new ScreenshotWatcher();
    @Autowired
    protected ConfigProvider config;
    @Autowired
    protected UIComponents uiComponents;
    protected Page page;

    @BeforeEach
    void setUp() {
        // Local OS fix
        Playwright playwright = Playwright.create(new Playwright.CreateOptions()
                .setEnv(Map.of("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1")));

        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(config.ui().isHeadless())
                .setSlowMo(config.ui().getSlowMo()));
        // ScreenshotWatcher manages closable functions

        BrowserContext context = browser.newContext();
        context.setDefaultTimeout(config.ui().getTimeout());
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        page = context.newPage();
        screenshotWatcher.setUp(context, playwright);
    }
}