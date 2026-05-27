package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import org.springframework.stereotype.Component;

@Component
public class SearchComponent extends BaseComponent {

    public void search(Locator inputLocator, String keyword) {
        waitForVisible(inputLocator);
        inputLocator.clear();
        inputLocator.fill(keyword);
    }

    public void clearSearch(Locator inputLocator) {
        waitForVisible(inputLocator);
        inputLocator.clear();
    }
}
