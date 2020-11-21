package testCoverage;

import config.ServerConfig;
import driver.DriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EventPage;
import pages.EventsPage;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
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
    @Epic("EPAM Events")
    @Feature("Watching upcoming events")
    @Story("Upcoming events are shown")
    @Description("На странице отображаются карточки предстоящих мероприятий. " +
            "Количество карточек равно счетчику на кнопке Upcoming Events")
    @Test
    public void checkUpcomingEventsTest() {

        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);
        String eventsNumber = events.getCounterValue("Upcoming");
        logger.info("Number of Upcoming Events according to counter: " + eventsNumber);
        String eventCardsNumber = "" + events.getAllEventCards().size();
        logger.info("Number of shown Upcoming Events: " + eventCardsNumber);
        assertEquals(eventsNumber, eventCardsNumber);
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
    @Epic("EPAM Events")
    @Feature("Watching upcoming events")
    @Story("Check information about events")
    @Description("На странице отображаются карточки предстоящих мероприятий. " +
            "В карточке указана информация о мероприятии:\n" +
            "• место проведения, язык\n" +
            "• название мероприятия\n" +
            "• дата мероприятия\n" +
            "• информация о регистрации\n" +
            "• список спикеров")
    @Test
    public void checkEventCardTest() {
        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);

        List<WebElement> allFoundEvents = events.getAllEventCards();
        for (int i = 0; i < allFoundEvents.size(); i++) {
            assertFalse(events.getEventCardPlaceValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check place of event: value is + " + events.getEventCardPlaceValue(allFoundEvents.get(i)));
            assertFalse(events.getEventCardLanguageValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check language of event: value is + " + events.getEventCardLanguageValue(allFoundEvents.get(i)));
            assertFalse(events.getEventCardEventNameValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check name of event: value is + " + events.getEventCardEventNameValue(allFoundEvents.get(i)));
            assertFalse(events.getEventCardEventDateValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check date of event: value is + " + events.getEventCardEventDateValue(allFoundEvents.get(i)));
            assertFalse(events.getEventCardEventRegistrationValue(allFoundEvents.get(i)).equals(null));
            logger.info("Check type of registration: value is + " + events.getEventCardEventRegistrationValue(allFoundEvents.get(i)));
            Stream<WebElement> allSpeakers = events.getEventCardEventSpeakersValue(allFoundEvents.get(i)).stream();
            allSpeakers.forEach(speaker -> {
                assertFalse(speaker.getAttribute("data-name").equals(null));
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
    @Epic("EPAM Events")
    @Feature("Watching upcoming events")
    @Story("Validate date of upcoming events")
    @Description(" В блоке This week даты проведения мероприятий больше или равны текущей дате и находятся в пределах текущей недели")
    @Test
    public void validateDateOfUpcomingEventsTest() {
        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
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
                assertTrue((dateOfEvent.after(dateNow) || dateOfEvent.equals(dateNow)) && weekNow == weekOfEvent,
                        "В блоке This week даты проведения мероприятий больше " +
                                                "или равны текущей дате и находятся в пределах текущей недели");
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
    @Epic("EPAM Events")
    @Feature("Watching upcoming events")
    @Story("Check information about chosen event")
    @Description("Пользователь нажимает на любую карточку\n" +
            "Происходит переход на страницу с подробной информацией о мероприятии\n" +
            " На странице с информацией о мероприятии отображается блок с кнопкой для регистрации,\n" +
            "дата и время, программа мероприятия")
    @Test
    public void checkDetailedInformationAboutEventTest() {
        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
        driver.get(cfg.url());
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonUpcommingEvents(events);
        EventPage pageOfOneEvent = openPageOfOneEvent(events);
        assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getButtonRegister()));
        assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getInformationAboutEvent()));
        assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getDateAndTimeOfEvent()));
        assertTrue(pageOfOneEvent.checkIfFieldIsShown(pageOfOneEvent.getAgenda()));
        logger.info("Test finished");
    }
}
