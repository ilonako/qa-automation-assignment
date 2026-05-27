package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BaseComponent {

    protected final Page page;

    protected BaseComponent(Page page) {
        this.page = page;
    }

    protected void waitForVisible(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    protected void waitForHidden(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }
}
