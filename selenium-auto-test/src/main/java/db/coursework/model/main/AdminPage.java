package db.coursework.model.main;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static db.coursework.utils.WaitUtils.waitIfNeed;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminPage extends Component {
    @FindBy(xpath = "/html/body/div/div/div/div[1]/div/form/div/div/div/div[1]/div[2]/input")
    private WebElement predeterminerInput;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div/div[2]/form/div/div/div/div[1]/div[2]/input")
    private WebElement orderInput;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/form/div[3]/div/div/div[1]/div[2]/input")
    private WebElement futureJobTypeSelectionInput;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/form/button")
    private WebElement sendOrderButton;

    @FindBy(xpath = "/html/body/div/div/nav/button")
    private WebElement exitButton;

    public void setPredeterminerInput(String predeterminer) {
        predeterminerInput.sendKeys(predeterminer);
    }

    public void setOrderInput(String order) {
        orderInput.sendKeys(order);
        pressEnter();
    }

    public void clickSendOrderButton() {
        sendOrderButton.click();
    }

    public void processOrder(String predeterminer, String order) {
        driver.get(baseUrl + "/user");
        waitIfNeed();
        setPredeterminerInput(predeterminer);
        waitIfNeed();
        setOrderInput(order);
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
