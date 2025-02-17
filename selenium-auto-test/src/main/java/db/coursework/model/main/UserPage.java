package db.coursework.model.main;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static db.coursework.utils.WaitUtils.waitIfNeed;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPage extends Component {
    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/form/div[1]/input")
    private WebElement quantityInput;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/form/div[2]/div/div/div[1]/div[2]/input")
    private WebElement castSelectionInput;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/form/div[3]/div/div/div[1]/div[2]/input")
    private WebElement futureJobTypeSelectionInput;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/form/button")
    private WebElement sendOrderButton;

    @FindBy(xpath = "/html/body/div/div/nav/button")
    private WebElement exitButton;

    public void setQuantityInput(String quantity) {
        quantityInput.sendKeys(quantity);
    }

    public void setCastSelectionInput(String cast) {
        castSelectionInput.sendKeys(cast);
        pressEnter();
    }

    public void setFutureJobTypeSelectionInput(String futureJobType) {
        futureJobTypeSelectionInput.sendKeys(futureJobType);
        pressEnter();
    }

    public void clickSendOrderButton() {
        sendOrderButton.click();
    }

    public void createOrder(String quantity, String cast, String futureJobType) {
        driver.get(baseUrl + "/user");
        waitIfNeed();
        setQuantityInput(quantity);
        waitIfNeed();
        setCastSelectionInput(cast);
        waitIfNeed();
        setFutureJobTypeSelectionInput(futureJobType);
        waitIfNeed();
        clickSendOrderButton();
    }

    public void exit(){
        driver.get(baseUrl + "/user");
        waitIfNeed();
        exitButton.click();
    }

    private void pressEnter(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
    }
}
