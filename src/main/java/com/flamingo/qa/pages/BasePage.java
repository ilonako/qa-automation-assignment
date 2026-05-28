package com.flamingo.qa.pages;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BasePage {

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
}
