package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebDriverSetup {
    WebDriver driver;
    public WebDriver getDriver(Properties properties) {
//        System.setProperty("webdriver.chrome.driver", properties.getProperty("chromedriver.path"));
//        WebDriver driver = new ChromeDriver();
        switch(properties.getProperty("browser").toLowerCase()){
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if(properties.getProperty("run.browser.headless").equalsIgnoreCase("true")){
                    chromeOptions.setHeadless(true); }
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new ChromeDriver();
                break; }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

}
