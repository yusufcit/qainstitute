package com.testapp.test.pages;

import com.testapp.test.DefaultDriver;
import com.testapp.test.WaitTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class CurrysLandingPage extends FrameworkTestBase {
    private static final Logger logger = LogManager.getLogger(CurrysLandingPage.class);

    private static final By kitchenApplienceTab = By.xpath("//*[@id=\"desktop-nav\"]/ul[1]/li[2]/a");
    private static final By signInLink = By.xpath("//*[@id=\"header\"]/div/div/div/div[3]/a[4]/span[2]");
    private static final By cookiePolicy = By.xpath("//*[@id='onetrust-accept-btn-handler']");


    public CurrysLandingPage(DefaultDriver driverRef){
        this.driver=driverRef;
    }

    public void clickOnKitchenApplienceTab() {
        driver.click(kitchenApplienceTab);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

    public void clickOnSignInLink() {
        driver.click(signInLink);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

    public void clickOnAcceptCookies() {
        logger.info("Clicking on accept cookies");
        driver.click(cookiePolicy);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

}
