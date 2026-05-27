package com.flamingo.qa.pages;

import com.flamingo.qa.components.UIComponents;
import com.flamingo.qa.config.ConfigProvider;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class FormPage extends BasePage {

    private static final String PATH = "/automation-practice-form";

    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator emailInput;
    private final Locator mobileInput;
    private final Locator dateOfBirthInput;
    private final Locator currentAddressInput;
    private final Locator fileUpload;
    private final Locator submitButton;
    private final Locator successModal;

    public FormPage(Page page, ConfigProvider config, UIComponents uiComponents) {
        super(page, config, uiComponents);
        this.firstNameInput = page.getByPlaceholder("First Name");
        this.lastNameInput = page.getByPlaceholder("Last Name");
        this.emailInput = page.getByPlaceholder("name@example.com");
        this.mobileInput = page.getByPlaceholder("Mobile Number");
        this.dateOfBirthInput = page.locator("#dateOfBirthInput");
        this.currentAddressInput = page.getByPlaceholder("Current Address");
        this.fileUpload = page.locator("#uploadPicture");
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.successModal = page.locator("#example-modal-sizes-title-lg");
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    public void fillName(String firstName, String lastName) {
        components.input.fillInput(firstNameInput, firstName);
        components.input.fillInput(lastNameInput, lastName);
    }

    public void fillEmail(String email) {
        components.input.fillInput(emailInput, email);
    }

    public void selectGender(String gender) {
        components.button.clickOnButton(page.getByLabel(gender));
    }

    public void fillMobile(String mobile) {
        components.input.fillInput(mobileInput, mobile);
    }

    public void fillDateOfBirth(String date) {
        components.input.clearAndFill(dateOfBirthInput, date);
        dateOfBirthInput.press("Enter");
    }

    public void selectHobby(String hobby) {
        components.button.clickOnButton(page.getByLabel(hobby));
    }

    public void uploadFile(String filePath) {
        fileUpload.setInputFiles(java.nio.file.Paths.get(filePath));
    }

    public void fillCurrentAddress(String address) {
        components.input.fillInput(currentAddressInput, address);
    }

    public void selectState(String state) {
        page.locator("#state").click();
        page.getByText(state, new Page.GetByTextOptions().setExact(true)).click();
    }

    public void selectCity(String city) {
        page.locator("#city").click();
        page.getByText(city, new Page.GetByTextOptions().setExact(true)).click();
    }

    public void submit() {
        components.button.clickOnButton(submitButton);
    }

    public boolean isSuccessModalVisible() {
        return successModal.isVisible();
    }

    public String getSuccessModalTitle() {
        return successModal.innerText();
    }
}
