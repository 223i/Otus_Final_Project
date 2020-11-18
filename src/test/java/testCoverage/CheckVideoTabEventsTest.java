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
import pages.StartPage;
import pages.VideoPage;
import pages.VideosPage;

import java.util.List;

public class CheckVideoTabEventsTest extends DriverHooks {


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
    @Test
    public void FilterPresentationsByCategoriesTest() {
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
        StartPage startPage = new StartPage(driver);
        VideosPage videosPage = startPage.clickButtonVideo();
        videosPage.clickFilterField(videosPage.getMoreFilters());
        videosPage.setFilterValue(videosPage.getDefinedFilter(), "Category", "Testing");
        videosPage.setFilterValue(videosPage.getDefinedFilter(), "Location", "Belarus");
        videosPage.setFilterValue(videosPage.getDefinedFilter(), "Language", "ENGLISH");

        List<String> allSelectedTags = videosPage.getAllSelectedTags();

        //TODO refactor into step
        for (int i = 0; i < videosPage.getAllVideosCards().size(); i++) {
            List<WebElement> allFoundVideos = videosPage.getAllVideosCards();
            VideoPage videoPage = videosPage.openVideoCard(allFoundVideos.get(i));
            List<String> tagsOfVideo = videoPage.getAllTopics();
            boolean hasVideoChosenTag = false;
            for (String tag : tagsOfVideo) {
                if (allSelectedTags.contains(tag)) {
                    hasVideoChosenTag = true;
                    break;
                }
            }
            Assert.assertTrue(hasVideoChosenTag);
            videosPage = videoPage.goBackToAllVideos();
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
    @Test
    public void searchReportByKeyWordTest(){
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
        StartPage startPage = new StartPage(driver);
        VideosPage videosPage = startPage.clickButtonVideo();
        videosPage.filterByKeyWord("QA");
        List<WebElement> allFoundVideos = videosPage.getAllVideosCards();
        for (WebElement videoCard: allFoundVideos) {
            Assert.assertTrue(videosPage.getVideoName(videoCard).contains("QA"));
        }
        logger.info("Test finished");
    }
}
