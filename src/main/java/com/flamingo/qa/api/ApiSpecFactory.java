package com.flamingo.qa.api;

import com.flamingo.qa.config.ConfigProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiSpecFactory {

    private final ConfigProvider config;

    public RequestSpecification bookingSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.api().getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public RequestSpecification graphQlSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.api().getGraphqlUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public ResponseSpecification successSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
