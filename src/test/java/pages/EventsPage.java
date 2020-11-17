package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EventsPage extends AbstractPage {
    protected String buttonUpcomingOrPastEvents = "//span[contains(text(), '%s')]";
    protected String counterEvents = "//span[contains(text(),'%s')]/following-sibling::*[@class = 'evnt-tab-counter evnt-label small white']";
    protected String anyEventCard = "//a/div[@class = 'evnt-card-wrapper']";
    protected String eventCardPlace = "//div[@class = 'evnt-card-heading']//div[@class = 'evnt-details-cell online-cell']/p";
    protected String eventCardLanguage = "//div[@class = 'evnt-card-heading']//div/p[@class = 'language']";
    protected String eventCardEventName = "//div[@class = 'evnt-card-body']//div[@class = 'evnt-event-name']";
    protected String eventCardEventDate = "//div[@class = 'evnt-event-dates']//span[@class = 'date']";
    protected String eventCardEventRegistration = "//div[@class = 'evnt-event-dates']//span[@class = 'status free-attend']";
    protected String eventCardEventSpeakers = "//div[@class = 'evnt-people-table']";
    protected String singleSpeaker = "//div[@class='evnt-speaker']";
    protected String thisWeekEventsContainer = "//div[@class='evnt-cards-container']/h3";
    protected String filterLocation = "//div[@id='filter_location']";
    protected String searchFieldInFilter = "/following-sibling::*//div[@class='evnt-filter-menu-search-wrapper']/input";
    protected String checkboxForValueInFilter = "//div[@class='evnt-checkbox form-check']/label[@data-value = '%s']";
    protected String appliedFilterValue = "//div[@class='evnt-tag evnt-filters-tags with-delete-elem']/label[contains(text(), '%s')]";

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    public String getCounterValue(String counterType) {
        WebElement counter = driver.findElement(By.xpath(counterEvents.replace("%s", counterType)));
        return counter.getText();
    }

    public void clickButtonEvents(String eventType) {
        String buttonLocator = buttonUpcomingOrPastEvents.replace("%s", eventType);
        elementClick(waitForElement(buttonLocator));
    }

    public void applyLocationFilter(String locationValue){
        WebElement filter = driver.findElement(By.xpath(filterLocation));
        elementClick(filter);
        WebElement filterField = filter.findElement(By.xpath(filterLocation + searchFieldInFilter));
        filterField.sendKeys(locationValue);
        filterField.findElement(By.xpath(checkboxForValueInFilter.replace("%s", locationValue))).click();
        elementClick(driver.findElement(By.xpath(filterLocation)));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appliedFilterValue.replace("%s", locationValue))));
    }

    public List<WebElement> getAllEventCards(){
        return driver.findElements(By.xpath(anyEventCard));
    }

    public List<WebElement> getThisWeekEventCards(){
        WebElement thisWeek = driver.findElement(By.xpath(thisWeekEventsContainer));
        return thisWeek.findElements(By.xpath(anyEventCard));
    }

    public String getEventCardPlaceValue(WebElement event) {
        return event.findElement(By.xpath(eventCardPlace)).getText();
    }

    public String getEventCardLanguageValue(WebElement event) {
        return event.findElement(By.xpath(eventCardLanguage)).getText();
    }

    public String getEventCardEventNameValue(WebElement event) {
        return event.findElement(By.xpath(eventCardEventName)).getText();
    }

    public String getEventCardEventDateValue(WebElement event) {
        return event.findElement(By.xpath(eventCardEventDate)).getText();
    }

    public String getEventCardEventRegistrationValue(WebElement event) {
        return event.findElement(By.xpath(eventCardEventRegistration)).getText();
    }

    public List<WebElement> getEventCardEventSpeakersValue(WebElement event) {
        WebElement tableWithSpeakers = event.findElement(By.xpath(eventCardEventSpeakers));
        return tableWithSpeakers.findElements(By.xpath(singleSpeaker));
    }
}
