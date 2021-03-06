package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class NotesTab {
    private WebDriver driver;
    WebDriverWait wait = null;

    public NotesTab(WebDriver driver) {
        wait = new WebDriverWait(driver, 50);
        PageFactory.initElements(driver, this);
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    private final JavascriptExecutor js;

    @FindBy(id = "add-note")
    private WebElement addNewNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement description;

    @FindBy(id = "note-title-table-elem")
    private WebElement noteTitleTableElem;

    @FindBy(id = "note-description-table-elem")
    private WebElement notDescriptionTableElem;

    @FindBy(id = "save-note")
    private WebElement saveNoteButton;

    @Getter
    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-link")
    private WebElement deleteNoteLink;

    public void createNote(String title, String description) {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.addNewNoteButton));
        js.executeScript("arguments[0].value='" + title + "';", WaitForVisibility(this.noteTitle));
        js.executeScript("arguments[0].value='" + description + "';", WaitForVisibility(this.description));
        js.executeScript("arguments[0].click();", WaitForVisibility(this.saveNoteButton));
    }

    public void editNote(String title, String description) {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.editNoteButton));
        js.executeScript("arguments[0].value='" + title + "';", WaitForVisibility(this.noteTitle));
        js.executeScript("arguments[0].value='" + description + "';", WaitForVisibility(this.description));
        js.executeScript("arguments[0].click();", WaitForVisibility(this.saveNoteButton));
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", WaitForVisibility(this.deleteNoteLink));
    }

    private WebElement WaitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
