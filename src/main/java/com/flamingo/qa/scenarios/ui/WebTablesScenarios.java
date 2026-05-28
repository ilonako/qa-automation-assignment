package com.flamingo.qa.scenarios.ui;

import com.flamingo.qa.pages.WebTablesPage;
import org.springframework.stereotype.Component;

@Component
public class WebTablesScenarios {

    public void addRecord(WebTablesPage page, String firstName, String lastName,
                          int age, String email, int salary, String department) {
        page.clickAdd();
        page.fillForm(firstName, lastName, age, email, salary, department);
        page.clickSubmit();
    }

    public void editRecord(WebTablesPage page, int rowIndex, String firstName, String lastName,
                           int age, String email, int salary, String department) {
        page.clickEdit(rowIndex);
        page.fillForm(firstName, lastName, age, email, salary, department);
        page.clickSubmit();
    }

}
