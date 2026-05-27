package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SearchComponent extends BaseComponent {

    private final Locator searchInput;

    public SearchComponent(Page page, String inputSelector) {
        super(page);
        this.searchInput = page.locator(inputSelector);
    }

    public void search(String keyword) {
        waitForVisible(searchInput);
        searchInput.clear();
        searchInput.fill(keyword);
    }

    public void clearSearch() {
        waitForVisible(searchInput);
        searchInput.clear();
    }
}
