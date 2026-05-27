package com.flamingo.qa.tests;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.microsoft.playwright.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseUiTest {

    protected final Faker faker = new Faker();
    @Autowired
    protected ConfigProvider config;
    @Autowired
    protected UIComponents uiComponents;
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

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
        page = context.newPage();
    }

    @AfterEach
    void closePage() {
        if (page != null) {
            page.context().close();
        }
    }

    @AfterAll
    void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
