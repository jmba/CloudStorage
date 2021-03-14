package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    private final JavascriptExecutor js;

    public enum TabName {FILES, NOTES, CREDENTIALS}

    @Getter
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement fileTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    public void navigateToTab(TabName tabName) {
        switch (tabName) {
            case NOTES:
                js.executeScript("arguments[0].click();", noteTab);
                break;
            case CREDENTIALS:
                js.executeScript("arguments[0].click();", credentialsTab);
                break;
            default:
                js.executeScript("arguments[0].click();", fileTab);
        }
    }
}
