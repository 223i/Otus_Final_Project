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
import pages.VideoPage;
import pages.VideosPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
public class CheckVideoTabEventsTest extends TestStepsUtil {


    private Logger logger = LogManager.getLogger(CheckUpcomingEventsTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    /**
     * Тест проверяет следующий сценарий:
     * 6. Фильтрация докладов по категориям:
     * 6.1 Пользователь переходит на вкладку Video
     * 6.2 Пользователь нажимает на More Filters
     * 6.3 Пользователь выбирает: Category – Testing, Location – Belarus, Language – English, На развернутой вкладке фильтров
     * 6.4 На странице отображаются карточки соответствующие правилам выбранных фильтров
     */
    @Epic("EPAM Events")
    @Feature("Filter presentations")
    @Story("Find presentations by categories")
    @Description("Пользователь выбирает: Category – Testing, Location – Belarus, Language – English, На развернутой вкладке фильтров\n" +
            "На странице отображаются карточки соответствующие правилам выбранных фильтров")
    @Test
    public void FilterPresentationsByCategoriesTest() {
        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
        openUrl(driver);
        VideosPage videos = openVideosPage(driver);
        openAllFiltersOnVideoPage(videos);
        setNecessaryFilter(videos, "Category", "Testing");
        setNecessaryFilter(videos, "Location", "Belarus");
        setNecessaryFilter(videos, "Language", "ENGLISH");
        List<String> allSelectedTags = videos.getAllSelectedTags();
        for (int i = 0; i < videos.getAllVideosCards().size(); i++) {
            List<WebElement> allFoundVideos = videos.getAllVideosCards();
            VideoPage videoPage = videos.openVideoCard(allFoundVideos.get(i));
            List<String> tagsOfVideo = videoPage.getAllTopics();
            boolean hasVideoChosenTag = false;
            for (String tag : tagsOfVideo) {
                if (allSelectedTags.contains(tag)) {
                    hasVideoChosenTag = true;
                    break;
                }
            }
            assertTrue(hasVideoChosenTag);
            videos = videoPage.goBackToAllVideos();
        }
        logger.info("Test finished");
    }

    /**
     * Tест проверяет следующий сценарий:
     * 7. Поиск докладов по ключевому слову:
     * 7.1 Пользователь переходит на вкладку Video
     * 7.2 Пользователь вводит ключевое слово QA в поле поиска
     * 7.3 На странице отображаются доклады, содержащие в названии ключевое слово поиска
     */
    @Epic("EPAM Events")
    @Feature("Filter presentations")
    @Story("Find presentations by key word")
    @Description("Пользователь переходит на вкладку Video\n" +
            "Пользователь вводит ключевое слово QA в поле поиска\n" +
            "На странице отображаются доклады, содержащие в названии ключевое слово поиска")
    @Test
    public void searchReportByKeyWordTest(){
        logger.info("Test starts");
//        WebDriver driver = getWebDriver();
        openUrl(driver);
        VideosPage videosPage = openVideosPage(driver);
        filterCardsByKeyWord(videosPage, cfg.keyWord());
        List<WebElement> allFoundVideos = videosPage.getAllVideosCards();
        for (WebElement videoCard : allFoundVideos) {
            assertTrue(videosPage.getVideoName(videoCard).contains(cfg.keyWord()));
        }
        logger.info("Test finished");
    }
}
