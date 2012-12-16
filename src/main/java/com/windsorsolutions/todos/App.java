package com.windsorsolutions.todos;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import com.windsorsolutions.todos.web.ToDoHomePage;
import org.springframework.beans.factory.annotation.Autowired;
import com.windsorsolutions.todos.dao.ToDoJpaDao;
import com.windsorsolutions.todos.dao.ContextJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.agilecoders.wicket.settings.BootstrapSettings;
import de.agilecoders.wicket.Bootstrap;

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

	// setup Spring interop
	getComponentInstantiationListeners()
	    .add(new SpringComponentInjector(this));

	// setup Twitter Bootstrap
	BootstrapSettings settings = new BootstrapSettings();
	settings.minify(false); // use minimized version of all bootstrap references
	Bootstrap.install(this, settings);

	// mount the home page
	mountPage("/index", ToDoHomePage.class);
    }

    @Override
    public Class<ToDoHomePage> getHomePage() {

	return ToDoHomePage.class;
    }
}
