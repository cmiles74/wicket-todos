$q(document).ready(function() {
    "use strict";

    module('todos');

    asyncTest('render', function () {
	expect(2);

	onPageLoad(function($) {

	    var $title = $('#title');
	    equal($title.length, 1, "Title heading present");
	    equal($title.text(), 'Your To-Do List', "Title heading correct")

	    start();
	});

	getIframe().attr('src', '/index');
    });

});
