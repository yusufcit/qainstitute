package com.testapp.test.pages;

import com.testapp.test.DefaultDriver;
import com.testapp.test.WaitTime;
import org.openqa.selenium.By;

public class SignInOrRegister extends FrameworkTestBase {
    private static final By emailAddress = By.xpath("//*[@id=\"input-sEmail\"]");
    private static final By noAccount = By.xpath("//*[@id=\"content\"]/div[2]/div/div/form/div[1]/div[1]/label/div");
    private static final By haveAccount = By.xpath("//*[@id=\"content\"]/div[2]/div/div/form/div[1]/div[2]/label/div");
    private static final By password = By.xpath("//*[@id=\"input-sPassword\"]");
    private static final By signInButton = By.xpath("//*[@id=\"content\"]/div[2]/div/div/form/div[3]/button");



    public SignInOrRegister(DefaultDriver driverRef){
        this.driver=driverRef;
    }

    public void enterEmailAddress(String email) {
        logger.info("Entering Email Address to register");
        driver.sendText(emailAddress, email);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

    public void selectNoAccount() {
        driver.click(noAccount);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

    public void selectHaveAccount() {
        driver.click(haveAccount);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

    public void enterPassword(String pass) {
        driver.sendText(password, pass);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

    public void clickSigninButton() {
        driver.click(signInButton);
        driver.sleep(WaitTime.XSMALL_WAIT);
    }

}
