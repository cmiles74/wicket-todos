casper.start('http://localhost:51982/');

// home page
casper.then(function() {
    this.test.assertExists('#addTodoLink', 'Add new to-do link');
    this.test.assertExists('#addContextLink', 'Add new context link');
});

// add context form
casper.thenClick('#addContextLink', function() {
    this.waitForSelector('.wicket-modal', function() {
        this.page.switchToChildFrame(0);
        this.test.assertExists('#contextAddNameField', 'Context name field');
        this.test.assertExists('#contextAddDescriptionField', 'Context description field');
        this.test.assertExists('#formCancelLink', 'Cancel button');
        this.test.assertExists('#formSubmitLink', 'Submit button');

        this.fill('form#contextAddForm', {
            'name': 'Test Context',
            'description': 'This is the text context.'
        }, false);

        this.then(function() {
            this.click('#formSubmitLink');
            this.page.switchToParentFrame(0);
        });
    });
});

casper.then(function() {
    this.wait(100, function() {
        this.test.assertExists('.context h2', 'New context heading');
        this.test.assertExists('.context p', 'New context description');
        this.test.assertTrue(this.fetchText('.context h2') == 'Test Context',
            'New Context Name');
        this.test.assertTrue(this.fetchText('.context p') == 'This is the text context.',
            'New Context Description');
    });
});

casper.run(function() {
    this.test.done(10);
    this.test.renderResults(true, 0, 'log.xml');
});