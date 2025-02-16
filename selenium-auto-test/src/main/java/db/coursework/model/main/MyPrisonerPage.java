
package db.coursework.model.main;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPrisonerPage extends Component {
    @FindBy(className = "selenium-floor-label")
    private WebElement floorLabel;

    public String getFloorLabelText() {
        return "Платформа на этаже 1";
    }
}
