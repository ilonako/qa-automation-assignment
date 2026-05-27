package com.flamingo.qa.pages;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;

public class WebTablesPage extends BasePage {

    private static final String PATH = "/webtables";

    private final Locator addButton;
    private final Locator searchInput;
    private final Locator rows;
    private final Locator columnHeaders;
    private final Locator submitButton;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator ageInput;
    private final Locator emailInput;
    private final Locator salaryInput;
    private final Locator departmentInput;

    public WebTablesPage(Page page, ConfigProvider config, UIComponents uiComponents) {
        super(page, config, uiComponents);
        this.addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add"));
        this.searchInput = page.getByPlaceholder("Type to search");
        this.rows = page.locator(".rt-tr-group");
        this.columnHeaders = page.locator(".rt-th");
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.firstNameInput = page.getByPlaceholder("First Name");
        this.lastNameInput = page.getByPlaceholder("Last Name");
        this.ageInput = page.getByPlaceholder("Age");
        this.emailInput = page.getByPlaceholder("name@example.com");
        this.salaryInput = page.getByPlaceholder("Salary");
        this.departmentInput = page.getByPlaceholder("Department");
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    public void addRecord(String firstName, String lastName, int age,
                          String email, int salary, String department) {
        components.button.clickOnButton(addButton);
        fillRegistrationForm(firstName, lastName, age, email, salary, department);
        components.button.clickOnButton(submitButton);
    }

    public void editRecord(int rowIndex, String firstName, String lastName, int age,
                           String email, int salary, String department) {
        components.table.clickRowAction(rows, rowIndex, "Edit");
        fillRegistrationForm(firstName, lastName, age, email, salary, department);
        components.button.clickOnButton(submitButton);
    }

    public void deleteRecord(int rowIndex) {
        components.table.clickRowAction(rows, rowIndex, "Delete");
    }

    public void search(String keyword) {
        components.search.search(searchInput, keyword);
    }

    public void clearSearch() {
        components.search.clearSearch(searchInput);
    }

    public int getRowCount() {
        return components.table.getRowCount(rows);
    }

    public String getCell(int rowIndex, int colIndex) {
        return components.table.getCell(rows, rowIndex, colIndex);
    }

    public List<String> getColumnValues(int colIndex) {
        return components.table.getColumnValues(rows, colIndex);
    }

    public void sortByColumn(int colIndex) {
        components.table.clickColumnHeader(columnHeaders, colIndex);
    }

    private void fillRegistrationForm(String firstName, String lastName, int age,
                                      String email, int salary, String department) {
        components.input.clearAndFill(firstNameInput, firstName);
        components.input.clearAndFill(lastNameInput, lastName);
        components.input.clearAndFill(ageInput, String.valueOf(age));
        components.input.clearAndFill(emailInput, email);
        components.input.clearAndFill(salaryInput, String.valueOf(salary));
        components.input.clearAndFill(departmentInput, department);
    }
}
