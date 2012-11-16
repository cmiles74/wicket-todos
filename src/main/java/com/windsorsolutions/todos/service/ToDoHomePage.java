package com.windsorsolutions.todos.service;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.*;
import org.apache.wicket.markup.html.basic.Label;
import com.windsorsolutions.todos.dao.ToDoDao;
import com.windsorsolutions.todos.dao.ContextDao;
import java.util.List;
import com.windsorsolutions.todos.entities.Context;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import com.windsorsolutions.todos.entities.ToDo;

public class ToDoHomePage extends WebPage {

    /**
     * Data access object for managing ToDo entities.
     */
    @SpringBean
    private ToDoDao todoDao;

    /**
     * Data access object for managing Context entities.
     */
    @SpringBean
    private ContextDao contextDao;

    /**
     * List of contexts.
     */
    private LoadableDetachableModel contextListModel = null;

    /**
     * Modal window for adding new contexts.
     */
    private ModalWindow contextAddModal = new ModalWindow("contextAddModal");

    /**
     * Modal window for adding new to-do items.
     */
    private ModalWindow todoAddModal = new ModalWindow("todoAddModal");

    /**
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(ToDoHomePage.class);

    /**
     * Provides the home page for the application.
     */
    public ToDoHomePage() {

	// fetch our contexts
	contextListModel = new LoadableDetachableModel() {
		protected Object load() {
		    return(contextDao.getAll());
		}
	    };

	// list view to display our contexts
	PropertyListView contextList =
	    new PropertyListView<Context>("contexts", contextListModel) {

	    @Override
	    public void populateItem(ListItem<Context> listItem) {

		// display our context
		final Context context = listItem.getModelObject();
		listItem.add(new Label("name", context.getName()));
		listItem.add(new Label("description", context.getDescription()));

		// fetch our to-do items
		LoadableDetachableModel todoListModel =
   		    new LoadableDetachableModel() {
		        protected Object load() {
			    return(todoDao.getWithContext(context));
			}
		    };

		// display our list of todo items for this context
		listItem.add(new PropertyListView<ToDo>("todos", todoListModel) {

			public void populateItem(ListItem<ToDo> listItem) {

			    ToDo todo = listItem.getModelObject();
			    listItem.add(new Label("content", todo.getContent()));
			}
		    });
	    }
	};

	// setup our list of contexts
	final WebMarkupContainer contextsContainer =
	    new WebMarkupContainer("contextsContainer");
	contextsContainer.setOutputMarkupId(true);
	contextsContainer.add(contextList);
	add(contextsContainer);

	// setup our modal for adding new contexts
	contextAddModal.setCookieName("contextAddModal");
	add(contextAddModal);

	// display the context add modal window
	contextAddModal.setPageCreator(new ModalWindow.PageCreator() {
		public Page createPage() {
		    return(new ContextFormPage(ToDoHomePage.this.getPageReference(),
					       contextAddModal));
		}
	    });

	// handle the closing of our modal window
	contextAddModal.setWindowClosedCallback(new ModalWindow
						.WindowClosedCallback() {
		public void onClose(AjaxRequestTarget target) {

		    // refresh our list of contexts when the modal closes
		    contextListModel.detach();
		    target.add(contextsContainer);
		}
	    });

	// handle the pressing of the modal window's close button
	contextAddModal.setCloseButtonCallback(new ModalWindow
					       .CloseButtonCallback() {
		public boolean onCloseButtonClicked(AjaxRequestTarget target) {

		    return(true);
		}
	    });

	// setup our modal for adding new todo items
	todoAddModal.setCookieName("todoAddModal");
	add(todoAddModal);

	// display the todo add modal window
	todoAddModal.setPageCreator(new ModalWindow.PageCreator() {
		public Page createPage() {
		    return(new ToDoFormPage(ToDoHomePage.this.getPageReference(),
					    todoAddModal,
					    contextListModel));
		}
	    });

	// handle the closing of our modal window
	todoAddModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
		public void onClose(AjaxRequestTarget target) {

		    // refresh our list of contexts when the modal closes
		    contextListModel.detach();
		    target.add(contextsContainer);
		}
	    });

	// handle the pressing of the modal window's close button
	todoAddModal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
		public boolean onCloseButtonClicked(AjaxRequestTarget target) {

		    return(true);
		}
	    });

	// add a link to display our context add modal
	add(new AjaxLink<Void>("addContextLink") {

		@Override
		public void onClick(AjaxRequestTarget target) {
		    contextAddModal.show(target);
		}
	    });

	// add a link to display our context add modal
	add(new AjaxLink<Void>("addTodoLink") {

		@Override
		public void onClick(AjaxRequestTarget target) {
		    todoAddModal.show(target);
		}
	    });
    }
}
