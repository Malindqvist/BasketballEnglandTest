package general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class StepHelper {
    WebDriver driver;

    public StepHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void enterText(By selector, String input) {
        driver.findElement(selector).sendKeys(input);
    }

    public void clickElement(By selector) {
        driver.findElement(selector).click();
    }

    public void clickElements(By selector, String input) {
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

    public void clickComPrefs(String comPrefs) {
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
