package com.flamingo.qa.dto.graphql;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

@Data
@NoArgsConstructor
public class GraphQLResponseDto {

    private Map<String, Object> data;
    private List<GraphQLErrorDto> errors;

    public boolean hasErrors() {
        return !CollectionUtils.isEmpty(errors);
    }

    public boolean hasData() {
        return Objects.nonNull(data);
    }
}
