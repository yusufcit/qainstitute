package com.testapp.test;

import com.testapp.test.pages.*;
import org.testng.annotations.Test;

public class LandingPageTest extends FrameworkTestBase {

    @Test
    public static void CurrysLoginTest() {
        logger.info("Starting Currys Login Test");
        CurrysLandingPage landingPage = new CurrysLandingPage(driver);
        SignInOrRegister signInOrRegister = new SignInOrRegister(driver);

        landingPage.clickOnAcceptCookies();
        landingPage.clickOnSignInLink();
        signInOrRegister.enterEmailAddress("faruk@gmail.com");
        signInOrRegister.selectHaveAccount();
        signInOrRegister.enterPassword("abcd1234?");
        signInOrRegister.clickSigninButton();
    }
}
