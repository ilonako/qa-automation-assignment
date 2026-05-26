package com.flamingo.qa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ui")
public class UiProperties {

    private String baseUrl;
    private String browser;
    private boolean headless;
    private int slowMo;
    private int timeout;
}
