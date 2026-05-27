package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InputComponent extends BaseComponent {

    public InputComponent(Page page) {
        super(page);
    }

    public void fillInput(Locator locator, String value) {
        waitForVisible(locator);
        locator.fill(value);
    }

    public void clearAndFill(Locator locator, String value) {
        waitForVisible(locator);
        locator.clear();
        locator.fill(value);
    }

    public String getValue(Locator locator) {
        waitForVisible(locator);
        return locator.inputValue();
    }
}
