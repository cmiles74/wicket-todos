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

    /**
     * Creates a new ContextFormPage instance.
     *
     * @param pageReference A reference to the parent page
     * @param modalWindow A reference to this page's modal window
     */
    public ContextFormPage(final PageReference pageReference,
			   final ModalWindow modalWindow) {

	// add HTML declaration
	add(new HtmlTag("html").locale(Locale.ENGLISH));

	// add Bootstrap
        add(new BootstrapBaseBehavior());

	ContextForm contextForm = new ContextForm("contextAddForm", modalWindow);
	add(contextForm);
    }

    /**
     * Creates a new ContextForm instance containg the UI elements for
     * creating a new Context entity.
     */
    public class ContextForm extends Form<ValueMap> {

	/**
	 * A reference to the parent modal window for the form
	 */
	ModalWindow modalWindow = null;

	/**
	 * Creates a new ContextForm instance.
	 *
	 * @param id A reference to the placeholder ID in the target web page
	 * @param modalWindow A reference to the parent ModalWindow
	 */
	public ContextForm(String id, final ModalWindow modalWindow) {

	    super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

	    // add our components to the modal window
	    this.modalWindow = modalWindow;
	    setMarkupId(id);
	    TextField textfieldName = new TextField<String>("name");
	    textfieldName.setType(String.class);
	    textfieldName.setMarkupId("contextAddNameField");
	    add(textfieldName);

	    TextArea textareaDescription =
		new TextArea<String>("description");
	    textareaDescription.setType(String.class);
	    textareaDescription.setMarkupId("contextAddDescriptionField");
	    add(textareaDescription);

	    // add a link to cancel the form action
	    AjaxSubmitLink cancelLink =
		new AjaxSubmitLink("formCancel", this) {

		    @Override
		    protected void onSubmit(AjaxRequestTarget target, Form form) {

			ContextForm contextForm = (ContextForm) form;
			ValueMap valueMap = contextForm.getModelObject();

			valueMap.put("name", "");
			valueMap.put("description", "");
			modalWindow.close(target);
		    }
		};
	    cancelLink.setMarkupId("formCancelLink");
	    add(cancelLink);

	    // add a link to create the new context
	    AjaxSubmitLink submitLink = new AjaxSubmitLink("formSubmit", this) {

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
		};
	    submitLink.setMarkupId("formSubmitLink");
	    add(submitLink);
	}
    }
}
