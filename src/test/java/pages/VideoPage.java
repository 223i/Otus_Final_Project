package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VideoPage extends AbstractPage {

    protected String moreFilters = "//div[@class='evnt-toogle-filters-text show-more']/span";
    protected String definedFilter = "//span[@class='evnt-filter-text' and contains(text(), '%s')]";
    protected String fieldForFilterValue = "/../following-sibling::*/div[@class='evnt-filter-menu-search-wrapper']";
    protected String checkboxForValueInFilter = "//div[@class='evnt-checkbox form-check']/label[@data-value = '%s']";
    protected String appliedFilterValue = "//div[@class='evnt-tag evnt-filters-tags with-delete-elem']/label[contains(text(), '%s')]";


    public VideoPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkIfFieldIsShown(String field){
        return driver.findElement(By.xpath(field)).isDisplayed();
    }

    public void clickFilterField(String field){
        elementClick(driver.findElement(By.xpath(field)));
    }

    public void setFilterValue(String field, String definitionOfField, String filterValue){
        WebElement filterList = driver.findElement(By.xpath(field.replace("%s", definitionOfField)));
        elementClick(filterList);
        filterList.findElement(By.xpath(checkboxForValueInFilter.replace("%s", filterValue))).click();
        elementClick(filterList);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appliedFilterValue.replace("%s", filterValue))));
    }

    public String getDefinedFilter() {
        return definedFilter;
    }

    public String getMoreFilters() {
        return moreFilters;
    }
}
