package com.flamingo.qa.tests.api;

import com.flamingo.qa.services.BookingService;
import com.flamingo.qa.tests.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Booking Authentication")
class BookingAuthTest extends BaseApiTest {

    @Autowired
    private BookingService bookingService;

    @Test
    @DisplayName("Valid credentials return a non-empty auth token")
    void shouldReturnValidToken() {
        String token = bookingService.getAuthToken();
        validator.assertTokenNotEmpty(token);
    }
}
