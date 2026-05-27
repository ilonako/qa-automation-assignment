package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.springframework.stereotype.Component;

@Component
public class ButtonComponent extends BaseComponent {

    public void clickOnButton(Locator locator) {
        waitForVisible(locator);
        locator.click();
    }

    public void clickAndNavigate(Locator locator, Page page) {
        waitForVisible(locator);
        locator.click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }
}
