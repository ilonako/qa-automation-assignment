package com.flamingo.qa.components;

import com.microsoft.playwright.Locator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TableComponent extends BaseComponent {

    public int getRowCount(Locator rows) {
        return rows.count();
    }

    public String getCell(Locator rows, int rowIndex, int colIndex) {
        return rows.nth(rowIndex).locator("td").nth(colIndex).innerText().trim();
    }

    public List<String> getColumnValues(Locator rows, int colIndex) {
        return rows.all().stream()
                .map(row -> row.locator("td").nth(colIndex).innerText().trim())
                .toList();
    }

    public void clickRowAction(Locator rows, int rowIndex, String title) {
        Locator action = rows.nth(rowIndex).getByTitle(title);
        waitForVisible(action);
        action.click();
    }

    public void clickColumnHeader(Locator headers, int colIndex) {
        Locator header = headers.nth(colIndex);
        waitForVisible(header);
        header.click();
    }
}
