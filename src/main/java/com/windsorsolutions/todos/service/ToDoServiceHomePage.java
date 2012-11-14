package com.windsorsolutions.todos.service;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.*;
import org.apache.wicket.markup.html.basic.Label;

public class ToDoServiceHomePage extends WebPage {

    @SpringBean
    private ToDoService todoService;

    public ToDoServiceHomePage(final PageParameters parameters) {

	add(new Label("message", todoService.getHello()));
    }
}
