package testCoverage;

import config.ServerConfig;
import driver.DriverManager;
import hooks.DriverHooks;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EventPage;
import pages.EventsPage;
import pages.StartPage;
import pages.VideosPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStepsUtil extends DriverManager {

    private Logger logger = LogManager.getLogger(CheckUpcomingEventsTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Step("Open start page of website 'Events EPAM'")
    public void openUrl(WebDriver driver){
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
    }

    @Step("Open page with all events")
    public EventsPage openEventsPage(WebDriver driver){
        StartPage startPage = new StartPage(driver);
        EventsPage events = startPage.clickButtonEvents();
        logger.info("Events page is opened");
        return events;
    }

    @Step("Open page with all videos")
    public VideosPage openVideosPage(WebDriver driver){
        StartPage startPage = new StartPage(driver);
        VideosPage videos = startPage.clickButtonVideo();
        logger.info("Videos page is opened");
        return videos;
    }

    @Step("Click button 'Upcomming events'")
    public void clickButtonUpcommingEvents(EventsPage eventsPage){
        eventsPage.clickButtonEvents("Upcoming events");
        assertTrue(eventsPage.getAllEventCards().size() != 0);
        logger.info("Button 'Upcoming events' is clicked");
    }

    @Step("Click button 'Past Events")
    public void clickButtonPastEvents(EventsPage eventsPage){
        eventsPage.clickButtonEvents("Past Events");
        assertTrue(eventsPage.getAllEventCards().size() != 0);
        logger.info("Button 'Past Events' is clicked");
    }

    @Step("Get current date")
    public Date getCurrentDate(){
        Date dateNow = new Date();
        logger.info("Current date number is " + dateNow.toString());
        return dateNow;
    }

    @Step("Parse date of event")
    public Date parseDateDateOfEvent(EventsPage page, WebElement eventCard, String dateFormat){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Date dateOfEvent = null;
        try {
            dateOfEvent = format.parse(page.getEventCardEventDateValue(eventCard).trim());
        } catch (ParseException e) {
            logger.warn("During parsing of date occurred " + e);
            e.printStackTrace();
        }
        return dateOfEvent;
    }

    @Step("Get number of week for defined date")
    public int getWeekNumber(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        logger.info("Week number for date " + date.toString() + " is " + week);
        return week;
    }

    @Step("Open page with chosen event")
    public EventPage openPageOfOneEvent(EventsPage eventsPage){
        EventPage pageOfOneEvent = eventsPage.openEventCard();
        logger.info("Page of event is opened");
        return pageOfOneEvent;
    }

    @Step("Apply location filter with value {location}")
    public void applyLocationFilter(EventsPage eventsPage, String location){
        eventsPage.applyLocationFilter(location);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Filter " + location +  " is applied" );
    }

    @Step("Open all filters on Video page")
    public void openAllFiltersOnVideoPage(VideosPage videos){
        videos.clickFilterField(videos.getMoreFilters());
        logger.info("All filters are opened");
    }

    @Step("Set filter {filter} with value {filterValue}")
    public void setNecessaryFilter(VideosPage videos, String filter, String filterValue){
        videos.setFilterValue(videos.getDefinedFilter(), filter, filterValue);
        logger.info("Videos are filtered by filter " + filter + " with value " + filterValue);
    }

    @Step("Filter cards by {word} key word")
    public void filterCardsByKeyWord(VideosPage page, String word){
        page.filterByKeyWord(word);
        logger.info("Videos are filtered by key word " + word);
    }
}
