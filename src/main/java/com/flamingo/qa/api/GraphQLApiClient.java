package com.flamingo.qa.api;

import com.flamingo.qa.dto.graphql.GraphQLRequestDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

/**
 * Hygraph GraphQL API client.
 * All operations go through a single POST to the GraphQL endpoint.
 *
 * @see <a href="https://hygraph.com/graphql-playground">GraphQL Playground</a>
 */
@Component
public class GraphQLApiClient {

    private final RequestSpecification spec;

    public GraphQLApiClient(ApiSpecFactory specFactory) {
        this.spec = specFactory.graphQlSpec();
    }

    public Response execute(GraphQLRequestDto request) {
        return given().spec(spec)
                .body(request)
                .post();
    }
}
