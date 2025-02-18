package db.coursework;

import db.coursework.model.auth.LoginForm;
import db.coursework.model.main.AdminPage;
import db.coursework.model.main.UserPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BusinessProcessTest extends SeleniumTest {
    private LoginForm loginForm;
    private UserPage userPage;
    private AdminPage adminPage;

    @BeforeEach
    void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver.exe");
        loginForm = new LoginForm();
        userPage = new UserPage();
        adminPage = new AdminPage();
    }

    @Test
    public void businessLogicTest() {
        // 1. войти в систему под ролью Предопределитель
        String clientLogin = "client";
        String clientPassword = "1111";

        loginForm.logIn(clientLogin, clientPassword);

        //проверяем что мы успешно вошли и страница обновилась
        checkUrlUpdate(baseUrl + "/user");

        // 2. создать заказ на определенную касту людей.
        String quantity = "3";
        String cast = "Бета";
        String futureJobType = "Высокие температуры";

        userPage.createOrder(quantity, cast, futureJobType);

        userPage.exit();

        // 3. войти в систему под ролью Админ
        String adminLogin = "admin";
        String adminPassword = "admin";

        loginForm.logIn(adminLogin, adminPassword);

        //проверяем что мы успешно вошли и страница обновилась
        checkUrlUpdate(baseUrl + "/admin");

        // 4. Обновить статус по заказу - продвижение заказа по всем стадиям до рождения.
        String clientName = "№2";
        String orderNumber = "№11";

        adminPage.processOrder(clientName, orderNumber);
        adminPage.exit();
    }

    private void checkUrlUpdate(String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }
}
