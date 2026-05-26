package com.flamingo.qa.faker;

import com.flamingo.qa.config.ApiProperties;
import com.flamingo.qa.dto.booking.AuthRequestDto;
import com.flamingo.qa.dto.booking.BookingDatesDto;
import com.flamingo.qa.dto.booking.BookingDto;
import com.flamingo.qa.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingDataFactory {

    private final ApiProperties apiProperties;
    private final Faker faker = new Faker();

    public BookingDto randomBooking() {
        return BookingDto.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(50, 1000))
                .depositPaid(faker.bool().bool())
                .bookingDates(randomBookingDates())
                .additionalNeeds(faker.options().option("Breakfast", "Lunch", "Dinner", "Airport transfer"))
                .build();
    }

    public BookingDatesDto randomBookingDates() {
        return BookingDatesDto.builder()
                .checkIn(DateUtils.toBookingDate(DateUtils.futureDate(1)))
                .checkout(DateUtils.toBookingDate(DateUtils.futureDate(5)))
                .build();
    }

    public AuthRequestDto adminCredentials() {
        return AuthRequestDto.builder()
                .username(apiProperties.getUsername())
                .password(apiProperties.getPassword())
                .build();
    }
}
