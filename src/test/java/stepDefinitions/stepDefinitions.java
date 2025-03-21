package stepDefinitions;

import general.StepHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class stepDefinitions {
    private WebDriver driver;
    WebDriverWait wait;
    StepHelper stepHelper;

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("the user is using the browser {string}")
    public void userBrowser(String browser) {
        if (browser.equals("Chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("Firefox")) {
            driver = new FirefoxDriver();
        }
    }

    @Given("the user is on the account creation page")
    public void userNavigatesToAccountCreationPage() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @And("the user enters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void theUserEntersInput(String dateOfBirth, String firstName, String lastName, String email, String password, String passwordConfirmation,String role, String comPrefs, String choice) {
        stepHelper = new StepHelper(driver);

        if(email.contains("mailnesia")){
            email = System.currentTimeMillis() + "@" + email;
        }

        stepHelper.enterText(By.cssSelector("#dp"), dateOfBirth);
        stepHelper.enterText(By.name("Forename"), firstName);
        stepHelper.enterText(By.name("Surname"), lastName);
        stepHelper.enterText(By.cssSelector("#member_emailaddress"), email);
        stepHelper.enterText(By.cssSelector("#member_confirmemailaddress"), email);
        stepHelper.enterText(By.cssSelector("#signupunlicenced_password"), password);
        stepHelper.enterText(By.cssSelector("#signupunlicenced_confirmpassword"), passwordConfirmation);
        stepHelper.clickElements(By.cssSelector("label[for^='signup_basketballrole']"), role);
        stepHelper.clickElement(By.cssSelector("label[for='sign_up_25"));
        stepHelper.clickElement(By.cssSelector("label[for='sign_up_26"));
        stepHelper.clickComPrefs(comPrefs);
        if(choice.equals("Accept")) {
            stepHelper.clickElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']"));
        }
    }

    @When("the user clicks on the Confirm and Join-button")
    public void theUserClicksOnTheConfirmAndJoinButton() {
        stepHelper.clickElement(By.cssSelector("input[name='join']"));
    }

    @Then("the account is created")
    public void theAccountIsCreated() {
        String actual = driver.findElement(By.cssSelector("h2")).getText();
        String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
        assertEquals(expected, actual);
    }

    @And("the user should see the error message {string}")
    public void theUserShouldSeeErrorMessage(String errorMessage) {
        switch (errorMessage) {
            case "Last Name is missing":
                assertTrue(driver.findElement(By.cssSelector("span[for='member_lastname']")).isDisplayed());
                break;
            case "Passwords do not match":
                assertTrue(driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword']")).isDisplayed());
                break;
            case "Code of Ethics and Conduct declined":
                assertTrue(driver.findElement(By.cssSelector("span[for^='AgreeToCodeOfEthicsAndConduct']")).isDisplayed());
                break;
            case "Parent last name missing":
                assertTrue(driver.findElement(By.cssSelector("span[data-valmsg-for='ParentSurname']")).isDisplayed());
                break;
        }
    }

    @And("the user enters {string}, {string}, {string}")
    public void theUserEntersParentalInput(String parentFirstName, String parentLastName, String parentEmail) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ParentForename")));

        if(parentEmail.contains("mailnesia")){
            parentEmail = System.currentTimeMillis() + "@" + parentEmail;
        }

        stepHelper.enterText(By.name("ParentForename"), parentFirstName);
        stepHelper.enterText(By.name("ParentSurname"), parentLastName);
        stepHelper.enterText(By.name("ParentEmailAddress"), parentEmail);
        stepHelper.enterText(By.name("ParentConfirmEmailAddress"), parentEmail);


    }
}
