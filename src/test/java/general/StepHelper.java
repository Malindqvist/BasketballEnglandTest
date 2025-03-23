package general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class StepHelper {

    private static WebElement waitForElement(WebDriver driver, By selector){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public static void enterText(WebDriver driver, By selector, String input) {
        driver.findElement(selector).sendKeys(input);
    }

    public static void clickElement(WebDriver driver, By selector) {
        //Wait for element to be clickable
        waitForElement(driver, selector).click();
    }

    public static void clickElements(WebDriver driver, By selector, String input) {
        //Used to click multiple roles
        //Creates an array with each choice separated
        if (input.contains(",")) {
            String[] choices = input.split(", ");
            List<WebElement> elements = driver.findElements(selector);

            //Clicks on the choices
            for (String choice : choices) {
                for (WebElement element : elements) {
                    if (element.getText().equals(choice)) {
                        element.click();
                    }

                }
            }
        }
    }

    public static void clickComPrefs(WebDriver driver, String comPrefs) {
        WebElement marketingCheckbox = driver.findElement(By.cssSelector("label[for='sign_up_27']"));
        WebElement partnersCheckbox = driver.findElement(By.cssSelector("label[for='sign_up_28']"));

        switch (comPrefs) {
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

}
