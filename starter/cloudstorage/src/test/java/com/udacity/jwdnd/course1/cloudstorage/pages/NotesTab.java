package com.udacity.jwdnd.course1.cloudstorage.pages;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

@Getter
public class NotesTab {
    private WebDriver driver;

    public NotesTab(WebDriver driver) {
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
        js.executeScript("arguments[0].click();", this.addNewNoteButton);
        js.executeScript("arguments[0].value='" + title + "';", this.noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", this.description);
        js.executeScript("arguments[0].click();", this.saveNoteButton);
    }

    public void editNote(String title, String description) {
        js.executeScript("arguments[0].click();", this.editNoteButton);
        js.executeScript("arguments[0].value='" + title + "';", this.noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", this.description);
        js.executeScript("arguments[0].click();", this.saveNoteButton);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", this.deleteNoteLink);
    }
}
