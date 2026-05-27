package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class TableComponent extends BaseComponent {

    private final Locator rows;
    private final Locator headers;

    public TableComponent(Page page, String rowSelector, String headerSelector) {
        super(page);
        this.rows = page.locator(rowSelector);
        this.headers = page.locator(headerSelector);
    }

    public int getRowCount() {
        return rows.count();
    }

    public String getCell(int rowIndex, int colIndex) {
        return rows.nth(rowIndex).locator("div.rt-td").nth(colIndex).innerText().trim();
    }

    public List<String> getColumnValues(int colIndex) {
        return rows.all().stream()
                .map(row -> row.locator("div.rt-td").nth(colIndex).innerText().trim())
                .toList();
    }

    public void clickRowAction(int rowIndex, String actionSelector) {
        Locator action = rows.nth(rowIndex).locator(actionSelector);
        waitForVisible(action);
        action.click();
    }

    public void clickColumnHeader(int colIndex) {
        Locator header = headers.nth(colIndex);
        waitForVisible(header);
        header.click();
    }
}
