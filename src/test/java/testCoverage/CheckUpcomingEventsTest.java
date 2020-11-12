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
import pages.EventsPage;
import pages.StartPage;

public class CheckUpcomingEventsTest extends DriverHooks {

    private Logger logger = LogManager.getLogger(CheckUpcomingEventsTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    /**
     * Тест проверяет следующий сценарий:
     *  1. Просмотр предстоящих мероприятий:
     *         1.1 + Пользователь переходит на вкладку events
     *         1.2 + Пользователь нажимает на Upcoming Events
     *         1.3 На странице отображаются карточки предстоящих мероприятий.
     *         Количество карточек равно счетчику на кнопке Upcoming Events
     */
    @Test
    public void checkUpcomingEventsTest(){
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
        StartPage startPage = new StartPage();
        EventsPage events = startPage.clickButtonEvents();
        logger.info("Events page is opened");
        events.clickButtonUpcomingEvents();
        logger.info("Button 'Upcoming events' is clicked");
        String eventsNumber = events.getCounterValue();
        String eventCardsNumber = "" + events.getAllEventCards().size();
        Assert.assertEquals(eventsNumber, eventCardsNumber);
        logger.info("Test finished");
    }
}
