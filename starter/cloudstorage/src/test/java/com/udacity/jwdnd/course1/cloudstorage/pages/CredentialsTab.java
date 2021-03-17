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
public class CredentialsTab {
    public CredentialsTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 50);
    }

    WebDriverWait wait = null;
    private final JavascriptExecutor js;

    @FindBy(id = "credential-url")
    private WebElement url;

    @FindBy(id = "credential-username")
    private WebElement username;

    @FindBy(id = "credential-password")
    private WebElement password;

    @FindBy(id = "add-credentials-button")
    private WebElement addCredentialsButton;

    @FindBy(id = "save-credentials")
    private WebElement saveCredentialButton;

    @FindBy(id = "close-credentials-modal")
    private WebElement closeCredentialsModal;

    @FindBy(id = "edit-credentials-button")
    private WebElement editCredentialsButton;

    @FindBy(id = "delete-credentials-link")
    private WebElement deleteCredentialsLink;

    @FindBy(id = "credentials-url-table-elem")
    private WebElement urlTableElement;

    @FindBy(id = "credentials-username-table-elem")
    private WebElement usernameTableElement;

    @FindBy(id = "credentials-password-table-elem")
    private WebElement passwordTableElement;

    public void createCredential(String Url, String username, String password) {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.addCredentialsButton));
        js.executeScript("arguments[0].value='" + Url + "';", WaitForVisibility(this.url));
        js.executeScript("arguments[0].value='" + username + "';", WaitForVisibility(this.username));
        js.executeScript("arguments[0].value='" + password + "';", WaitForVisibility(this.password));
        js.executeScript("arguments[0].click();", WaitForVisibility(this.saveCredentialButton));
    }

    public void editCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.editCredentialsButton));
        js.executeScript("arguments[0].value='" + url + "';", WaitForVisibility(this.url));
        js.executeScript("arguments[0].value='" + username + "';", WaitForVisibility(this.username));
        js.executeScript("arguments[0].value='" + password + "';", WaitForVisibility(this.password));
        js.executeScript("arguments[0].click();", WaitForVisibility(this.saveCredentialButton));
    }

    public void clickEditCredentials() {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.editCredentialsButton));
    }

    public void closeEditCredentialsModal() {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.closeCredentialsModal));
    }

    public void deleteCredntial() {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.deleteCredentialsLink));
    }

    private WebElement WaitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
