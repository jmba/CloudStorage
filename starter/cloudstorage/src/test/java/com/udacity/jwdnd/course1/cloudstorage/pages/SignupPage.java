package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SignupPage {
    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

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
        js.executeScript("arguments[0].value='" + firstName + "';", this.firstName);
        js.executeScript("arguments[0].value='" + lastName + "';", this.lastName);
        js.executeScript("arguments[0].value='" + username + "';", this.username);
        js.executeScript("arguments[0].value='" + password + "';", this.password);
        js.executeScript("arguments[0].click();", this.submitButton);
    }
}
