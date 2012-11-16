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
import de.agilecoders.wicket.markup.html.bootstrap.html.HtmlTag;
import java.util.Locale;
import de.agilecoders.wicket.markup.html.bootstrap.behavior.BootstrapBaseBehavior;

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

	// add HTML declaration
	add(new HtmlTag("html").locale(Locale.ENGLISH));

	// add Bootstrap
        add(new BootstrapBaseBehavior());

	ContextForm contextForm = new ContextForm("contextAddForm", modalWindow);
	add(contextForm);
    }

    public class ContextForm extends Form<ValueMap> {

	ModalWindow modalWindow = null;

	public ContextForm(String id, final ModalWindow modalWindow) {

	    super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

	    this.modalWindow = modalWindow;
	    setMarkupId(id);
	    add(new TextField<String>("name").setType(String.class));
	    add(new TextArea<String>("description").setType(String.class));

	    add(new AjaxSubmitLink("formCancel", this) {

		    @Override
		    protected void onSubmit(AjaxRequestTarget target, Form form) {

			ContextForm contextForm = (ContextForm) form;
			ValueMap valueMap = contextForm.getModelObject();

			valueMap.put("name", "");
			valueMap.put("description", "");
			modalWindow.close(target);
		    }
		});
	    add(new AjaxSubmitLink("formSubmit", this) {

		    @Override
		    protected void onSubmit(AjaxRequestTarget target, Form form) {

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
    }
}
