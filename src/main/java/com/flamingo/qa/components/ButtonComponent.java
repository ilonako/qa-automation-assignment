package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import org.springframework.stereotype.Component;

@Component
public class ButtonComponent extends BaseComponent {

    public void clickOnButton(Locator locator) {
        waitForVisible(locator);
        locator.click();
    }

}
