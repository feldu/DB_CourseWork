package db.coursework;

import db.coursework.config.DriverFactory;
import db.coursework.utils.PropUtils;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;


public abstract class SeleniumTest {

    public static final String CHROME_SYSTEM_PROPERTY_NAME = "webdriver.chrome.driver";
    protected final WebDriver driver;
    public final String baseUrl;


    public SeleniumTest() {
        this.baseUrl = PropUtils.get("baseUrl");
        System.setProperty(CHROME_SYSTEM_PROPERTY_NAME, PropUtils.get("driver.path"));
        this.driver = DriverFactory.getDriver();
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
        DriverFactory.unloadDriver();
    }
}
