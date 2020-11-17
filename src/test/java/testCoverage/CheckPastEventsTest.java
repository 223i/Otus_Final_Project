package testCoverage;

import config.ServerConfig;
import driver.DriverManager;
import hooks.DriverHooks;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EventsPage;
import pages.StartPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckPastEventsTest extends DriverHooks {

    private Logger logger = LogManager.getLogger(CheckUpcomingEventsTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);


    /**
     * 4. Просмотр прошедших мероприятий в Канаде:
     * 4.1 Пользователь переходит на вкладку events
     * 4.2 Пользователь нажимает на Past Events
     * 4.3 Пользователь нажимает на Location в блоке фильтров и выбирает Canada в выпадающем списке
     * 4.4 На странице отображаются карточки прошедших мероприятий. Количество карточек равно счетчику на кнопке Past Events.
     *      Даты проведенных мероприятий меньше текущей даты.
     */
    @Test
    public void checkPastEventsInCanadaTest() throws InterruptedException {
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
        StartPage startPage = new StartPage(driver);
        EventsPage events = startPage.clickButtonEvents();
        logger.info("Events page is opened");
        events.clickButtonEvents("Past Events");
        events.applyLocationFilter("Canada");
        Thread.sleep(1000);
        String counterOfPastEvents = events.getCounterValue("Past Events");
        logger.info("Number of past events is " + counterOfPastEvents);
        List<WebElement> allFoundEvents = events.getAllEventCards();
        String numberOfShownPastEvents = String.valueOf(allFoundEvents.size());
        logger.info("Number of shown past events is " + counterOfPastEvents);
        Assert.assertTrue(counterOfPastEvents.equals(numberOfShownPastEvents));

        Date dateNow = new Date();
        logger.info("Current date number is " + dateNow.toString());
        SimpleDateFormat format = new SimpleDateFormat("dd - dd MMM yyyy",  Locale.ENGLISH);

        for (int i = 0; i < allFoundEvents.size(); i++) {
            try {
                Date dateOfEvent = format.parse(events.getEventCardEventDateValue(allFoundEvents.get(i)).trim());
                Assert.assertTrue(dateNow.after(dateOfEvent));
            }catch(ParseException e){
                logger.warn("During parsing of date occured " + e);
            }
        }

        logger.info("Test finished");
    }
}
