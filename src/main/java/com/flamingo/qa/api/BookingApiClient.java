package com.flamingo.qa.api;

import com.flamingo.qa.dto.booking.AuthRequestDto;
import com.flamingo.qa.dto.booking.BookingDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

/**
 * Restful Booker API client.
 * Public endpoints (auth, create, read) require no token.
 * Mutating endpoints (update, delete) require a session token via Cookie — per API spec.
 *
 * @see <a href="https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking">Booking API</a>
 */
@Component
public class BookingApiClient {

    private static final String BOOKING_BY_ID_PATH = "/booking/{id}";
    private static final String BOOKING_ID = "id";
    private static final String TOKEN_COOKIE = "token";

    private final RequestSpecification spec;

    public BookingApiClient(ApiSpecFactory specFactory) {
        this.spec = specFactory.bookingSpec();
    }

    public Response authenticate(AuthRequestDto request) {
        return given().spec(spec)
                .body(request)
                .post("/auth");
    }

    public Response createBooking(BookingDto booking) {
        return given().spec(spec)
                .body(booking)
                .post("/booking");
    }

    public Response getBooking(int id) {
        return given().spec(spec)
                .pathParam(BOOKING_ID, id)
                .get(BOOKING_BY_ID_PATH);
    }

    public Response updateBooking(int id, BookingDto booking, String token) {
        return given().spec(spec)
                .cookie(TOKEN_COOKIE, token)
                .pathParam(BOOKING_ID, id)
                .body(booking)
                .put(BOOKING_BY_ID_PATH);
    }

    public Response deleteBooking(int id, String token) {
        return given().spec(spec)
                .cookie(TOKEN_COOKIE, token)
                .pathParam(BOOKING_ID, id)
                .delete(BOOKING_BY_ID_PATH);
    }
}
