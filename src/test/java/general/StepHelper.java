package general;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StepHelper {

    //Waits for element to be clickable
    private static WebElement waitForElement(WebDriver driver, By selector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        return wait.until(ExpectedConditions.elementToBeClickable(selector));
    }

    //Enters user input into the selector's text field
    public static void enterText(WebDriver driver, By selector, String input) {
        driver.findElement(selector).sendKeys(input);
    }

    //Clicks on the selector element
    public static void clickElement(WebDriver driver, By selector) {
        //Wait for element to be clickable
        waitForElement(driver, selector).click();
    }

    //Used to click on multiple elements (roles in this case)
    public static void clickElements(WebDriver driver, By selector, String input) {
        //Creates an array with each choice separated
        String[] choices = input.split(", ");
        List<WebElement> elements = driver.findElements(selector);
        //Clicks on the choices
        for (String choice : choices) {
            for (WebElement element : elements) {
                if (element.getText().equals(choice)) {
                    if (driver instanceof FirefoxDriver) {
                        //Used to be able to click "Player" and "Player's relative/guardian" in Firefox
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].click();", element);
                    } else {
                        element.click();
                    }
                    break;
                }
            }
        }
    }

    //Click on the chosen communication preferences
    public static void clickComPrefs(WebDriver driver, String comPrefs) {
        if (comPrefs.contains("Marketing")) {
            clickElement(driver, By.cssSelector("label[for='sign_up_27']"));
        }

        if (comPrefs.contains("Partners")) {
            clickElement(driver, By.cssSelector("label[for='sign_up_28']"));
        }
    }

    //Randomize email address to avoid failed tests caused by reused test data
    public static String randomizeEmailAddress(String email) {
        return System.currentTimeMillis() + "@" + email;
    }

}
