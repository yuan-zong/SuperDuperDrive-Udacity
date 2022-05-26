package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	public String baseURL;
	private WebDriver driver;

	private String myTestUsername = "myTestUsername";
	private String myTestPassword = "myTestPassword";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void saveNoteNow(HomePage homePage, String noteTitleStr, String noteDescriptionStr) {
		homePage.noteTitle.clear();
		homePage.noteTitle.click();
		homePage.noteTitle.sendKeys(noteTitleStr);
		homePage.noteDescription.clear();
		homePage.noteDescription.click();
		homePage.noteDescription.sendKeys(noteDescriptionStr);
		homePage.noteSubmitButton.click();
	}

	public void addNote(HomePage homePage, WebDriverWait webDriverWait, String noteTitleStr, String noteDescriptionStr){
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteAddButton")));
		homePage.noteAddButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		saveNoteNow(homePage, noteTitleStr, noteDescriptionStr);
	}
	public void editNote(HomePage homePage, WebDriver driver, WebDriverWait webDriverWait, int idx, String newTitle, String newDescription){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteEditButton")));
		List<WebElement> credentialEditButtons = driver.findElements(By.id("noteEditButton"));
		credentialEditButtons.get(idx).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		saveNoteNow(homePage, newTitle, newDescription);
	}

	public void deleteNote(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDeleteButton")));
		List<WebElement> noteDeleteButtons = driver.findElements(By.id("noteDeleteButton"));
		noteDeleteButtons.get(idx).click();
	}


	public String getNoteTitleDisplay(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleDisplay")));
		List<WebElement> noteTitleDisplays = driver.findElements(By.id("noteTitleDisplay"));
		return noteTitleDisplays.get(idx).getAttribute("innerHTML");
	}

	public String getNoteDescriptionDisplay(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescriptionDisplay")));
		List<WebElement> noteDescriptionDisplays = driver.findElements(By.id("noteDescriptionDisplay"));
		return noteDescriptionDisplays.get(idx).getAttribute("innerHTML");
	}

	private void saveCredentialNow(HomePage homePage, String url, String username, String password) {
		homePage.credentialURL.clear();
		homePage.credentialURL.click();
		homePage.credentialURL.sendKeys(url);
		homePage.credentialUsername.clear();
		homePage.credentialUsername.click();
		homePage.credentialUsername.sendKeys(username);
		homePage.credentialPasswd.clear();
		homePage.credentialPasswd.click();
		homePage.credentialPasswd.sendKeys(password);
		homePage.credentialSubmitButton.click();
	}

	public void addCredential(HomePage homePage, WebDriverWait webDriverWait, String url, String username, String password){
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialAddButton")));
		homePage.credentialAddButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		saveCredentialNow(homePage, url, username, password);
	}
	public void editCredential(HomePage homePage, WebDriver driver, WebDriverWait webDriverWait, int idx, String newURL, String newUsername, String newPassword){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialEditButton")));
		List<WebElement> credentialEditButtons = driver.findElements(By.id("credentialEditButton"));
		credentialEditButtons.get(idx).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		saveCredentialNow(homePage, newURL, newUsername, newPassword);
	}

	public void deleteCredential(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialDeleteButton")));
		List<WebElement> credentialDeleteButtons = driver.findElements(By.id("credentialDeleteButton"));
		credentialDeleteButtons.get(idx).click();
	}

	public String getCredentialURLDisplay(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialURLDisplay")));
		List<WebElement> credentialURLDisplays = driver.findElements(By.id("credentialURLDisplay"));
		return credentialURLDisplays.get(idx).getAttribute("innerHTML");
	}

	public String getCredentialUsernameDisplay(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUsernameDisplay")));
		List<WebElement> credentialUsernameDisplays = driver.findElements(By.id("credentialUsernameDisplay"));
		return credentialUsernameDisplays.get(idx).getAttribute("innerHTML");
	}

	public String getCredentialPasswordDisplay(WebDriver driver, WebDriverWait webDriverWait, int idx){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialPasswordDisplay")));
		List<WebElement> credentialPasswordDisplays = driver.findElements(By.id("credentialPasswordDisplay"));
		return credentialPasswordDisplays.get(idx).getAttribute("innerHTML");
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	@Order(2)
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	@Order(3)
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	@Order(7)
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUploadPath")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUploadPath"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	@Order(4)
	public void testHomePageAccessibility() {

		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getCurrentUrl().contains("home"));
		driver.get(baseURL + "/login");

		// login with a user that doesn't exist
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("HomeT", "123");
		Assertions.assertFalse(driver.getCurrentUrl().contains("home"));

		// Create a test account
		doMockSignUp("Large File","Test",myTestUsername, myTestPassword);
		doLogIn(myTestUsername, myTestPassword);
		Assertions.assertTrue(driver.getCurrentUrl().contains("home"));

		HomePage homePage = new HomePage(driver);
		homePage.logout();
		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getCurrentUrl().contains("home"));

	}



	@Test
	@Order(5)
	public void testNotes(){

		// use the username and password created in testHomePageAccessibility()
		doLogIn(myTestUsername, myTestPassword);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		HomePage homePage = new HomePage(driver);
		// add a note
		homePage.noteTab.click();
		addNote(homePage, webDriverWait, "note title", "note description");
		homePage.noteTab.click();
		Assertions.assertEquals(getNoteTitleDisplay(driver, webDriverWait, 0), "note title");
		Assertions.assertEquals(getNoteDescriptionDisplay(driver, webDriverWait, 0), "note description");

		// modify the note
		editNote(homePage, driver, webDriverWait, 0, "note title modified", "note description modified");
		homePage.noteTab.click();
		Assertions.assertEquals(getNoteTitleDisplay(driver, webDriverWait, 0), "note title modified");
		Assertions.assertEquals(getNoteDescriptionDisplay(driver, webDriverWait, 0), "note description modified");

		// logout and back in and check again
		homePage.logout();
		doLogIn(myTestUsername, myTestPassword);
		homePage.noteTab.click();
		Assertions.assertEquals(getNoteTitleDisplay(driver, webDriverWait, 0), "note title modified");
		Assertions.assertEquals(getNoteDescriptionDisplay(driver, webDriverWait, 0), "note description modified");

		// delete the note
		deleteNote(driver, webDriverWait, 0);
		homePage.noteTab.click();
		Assertions.assertFalse(driver.getPageSource().contains("note title modified"));
		Assertions.assertFalse(driver.getPageSource().contains("note description modified"));
	}


	@Test
	@Order(6)
	public void testCredentials(){

		// use the username and password created in testHomePageAccessibility()
		doLogIn(myTestUsername, myTestPassword);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		HomePage homePage = new HomePage(driver);
		// add a credential
		homePage.credentialTab.click();
		addCredential(homePage, webDriverWait, "aURL", "aUsername", "aPassword");
		homePage.credentialTab.click();
		Assertions.assertEquals(getCredentialURLDisplay(driver, webDriverWait, 0), "aURL");
		Assertions.assertEquals(getCredentialUsernameDisplay(driver, webDriverWait, 0), "aUsername");
		// password displayed here should be encrypted
		Assertions.assertNotEquals(getCredentialUsernameDisplay(driver, webDriverWait, 0), "aPassword");
		// check if the decrypted password match
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialEditButton")));
		List<WebElement> credentialEditButtons = driver.findElements(By.id("credentialEditButton"));
		credentialEditButtons.get(0).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		Assertions.assertEquals(homePage.credentialPasswd.getAttribute("value"), "aPassword");
		homePage.credentialCancelButton.click();

		// modify the credential
		editCredential(homePage, driver, webDriverWait, 0, "differentURL", "differentUsername", "differentPassword");
		homePage.credentialTab.click();
		Assertions.assertEquals(getCredentialURLDisplay(driver, webDriverWait, 0), "differentURL");
		Assertions.assertEquals(getCredentialUsernameDisplay(driver, webDriverWait, 0), "differentUsername");
		// password displayed here should be encrypted
		Assertions.assertNotEquals(getCredentialUsernameDisplay(driver, webDriverWait, 0), "differentPassword");
		// check if the decrypted password match
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialEditButton")));
		credentialEditButtons = driver.findElements(By.id("credentialEditButton"));
		credentialEditButtons.get(0).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		Assertions.assertEquals(homePage.credentialPasswd.getAttribute("value"), "differentPassword");
		homePage.credentialCancelButton.click();

		// logout and back in and check again
		homePage.logout();
		doLogIn( myTestUsername, myTestPassword);
		homePage.credentialTab.click();
		Assertions.assertEquals(getCredentialURLDisplay(driver, webDriverWait, 0), "differentURL");
		Assertions.assertEquals(getCredentialUsernameDisplay(driver, webDriverWait, 0), "differentUsername");
		// password displayed here should be encrypted
		Assertions.assertNotEquals(getCredentialUsernameDisplay(driver, webDriverWait, 0), "differentPassword");
		// check if the decrypted password match
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialEditButton")));
		credentialEditButtons = driver.findElements(By.id("credentialEditButton"));
		credentialEditButtons.get(0).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		Assertions.assertEquals(homePage.credentialPasswd.getAttribute("value"), "differentPassword");
		homePage.credentialCancelButton.click();

		// delete the Credential
		deleteCredential(driver, webDriverWait, 0);
		homePage.credentialTab.click();
		Assertions.assertFalse(driver.getPageSource().contains("differentURL"));
		Assertions.assertFalse(driver.getPageSource().contains("differentUsername"));
	}
}
