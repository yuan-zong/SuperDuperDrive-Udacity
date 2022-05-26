package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;

public class HomePage {

    private WebDriver webDriver;
    public HomePage(WebDriver webDriver) {
        this.webDriver=webDriver;
        PageFactory.initElements(webDriver, this);
    }




    @FindBy(id="logout")
    WebElement logoutButton;

//    @FindBy(id="nav-files-tab")
//    WebElement fileTab;
//    @FindBy(id="uploadButton")
//    WebElement uploadButton;
//    @FindBy(id="fileUploadPath")
//    WebElement fileUploadPath;

    @FindBy(id="nav-notes-tab")
    WebElement noteTab;
    @FindBy(id="noteAddButton")
    WebElement noteAddButton;
//    @FindBy(id="noteTitleDisplay")
//    WebElement noteTitleDisplay;
//    @FindBy(id="noteDescriptionDisplay")
//    WebElement noteDescriptionDisplay;
    @FindBy(id="note-title")
    WebElement noteTitle;
    @FindBy(id="note-description")
    WebElement noteDescription;
    @FindBy(id="noteSubmitButton")
    WebElement noteSubmitButton;

    @FindBy(id="nav-credentials-tab")
    WebElement credentialTab;
    @FindBy(id="credentialAddButton")
    WebElement credentialAddButton;

    @FindBy(id="credential-url")
    WebElement credentialURL;
    @FindBy(id="credential-username")
    WebElement credentialUsername;
    @FindBy(id="credential-password")
    WebElement credentialPasswd;
    @FindBy(id="credentialCancelButton")
    WebElement credentialCancelButton;
    @FindBy(id="credentialSubmitButton")
    WebElement credentialSubmitButton;


    public void logout(){
        this.logoutButton.click();
    }
}
