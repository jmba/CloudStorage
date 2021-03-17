package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class HomePage {
    private final JavascriptExecutor js;
    public enum TabName {FILES, NOTES, CREDENTIALS}
    WebDriverWait wait = null;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 5);
    }

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
                js.executeScript("arguments[0].click();", WaitForVisibility(noteTab));
                break;
            case CREDENTIALS:
                js.executeScript("arguments[0].click();", WaitForVisibility(credentialsTab));
                break;
            default:
                js.executeScript("arguments[0].click();", WaitForVisibility(fileTab));
        }
    }

    private WebElement WaitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
