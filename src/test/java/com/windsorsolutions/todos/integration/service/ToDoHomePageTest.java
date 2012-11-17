package com.windsorsolutions.todos.integration.service;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ToDoHomePageTest {

    /**
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(ToDoHomePageTest.class);

    @Test
    public void testToDoHomePage() {

	// fetch the page
	WebDriver webDriver = new ChromeDriver();
	webDriver.get("http://localhost:8080/");

	// verify the application is displayed
	List elements = webDriver.findElements(By.id("appContainer"));
	Assert.assertEquals(elements.size(), 1);
	webDriver.quit();
    }

    //@Test
    public void testAddContext() {

	// fetch the page
	WebDriver webDriver = new ChromeDriver();
	webDriver.get("http://localhost:8080/");

	// get a count of our contexts
	WebElement contextsContainer =
	    webDriver.findElement(By.id("contextsContainer"));
	List contexts = contextsContainer.findElements(By.className("context"));
	int startNumContexts = contexts.size();
	logger.debug("Starting with " + startNumContexts + " Context entities");

	// get the add context button and click it
	WebElement addContext = webDriver.findElement(By.id("addContextLink"));
	addContext.click();
	WebDriverWait wait = new WebDriverWait(webDriver, 5);
	wait.until(new ExpectedCondition<WebElement>() {
		public WebElement apply(final WebDriver webDriver) {
		    return(webDriver.findElement(By.id("contextAddForm")));
		}
	    });

	// get a handle on our form
	WebElement addContextModal = webDriver.findElement(By.id("contextAddForm"));

	// fill in the form
	WebElement nameField = addContextModal.findElement(By.name("name"));
	nameField.click();
	nameField.sendKeys("Test Context");

	WebElement descriptionField =
	    addContextModal.findElement(By.name("description"));
	descriptionField.click();
	descriptionField.sendKeys("For all of the testing.");

	// submit the form
	WebElement submitButton =
	    addContextModal.findElement(By.id("formSubmitLink"));
	submitButton.click();

	// check for our new context
	contextsContainer =
	    webDriver.findElement(By.id("contextsContainer"));
	contexts = contextsContainer.findElements(By.className("context"));
	int endNumContexts = contexts.size();
	logger.debug("Ending with " + startNumContexts + " Context entities");
	Assert.assertTrue(startNumContexts > endNumContexts);
    }
}
