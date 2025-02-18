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

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div/div[2]/div/div/div[2]/form/button")
    private WebElement processOrderButton;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div/div[2]/div/div/div/form[1]/button")
    private WebElement enter1StepButton;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div/div[2]/div/div/div/form[2]/button")
    private WebElement enter2StepButton;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div/div[2]/div/div/div/form[3]/button")
    private WebElement enter3StepButton;

    @FindBy(xpath = "/html/body/div/div/nav/button")
    private WebElement exitButton;

    public void setPredeterminerInput(String predeterminer) {
        predeterminerInput.sendKeys(predeterminer);
        pressEnter();
    }

    public void setOrderInput(String order) {
        orderInput.sendKeys(order);
        pressEnter();
    }

    public void clickProcessOrderButton() {
        processOrderButton.click();
    }

    public void clickEnter1StepButton() {
        enter1StepButton.click();
    }

    public void clickEnter2StepButton() {
        enter2StepButton.click();
    }

    public void clickEnter3StepButton() {
        enter3StepButton.click();
    }

    public void processOrder(String predeterminer, String order) {
        driver.get(baseUrl + "/admin");
        waitIfNeed();
        setPredeterminerInput(predeterminer);
        waitIfNeed();
        setOrderInput(order);
        waitIfNeed();
        clickProcessOrderButton();
        waitIfNeed();
        clickEnter1StepButton();
        waitIfNeed();
//        clickEnter2StepButton();
//        waitIfNeed();
        clickEnter3StepButton();
        waitIfNeed();
    }

    public void exit(){
        driver.get(baseUrl + "/admin");
        waitIfNeed();
        exitButton.click();
    }

    private void pressEnter(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
    }
}
