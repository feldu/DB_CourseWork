package db.coursework.model.auth;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static db.coursework.utils.WaitUtils.waitIfNeed;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm extends Component {
    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/form/div[1]/input")
    private WebElement loginInput;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/form/div[2]/div/input")
    private WebElement passwordInput;

    @FindBy(xpath = "/html/body/div/div/div/div/div[2]/form/button")
    private WebElement signInButton;

    public void setLogin(String login) {
        loginInput.sendKeys(login);
    }

    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void logIn(String login, String password) {
        driver.get(baseUrl + "/auth/signin");
        waitIfNeed();
        setLogin(login);
        waitIfNeed();
        setPassword(password);
        waitIfNeed();
        clickSignInButton();
    }
}
