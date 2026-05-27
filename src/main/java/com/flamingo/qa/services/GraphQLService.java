package com.flamingo.qa.services;

import com.flamingo.qa.api.GraphQLApiClient;
import com.flamingo.qa.dto.graphql.GraphQLRequestDto;
import com.flamingo.qa.utils.graphql.GraphQLPaths;
import com.flamingo.qa.utils.graphql.GraphQLQueryLoader;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GraphQLService {

    private final GraphQLApiClient graphQLApiClient;
    private final GraphQLQueryLoader queryLoader;

    public Response queryMoviesWithPagination(int limit) {
        return graphQLApiClient.execute(GraphQLRequestDto.builder()
                .query(queryLoader.load(GraphQLPaths.MOVIES_PAGINATED))
                .variables(Map.of("first", limit))
                .build());
    }

    public Response queryMovieById(String id) {
        return graphQLApiClient.execute(GraphQLRequestDto.builder()
                .query(queryLoader.load(GraphQLPaths.MOVIE_BY_ID))
                .variables(Map.of("id", id))
                .build());
    }

    public Response queryNestedFields(String id) {
        return graphQLApiClient.execute(GraphQLRequestDto.builder()
                .query(queryLoader.load(GraphQLPaths.MOVIE_NESTED_FIELDS))
                .variables(Map.of("id", id))
                .build());
    }

    public Response executeRawQuery(String query) {
        return graphQLApiClient.execute(GraphQLRequestDto.builder()
                .query(query)
                .build());
    }

    public Response executeWithVariables(String query, Map<String, Object> variables) {
        return graphQLApiClient.execute(GraphQLRequestDto.builder()
                .query(query)
                .variables(variables)
                .build());
    }
}
