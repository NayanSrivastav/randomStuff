package crawling;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * This class is used for creating headless chrome browser
 * @author dhiren
 * @since 28-05-2018
 */
public final class WebDriverFactory {

    private static final String SELENIUM_DRIVER_URL = "";

    public static WebDriver getDriver() throws MalformedURLException {
        WebDriver webDriver = new RemoteWebDriver(new URL(SELENIUM_DRIVER_URL), DesiredCapabilities.chrome());
        webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS).pageLoadTimeout(50,TimeUnit.SECONDS).setScriptTimeout(50, TimeUnit.SECONDS);
        return webDriver;
    }
}
