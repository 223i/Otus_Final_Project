package testCoverage;

import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EventsPage;

import java.util.Date;
import java.util.List;

public class CheckPastEventsTest extends TestStepsUtil {

    private Logger logger = LogManager.getLogger(CheckUpcomingEventsTest.class);

    /**
     * Тест проверяет следующий сценарий:
     * 4. Просмотр прошедших мероприятий в Канаде:
     * 4.1 Пользователь переходит на вкладку events
     * 4.2 Пользователь нажимает на Past Events
     * 4.3 Пользователь нажимает на Location в блоке фильтров и выбирает Canada в выпадающем списке
     * 4.4 На странице отображаются карточки прошедших мероприятий. Количество карточек равно счетчику на кнопке Past Events.
     *      Даты проведенных мероприятий меньше текущей даты.
     */
    @Test
    public void checkPastEventsInCanadaTest(){
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonPastEvents(events);
        applyLocationFilter(events, "Canada");
        String counterOfPastEvents = events.getCounterValue("Past Events");
        logger.info("Number of past events is " + counterOfPastEvents);
        List<WebElement> allFoundEvents = events.getAllEventCards();
        String numberOfShownPastEvents = String.valueOf(allFoundEvents.size());
        logger.info("Number of shown past events is " + counterOfPastEvents);
        Assert.assertTrue(counterOfPastEvents.equals(numberOfShownPastEvents));
        Date dateNow = getCurrentDate();

        allFoundEvents.stream().forEach(foundEvent ->{
            Date dateOfEvent = parseDateDateOfEvent(events, foundEvent,"dd - dd MMM yyyy");
            Assert.assertTrue(dateNow.after(dateOfEvent));
        });
        logger.info("Test finished");
    }
}
