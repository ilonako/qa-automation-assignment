package com.flamingo.qa.tests.ui;

import com.flamingo.qa.pages.FormPage;
import com.flamingo.qa.scenarios.ui.FormScenarios;
import com.flamingo.qa.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Form Submission")
class FormTest extends BaseUiTest {

    @Autowired
    private FormScenarios scenarios;

    private FormPage formPage;

    @BeforeEach
    void openForm() {
        formPage = new FormPage(page, config, uiComponents);
        formPage.navigate();
    }

    @Test
    @DisplayName("Fully completed registration form shows success modal")
    void submitFormSuccessfully() {
        String state = faker.options().option("NCR", "Uttar Pradesh", "Haryana", "Rajasthan");

        String modalTitle = scenarios.submitRegistrationForm(
                formPage,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                faker.options().option("Male", "Female", "Other"),
                faker.number().digits(10),
                LocalDate.now().minusYears(faker.number().numberBetween(18, 60))
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                faker.options().option("Sports", "Reading", "Music"),
                faker.address().streetAddress(),
                state,
                randomCity(state)
        );

        assertThat(modalTitle).isEqualTo("Thanks for submitting the form");
    }

    private String randomCity(String state) {
        return switch (state) {
            case "NCR" -> faker.options().option("Delhi", "Gurgaon", "Noida");
            case "Uttar Pradesh" -> faker.options().option("Agra", "Lucknow", "Merrut");
            case "Haryana" -> faker.options().option("Karnal", "Panipat");
            case "Rajasthan" -> faker.options().option("Jaipur", "Jaiselmer");
            default -> throw new IllegalArgumentException("Unknown state: " + state);
        };
    }
}
