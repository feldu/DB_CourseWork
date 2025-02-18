package db.coursework;


import db.coursework.model.auth.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends SeleniumTest {

    private LoginForm loginForm;

    @BeforeEach
    void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver.exe"); // Укажите путь к вашему chromedriver
        loginForm = new LoginForm(); // Инициализация формы входа
    }

    @Test
    void testSuccessfulLogin() throws InterruptedException {
        String username = "admin";
        String password = "admin";

        loginForm.logIn(username, password);

        //проверяем что появилась кнопка выхода
        assertTrue(driver.findElement(By.xpath("//*[@id=\"root\"]/div/nav/button")).isDisplayed());
    }
}

