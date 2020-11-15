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

import java.util.List;
import java.util.stream.Stream;

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
        StartPage startPage = new StartPage(driver);
        EventsPage events = startPage.clickButtonEvents();
        logger.info("Events page is opened");
        events.clickButtonUpcomingEvents();
        Assert.assertTrue(events.getAllEventCards().size() != 0);
        logger.info("Button 'Upcoming events' is clicked");
        String eventsNumber = events.getCounterValue();
        String eventCardsNumber = "" + events.getAllEventCards().size();
        Assert.assertEquals(eventsNumber, eventCardsNumber);
        logger.info("Test finished");
    }

    /**
     * Тест проверяет следующий сценарий:
     *  2. Просмотр карточек мероприятий:
     *      2.1 Пользователь переходит на вкладку events
     *      2.2 Пользователь нажимает на Upcoming Events
     *      2.3 На странице отображаются карточки предстоящих мероприятий.
     *      2.4 В карточке указана информация о мероприятии:
     *          • место проведения, язык
     *          • название мероприятия
     *          • дата мероприятия
     *          • информация о регистрации
     *          • список спикеров
     */
    @Test
    public void checkEventCardTest(){
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
        StartPage startPage = new StartPage(driver);
        EventsPage events = startPage.clickButtonEvents();
        logger.info("Events page is opened");
        events.clickButtonUpcomingEvents();
        logger.info("Button 'Upcoming events' is clicked");
        List<WebElement> allFoundEvents = events.getAllEventCards();
        for (int i = 0; i < allFoundEvents.size(); i++) {
            Assert.assertFalse(events.getEventCardPlaceValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check place of event: value is + " + events.getEventCardPlaceValue(allFoundEvents.get(i)));
            Assert.assertFalse(events.getEventCardLanguageValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check language of event: value is + " + events.getEventCardLanguageValue(allFoundEvents.get(i)));
            Assert.assertFalse(events.getEventCardEventNameValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check name of event: value is + " + events.getEventCardEventNameValue(allFoundEvents.get(i)));
            Assert.assertFalse(events.getEventCardEventDateValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check date of event: value is + " + events.getEventCardEventDateValue(allFoundEvents.get(i)));
            Assert.assertFalse(events.getEventCardEventRegistrationValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check type of registration: value is + " + events.getEventCardEventRegistrationValue(allFoundEvents.get(i)));
            Stream<WebElement> allSpeakers = events.getEventCardEventSpeakersValue(allFoundEvents.get(i)).stream();
            allSpeakers.forEach(speaker->{
                Assert.assertFalse(speaker.getAttribute("data-name").equals(null));
                logger.info("Check speaker: value is + " + speaker.getAttribute("data-name"));
            });
        }
        logger.info("Test finished");
    }
}
