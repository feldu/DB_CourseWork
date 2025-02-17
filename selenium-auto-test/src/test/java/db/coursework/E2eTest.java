package db.coursework;

public class E2eTest extends SeleniumTest {
//    private final String PRISONER_PATH = baseUrl + "/main/prisoners";
//    private final String DISH_PATH = baseUrl + "/main/dishes";
//    private final String PLATFORM_PATH = baseUrl + "/main/platform";
//    private SideNavComponent sideNavComponent;
//
//    @BeforeEach
//    public void init() throws InterruptedException {
//        new LoginForm().logIn(PropUtils.get("auth.admin.login"), PropUtils.get("auth.admin.password"));
//    }
//
//    @Test
//    public void businessLogicTest() {
//        driver.get(PRISONER_PATH);
//        sideNavComponent = new SideNavComponent();
//
//        final String lastName = RandomUtils.randowString();
//        final String firstName = RandomUtils.randowString();
//        final String patronymic = RandomUtils.randowString();
//        final String passport = RandomUtils.randomPassport();
//        final Integer weight = RandomUtils.randomWeight();
//        final String password = RandomUtils.randowString();
//        final String favoriteDish = RandomUtils.randowString();
//
//        // 1. Создание заключенного регистратором заключенных
//        var prisonerPage = new PrisonerPage();
//        prisonerPage.clickAddButton();
//
//        var prisonerForm = new PrisonerPage.PrisonerForm();
//        prisonerForm.enterLastName(lastName);
//        prisonerForm.enterFirstName(firstName);
//        prisonerForm.enterPatronymic(patronymic);
//        prisonerForm.enterPassport(passport);
//        prisonerForm.enterWeight(weight);
//        prisonerForm.enterFavoriteDish(favoriteDish);
//        prisonerForm.enterPassword(password);
//        prisonerForm.clickSubmitButton();
//
//        // 2. Создание блюда поваром
//        changeUser("cook");
//        driver.get(DISH_PATH);
//
//        DishPage dishPage = new DishPage();
//        WebElement addNewDishButton = dishPage.getAddNewDishButton(favoriteDish);
//        Assertions.assertNotNull(addNewDishButton);
//        addNewDishButton.click();
//
//        DishPage.DishEditForm dishEditForm = new DishPage.DishEditForm();
//        dishEditForm.enterReceipt(RandomUtils.randowString());
//        dishEditForm.clickEditButton();
//
//        // 3. Добавление баллов аналитиком
//        changeUser("analytic");
//        driver.get(PRISONER_PATH);
//        var prisonerTable = new PrisonerPage.PrisonerTable();
//        prisonerPage.enterSearch(lastName);
//        prisonerTable.clickAddPointsButton(lastName);
//        var prisonerEditPopup = new PrisonerPage.PrisonerAddPointPopup();
//        prisonerEditPopup.enterPointsAmount(100);
//        prisonerEditPopup.clickAddPointsButton();
//
//        // 4. Перераспледение заключенных по этажам упарвляющий платформы
//        changeUser("platform_manager");
//        driver.get(PLATFORM_PATH);
//        var platformPage = new PlatformPage();
//        platformPage.clickStopButton();
//        platformPage.clickDistributeButton();
//        platformPage.clickStartButton();
//
//        changeUser(passport, password);
//        var myPrisonerPage = new MyPrisonerPage();
//        Assertions.assertEquals("Платформа на этаже 1", myPrisonerPage.getFloorLabelText());
//    }
//
//    private void changeUser(String login, String password) {
//        sideNavComponent.logoutClick();
//        new LoginForm().logIn(login, password);
//    }
//
//    private void changeUser(String role) {
//        sideNavComponent.logoutClick();
//        new LoginForm().logIn(PropUtils.get(String.format("auth.%s.login", role)), PropUtils.get(String.format("auth.%s.password", role)));
//    }
}
