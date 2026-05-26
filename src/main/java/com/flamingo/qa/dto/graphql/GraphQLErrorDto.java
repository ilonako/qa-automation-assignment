package com.flamingo.qa.dto.graphql;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class GraphQLErrorDto {

    private String message;
    private List<Map<String, Integer>> locations;
    private List<Object> path;
    private Map<String, Object> extensions;
}
