package testCoverage;

import config.ServerConfig;
import driver.DriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EventPage;
import pages.EventsPage;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class CheckUpcomingEventsTest extends TestStepsUtil {

    private Logger logger = LogManager.getLogger(CheckUpcomingEventsTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    /**
     * Тест проверяет следующий сценарий:
     * 1. Просмотр предстоящих мероприятий:
     * 1.1 + Пользователь переходит на вкладку events
     * 1.2 + Пользователь нажимает на Upcoming Events
     * 1.3 На странице отображаются карточки предстоящих мероприятий.
     * Количество карточек равно счетчику на кнопке Upcoming Events
     */
    @Test
    public void checkUpcomingEvents2Test() {

        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);
        String eventsNumber = events.getCounterValue("Upcoming");
        logger.info("Number of Upcoming Events according to counter: " + eventsNumber);
        String eventCardsNumber = "" + events.getAllEventCards().size();
        logger.info("Number of shown Upcoming Events: " + eventCardsNumber);
        Assert.assertEquals(eventsNumber, eventCardsNumber);
        logger.info("Test finished");
    }

    /**
     * Тест проверяет следующий сценарий:
     * 2. Просмотр карточек мероприятий:
     * 2.1 Пользователь переходит на вкладку events
     * 2.2 Пользователь нажимает на Upcoming Events
     * 2.3 На странице отображаются карточки предстоящих мероприятий.
     * 2.4 В карточке указана информация о мероприятии:
     * • место проведения, язык
     * • название мероприятия
     * • дата мероприятия
     * • информация о регистрации
     * • список спикеров
     */
    @Test
    public void checkEventCardTest() {
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);

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
            allSpeakers.forEach(speaker -> {
                Assert.assertFalse(speaker.getAttribute("data-name").equals(null));
                logger.info("Check speaker: value is + " + speaker.getAttribute("data-name"));
            });
        }
        logger.info("Test finished");
    }

    /**
     * Тест проверяет следующий сценарий:
     * 3. Валидация дат предстоящих мероприятий:
     * 3.1 Пользователь переходит на вкладку events
     * 3.2 Пользователь нажимает на Upcoming Events
     * 3.3 На странице отображаются карточки предстоящих мероприятий.
     * 3.4 В блоке This week даты проведения мероприятий больше или равны текущей дате и находятся в пределах текущей недели.
     */
    @Test
    public void validateDateOfUpcomingEventsTest() {
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);

        Date dateNow = getCurrentDate();
        int weekNow = getWeekNumber(dateNow);
        List<WebElement> allEventsThisWeek = events.getThisWeekEventCards();
        if (allEventsThisWeek.isEmpty()) {
            logger.warn("There is no any events this week");
        } else {
            allEventsThisWeek.stream().forEach(eventAtThisWeek -> {
                Date dateOfEvent = parseDateDateOfEvent(events, eventAtThisWeek, "dd MMM yyyy");
                int weekOfEvent = getWeekNumber(dateOfEvent);
                Assert.assertTrue("В блоке This week даты проведения мероприятий больше " +
                                "или равны текущей дате и находятся в пределах текущей недели",
                        (dateOfEvent.after(dateNow) || dateOfEvent.equals(dateNow)) && weekNow == weekOfEvent);
            });
        }
        logger.info("Test finished");
    }


    /**
     * Тест проверяет следующий сценарий:
     * 5. Просмотр детальной информации о мероприятии:
     * 5.1 Пользователь переходит на вкладку events
     * 5.2 Пользователь нажимает на Upcoming Events
     * 5.3 На странице отображаются карточки предстоящих мероприятий.
     * 5.4 Пользователь нажимает на любую карточку
     * 5.5 Происходит переход на страницу с подробной информацией о мероприятии
     * 5.6 На странице с информацией о мероприятии отображается блок с кнопкой для регистрации,
     * дата и время, программа мероприятия
     */
    @Test
    public void checkDetailedInformationAboutEventTest() {
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);
        EventPage pageOfOneEvent = openPageOfOneEvent(events);
        Assertions.assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getButtonRegister()));
        Assertions.assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getInformationAboutEvent()));
        Assertions.assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getDateAndTimeOfEvent()));
        Assertions.assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getAgenda()));
        logger.info("Test finished");
    }
}
