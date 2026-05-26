package com.flamingo.qa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private String baseUrl;
    private String graphqlUrl;
    private int connectTimeout;
    private int readTimeout;
    private String username;
    private String password;
}
