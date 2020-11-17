package pages;

import org.openqa.selenium.WebDriver;

public class StartPage extends AbstractPage {

    public StartPage(WebDriver driver) {
        super(driver);
    }

    public EventsPage clickButtonEvents(){
        elementClick(waitForElement(menuEventsButton));
        return new EventsPage(driver);
    }

    public VideoPage clickButtonVideo(){
        elementClick(waitForElement(menuVideoButton));
        return new VideoPage(driver);
    }

}
