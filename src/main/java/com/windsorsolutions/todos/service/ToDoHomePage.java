package com.windsorsolutions.todos.service;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.*;
import org.apache.wicket.markup.html.basic.Label;
import com.windsorsolutions.todos.dao.ToDoJpaDao;
import com.windsorsolutions.todos.dao.ContextJpaDao;
import java.util.List;
import com.windsorsolutions.todos.entities.Context;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.list.ListItem;

public class ToDoHomePage extends WebPage {

    /**
     * Data access object for managing ToDo entities.
     */
    @SpringBean
    private ToDoJpaDao todoJpaDao;

    /**
     * Data access object for managing Context entities.
     */
    @SpringBean
    private ContextJpaDao contextJpaDao;

    /**
     * Provides the home page for the application.
     */
    public ToDoHomePage() {

	add(new PropertyListView<Context>("contexts", contextJpaDao.getAll()) {

		@Override
		public void populateItem(final ListItem<Context> listItem) {

		    final Context context = listItem.getModelObject();
		    listItem.add(new Label("name", context.getName()));
		    listItem.add(new Label("description", context.getDescription()));
		}
	    });
    }
}
