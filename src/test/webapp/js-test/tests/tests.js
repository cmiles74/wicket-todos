$q(document).ready(function() {
    module('todos');

    // rendering of the home page
    asyncTest('render', function () {
	expect(1);

	onPageLoad(function($) {

	    var $title = $('#title');
	    ok($title.length, "Title heading is not present");

	    start();
	});

	getIframe().attr('src', '/index');
    });

    // context create button
    asyncTest('addContext', function () {
	expect(3);

	onPageLoad(function($) {

	    // add context button
	    var addContextButton = "#addContextLink";

	    // verify the button is present
	    ok($(addContextButton).length, "Context button is not present");

	    // verify the form and fill it in
	    onAjaxComplete(this, function() {

		// verify the add form is present
		ok(getModalElement($, "#contextAddForm"),
		   "Iframe form not present");

		// fill in the form
		getModalElement($, "#contextAddNameField").val("Test Context");
		getModalElement($, "#contextAddDescriptionField").val("Description of text context");

		// verify the submit form is present
		ok(getModalElement($, "#formSubmitLink"),
		   "Submit link not present in form");

		// submit the form
		getModalElement($, "#formSubmitlLink").click();

		// // verify the context is on the list
		// console.log($("#contextsContainer").find(".context h2 span")[0].innerHTML);
		// equal($("#contextsContainer").find(".context h2 span")[0].innerHTML,
		//      "Text Context");

		start();
	    });

	    // click the button
	    $(addContextButton).click();
	});

	getIframe().attr('src', '/index');
    });
});
