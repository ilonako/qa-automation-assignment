package com.flamingo.qa.services;

import com.flamingo.qa.api.BookingApiClient;
import com.flamingo.qa.dto.booking.BookingDto;
import com.flamingo.qa.dto.booking.BookingResponseDto;
import com.flamingo.qa.faker.BookingDataFactory;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingApiClient bookingApiClient;
    private final BookingDataFactory bookingDataFactory;

    public String getAuthToken() {
        return bookingApiClient
                .authenticate(bookingDataFactory.adminCredentials())
                .then()
                .statusCode(OK.value())
                .extract()
                .path("token");
    }

    public BookingResponseDto createBooking(BookingDto booking) {
        return bookingApiClient
                .createBooking(booking)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(BookingResponseDto.class);
    }

    public BookingDto getBooking(int id) {
        return bookingApiClient
                .getBooking(id)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(BookingDto.class);
    }

    public BookingDto updateBooking(int id, BookingDto booking, String token) {
        return bookingApiClient
                .updateBooking(id, booking, token)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(BookingDto.class);
    }

    public Response deleteBooking(int id, String token) {
        return bookingApiClient
                .deleteBooking(id, token)
                .then()
                .statusCode(CREATED.value())
                .extract()
                .response();
    }
}
