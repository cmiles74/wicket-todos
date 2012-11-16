package com.windsorsolutions.todos.service;

import org.apache.wicket.markup.html.WebPage;
import com.windsorsolutions.todos.dao.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.spring.injection.annot.*;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.markup.html.form.TextField;
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
     * Data access object for managing ToDo entities.
     */
    @SpringBean
    private ToDoDao todoDao;

    /**
     * List of available Contexts.
     */
    private IModel contextListModel = null;

    /**
     * Logger instance.
     */
    private Logger logger = LoggerFactory.getLogger(ToDoFormPage.class);

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

    public class ToDoForm extends Form<ValueMap> {

	ModalWindow modalWindow = null;

	public ToDoForm(String id, final ModalWindow modalWindow) {

	    super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
	    setMarkupId(id);

	    add(new TextArea<String>("content").setType(String.class));
	    add(new DropDownChoice<Context>("context", contextListModel,
					    new ChoiceRenderer<Context>("name",
									"id")));

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
			todo = todoDao.persist(todo);
			logger.debug("ToDo saved with ID " + todo.getId());

			valueMap.put("content", "");
			valueMap.put("context", "");
			modalWindow.close(target);
		    }
		});
	}
    }
}
