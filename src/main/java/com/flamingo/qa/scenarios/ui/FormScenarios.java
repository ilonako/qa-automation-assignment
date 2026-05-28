package com.flamingo.qa.scenarios.ui;

import com.flamingo.qa.pages.FormPage;
import org.springframework.stereotype.Component;

@Component
public class FormScenarios {

    public String submitRegistrationForm(FormPage page,
                                         String firstName, String lastName, String email,
                                         String gender, String mobile, String dateOfBirth,
                                         String subject, String hobby, String filePath,
                                         String address, String state, String city) {
        page.fillName(firstName, lastName);
        page.fillEmail(email);
        page.selectGender(gender);
        page.fillMobile(mobile);
        page.fillDateOfBirth(dateOfBirth);
        page.fillSubject(subject);
        page.selectHobby(hobby);
        page.uploadFile(filePath);
        page.fillCurrentAddress(address);
        page.selectState(state);
        page.selectCity(city);
        page.submit();
        return page.getSuccessModalTitle();
    }
}
