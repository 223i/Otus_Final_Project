package testCoverage;

import driver.DriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.EventsPage;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
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
    @Epic("EPAM Events")
    @Feature("Watching past events in Canada")
    @Story("Past events in Canada are shown")
    @Description("Тест проверяет, что На странице отображаются карточки прошедших мероприятий. " +
            "Количество карточек равно счетчику на кнопке Past Events." +
            "Даты проведенных мероприятий меньше текущей даты")
    @Test
    public void checkPastEventsInCanadaTest(){
        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
        openUrl(driver);
        EventsPage events = openEventsPage(driver);
        clickButtonPastEvents(events);
        applyLocationFilter(events, "Canada");
        String counterOfPastEvents = events.getCounterValue("Past Events");
        logger.info("Number of past events is " + counterOfPastEvents);
        List<WebElement> allFoundEvents = events.getAllEventCards();
        String numberOfShownPastEvents = String.valueOf(allFoundEvents.size());
        logger.info("Number of shown past events is " + counterOfPastEvents);
        assertTrue(counterOfPastEvents.equals(numberOfShownPastEvents));
        Date dateNow = getCurrentDate();

        allFoundEvents.stream().forEach(foundEvent ->{
            Date dateOfEvent = parseDateDateOfEvent(events, foundEvent,"dd - dd MMM yyyy");
            assertTrue(dateNow.after(dateOfEvent));
        });
        logger.info("Test finished");
    }
}
