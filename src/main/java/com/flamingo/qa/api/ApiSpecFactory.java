package com.flamingo.qa.api;

import com.flamingo.qa.config.ConfigProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiSpecFactory {

    private final ConfigProvider config;

    public RequestSpecification bookingSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.api().getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept("application/json")
                .addFilters(List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()))
                .build();
    }

    public RequestSpecification graphQlSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.api().getGraphqlUrl())
                .setContentType(ContentType.JSON)
                .setAccept("application/json")
                .addFilters(List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()))
                .build();
    }

}
