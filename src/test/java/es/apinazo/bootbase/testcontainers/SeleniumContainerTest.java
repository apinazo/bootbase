package es.apinazo.bootbase.testcontainers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.BrowserWebDriverContainer;

@RunWith(SpringRunner.class)
public class SeleniumContainerTest {

    @Rule
    public BrowserWebDriverContainer chrome =
        new BrowserWebDriverContainer()
                .withDesiredCapabilities(DesiredCapabilities.chrome());

    @Test
    public void whenNavigatedToPage_thenHeadingIsInThePage() {
        RemoteWebDriver driver = chrome.getWebDriver();
        driver.get("https://saucelabs.com/test/guinea-pig");
        String heading = driver
            .findElement(By.xpath("/html/body/h1")).getText();
    }

}
