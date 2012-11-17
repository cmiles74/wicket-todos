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
import de.agilecoders.wicket.markup.html.bootstrap.html.HtmlTag;
import java.util.Locale;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.model.Model;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.markup.html.bootstrap.behavior.BootstrapBaseBehavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.markup.html.list.ListView;

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
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(ToDoHomePage.class);

    /**
     * Provides the home page for the application.
     */
    public ToDoHomePage() {

	// add HTML declaration
	add(new HtmlTag("html").locale(Locale.ENGLISH));

	// add Bootstrap
        add(new BootstrapBaseBehavior());

	// setup our navigation bar
	Navbar navbar = new Navbar("navigationBar");
	navbar.brandName(Model.of("Windsor Solutions To-Do Management System"));
	add(navbar);

	// fetch our contexts
	LoadableDetachableModel contextListModel = new LoadableDetachableModel() {
		protected Object load() {
		    return(contextDao.getAll());
		}
	    };

	// container and list of contexts
	WebMarkupContainer contextsContainer =
	    new WebMarkupContainer("contextsContainer");
	contextsContainer.setMarkupId("contextsContainer");
	contextsContainer.setOutputMarkupId(true);
	contextsContainer.add(buildContextList("context", contextListModel,
					       contextsContainer));
	add(contextsContainer);

	// setup our modal for adding new contexts
	final ModalWindow contextAddModal =
	    buildContextAddModalWindow("contextAddModal", contextListModel,
				       contextsContainer);
	add(contextAddModal);

	// setup our modal for adding new todo items
	final ModalWindow todoAddModal =
	    buildToDoAddModalWindow("todoAddModal", contextListModel,
				    contextsContainer);
	add(todoAddModal);

	// add a link to display our context add modal
	AjaxLink addContextLink = new AjaxLink<Void>("addContextLink") {

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		contextAddModal.show(target);
	    }
	};

	addContextLink.setMarkupId("addContextLink");
	add(addContextLink);

	// add a link to display our context add modal
	AjaxLink addTodoLink = new AjaxLink<Void>("addTodoLink") {

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		todoAddModal.show(target);
	    }
	};

	addTodoLink.setMarkupId("addTodoLink");
	add(addTodoLink);
    }

    // private methods

    /**
     * Returns a ListView of Context entities.
     *
     * @param id The placeholder ID in the target web page
     * @param listModel A list of Context entities
     * @param contextContainer The parent view of the Context items
     * @returns A ListView of Context entities
     */
    private ListView buildContextList(String id,
				      final LoadableDetachableModel listModel,
				      final WebMarkupContainer contextsContainer) {

	PropertyListView<Context> listView =
	    new PropertyListView<Context>(id, listModel) {

	    @Override
	    public void populateItem(ListItem<Context> listItem) {

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
		listItem.add(buildToDoListView("todos", todoListModel, listModel,
					       contextsContainer));
	    }
	};

	return(listView);
    };

    /**
     * Returns a ListView of ToDo entities. This view requires a
     * handle on the parent list of Context entities and it's matching
     * view, these are used to refresh that list when a ToDo item is
     * changed, added or removed.
     *
     * @param id The placeholder ID in the target web page
     * @param listModel A list of ToDo entities
     * @param contextListModel A list of parent contexts
     * @param contextContainer The parent view of Context items
     * @returns A ListView of ToDo entities
     */
    private ListView
	buildToDoListView(String id, IModel listModel,
			  final LoadableDetachableModel contextListModel,
			  final WebMarkupContainer contextsContainer) {

	PropertyListView<ToDo> listView =
	    new PropertyListView<ToDo>(id, listModel) {

	    @Override
	    public void populateItem(ListItem<ToDo> listItem) {

		final ToDo todo = listItem.getModelObject();
		listItem.add(new Label("content", todo.getContent()));
		listItem.add(new AjaxLink("deleteLink") {

			@Override
			public void onClick(AjaxRequestTarget target) {
			    ToDo todoRemove = todoDao.get(todo.getId());
			    todoDao.remove(todoRemove);
			    contextListModel.detach();
			    target.add(contextsContainer);
			}
		    });
	    }
	};

	return(listView);
    }

    /**
     * Returns a ModalWindow that prompts the customer to create a new
     * Context entity. Note that this method requires a handle on the
     * list of Context entities and its view, these will be refreshed
     * when a new Context is added.
     *
     * @param id The placeholder ID in the target webpage
     * @param contextListModel A list of parent Context entities
     * @param contextsContainer The parent view of Context items
     * @returns A ModalWindow instance
     */
    private ModalWindow
	buildContextAddModalWindow(String id,
				   final LoadableDetachableModel contextListModel,
				   final WebMarkupContainer contextsContainer) {

	final ModalWindow modalWindow = new ModalWindow(id);
	modalWindow.setInitialWidth(550);
	modalWindow.setInitialHeight(300);

	// display the context add modal window
	modalWindow.setPageCreator(new ModalWindow.PageCreator() {
		public Page createPage() {
		    return(new ContextFormPage(ToDoHomePage.this.getPageReference(),
					       modalWindow));
		}
	    });

	// handle the closing of our modal window
	modalWindow.setWindowClosedCallback(new ModalWindow
						.WindowClosedCallback() {
		public void onClose(AjaxRequestTarget target) {

		    // refresh our list of contexts when the modal closes
		    contextListModel.detach();
		    target.add(contextsContainer);
		}
	    });

	// handle the pressing of the modal window's close button
	modalWindow.setCloseButtonCallback(new ModalWindow
					       .CloseButtonCallback() {
		public boolean onCloseButtonClicked(AjaxRequestTarget target) {

		    return(true);
		}
	    });

	return(modalWindow);
    }

    /**
     * Returns a ModalWindow that prompts the customer to create a new
     * ToDo entity. Note that this method requires a handle on the
     * list of Context entities and its view, these will be refreshed
     * when a new ToDo is added.
     *
     * @param id The placeholder ID in the target webpage
     * @param contextListModel A list of parent Context entities
     * @param contextsContainer The parent view of Context entities
     * @returns A ModalWindow instance
     */
    private ModalWindow
	buildToDoAddModalWindow(String id,
				final LoadableDetachableModel contextListModel,
				final WebMarkupContainer contextsContainer) {

	final ModalWindow modalWindow = new ModalWindow(id);
	modalWindow.setInitialWidth(550);
	modalWindow.setInitialHeight(325);

	// display the todo add modal window
	modalWindow.setPageCreator(new ModalWindow.PageCreator() {
		public Page createPage() {
		    return(new ToDoFormPage(ToDoHomePage.this.getPageReference(),
					    modalWindow,
					    contextListModel));
		}
	    });

	// handle the closing of our modal window
	modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
		public void onClose(AjaxRequestTarget target) {

		    // refresh our list of contexts when the modal closes
		    contextListModel.detach();
		    target.add(contextsContainer);
		}
	    });

	// handle the pressing of the modal window's close button
	modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
		public boolean onCloseButtonClicked(AjaxRequestTarget target) {

		    return(true);
		}
	    });

	return(modalWindow);
    }
}
