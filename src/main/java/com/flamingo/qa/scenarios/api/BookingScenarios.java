package com.flamingo.qa.scenarios.api;

import com.flamingo.qa.dto.booking.BookingDto;
import com.flamingo.qa.dto.booking.BookingResponseDto;
import com.flamingo.qa.faker.BookingDataFactory;
import com.flamingo.qa.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingScenarios {

    private final BookingService bookingService;
    private final BookingDataFactory bookingDataFactory;

    public BookingResponseDto createRandomBooking() {
        return bookingService.createBooking(bookingDataFactory.randomBooking());
    }

    public BookingDto createAndFetchBooking(BookingDto booking) {
        BookingResponseDto created = bookingService.createBooking(booking);
        return bookingService.getBooking(created.getBookingId());
    }

}
