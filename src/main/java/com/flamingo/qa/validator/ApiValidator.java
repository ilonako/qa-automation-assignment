package com.flamingo.qa.validator;

import com.flamingo.qa.dto.booking.BookingDto;
import com.flamingo.qa.dto.graphql.GraphQLResponseDto;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ApiValidator {

    public void assertStatusCode(Response response, HttpStatus expectedStatus) {
        assertThat(response.statusCode())
                .withFailMessage("Expected HTTP status %s but got %d", expectedStatus, response.statusCode())
                .isEqualTo(expectedStatus.value());
    }

    public void assertTokenNotEmpty(String token) {
        assertThat(token)
                .as("auth token")
                .isNotNull()
                .isNotEmpty();
    }

    public void assertBookingEquals(BookingDto expected, BookingDto actual) {
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(actual.getFirstname()).as("firstname").isEqualTo(expected.getFirstname());
            soft.assertThat(actual.getLastname()).as("lastname").isEqualTo(expected.getLastname());
            soft.assertThat(actual.getTotalPrice()).as("totalPrice").isEqualTo(expected.getTotalPrice());
            soft.assertThat(actual.isDepositPaid()).as("depositPaid").isEqualTo(expected.isDepositPaid());
            soft.assertThat(actual.getAdditionalNeeds()).as("additionalNeeds").isEqualTo(expected.getAdditionalNeeds());
        });
    }

    public void assertGraphQLSuccess(GraphQLResponseDto response) {
        assertThat(response.hasErrors())
                .withFailMessage("GraphQL response must not contain errors but got: %s", response.getErrors())
                .isFalse();
        assertThat(response.hasData())
                .withFailMessage("GraphQL response must contain data but got none")
                .isTrue();
    }

    public void assertGraphQLErrors(GraphQLResponseDto response) {
        assertThat(response.hasErrors())
                .withFailMessage("GraphQL response must contain errors but none were returned")
                .isTrue();
        assertThat(response.getErrors())
                .as("errors[].message")
                .allSatisfy(error -> assertThat(error.getMessage()).isNotEmpty());
    }

    public void assertGraphQLNullData(GraphQLResponseDto response) {
        assertThat(response.hasData())
                .withFailMessage("GraphQL response data must be null for non-existent entity but got: %s", response.getData())
                .isFalse();
    }
}
