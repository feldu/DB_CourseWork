package db.coursework;


import db.coursework.model.login.LoginForm;
import db.coursework.utils.PropUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends SeleniumTest {

    @BeforeEach
    public void init() {
        driver.get(baseUrl + "/login");
    }

    @Test
    public void successLogin() {
        LoginForm loginForm = new LoginForm();
        assertTrue(driver.getCurrentUrl().contains("/login"));

        loginForm.logIn(PropUtils.get("auth.admin.login"), PropUtils.get("auth.admin.password"));
        assertTrue(driver.getCurrentUrl().contains("/main"));
    }

}
