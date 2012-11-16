package com.windsorsolutions.todos.service;

import org.apache.wicket.spring.injection.annot.*;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.windsorsolutions.todos.dao.ContextDao;
import com.windsorsolutions.todos.entities.Context;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Provides a form for creating a new Context entity.
 */
public class ContextFormPage extends WebPage {

    /**
     * Data access object for managing Context entities.
     */
    @SpringBean
    private ContextDao contextDao;

    /**
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(ContextFormPage.class);

    public ContextFormPage(final PageReference pageReference,
			   final ModalWindow modalWindow) {

	ContextForm contextForm = new ContextForm("contextAddForm", modalWindow);
	add(contextForm);
	add(new AjaxSubmitLink("formSubmit", contextForm) {

		@Override
		protected void onSubmit(AjaxRequestTarget target,
					Form form) {

		    ContextForm contextForm = (ContextForm) form;
		    ValueMap valueMap = contextForm.getModelObject();
		    logger.debug("Saving new Context...");
		    logger.debug("  Name: " + valueMap.get("name"));
		    logger.debug("  Description: " + valueMap.get("description"));


		    Context context = new Context();
		    context.setName((String) valueMap.get("name"));
		    context.setDescription((String) valueMap.get("description"));
		    context = contextDao.persist(context);
		    logger.debug("Context save with ID " + context.getId());

		    valueMap.put("name", "");
		    valueMap.put("description", "");
		    modalWindow.close(target);
		}
	    });
    }

    public class ContextForm extends Form<ValueMap> {

	ModalWindow modalWindow = null;

	public ContextForm(String id, ModalWindow modalWindow) {

	    super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

	    this.modalWindow = modalWindow;
	    setMarkupId(id);
	    add(new TextField<String>("name").setType(String.class));
	    add(new TextArea<String>("description").setType(String.class));
	}
    }
}
