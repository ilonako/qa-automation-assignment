package com.flamingo.qa.tests.ui;

import com.flamingo.qa.pages.WebTablesPage;
import com.flamingo.qa.scenarios.ui.WebTablesScenarios;
import com.flamingo.qa.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest(name = "{0}")
    @MethodSource("recordProvider")
    @DisplayName("Added record appears in search results by first name")
    void addAndFindRecord(TableRecord tableRecord) {
        String cell = scenarios.addAndFind(webTablesPage,
                tableRecord.firstName(), tableRecord.lastName(), tableRecord.age(),
                tableRecord.email(), tableRecord.salary(), tableRecord.department());

        assertThat(cell).isEqualTo(tableRecord.firstName());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("recordProvider")
    @DisplayName("Edited record reflects updated first name in the table")
    void editRecord(TableRecord tableRecord) {
        scenarios.editRecord(webTablesPage, 0,
                tableRecord.firstName(), tableRecord.lastName(), tableRecord.age(),
                tableRecord.email(), tableRecord.salary(), tableRecord.department());

        assertThat(webTablesPage.getCell(0, 0)).isEqualTo(tableRecord.firstName());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("recordProvider")
    @DisplayName("Deleted record is no longer visible when searched")
    void deleteRecord(TableRecord tableRecord) {
        scenarios.addRecord(webTablesPage,
                tableRecord.firstName(), tableRecord.lastName(), tableRecord.age(),
                tableRecord.email(), tableRecord.salary(), tableRecord.department());
        webTablesPage.search(tableRecord.firstName());
        assertThat(webTablesPage.getCell(0, 0)).isEqualTo(tableRecord.firstName());

        webTablesPage.deleteRecord(0);

        assertThat(webTablesPage.getCell(0, 0)).isEmpty();
    }

    @Test
    @DisplayName("Last name column values are sorted in ascending order")
    void sortLastNameColumnAscending() {
        webTablesPage.sortByColumn(1);

        List<String> lastNames = webTablesPage.getColumnValues(1);
        List<String> nonEmpty = lastNames.stream().filter(s -> !s.isBlank()).toList();

        assertThat(nonEmpty).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);
    }


    Stream<Arguments> recordProvider() {
        return Stream.of(
                createProfile("junior profile", 18, 25, 30_000, 50_000, "QA"),
                createProfile("mid-level profile", 26, 40, 50_000, 100_000, "Engineering"),
                createProfile("senior profile", 41, 65, 100_000, 150_000, "Management")
        );
    }

    private Arguments createProfile(
            String profileName,
            int minAge,
            int maxAge,
            int minSalary,
            int maxSalary,
            String department
    ) {
        TableRecord tableRecord = new TableRecord(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.number().numberBetween(minAge, maxAge),
                faker.internet().emailAddress(),
                faker.number().numberBetween(minSalary, maxSalary),
                department
        );

        return Arguments.of(Named.of(profileName, tableRecord));
    }

    record TableRecord(String firstName, String lastName, int age,
                       String email, int salary, String department) {
    }
}
