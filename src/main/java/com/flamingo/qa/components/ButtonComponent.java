package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ButtonComponent extends BaseComponent {

    public ButtonComponent(Page page) {
        super(page);
    }

    public void clickOnButton(Locator locator) {
        waitForVisible(locator);
        locator.click();
    }

    public void clickOnButton(String selector) {
        clickOnButton(page.locator(selector));
    }
}
