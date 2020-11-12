package pages;

public class StartPage extends AbstractPage {

    public EventsPage clickButtonEvents(){
        elementClick(waitForElement(menuEventsButton));
        return new EventsPage();
    }
}
