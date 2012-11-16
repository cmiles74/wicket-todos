package com.windsorsolutions.todos;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import com.windsorsolutions.todos.service.ToDoHomePage;
import org.springframework.beans.factory.annotation.Autowired;
import com.windsorsolutions.todos.dao.ToDoJpaDao;
import com.windsorsolutions.todos.dao.ContextJpaDao;
import com.windsorsolutions.todos.entities.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides an application for managing a To-Do list.
 */
public class App extends WebApplication {

    /**
     * Data access object for managing ToDo entities.
     */
    @Autowired
    private ToDoJpaDao todoJpaDao = null;

    /**
     * Data access object for managing Context entities.
     */
    @Autowired
    private ContextJpaDao contextJpaDao = null;

    /**
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    protected void init() {
	super.init();
	getComponentInstantiationListeners()
	    .add(new SpringComponentInjector(this));
    }

    @Override
    public Class<ToDoHomePage> getHomePage() {

	return ToDoHomePage.class;
    }
}
