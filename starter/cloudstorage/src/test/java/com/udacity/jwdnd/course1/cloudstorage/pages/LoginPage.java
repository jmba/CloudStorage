package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class LoginPage {

    WebDriverWait wait = null;

    public LoginPage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 50);
    }

    private final JavascriptExecutor js;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public void login(String username, String password) {
        js.executeScript("arguments[0].value='" + username + "';", WaitForVisibility(this.username));
        js.executeScript("arguments[0].value='" + password + "';", WaitForVisibility(this.password));
        js.executeScript("arguments[0].click();", WaitForVisibility(submitButton));
    }

    private WebElement WaitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}


