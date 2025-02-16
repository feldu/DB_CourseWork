package db.coursework.utils;

import db.coursework.model.login.LoginForm;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestUtils {

    public static void login(WebDriver driver) {
        LoginForm loginForm = new LoginForm();
//        loginForm.logIn();
    }

    public static void waitUntilPageReload(WebDriver driver) {
        WebDriverWait waitDriver = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitDriver.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}

