package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class VideosPage extends AbstractPage {

    protected String moreFilters = "//div[@class='evnt-toogle-filters-text show-more']/span";
    protected String definedFilter = "//span[@class='evnt-filter-text' and contains(text(), '%s')]";
    protected String checkboxForValueInFilter = "//div[@class='evnt-checkbox form-check']/label[@data-value = '%s']";
    protected String appliedFilterValue = "//div[@class='evnt-tag evnt-filters-tags with-delete-elem']/label[contains(text(), '%s')]";
    protected String anyVideoCard = "//div[@class='evnt-card-wrapper']";
    protected String anyChosenTag = "//div[@class = 'evnt-filters-content-cell evnt-tags-cell']//label";

    public VideosPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkIfFieldIsShown(String field) {
        return driver.findElement(By.xpath(field)).isDisplayed();
    }

    public void clickFilterField(String field) {
        elementClick(driver.findElement(By.xpath(field)));
    }

    public void setFilterValue(String field, String definitionOfField, String filterValue) {
        WebElement filterList = driver.findElement(By.xpath(field.replace("%s", definitionOfField)));
        elementClick(filterList);
        filterList.findElement(By.xpath(checkboxForValueInFilter.replace("%s", filterValue))).click();
        elementClick(filterList);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appliedFilterValue.replace("%s", filterValue))));
    }

    public List<WebElement> getAllVideosCards() {
        return driver.findElements(By.xpath(anyVideoCard));
    }

    public List<String> getAllSelectedTags() {
        List<WebElement> allTags = driver.findElements(By.xpath(anyChosenTag));
        List<String> stringRepresentationsOfTags = new ArrayList<>();
        for (int i = 0; i < allTags.size(); i++) {
            stringRepresentationsOfTags.add(allTags.get(i).getText());
        }
        return stringRepresentationsOfTags;
    }

    public VideoPage openVideoCard(WebElement videoCard) {
        elementClick(videoCard);
        return new VideoPage(driver);
    }

    public String getDefinedFilter() {
        return definedFilter;
    }

    public String getMoreFilters() {
        return moreFilters;
    }
}
