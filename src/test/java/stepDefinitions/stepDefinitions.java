package stepDefinitions;

import general.StepHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class stepDefinitions {
    private WebDriver driver;

    @After
    //Close the driver after the test is completed
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user is using the browser {string}")
    //Create a driver of the type specified in the Scenario Outline
    public void userBrowser(String browser) {
        if (browser.equals("Chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("Firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Missing driver for " + browser);
        }
    }

    @Given("the user is on the account creation page")
    public void userNavigatesToAccountCreationPage() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @And("the user enters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void theUserEntersInput(String dateOfBirth, String firstName, String lastName, String email, String password, String passwordConfirmation, String role, String comPrefs, String acceptsCoEaC) {
        //Randomize email address to avoid failed tests caused by reused test data
        if (email.contains("mailnesia")) {
            email = StepHelper.randomizeEmailAddress(email);
        }

        //Enter user information
        StepHelper.enterText(driver, By.cssSelector("#dp"), dateOfBirth);
        StepHelper.enterText(driver, By.name("Forename"), firstName);
        StepHelper.enterText(driver, By.name("Surname"), lastName);
        StepHelper.enterText(driver, By.cssSelector("#member_emailaddress"), email);
        StepHelper.enterText(driver, By.cssSelector("#member_confirmemailaddress"), email);
        StepHelper.enterText(driver, By.cssSelector("#signupunlicenced_password"), password);
        StepHelper.enterText(driver, By.cssSelector("#signupunlicenced_confirmpassword"), passwordConfirmation);

        //Click on roles
        StepHelper.clickElements(driver, By.cssSelector("label[for^='signup_basketballrole']"), role);

        //Accept Terms and Conditions
        StepHelper.clickElement(driver, By.cssSelector("label[for='sign_up_25']"));

        //Confirm age over 18
        StepHelper.clickElement(driver, By.cssSelector("label[for='sign_up_26']"));

        //Click on communication preferences
        StepHelper.clickComPrefs(driver, comPrefs);

        //Accept Code of Ethics and Conduct if acceptsCoEaC is true
        if (acceptsCoEaC.equals("true")) {
            StepHelper.clickElement(driver, By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']"));
        }
    }

    @When("the user clicks on the Confirm and Join-button")
    public void theUserClicksOnTheConfirmAndJoinButton() {
        StepHelper.clickElement(driver, By.cssSelector("input[name='join']"));
    }

    @Then("the account is created")
    public void theAccountIsCreated() {
        String actual = driver.findElement(By.cssSelector("h2")).getText();
        String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
        assertEquals(expected, actual);
    }

    @And("the user should see the error message {string}")
    public void theUserShouldSeeErrorMessage(String errorMessage) {
        //Assert that the correct error message is shown
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
        //Randomize email address to avoid failed tests caused by reused test data
        if (parentEmail.contains("mailnesia")) {
            parentEmail = StepHelper.randomizeEmailAddress(parentEmail);
        }

        //Enter parent/guardian information
        StepHelper.enterText(driver, By.name("ParentForename"), parentFirstName);
        StepHelper.enterText(driver, By.name("ParentSurname"), parentLastName);
        StepHelper.enterText(driver, By.name("ParentEmailAddress"), parentEmail);
        StepHelper.enterText(driver, By.name("ParentConfirmEmailAddress"), parentEmail);
    }

}
