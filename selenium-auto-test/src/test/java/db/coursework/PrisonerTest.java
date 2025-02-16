//package itmo.sadiva;
//
//import itmo.sadiva.model.main.prisoners.PrisonerForm;
//import itmo.sadiva.model.main.PrisonerPage;
//import itmo.sadiva.model.main.prisoners.PrisonerTable;
//import itmo.sadiva.utils.RandomUtils;
//import itmo.sadiva.utils.TestUtils;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class PrisonerTest extends SeleniumTest {
//
//    @BeforeEach
//    public void setUp() {
//        TestUtils.login(webDriver);
//        webDriver.get(baseUrl + "/main/prisoners");
//    }
//
//    // Тест на добавление заключенного
//    @Test
//    public void addPrisonerTest() throws InterruptedException {
//        // Данные для нового заключенного
//        final String expectedLastName = RandomUtils.randowString();
//        final String expectedFirstName = RandomUtils.randowString();
//        final String expectedPatronymic = RandomUtils.randowString();
//        final String expectedPassport = RandomUtils.randomPassport();
//        final Integer expectedWeight = RandomUtils.randomWeight();
//        final String expectedFavoriteDish = "Борщ";
//
//        PrisonerPage prisonerPage = new PrisonerPage();
//        assertTrue(webDriver.getCurrentUrl().contains("/main/prisoners"));
//
//        // Создание заключенного
//        prisonerPage.getCreatePrisonerButton().click();
//        PrisonerForm prisonerForm = new PrisonerForm();
//        prisonerForm.getLastNameInput().sendKeys(expectedLastName);
//        prisonerForm.getFirstNameInput().sendKeys(expectedFirstName);
//        prisonerForm.getPatronymicInput().sendKeys(expectedPatronymic);
//        prisonerForm.getPassportInput().sendKeys(expectedPassport);
//        prisonerForm.getWeightInput().sendKeys(expectedWeight.toString());
//        prisonerForm.getFavoriteDishInput().sendKeys(expectedFavoriteDish);
//        prisonerForm.getCreateButton().click();
//
//        PrisonerTable prisonerTable = new PrisonerTable();
//
//        // Проверка, что заключенный отображается в таблице
//        prisonerPage.getSearchInput().sendKeys(expectedLastName);
//    }
//
//    // Тест на добавление заключенного с некорректным паспортом
//    @Test
//    public void addPrisonerWithInvalidPassportTest() throws InterruptedException {
//        // Данные для заключенного с некорректным паспортом
//        final String expectedLastName = RandomUtils.randowString();
//        final String expectedFirstName = RandomUtils.randowString();
//        final String expectedPatronymic = RandomUtils.randowString();
//        final String invalidPassport = "12345"; // Некорректный паспорт
//        final Integer expectedWeight = RandomUtils.randomWeight();
//        final String expectedFavoriteDish = "Борщ";
//
//        PrisonerPage prisonerPage = new PrisonerPage();
//        assertTrue(webDriver.getCurrentUrl().contains("/main/prisoners"));
//
//        // Создание заключенного с некорректным паспортом
//        prisonerPage.getCreatePrisonerButton().click();
//        PrisonerForm prisonerForm = new PrisonerForm();
//        prisonerForm.getLastNameInput().sendKeys(expectedLastName);
//        prisonerForm.getFirstNameInput().sendKeys(expectedFirstName);
//        prisonerForm.getPatronymicInput().sendKeys(expectedPatronymic);
//        prisonerForm.getPassportInput().sendKeys(invalidPassport); // Вводим некорректный паспорт
//        prisonerForm.getWeightInput().sendKeys(expectedWeight.toString());
//        prisonerForm.getFavoriteDishInput().sendKeys(expectedFavoriteDish);
//        prisonerForm.getCreateButton().click();
//
//
//        // Проверка ошибки на некорректный паспорт
//        assertTrue(prisonerForm.getPassportError().isDisplayed(), "Ошибка для паспорта не отображается!");
//    }
//
//    // Тест на успешное добавление заключенного с валидными данными
//    @Test
//    public void addPrisonerWithValidDataTest() throws InterruptedException {
//        // Данные для нового заключенного
//        final String expectedLastName = RandomUtils.randowString();
//        final String expectedFirstName = RandomUtils.randowString();
//        final String expectedPatronymic = RandomUtils.randowString();
//        final String expectedPassport = RandomUtils.randomPassport();
//        final Integer expectedWeight = RandomUtils.randomWeight();
//        final String expectedFavoriteDish = "Борщ";
//
//        PrisonerPage prisonerPage = new PrisonerPage();
//        assertTrue(webDriver.getCurrentUrl().contains("/main/prisoners"));
//
//        // Создание заключенного
//        prisonerPage.getCreatePrisonerButton().click();
//        PrisonerForm prisonerForm = new PrisonerForm();
//        prisonerForm.getLastNameInput().sendKeys(expectedLastName);
//        prisonerForm.getFirstNameInput().sendKeys(expectedFirstName);
//        prisonerForm.getPatronymicInput().sendKeys(expectedPatronymic);
//        prisonerForm.getPassportInput().sendKeys(expectedPassport);
//        prisonerForm.getWeightInput().sendKeys(expectedWeight.toString());
//        prisonerForm.getFavoriteDishInput().sendKeys(expectedFavoriteDish);
//        prisonerForm.getCreateButton().click();
//
//
//        // Проверка, что заключенный отображается в таблице
//        prisonerPage.getSearchInput().sendKeys(expectedLastName);
//    }
//
//    @AfterEach
//    public void tearDown() {
////        webDriver.quit();
//    }
//}
