package com.testapp.test.pages;

import com.testapp.test.DefaultDriver;
import com.testapp.test.DriverFactory;
import com.testapp.test.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


public class FrameworkTestBase {

    public static final Logger logger = LogManager.getLogger(FrameworkTestBase.class);
    public static DefaultDriver driver;
    protected WebDriver wdriver;
    public static PropertiesReader propertiesReader;

    @BeforeSuite(alwaysRun = true)
    public void frameworkBeforeSuite(ITestContext testContext) {
        logger.info("Start of framework before suite");
        try {
            //setup framework
            logger.info("Setting up environment properties reader");
            propertiesReader = new PropertiesReader();
            propertiesReader.readPropertiesFile();

            logger.info("Getting driver from DriverFactory");
            DriverFactory driverFactory = new DriverFactory();
            wdriver = driverFactory.getLocalChromeDriver();
            driver = new DefaultDriver(wdriver);
            driver.windowMaximize();
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("End of framework before suite");
    }

    @BeforeTest(alwaysRun = true)
    public void frameworkBeforeTest(ITestContext testContext) {
        logger.info("Start of framework before test");
        try {
            logger.info("Setting up Test Case");
            driver.OpenURL(propertiesReader.getProperty("baseUrl"));
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("End of framework before test");
    }

    @AfterTest(alwaysRun = true)
    public void frameworkAfterTest(ITestContext testContext) {
        logger.info("Start of framework after test");
        try {
            driver.close();
            logger.info("tearing down Test Case");
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("End of framework after test");
    }

    @AfterSuite(alwaysRun = true)
    public void frameworkAfterSuite(ITestContext testContext) {
        logger.info("Start of framework After suite");
        try {
            //Tear down framework
            logger.info("Tearing down framework");
            driver.quit();
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("End of framework before suite");
    }

}

