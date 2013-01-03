package com.windsorsolutions.todos.web;

import com.windsorsolutions.todos.service.ToDoService;
import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.spring.injection.annot.*;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import com.windsorsolutions.todos.entities.Context;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import com.windsorsolutions.todos.entities.ToDo;
import de.agilecoders.wicket.markup.html.bootstrap.html.HtmlTag;
import java.util.Locale;
import de.agilecoders.wicket.markup.html.bootstrap.behavior.BootstrapBaseBehavior;


/**
 * Provides a form for creating an new Todo entity.
 */
public class ToDoFormPage extends WebPage {

    /**
     * Service object for managing ToDo entities.
     */
    @SpringBean
    private ToDoService toDoService;

    /**
     * List of available Contexts.
     */
    private IModel contextListModel = null;

    /**
     * Logger instance.
     */
    private Logger logger = LoggerFactory.getLogger(ToDoFormPage.class);

    /**
     * Creates a new ToDoFormPage instance.
     *
     * @param pageReference A reference to the parent page
     * @param modalWindow A reference to this page's modal window
     * @param contextListModel A list model of Context entities
     */
    public ToDoFormPage(final PageReference pageReference,
			final ModalWindow modalWindow,
			final IModel contextListModel) {

	this.contextListModel = contextListModel;

	// add HTML declaration
	add(new HtmlTag("html").locale(Locale.ENGLISH));

	// add Bootstrap
        add(new BootstrapBaseBehavior());

	ToDoForm todoForm = new ToDoForm("todoAddForm", modalWindow);
	add(todoForm);
    }

    /**
     * Creates a new ToDoForm instance containing the UI elements for
     * creating a new ToDo entity.
     */
    public class ToDoForm extends Form<ValueMap> {

	/**
	 * A reference to the parent modal window for the form
	 */
	ModalWindow modalWindow = null;

	/**
	 * Creates a new ToDoForm instance.
	 *
	 * @param id A reference to the placeholder ID in the target web page
	 * @param modalWindow A reference to the parent modal window
	 */
	public ToDoForm(String id, final ModalWindow modalWindow) {

	    super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

	    // add our components to the modal window
	    setMarkupId(id);
	    add(new TextArea<String>("content").setType(String.class));
	    add(new DropDownChoice<Context>("context", contextListModel,
					    new ChoiceRenderer<Context>("name",
									"id")));

	    // add a link to cancel the form action
	    add(new AjaxSubmitLink("formCancel", this) {

		    @Override
		    protected void onSubmit(AjaxRequestTarget target, Form form) {

			ToDoForm todoForm = (ToDoForm) form;
			ValueMap valueMap = todoForm.getModelObject();

			valueMap.put("name", "");
			valueMap.put("description", "");
			modalWindow.close(target);
		    }
		});

	    // add a link to create the new ToDo entity
	    add(new AjaxSubmitLink("formSubmit", this) {

		    @Override
		    protected void onSubmit(AjaxRequestTarget target,
					    Form form) {

			ToDoForm todoForm = (ToDoForm) form;
			ValueMap valueMap = todoForm.getModelObject();
			logger.debug("Saving new ToDo...");
			logger.debug(" Content: " + valueMap.get("content"));
			logger.debug(" Context: " + valueMap.get("context"));

			ToDo todo = new ToDo();
			todo.setContext((Context) valueMap.get("context"));
			todo.setContent((String) valueMap.get("content"));
			todo = toDoService.save(todo);
			logger.debug("ToDo saved with ID " + todo.getId());

			valueMap.put("content", "");
			valueMap.put("context", "");
			modalWindow.close(target);
		    }
		});
	}
    }
}
