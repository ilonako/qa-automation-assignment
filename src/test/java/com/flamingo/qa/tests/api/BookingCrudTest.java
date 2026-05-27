package com.flamingo.qa.tests.api;

import com.flamingo.qa.api.BookingApiClient;
import com.flamingo.qa.dto.booking.BookingDto;
import com.flamingo.qa.dto.booking.BookingResponseDto;
import com.flamingo.qa.faker.BookingDataFactory;
import com.flamingo.qa.listener.BookingTest;
import com.flamingo.qa.scenarios.api.BookingScenarios;
import com.flamingo.qa.services.AuthService;
import com.flamingo.qa.services.BookingService;
import com.flamingo.qa.tests.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@BookingTest
@DisplayName("Booking CRUD")
class BookingCrudTest extends BaseApiTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingScenarios bookingScenarios;
    @Autowired
    private BookingDataFactory bookingDataFactory;
    @Autowired
    private BookingApiClient bookingApiClient;

    @Test
    @DisplayName("Create booking returns a positive booking ID")
    void shouldCreateBookingAndReturnId() {
        BookingResponseDto response = bookingScenarios.createRandomBooking();

        assertThat(response.getBookingId())
                .as("bookingId")
                .isPositive();
    }

    @Test
    @DisplayName("Fetched booking matches the created booking fields")
    void shouldGetBookingById() {
        BookingDto booking = bookingDataFactory.randomBooking();
        BookingDto fetched = bookingScenarios.createAndFetchBooking(booking);

        validator.assertBookingEquals(booking, fetched);
    }

    @Test
    @DisplayName("Updated booking reflects the new field values")
    void shouldUpdateBooking() {
        BookingResponseDto created = bookingScenarios.createRandomBooking();
        BookingDto updated = bookingDataFactory.randomBooking();

        BookingDto result = bookingService.updateBooking(created.getBookingId(), updated, authService.getToken());

        validator.assertBookingEquals(updated, result);
    }

    @Test
    @DisplayName("Deleted booking returns 404 on subsequent fetch")
    void shouldDeleteBooking() {
        BookingResponseDto created = bookingScenarios.createRandomBooking();

        bookingService.deleteBooking(created.getBookingId(), authService.getToken());

        Response getResponse = bookingApiClient.getBooking(created.getBookingId());
        validator.assertStatusCode(getResponse, HttpStatus.NOT_FOUND);
    }
}
