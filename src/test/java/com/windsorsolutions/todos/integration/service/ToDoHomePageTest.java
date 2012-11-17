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
}
