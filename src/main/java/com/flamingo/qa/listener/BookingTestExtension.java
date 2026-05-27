package com.flamingo.qa.listener;

import com.flamingo.qa.services.AuthService;
import lombok.NonNull;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class BookingTestExtension implements BeforeEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(@NonNull ExtensionContext context) {
        authService(context).initToken();
    }

    @Override
    public void afterAll(@NonNull ExtensionContext context) {
        authService(context).clearToken();
    }

    private AuthService authService(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context).getBean(AuthService.class);
    }
}
