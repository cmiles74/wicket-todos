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
     * Logger instance.
     */
    private Logger logger = LoggerFactory.getLogger(ToDoFormPage.class);

    public ToDoFormPage(final PageReference pageReference,
			final ModalWindow modalWindow,
			final IModel contextListModel) {

	ToDoForm todoForm = new ToDoForm("todoAddForm", modalWindow);
	add(todoForm);
	add(new AjaxSubmitLink("formSubmit", todoForm) {

		@Override
		protected void onSubmit(AjaxRequestTarget target,
					Form form) {

		    ToDoForm todoForm = (ToDoForm) form;
		    ValueMap valueMap = todoForm.getModelObject();
		    logger.debug("Saving new ToDo...");
		    logger.debug(" Content: " + valueMap.get("content"));

		    valueMap.put("content", "");
		    modalWindow.close(target);
		}
	    });
    }

    public class ToDoForm extends Form<ValueMap> {

	ModalWindow modalWindow = null;

	public ToDoForm(String id, ModalWindow modalWindow) {

	    super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

	    this.modalWindow = modalWindow;
	    setMarkupId(id);
	    add(new TextArea<String>("content").setType(String.class));
	}
    }
}
