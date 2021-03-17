package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage.TabName;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

    @LocalServerPort
    private static Integer port = 8080;
    private static WebDriver driver;
    private static SignupPage signupPage;
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static NotesTab notesTab;
    private static CredentialsTab credentialsTab;
    private String TESTUSERNAME = "User";
    private String TESTPASSWORD = "Password";
    private String TESTFIRSTNAME = "FirstName";
    private String TESTLASTNAME = "LastName";
    private static WebDriverWait wait = null;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        notesTab = new NotesTab(driver);
        credentialsTab = new CredentialsTab(driver);
        wait = new WebDriverWait(driver, 50);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    private static void navigate(String endpoint) {
        driver.get("http://localhost:" + port + endpoint);
    }

    @Test
    @Order(0)
    public void unauthorizedUserComesInTest() {
        navigate("/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        navigate("/login");
        Assertions.assertEquals("Login", driver.getTitle());
        navigate("/home");
        Assertions.assertEquals("Login", driver.getTitle());
        navigate("/blablabla");
        Assertions.assertEquals("Login", driver.getTitle());
        navigate("");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(1)
    public void signUpLoginLogoutTest() {
        navigate("/signup");
        signupPage.signUp(TESTFIRSTNAME, TESTLASTNAME, TESTUSERNAME, TESTPASSWORD);
        assertEquals(WaitForVisibility(loginPage.getSuccessMessage()).getText(), "You successfully signed up!");
        navigate("/signup");
        signupPage.signUp(TESTFIRSTNAME, TESTLASTNAME, TESTUSERNAME, TESTPASSWORD);
        assertEquals(WaitForVisibility(signupPage.getErrorMessage()).getText(), "The username already exists.");
        assertThrows(NoSuchElementException.class, () -> signupPage.getSuccessMessage().getText());
        navigate("/login");
        loginPage.login("Donald", "Trump");
        assertEquals(WaitForVisibility(loginPage.getErrorMessage()).getText(), "Invalid username or password");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        WaitForVisibility(homePage.getLogoutButton()).submit();
        Assertions.assertEquals("Login", driver.getTitle());
        navigate("/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    @Order(2)
    public void createNoteTest() {
        navigate("/login");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.NOTES);
        String title = "Title";
        String description = "Description";
        notesTab.createNote(title, description);
        Assertions.assertEquals(title, WaitForVisibility(notesTab.getNoteTitleTableElem()).getText());
        Assertions.assertEquals(description, WaitForVisibility(notesTab.getNotDescriptionTableElem()).getText());
    }

    @Test
    @Order(3)
    public void editNoteTest() {
        navigate("/login");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.NOTES);
        String titleNew = "NEW_TITLE";
        String descriptionNew = "NEW_Description";
        notesTab.editNote(titleNew, descriptionNew);
        Assertions.assertEquals(titleNew, WaitForVisibility(notesTab.getNoteTitleTableElem()).getText());
        Assertions.assertEquals(descriptionNew, WaitForVisibility(notesTab.getNotDescriptionTableElem()).getText());
    }

    @Test
    @Order(4)
    public void deleteNoteTest() {
        navigate("/login");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.NOTES);
        notesTab.deleteNote();
        assertThrows(NoSuchElementException.class, () -> notesTab.getNoteTitleTableElem().getText());
        assertThrows(NoSuchElementException.class, () -> notesTab.getNotDescriptionTableElem().getText());
    }

    @Test
    @Order(5)
    public void createCredentialsTest() {
        navigate("/login");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.CREDENTIALS);
        String url = "https://classroom.udacity.com/";
        String userName = "Username";
        String password = "Password";
        homePage.navigateToTab(TabName.CREDENTIALS);
        credentialsTab.createCredential(url, userName, password);
        Assertions.assertEquals(url, WaitForVisibility(credentialsTab.getUrlTableElement()).getText());
        Assertions.assertEquals(userName, WaitForVisibility(credentialsTab.getUsernameTableElement()).getText());
        Assertions.assertNotEquals(password, WaitForVisibility(credentialsTab.getPasswordTableElement()).getText());
    }

    @Test
    @Order(6)
    public void editCredentialsTest() {
        navigate("/login");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.CREDENTIALS);
        String newUrl = "https://newUrl.com/";
        String newUserName = "newUsername";
        String newPassword = "newPassword";
        String oldPassword = "Password";
        credentialsTab.clickEditCredentials();
        Assertions.assertEquals(oldPassword, WaitForVisibility(credentialsTab.getPassword()).getAttribute("value"));
        credentialsTab.closeEditCredentialsModal();
        credentialsTab.editCredential(newUrl, newUserName, newPassword);
        credentialsTab.clickEditCredentials();
        Assertions.assertEquals(newPassword, WaitForVisibility(credentialsTab.getPassword()).getAttribute("value"));
        Assertions.assertEquals(newUrl, WaitForVisibility(credentialsTab.getUrl()).getAttribute("value"));
        Assertions.assertEquals(newUserName, WaitForVisibility(credentialsTab.getUsername()).getAttribute("value"));
    }

    @Test
    @Order(7)
    public void deleteCredentialsTest() {
        navigate("/login");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.CREDENTIALS);
        credentialsTab.deleteCredntial();
        assertThrows(NoSuchElementException.class, () -> credentialsTab.getUrlTableElement().getText());
        assertThrows(NoSuchElementException.class, () -> credentialsTab.getUsernameTableElement().getText());
        assertThrows(NoSuchElementException.class, () -> credentialsTab.getPasswordTableElement().getText());
    }

    private WebElement WaitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
