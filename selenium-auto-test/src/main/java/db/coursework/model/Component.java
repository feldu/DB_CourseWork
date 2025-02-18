package db.coursework.model;

import db.coursework.config.DriverFactory;
import db.coursework.utils.PropUtils;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Data
public class Component {
    protected final WebDriver driver;
    protected final String baseUrl;

    public Component() {
        this.driver = DriverFactory.getDriver();
        this.baseUrl = PropUtils.get("baseUrl");
        PageFactory.initElements(driver, this);
    }
}