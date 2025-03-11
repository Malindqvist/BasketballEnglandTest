package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class stepDefinitions {
    private WebDriver driver;

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
        String expected = "https://membership.basketballengland.co.uk/NewSupporterAccount";
        String actual = driver.getCurrentUrl();

        assertEquals(expected, actual);
    }

    @And("the user enters {string} in the Date of Birth field")
    public void userEntersDateOfBirth(String dateOfBirth) {
        driver.findElement(By.cssSelector("#dp")).sendKeys(dateOfBirth);

    }

    @And("the user enters {string} in the First Name field")
    public void userEntersFirstName(String expected) {
        WebElement firstname = driver.findElement(By.name("Forename"));
        firstname.sendKeys(expected);
        String actual = firstname.getDomProperty("value");
        assertEquals(expected, actual);
    }

    @And("the user enters {string} in the Last Name field")
    public void userEntersLastName(String expected) {
        WebElement lastname = driver.findElement(By.name("Surname"));
        lastname.sendKeys(expected);
        String actual = lastname.getDomProperty("value");
        assertEquals(expected, actual);
    }

    @And("the user enters {string} in the Email fields")
    public void theUserEntersEmail(String email) {
        WebElement emailAdress = driver.findElement(By.cssSelector("#member_emailaddress"));
        WebElement confirmEmailAdress = driver.findElement(By.cssSelector("#member_confirmemailaddress"));
        emailAdress.sendKeys(email);
        confirmEmailAdress.sendKeys(email);
    }

    @And("the user enters {string} and {string} in the Password Fields")
    public void theUserEntersPassword(String password, String password2) {
        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys(password);
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys(password2);
    }

    @And("the user selects their {string} in the Role form")
    public void theUserSelectsTheirRole(String role) {
        //Creates an array with each role separated
        String[] roles = role.split(", ");
        List<WebElement> supporterRoles = driver.findElements(By.cssSelector("label[for^='signup_basketballrole']"));

        for (String s : roles) {
            for (WebElement supporterRole : supporterRoles) {
                if (supporterRole.getText().equals(s)) {
                    supporterRole.click();
                }

            }
        }

    }

    @And("the user accepts the Terms and Conditions")
    public void theUserAcceptsTheTermsAndConditions() {
        driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
    }

    @And("the user confirms age over 18")
    public void theUserConfirmsAgeOver18() {
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
    }

    @And("the user checks their communication preferences {string}")
    public void theUserChecksTheir(String preferences) {
        WebElement marketingCheckbox = driver.findElement(By.cssSelector("label[for='sign_up_27']"));
        WebElement partnersCheckbox = driver.findElement(By.cssSelector("label[for='sign_up_28']"));

        switch (preferences) {
            case "Marketing":
                marketingCheckbox.click();
                break;
            case "Partners":
                partnersCheckbox.click();
                break;
            case "Marketing & Partners":
                marketingCheckbox.click();
                partnersCheckbox.click();
                break;
        }
    }


    @And("the user {string} the Code of Ethics and Conduct")
    public void theUserAcceptsTheCodeOfEthicsAndConduct(String choice) {

        if(choice.equals("Accept")){
            driver.findElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();
        }

    }

    @When("the user clicks on the Confirm and Join-button")
    public void theUserClicksOnTheConfirmAndJoinButton() {
        driver.findElement(By.cssSelector("input[name='join']")).click();
    }

    @Then("{string} should occur")
    public void expectedOutcome(String expectedOutcome) {
        System.out.println(expectedOutcome);

    }

    @And("if invalid the user should see the error message {string}")
    public void ifInvalidTheUserShouldSeeTheErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        switch (errorMessage) {
            case "Last Name is required":
                assertTrue(driver.findElement(By.cssSelector("span[for='member_lastname']")).isDisplayed());
                break;
            case "Password did not match":
                assertTrue(driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword']")).isDisplayed());
                break;
            case "Code of Ethics and Conduct":
                assertTrue(driver.findElement(By.cssSelector("span[for^='AgreeToCodeOfEthicsAndConduct']")).isDisplayed());
                break;

        }
    }

    @Then("the account should be created")
    public void theAccountShouldBeCreated() {
//        String actual = driver.findElement(By.cssSelector("h2")).getText();
//        String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
//        assertEquals(expected, actual);
        driver.quit();
    }

}
