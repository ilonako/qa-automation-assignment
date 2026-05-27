package com.flamingo.qa.utils.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class GraphQLQueryLoader {

    @Value("${graphql.base-path}")
    private String basePath;

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final ResourceLoader resourceLoader;

    public String load(String relativePath) {
        return cache.computeIfAbsent(relativePath, this::readFromClasspath);
    }

    private String readFromClasspath(String relativePath) {
        Resource resource = resourceLoader.getResource(basePath + relativePath);
        try {
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("GraphQL file not found: " + relativePath, e);
        }
    }
}
