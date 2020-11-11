package HomeWork5.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static HomeWork5.driver.DriverManager.getWebDriver;

public abstract class AbstractPage {

    protected static WebDriver driver;
    protected List<WebElement> menuItems;
    protected String headline = "//div[@class = 'new-log-reg__login']//div[@class= 'new-log-reg__title']";

    public AbstractPage() {
        this.driver = getWebDriver();
    }


    protected WebElement waitForElement(By elementBy) {
        WebDriverWait waitForOne = new WebDriverWait(driver, 15);
        waitForOne.until(ExpectedConditions.presenceOfElementLocated(elementBy));
        return driver.findElement(elementBy);
    }

    protected void clickMenuItem(String menuItemName) {
        for (WebElement item : menuItems) {
            if (item.getText().toLowerCase().equals(menuItemName.toLowerCase())) {
                item.click();
                break;
            }
        }
    }

    protected void elementClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
