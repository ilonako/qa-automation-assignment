package com.flamingo.qa.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingResponseDto {

    @JsonProperty("bookingid")
    private int bookingId;
    private BookingDto booking;
}
