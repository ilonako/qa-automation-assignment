package com.flamingo.qa.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UIComponents {

    public final ButtonComponent button;
    public final InputComponent input;
    public final SearchComponent search;
    public final TableComponent table;
}
