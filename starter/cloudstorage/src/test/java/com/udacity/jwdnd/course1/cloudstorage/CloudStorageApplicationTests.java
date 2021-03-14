package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage.TabName;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        navigate("/signup");
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        notesTab = new NotesTab(driver);
        credentialsTab = new CredentialsTab(driver);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
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
        assertEquals(signupPage.getSuccessMessage().getText(), "You successfully signed up! Please continue to the login page.");
        assertThrows(NoSuchElementException.class, () -> signupPage.getErrorMessage().getText());
        signupPage.signUp(TESTFIRSTNAME, TESTLASTNAME, TESTUSERNAME, TESTPASSWORD);
        assertEquals(signupPage.getErrorMessage().getText(), "The username already exists.");
        assertThrows(NoSuchElementException.class, () -> signupPage.getSuccessMessage().getText());
        navigate("/login");
        loginPage.login("Donald", "Trump");
        assertEquals(loginPage.getErrorMessage().getText(), "Invalid username or password");
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.getLogoutButton().submit();
        Assertions.assertEquals("Login", driver.getTitle());
        navigate("/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    @Order(2)
    public void createNoteTest() {
        String title = "Title";
        String description = "Description";
        loginPage.login(TESTUSERNAME, TESTPASSWORD);
        homePage.navigateToTab(TabName.NOTES);
        notesTab.createNote(title, description);
        Assertions.assertEquals(title, notesTab.getNoteTitleTableElem().getText());
        Assertions.assertEquals(description, notesTab.getNotDescriptionTableElem().getText());
    }

    @Test
    @Order(3)
    public void editNoteTest() {
        String titleNew = "NEW_TITLE";
        String descriptionNew = "NEW_Description";
        notesTab.editNote(titleNew, descriptionNew);
        Assertions.assertEquals(titleNew, notesTab.getNoteTitleTableElem().getText());
        Assertions.assertEquals(descriptionNew, notesTab.getNotDescriptionTableElem().getText());
    }

    @Test
    @Order(4)
    public void deleteNoteTest() {
        notesTab.deleteNote();
        assertThrows(NoSuchElementException.class, () -> notesTab.getNoteTitleTableElem().getText());
        assertThrows(NoSuchElementException.class, () -> notesTab.getNotDescriptionTableElem().getText());
    }

    @Test
    @Order(5)
    public void createCredentialsTest() {
        String url = "https://classroom.udacity.com/";
        String userName = "Username";
        String password = "Password";
        homePage.navigateToTab(TabName.CREDENTIALS);
        credentialsTab.createCredential(url, userName, password);
        Assertions.assertEquals(url, credentialsTab.getUrlTableElement().getText());
        Assertions.assertEquals(userName, credentialsTab.getUsernameTableElement().getText());
        Assertions.assertNotEquals(password, credentialsTab.getPasswordTableElement().getText());
    }

    @Test
    @Order(6)
    public void editCredentialsTest() {
        String newUrl = "https://newUrl.com/";
        String newUserName = "newUsername";
        String newPassword = "newPassword";
        String oldPassword = "Password";
        credentialsTab.clickEditCredentials();
        Assertions.assertEquals(oldPassword, credentialsTab.getPassword().getAttribute("value"));
        credentialsTab.closeEditCredentialsModal();
        credentialsTab.editCredential(newUrl, newUserName, newPassword);
        credentialsTab.clickEditCredentials();
        Assertions.assertEquals(newPassword, credentialsTab.getPassword().getAttribute("value"));
        Assertions.assertEquals(newUrl, credentialsTab.getUrl().getAttribute("value"));
        Assertions.assertEquals(newUserName, credentialsTab.getUsername().getAttribute("value"));
    }

    @Test
    @Order(7)
    public void deleteCredentialsTest() {
        credentialsTab.deleteCredntial();
        assertThrows(NoSuchElementException.class, () -> credentialsTab.getUrlTableElement().getText());
        assertThrows(NoSuchElementException.class, () -> credentialsTab.getUsernameTableElement().getText());
        assertThrows(NoSuchElementException.class, () -> credentialsTab.getPasswordTableElement().getText());
    }
}
