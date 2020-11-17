package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EventPage extends AbstractPage {

    protected String informationAboutEvent = "//div[@class = 'evnt-content-wrapper']";
    protected String buttonRegister = "//div[@class = 'evnt-content-wrapper']//div[@class = 'evnt-reg-wrapper']/button";
    protected String dateAndTimeOfEvent  = "//i[@class='fa fa-calendar']/../../following-sibling::div/*";
    protected String agenda = "//div[@id='agenda']";

    public EventPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkIfFieldIsShown(String field){
        return driver.findElement(By.xpath(field)).isDisplayed();
    }

    public String getInformationAboutEvent() {
        return informationAboutEvent;
    }

    public String getButtonRegister() {
        return buttonRegister;
    }

    public String getDateAndTimeOfEvent() {
        return dateAndTimeOfEvent;
    }

    public String getAgenda() {
        return agenda;
    }
}
