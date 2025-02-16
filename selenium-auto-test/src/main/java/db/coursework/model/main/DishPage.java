package db.coursework.model.main;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DishPage extends Component {

    public WebElement getAddNewDishButton(String dishName) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> dishes = driver.findElements(By.className("selenium-selector-add-dish"));

        for (WebElement dish : dishes) {
            if (Arrays.stream(dish.getText().split("\\s+")).anyMatch(t -> t.contains(dishName))) {
                return dish;
            }
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class DishEditForm extends Component {
        @FindBy(id = "dishReceipt")
        private WebElement receiptInput;

        @FindBy(className = "selenium_dish_edit_button")
        private WebElement editButton;

        public void enterReceipt(String receipt) {
            receiptInput.sendKeys(receipt);
        }

        public void clickEditButton() {
            editButton.click();
        }

    }
}
