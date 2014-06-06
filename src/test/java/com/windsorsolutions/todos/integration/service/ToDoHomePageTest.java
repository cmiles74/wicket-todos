package com.windsorsolutions.todos.integration.service;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class ToDoHomePageTest {

    /**
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(ToDoHomePageTest.class);

    /**
     * Maximum time to wait for a UI element (in seconds).
     */
    private final static int MAX_WAIT_TIME = 30;

    /**
     * Selenium web driver instance.
     */
    private WebDriver driver = null;

    @Before
    public void setUp() {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        driver = new PhantomJSDriver(desiredCapabilities);
        driver.manage().window().setSize(new Dimension(800, 600));
        driver.manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
    }

    @Test
    public void testHomePageRenders() {

        driver.get("http://localhost:51982/");

        Assert.assertNotSame(driver.findElements(By.id("title")).size(), 0);
        Assert.assertNotSame(driver.findElements(By.id("addTodoLink")).size(), 0);
        Assert.assertNotSame(driver.findElements(By.id("addContextLink")).size(), 0);
    }

    /**
     * Fills out the "Add Context" form and verifies that the new context is
     * created.
     */
    @Test
    public void textAddContext() {

        driver.get("http://localhost:51982/");

        // get all of the current context identifiers
        final List<String> contextIdList = new ArrayList<String>();
        for(WebElement context : driver.findElements(By.cssSelector(".context"))) {
            contextIdList.add(context.getAttribute("id"));
        }

        // open the "add context" modal
        driver.findElement(By.id("addContextLink")).click();
        Assert.assertNotSame(driver.findElements(By.className("wicket_modal")).size(), 0);

        // switch focus to the modal
        WebElement webElementModal = driver.findElement(By.className("wicket_modal"));
        logger.info("Modal: " + webElementModal + ", " + webElementModal.getAttribute("id"));
        driver.switchTo().frame(webElementModal);
        logger.info("Focused on modal window");

        // verify form elements rendered, fill out the form
        String contextName = "Test Context (" + (new Date().getTime()) + ")";
        Assert.assertNotSame(driver.findElements(By.id("contextAddNameField")).size(), 0);
        driver.findElement(By.id("contextAddNameField")).sendKeys("Test Context");
        Assert.assertNotSame(driver.findElements(By.id("contextAddDescriptionField")).size(), 0);
        driver.findElement(By.id("contextAddDescriptionField")).sendKeys("For all the testing");
        Assert.assertNotSame(driver.findElements(By.id("formCancelLink")).size(), 0);
        Assert.assertNotSame(driver.findElements(By.id("formSubmitLink")).size(), 0);

        // submit the form
        driver.findElement(By.id("formSubmitLink")).click();

        // switch focus back to the main window
        driver.switchTo().defaultContent();

        // wait for the new context to appear
        WebElement webElementContextNew =
                (new WebDriverWait(driver, MAX_WAIT_TIME)).until(new ExpectedCondition<WebElement>() {

            @Override
            public WebElement apply(WebDriver webDriver) {

                // holder for the new context element
                WebElement webElementNew = null;

                // loop through all of our context identifiers
                for(WebElement context : webDriver.findElements(By.cssSelector(".context"))) {

                    if(!contextIdList.contains(context.getAttribute("id"))) {
                        logger.info("New context found with ID " + context.getAttribute("id"));
                        webElementNew = context;
                    }
                }

                return  webElementNew;
            }
        });
        Assert.assertNotNull(webElementContextNew);
    }

    @After
    public void breakDown() {
        driver.quit();
    }

    private void grabScreenshot(WebDriver driver, String filename) {

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File(filename));
        } catch (IOException e2) {
            logger.info("Couldn't save screenshot: " + e2.getMessage());
        }
    }
}
