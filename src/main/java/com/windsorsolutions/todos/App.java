package com.windsorsolutions.todos;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import com.windsorsolutions.todos.service.ToDoServiceHomePage;

/**
 * Provides an application for managing a To-Do list.
 */
public class App extends WebApplication {

    @Override
    public Class<ToDoServiceHomePage> getHomePage() {
	return ToDoServiceHomePage.class;
    }

    @Override
    protected void init() {
	super.init();
	getComponentInstantiationListeners()
	    .add(new SpringComponentInjector(this));
    }
}
