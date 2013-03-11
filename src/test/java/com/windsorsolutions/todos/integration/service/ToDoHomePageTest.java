package com.windsorsolutions.todos.integration.service;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ToDoHomePageTest {

    /**
     * Logger instance
     */
    private Logger logger = LoggerFactory.getLogger(ToDoHomePageTest.class);

    /**
     * Selenium web driver instance.
     */
    private WebDriver driver = null;

    @Before
    public void setUp() {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        driver = new PhantomJSDriver(desiredCapabilities);
        driver.manage().window().setSize(new Dimension(800, 600));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testHomePageRenders() {

        driver.get("http://localhost:51982/");

        Assert.assertNotSame(driver.findElements(By.id("title")).size(), 0);
        Assert.assertNotSame(driver.findElements(By.id("addTodoLink")).size(), 0);
        Assert.assertNotSame(driver.findElements(By.id("addContextLink")).size(), 0);
    }

    @Test
    public void textAddContext() {

        driver.get("http://localhost:51982/");
        driver.findElement(By.id("addContextLink")).click();

        WebElement webElementModal = driver.findElement(By.className("wicket_modal"));
        logger.info("Modal: " + webElementModal + ", " + webElementModal.getAttribute("id"));

        List<WebElement> elements = driver.findElements(By.tagName("iframe"));
        for(WebElement webElement : elements) {
            logger.info("  Iframe: " + webElement + ", " + webElement.getAttribute("id"));

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshot, new File("screenshot.png"));
            } catch (IOException e2) {
                logger.info("Couldn't save screenshot: " + e2.getMessage());
            }

            driver.switchTo().frame(webElement);
        }

        driver.findElement(By.id("contextAddNameField"));

        Assert.assertNotSame(driver.findElements(By.className("wicket-modal")).size(), 0);
    }

    @After
    public void breakDown() {
        driver.quit();
    }
}
