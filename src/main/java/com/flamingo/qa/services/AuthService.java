package com.flamingo.qa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final ThreadLocal<String> TOKEN_HOLDER = new ThreadLocal<>();

    private final BookingService bookingService;

    public void initToken() {
        if (TOKEN_HOLDER.get() == null) {
            TOKEN_HOLDER.set(bookingService.getAuthToken());
        }
    }

    public String getToken() {
        return TOKEN_HOLDER.get();
    }

    public void clearToken() {
        TOKEN_HOLDER.remove();
    }
}
