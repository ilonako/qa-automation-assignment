package com.flamingo.qa.tests;

import com.flamingo.qa.validator.ApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseApiTest {

    @Autowired
    protected ApiValidator validator;
}
