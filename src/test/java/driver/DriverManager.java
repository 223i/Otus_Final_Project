package driver;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Execution(ExecutionMode.CONCURRENT)
public class DriverManager {
    //    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    protected WebDriver driver;
//
    public DriverManager() {
    }

    @SneakyThrows
    @BeforeEach()
    public void setupDriver() {
//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        String slenoidURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");
        caps.setVersion("87.0");
        caps.setCapability("enableVNC", true);
        caps.setCapability("screenResolution", "1280x1024");
//        caps.setCapability("enableVideo", true);
//        caps.setCapability("enableLog", true);

//        webDriverThreadLocal.set(new RemoteWebDriver(new URL(slenoidURL), caps));
         driver = new RemoteWebDriver(new URL(slenoidURL), caps);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

//    public WebDriver getWebDriver() {
//        return webDriverThreadLocal.get();
//    }

    @AfterEach()
    public void quitDriver() {
//        Optional.ofNullable(getWebDriver()).ifPresent(WebDriver::quit);
        if (driver != null) {
            driver.quit();
        }
    }
}
