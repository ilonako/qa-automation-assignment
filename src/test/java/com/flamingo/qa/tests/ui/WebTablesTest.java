package com.flamingo.qa.tests.ui;

import com.flamingo.qa.pages.WebTablesPage;
import com.flamingo.qa.scenarios.ui.WebTablesScenarios;
import com.flamingo.qa.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Web Tables")
class WebTablesTest extends BaseUiTest {

    @Autowired
    private WebTablesScenarios scenarios;

    private WebTablesPage webTablesPage;

    @BeforeEach
    void openWebTables() {
        webTablesPage = new WebTablesPage(page, config, uiComponents);
        webTablesPage.navigate();
    }

    @Test
    @DisplayName("Added record appears in search results")
    void addNewRecord() {
        String firstName = faker.name().firstName();

        addRandomRecord(firstName);
        webTablesPage.search(firstName);

        assertThat(webTablesPage.getCell(0, 0)).isEqualTo(firstName);
    }

    @Test
    @DisplayName("Edited record reflects updated values in the table")
    void editExistingRecord() {
        String newFirstName = faker.name().firstName();

        scenarios.editRecord(webTablesPage, 0, newFirstName, faker.name().lastName(),
                faker.number().numberBetween(18, 65), faker.internet().emailAddress(),
                faker.number().numberBetween(30_000, 150_000),
                faker.options().option("QA", "Engineering", "Management", "HR"));

        assertThat(webTablesPage.getCell(0, 0)).isEqualTo(newFirstName);
    }

    @Test
    @DisplayName("Deleted record is no longer present in search results")
    void deleteRecord() {
        String firstName = faker.name().firstName();

        addRandomRecord(firstName);
        webTablesPage.search(firstName);
        assertThat(webTablesPage.getCell(0, 0)).isEqualTo(firstName);

        webTablesPage.deleteRecord(0);

        assertThat(webTablesPage.getRowCount()).isZero();
    }

    @Test
    @DisplayName("Search filters table to matching records; clearing search restores all rows")
    void searchFiltersByFirstName() {
        String firstName = faker.name().firstName();

        addRandomRecord(firstName);
        webTablesPage.search(firstName);
        assertThat(webTablesPage.getCell(0, 0)).isEqualTo(firstName);

        webTablesPage.clearSearch();
        List<String> allNames = webTablesPage.getColumnValues(0).stream()
                .filter(s -> !s.isBlank()).toList();
        assertThat(allNames).hasSizeGreaterThan(1);
    }

    private void addRandomRecord(String firstName) {
        scenarios.addRecord(webTablesPage, firstName, faker.name().lastName(),
                faker.number().numberBetween(18, 65), faker.internet().emailAddress(),
                faker.number().numberBetween(30_000, 150_000),
                faker.options().option("QA", "Engineering", "Management", "HR"));
    }

    @Test
    @DisplayName("Last name column is sorted in ascending order after clicking the header")
    void sortLastNameColumnAscending() {
        webTablesPage.sortByColumn(1);

        List<String> nonEmpty = webTablesPage.getColumnValues(1).stream()
                .filter(s -> !s.isBlank()).toList();
        assertThat(nonEmpty).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);
    }
}
