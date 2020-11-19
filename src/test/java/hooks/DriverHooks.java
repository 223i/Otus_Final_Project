package hooks;

import driver.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DriverHooks {
    @BeforeEach()
    public void setupDriver() {
        DriverManager.setupDriver();
    }

    @AfterEach()
    public void quitDriver() {
        DriverManager.quitDriver();
    }
}
