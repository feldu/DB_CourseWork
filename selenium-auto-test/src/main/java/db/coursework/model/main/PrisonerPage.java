package db.coursework.model.main;

import db.coursework.model.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrisonerPage extends Component {
    @FindBy(className = "selenium_search_input")
    private WebElement searchInput;

    @FindBy(className = "selenium_add_prisoner_button")
    private WebElement addButton;


    public void clickAddButton() {
        addButton.click();
    }

    public void enterSearch(String search) {
        searchInput.sendKeys(search);
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrisonerForm extends Component {

        // Inputs
        @FindBy(id = "mat-input-1")
        private WebElement lastNameInput;

        @FindBy(id = "mat-input-2")
        private WebElement firstNameInput;

        @FindBy(id = "mat-input-3")
        private WebElement patronymicInput;

        @FindBy(id = "mat-input-4")
        private WebElement passportInput;

        @FindBy(id = "mat-input-5")
        private WebElement weightInput;

        @FindBy(id = "mat-input-7")
        private WebElement favoriteDishInput;

        @FindBy(id = "mat-input-8")
        private WebElement passwordInput;

        @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/div[2]/app-prisoners/app-prisoners-table/div/div[2]/app-prisoners-form/mat-card/form/div[2]/div/button[2]")
        private WebElement submitButton;

        public void enterLastName(String lastName) {
            lastNameInput.sendKeys(lastName);
        }

        public void enterFirstName(String firstName) {
            firstNameInput.sendKeys(firstName);
        }

        public void enterPatronymic(String patronymic) {
            patronymicInput.sendKeys(patronymic);
        }

        public void enterPassport(String passport) {
            passportInput.sendKeys(passport);
        }

        public void enterWeight(Integer weight) {
            weightInput.sendKeys(weight.toString());
        }

        public void enterFavoriteDish(String favoriteDish) {
            favoriteDishInput.sendKeys(favoriteDish);
        }

        public void enterPassword(String password) {
            passwordInput.sendKeys(password);
        }

        public void clickSubmitButton() {
            submitButton.click();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrisonerTable extends Component {

        @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/app-prisoners/app-prisoners-table/div/table/tbody/tr/td[2]")
        private WebElement lastNameTd;
        @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/app-prisoners/app-prisoners-table/div/table/tbody/tr/td[3]")
        private WebElement firstNameTd;
        @FindBy(xpath = "/html/body/app-root/app-main-page/mat-sidenav-container/mat-sidenav-content/div/app-prisoners/app-prisoners-table/div/table/tbody/tr/td[4]")
        private WebElement patronymicTd;


        public void clickAddPointsButton(String lastName) {
//            driver.findElement(By.className("selenium-add-points-" + lastName)).click();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrisonerAddPointPopup extends Component {
        @FindBy(className = "selenium-add-points-input")
        private WebElement addPointsInput;
        @FindBy(xpath = "//*[@id=\"mat-dialog-0\"]/app-add-points-dialog/div[2]/button[2]")
        private WebElement addPointsButton;

        public void enterPointsAmount(Integer amount) {
//            addPointsInput.sendKeys(amount.toString());
        }

        public void clickAddPointsButton(){
//            addPointsInput.click();
        }
    }

}
