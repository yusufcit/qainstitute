package com.testapp.test;

import com.google.common.base.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.testapp.test.pages.FrameworkTestBase.propertiesReader;


/**
 * Class to wrap selenium interactions into safe operations
 */
public class DefaultDriver {

    private static final Logger logger = LogManager.getLogger(DefaultDriver.class);

    private final WebDriver driver;
    private String lastException;


    // Constructor from a WebDriver instance
    public DefaultDriver(WebDriver wDriver) {
        driver = wDriver;
        logger.info("Driver object is " + driver);
    }

    public void windowMaximize() {
        logger.info("maximizing window ... ");
        driver.manage().window().maximize();
        logger.info(String.format("Window dimensions are %s", driver.manage().window().getSize()));
    }

    public void OpenURL(String url) {
        logger.info("Navigating to " + url);
        driver.get(url);
    }

    public String getTitle()  {
        logger.info("Getting Page Title");
        return driver.getTitle();
    }

    public boolean isElementPresent(By by)  {
        logger.info(String.format("Checking if %s Element is Present", by.toString()));
        return driver.findElement(by).isDisplayed();
    }

    public void waitForElementVisible(By by) {
        logger.info(propertiesReader.getProperty("interactionTimeout"));
        WebDriverWait wait = new WebDriverWait(driver, SeleniumSettings.getInteractionTimeout());
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

//    public void click(By by)  {
//        logger.info(String.format("Left Clicking on %s Element", by.toString()));
//        driver.findElement(by).click();
//    }

    public void quit() {
        driver.quit();
    }
    public void close() {
        driver.close();
    }


    /**
     * Method allows the default driver session to to sleep temporarily cease
     * @param sleep: Duration in milliseconds
     */
    public void sleep(WaitTime sleep) {
        try {
            logger.info(String.format("Wait for %d seconds...", SeleniumSettings.getWaitTime(sleep)/1000));
            Thread.sleep(SeleniumSettings.getWaitTime(sleep));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("An Exception was caught while waiting %s: " , e);
        }
    }

    /**
     * Enters text onto an element
     * @param locator Locator for the element
     * @param text Text to type into the element
     */
    public void sendText(final By locator, final String text) {
        logger.info(String.format("Entering text %s onto element %s: ", text, locator));
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(SeleniumSettings.getInteractionTimeoutDuration())
                .pollingEvery(SeleniumSettings.getPollingIntervalDuration())
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        WebElement element = driver.findElement(locator);
                        if (!(element.isEnabled() && element.isDisplayed()))
                            return false;
                        element.clear();
                        element.sendKeys(text);
                        String actualText = element.getText();
                        String actualValue = element.getAttribute("value");
                        if (actualText == null) actualText = "";
                        if (actualValue == null) actualValue = "";
                        return (actualText.trim().equalsIgnoreCase(text) || actualValue.trim().equalsIgnoreCase(text));
                    } catch (Exception e) {
                        lastException = e.getMessage();
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            logger.error(String.format("Unable to enter text into : %s : Because of : %s", locator, lastException));
            throw new FrameworkException(String.format("Unable to enter text into : %s", locator));

        } finally {
            //reset implicit wait
            driver.manage().timeouts().implicitlyWait(SeleniumSettings.getImplicitWait(), TimeUnit.SECONDS);
        }
    }

    /**
     * Gets the text of a web element
     * @param locator - element locator
     * @return String text
     */
    public String safeGetText(final By locator) {
        this.lastException = null;
        final String[] elementText = {null};
        //change implicit wait to allow polling
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(SeleniumSettings.getInteractionTimeoutDuration())
                .pollingEvery(SeleniumSettings.getPollingIntervalDuration());
        try {

            wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        WebElement element = driver.findElement(locator);
                        elementText[0] = element.getText();
                        logger.info(String.format("Element text is : %s", elementText[0]));
                        return true;
                    } catch (Exception e) {
                        lastException = e.getMessage();
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            logger.error(String.format("Unable to get text from : %s : Because of : %s", locator, lastException));
            throw new FrameworkException(String.format("Locator is not click-able : %s", locator));
        } finally {
            //reset implicit wait
            driver.manage().timeouts().implicitlyWait(SeleniumSettings.getImplicitWait(), TimeUnit.SECONDS);
        }
        return elementText[0].trim();
    }

    /**
     * Clicks on an element
     * @param locator Locator for the element
     */
    public void click(By locator) {
        this.lastException = null;
        //change implicit wait to allow polling
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(SeleniumSettings.getInteractionTimeoutDuration())
                .pollingEvery(SeleniumSettings.getPollingIntervalDuration());
        try
        {
            wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try
                    {
                        WebElement element = driver.findElement(locator);
                        if(!(element.isEnabled() && element.isDisplayed()))
                            return false;
                        element.click();
                        logger.info(String.format("Clicked on element : %s", locator.toString()));
                        return true;
                    }
                    catch (Exception e)
                    {
                        lastException = e.getMessage();
                        return false;
                    }
                }
            });
        }
        catch (Exception e)
        {
            logger.error(String.format("Element is not click-able : %s : Because of : %s", locator, lastException));
            throw new FrameworkException(String.format("Locator is not click-able : %s", locator));
        }
        finally {
            //reset implicit wait
            driver.manage().timeouts().implicitlyWait(SeleniumSettings.getImplicitWait(), TimeUnit.SECONDS);
        }
    }


    /**
     * Waits until the specified element is Displayed
     * @param locator Locator for element
     * @return True/False if the element was found to be displayed
     */
    public boolean isPresentAndVisible(By locator) {
        logger.info(String.format("Checking if element is present and visible: %s", locator));
        this.lastException = null;
        //change implicit wait to allow polling
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(SeleniumSettings.getInteractionTimeoutDuration())
                .pollingEvery(SeleniumSettings.getPollingIntervalDuration());
        try {
            Boolean result = wait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver){
                    try {
                        WebElement element = driver.findElement(locator);
                        return element.isDisplayed();
                    } catch (Exception e) {
                        lastException = e.getMessage();
                        return false;
                    }
                }
            });
            logger.info("Element was found and displayed");
            return result;
        } catch (Exception e) {
            logger.error("Element is not present and visible");
            logger.warn(lastException);
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(SeleniumSettings.getImplicitWait(), TimeUnit.SECONDS);
        }
    }




}
