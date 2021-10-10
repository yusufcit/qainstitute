package com.testapp.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Collections;
import java.util.HashMap;


/**
 * Class that creates a new WebDriver instance
 */
public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final String CHROME_DRIVER = "chromedriver.exe";

    /**
     * Creates a local Chrome browser instance
     * @return WebDriver instance for the new Chrome instance
     */
    public WebDriver getLocalChromeDriver() {
        log.debug("Creating local chrome browser instance");
        WebDriver driver;

        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, CHROME_DRIVER);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        chromeOptions.setAcceptInsecureCerts(true);

        HashMap<String, Object> prefs = new HashMap<String,Object>();
        prefs.put("credentials_enable_service", Boolean.FALSE);
        prefs.put("profile.password_manager_enabled", Boolean.FALSE);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        //turn off plugins
        chromeOptions.addArguments("--allow-running-insecure-content","--disable-plugins", "--no-sandbox");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        //create the driver
        driver = new ChromeDriver(chromeOptions);
        driver.manage().deleteAllCookies();
        log.debug("Local chrome instance created");
        return driver;
    }

}