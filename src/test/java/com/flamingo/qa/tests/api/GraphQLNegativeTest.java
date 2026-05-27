package com.flamingo.qa.tests.api;

import com.flamingo.qa.dto.graphql.GraphQLResponseDto;
import com.flamingo.qa.services.GraphQLService;
import com.flamingo.qa.tests.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GraphQL Negative Tests")
class GraphQLNegativeTest extends BaseApiTest {

    @Autowired
    private GraphQLService graphQLService;

    @Test
    @DisplayName("Non-existent movie ID returns null data for that entity")
    void returnNullMovieForNonExistentId() {
        Response response = graphQLService.queryMovieById("non-existent-id-00000000");
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        assertThat(dto.getData().get("movie"))
                .as("movie data for non-existent ID")
                .isNull();
    }

    @Test
    @DisplayName("Malformed GraphQL query returns errors in the response")
    void returnErrorForMalformedQuery() {
        Response response = graphQLService.executeRawQuery("{ this is not valid graphql }");
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        validator.assertGraphQLErrors(dto);
    }

    @Test
    @DisplayName("Querying a non-existent field returns a schema validation error")
    void returnErrorForNonExistentField() {
        Response response = graphQLService.executeRawQuery("{ movies { nonExistentField } }");
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        validator.assertGraphQLErrors(dto);
    }
}
