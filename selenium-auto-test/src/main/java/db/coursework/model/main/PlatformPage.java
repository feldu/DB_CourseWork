package db.coursework.model.main;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlatformPage extends Component {
    @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/div[2]/app-platform/div/div/button[4]")
    private WebElement stopPlatformButton;

    @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/div[2]/app-platform/div/div/button[2]")
    private WebElement distributePlatformButton;

    @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/div[2]/app-platform/div/div/button[2]")
    private WebElement startPlatformButton;


    public void clickStartButton() {
        startPlatformButton.click();
    }

    public void clickDistributeButton() {
        distributePlatformButton.click();
    }

    public void clickStopButton() {
        stopPlatformButton.click();
    }

}
