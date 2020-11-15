package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EventsPage extends AbstractPage {
    protected String buttonUpcomingEvents = "//span[contains(text(), 'Upcoming events')]";
    protected String counterUpcomingEvents = "//span[@class = 'evnt-tab-counter evnt-label small white']";
    protected String anyEventCard = "//a/div[@class = 'evnt-card-wrapper']";
    protected String eventCardPlace = "//div[@class = 'evnt-card-heading']//div[@class = 'evnt-details-cell online-cell']/p";
    protected String eventCardLanguage = "//div[@class = 'evnt-card-heading']//div/p[@class = 'language']";
    protected String eventCardEventName = "//div[@class = 'evnt-card-body']//div[@class = 'evnt-event-name']";
    protected String eventCardEventDate = "//div[@class = 'evnt-event-dates']//span[@class = 'date']";
    protected String eventCardEventRegistration = "//div[@class = 'evnt-event-dates']//span[@class = 'status free-attend']";
    protected String eventCardEventSpeakers = "//div[@class = 'evnt-people-table']";
    protected String singleSpeaker = "//div[@class='evnt-speaker']";

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    public String getCounterValue() {
        return driver.findElement(By.xpath(counterUpcomingEvents)).getText();
    }

    public void clickButtonUpcomingEvents() {
        elementClick(waitForElement(buttonUpcomingEvents));
    }

    public List<WebElement> getAllEventCards(){
        return driver.findElements(By.xpath(anyEventCard));
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
