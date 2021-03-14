package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CredentialsTab {
    public CredentialsTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

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
        js.executeScript("arguments[0].click();", this.addCredentialsButton);
        js.executeScript("arguments[0].value='" + Url + "';", this.url);
        js.executeScript("arguments[0].value='" + username + "';", this.username);
        js.executeScript("arguments[0].value='" + password + "';", this.password);
        js.executeScript("arguments[0].click();", this.saveCredentialButton);
    }

    public void editCredential(String url, String username, String password) {
        js.executeScript("arguments[0].click();", this.editCredentialsButton);
        js.executeScript("arguments[0].value='" + url + "';", this.url);
        js.executeScript("arguments[0].value='" + username + "';", this.username);
        js.executeScript("arguments[0].value='" + password + "';", this.password);
        js.executeScript("arguments[0].click();", this.saveCredentialButton);
    }

    public void clickEditCredentials() {
        js.executeScript("arguments[0].click();", this.editCredentialsButton);
    }

    public void closeEditCredentialsModal() {
        js.executeScript("arguments[0].click();", this.closeCredentialsModal);
    }

    public void deleteCredntial() {
        js.executeScript("arguments[0].click();", this.deleteCredentialsLink);
    }
}
