package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import org.springframework.stereotype.Component;

@Component
public class InputComponent extends BaseComponent {

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
