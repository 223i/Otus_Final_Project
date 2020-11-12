package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class EventsPage extends AbstractPage {
    protected String buttonUpcomingEvents= "//span[contains(text(), 'Upcoming events')]";
    protected String counterUpcomingEvents= "//span[@class = 'evnt-tab-counter evnt-label small white']";
    protected String anyEventCard = "//a/div[@class = 'evnt-card-wrapper']";

    public String getCounterValue(){
        return driver.findElement(By.xpath(counterUpcomingEvents)).getText();
    }

    public void clickButtonUpcomingEvents(){
        elementClick(waitForElement(buttonUpcomingEvents));
        //TODO: добавить ассерт
    }

    public List<WebElement> getAllEventCards(){
        return driver.findElements(By.xpath(anyEventCard));
    }
}
