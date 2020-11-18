package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class VideoPage extends AbstractPage {

    protected String eventsTopics = "//div[@class = 'evnt-topics-wrapper']//label";
    protected String navigateBack = "//div[@class = 'evnt-nav-cell link']/a";

    public VideoPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAllTopics() {
        List<WebElement> allTags = driver.findElements(By.xpath(eventsTopics));
        List<String> stringRepresentationsOfTags = new ArrayList<>();
        for (int i = 0; i < allTags.size(); i++) {
            stringRepresentationsOfTags.add(allTags.get(i).getText());
        }
        return stringRepresentationsOfTags;
    }


    public VideosPage goBackToAllVideos() {
        WebElement buttonBack = driver.findElement(By.xpath(navigateBack));
        elementClick(buttonBack);
        return new VideosPage(driver);
    }
}
