package com.flamingo.qa.tests.api;

import com.flamingo.qa.dto.graphql.GraphQLResponseDto;
import com.flamingo.qa.services.GraphQLService;
import com.flamingo.qa.tests.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GraphQL Positive Tests")
class GraphQLPositiveTest extends BaseApiTest {

    @Autowired
    private GraphQLService graphQLService;

    @Test
    @DisplayName("Paginated movies query returns a non-empty list")
    void returnMoviesWithPagination() {
        Response response = graphQLService.queryMoviesWithPagination(3);
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        validator.assertGraphQLSuccess(dto);
        assertThat((List<?>) dto.getData().get("movies"))
                .as("movies list")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Movie fetched by ID returns matching ID in response")
    @SuppressWarnings("unchecked")
    void fetchMovieById() {
        List<Map<String, Object>> movies = fetchMovies(1);
        String movieId = (String) movies.get(0).get("id");

        Response response = graphQLService.queryMovieById(movieId);
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        validator.assertGraphQLSuccess(dto);
        Map<String, Object> movie = (Map<String, Object>) dto.getData().get("movie");
        assertThat(movie)
                .as("movie")
                .hasSize(1)
                .containsEntry("id", movieId);
    }

    @Test
    @DisplayName("Pagination limit variable is respected by the API")
    void respectPaginationLimit() {
        int limit = 2;
        Response response = graphQLService.queryMoviesWithPagination(limit);
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        validator.assertGraphQLSuccess(dto);
        assertThat((List<?>) dto.getData().get("movies"))
                .as("movies count with limit %d", limit)
                .hasSizeLessThanOrEqualTo(limit);
    }

    @Test
    @DisplayName("Nested publisher fields are returned for a valid movie ID")
    @SuppressWarnings("unchecked")
    void returnNestedFields() {
        List<Map<String, Object>> movies = fetchMovies(1);
        String movieId = (String) movies.get(0).get("id");

        Response response = graphQLService.queryNestedFields(movieId);
        GraphQLResponseDto dto = response.as(GraphQLResponseDto.class);

        validator.assertGraphQLSuccess(dto);
        Map<String, Object> movie = (Map<String, Object>) dto.getData().get("movie");
        assertThat(movie)
                .as("movie nested fields")
                .containsKey("publishedBy");
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> fetchMovies(int limit) {
        GraphQLResponseDto dto = graphQLService.queryMoviesWithPagination(limit)
                .as(GraphQLResponseDto.class);
        validator.assertGraphQLSuccess(dto);
        return (List<Map<String, Object>>) dto.getData().get("movies");
    }
}
