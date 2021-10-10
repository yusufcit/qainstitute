package com.testapp.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

import static com.testapp.test.pages.FrameworkTestBase.propertiesReader;

public class SeleniumSettings {

    private static final Logger log = LogManager.getLogger(SeleniumSettings.class);


    //Implicit wait
    public static long getImplicitWait() {
        String value = propertiesReader.getProperty("selenium.implicitwait");
        log.info(String.format("selenium.implicitwait = %s", value));
        return Long.parseLong(value);
    }

    // Interaction timeout as a duration
    public static Duration getInteractionTimeoutDuration() {
        String value = propertiesReader.getProperty("selenium.interactiontimeout");
        log.debug(String.format("selenium.interactiontimeout = %s", value));
        Long seconds = Long.parseLong(value);
        return Duration.ofSeconds(seconds);
    }


    // Interaction timeout as a long
    public static Long getInteractionTimeout() {
        String value = propertiesReader.getProperty("selenium.interactiontimeout");
        log.debug(String.format("selenium.interactiontimeout = %s", value));
        return Long.parseLong(value);
    }

    // Fluent wait polling interval
    public static Duration getPollingIntervalDuration() {
        String value = propertiesReader.getProperty("selenium.pollinginterval");
        log.debug(String.format("selenium.pollinginterval = %s", value));
        Long seconds = Long.parseLong(value);
        return Duration.ofSeconds(seconds);
    }


    /**
     * Returns specific timeout value from selleniumsettings.properties file
     * @return returns the time out from selenium.specifictimeout
     */
    public static int getSpecificTimeOut() {
        String value = propertiesReader.getProperty("selenium.specifictimeout");
        log.debug(String.format("selenium.specifictimeout = %s", value));
        return Integer.parseInt(value);
    }

    // Default driver wait time
    public static long getWaitTime(WaitTime waitTime) {
        String value;
        try {
            switch (waitTime) {
                case XSMALL_WAIT:
                    value = propertiesReader.getProperty("xsmall.wait");
                    break;
                case SMALL_WAIT:
                    value = propertiesReader.getProperty("small.wait");
                    break;
                case MEDIUM_WAIT:
                    value = propertiesReader.getProperty("medium.wait");
                    break;
                case LONG_WAIT:
                    value = propertiesReader.getProperty("long.wait");
                    break;
                default:
                    throw new Exception(String.format("Unable to get waitTime time for %s", waitTime));
            }
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
        return Long.parseLong(value)*1000;
    }


}
