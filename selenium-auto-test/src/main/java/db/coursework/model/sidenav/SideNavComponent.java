package db.coursework.model.sidenav;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SideNavComponent extends Component {
    @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav/div/mat-nav-list/a[3]")
    private WebElement menuButton;
    @FindBy(className = "login-selenium-selector")
    private WebElement logoutButton;

    public void menuClick() {
        menuButton.click();
    }
    public void logoutClick() {
        logoutButton.click();
    }
}
