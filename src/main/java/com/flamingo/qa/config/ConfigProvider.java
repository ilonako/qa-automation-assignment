package com.flamingo.qa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigProvider {

    private final ApiProperties api;
    private final UiProperties ui;

    public ApiProperties api() {
        return api;
    }

    public UiProperties ui() {
        return ui;
    }
}
