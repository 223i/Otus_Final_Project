package testCoverage;

import config.ServerConfig;
import driver.DriverManager;
import hooks.DriverHooks;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.StartPage;
import pages.VideoPage;

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
    public void FilterPresentationsByCategoriesTest(){
        logger.info("Test starts");
        WebDriver driver = DriverManager.getWebDriver();
        driver.get(cfg.url());
        logger.info("Start page 'Events EPAM URL' is opened");
        StartPage startPage = new StartPage(driver);
        VideoPage videoPage = startPage.clickButtonVideo();
        videoPage.clickFilterField(videoPage.getMoreFilters());
        videoPage.setFilterValue(videoPage.getDefinedFilter(), "Category", "Testing");
        videoPage.setFilterValue(videoPage.getDefinedFilter(), "Location", "Belarus");
        videoPage.setFilterValue(videoPage.getDefinedFilter(), "Language", "ENGLISH");

    }
}
