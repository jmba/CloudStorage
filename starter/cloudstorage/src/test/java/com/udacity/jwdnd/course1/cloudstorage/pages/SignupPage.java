package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class SignupPage {
    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 5);
    }

    WebDriverWait wait = null;

    private final JavascriptExecutor js;

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    public void signUp(String firstName, String lastName, String username, String password) {
        js.executeScript("arguments[0].value='" + firstName + "';", WaitForVisibility(this.firstName));
        js.executeScript("arguments[0].value='" + lastName + "';", WaitForVisibility(this.lastName));
        js.executeScript("arguments[0].value='" + username + "';", WaitForVisibility(this.username));
        js.executeScript("arguments[0].value='" + password + "';", WaitForVisibility(this.password));
        js.executeScript("arguments[0].click();", WaitForVisibility(this.submitButton));
    }

    private WebElement WaitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
