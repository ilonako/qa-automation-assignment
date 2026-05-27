package com.flamingo.qa.tests;

import com.flamingo.qa.validator.ApiValidator;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("api")
@SpringBootTest
public abstract class BaseApiTest {

    @Autowired
    protected ApiValidator validator;
}
