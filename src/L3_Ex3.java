import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class L3_Ex3 {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "12");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/mbpro/Desktop/JavAppiumAutomation/JavaAppiumAuto/JavaAppiumAuto/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub/"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void test_l3_ex3() {
        //Пропустим онбординг кликнув Skip
        waitForElementForClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Not find button Skip",
                15
        );

        //Тапнем в поле поиска
        waitForElementForClick(
                By.id("org.wikipedia:id/search_container"),
                "Can not find input id 'org.wikipedia:id/search_container'",
                5
        );


        //Тап в поле ввода и ввод текста
        waitForElementSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot search input",
                15

        );

        //Проверяем что есть первая статья
        waitForElementPresent(
                By.xpath("//*[@text='Island in Indonesia']"),
                "Cannot find 'Island in Indonesia' topic",
                15
        );

        //Проверяем что есть вторая статья
        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic search by Java",
                15
        );

        //Проверяем что есть третья статья
        waitForElementPresent(
                By.xpath("//*[@text='High-level programming language']"),
                "Cannot find 'High-level programming language' topic",
                15
        );

        //Очищаем поле ввод
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Can not find id 'org.wikipedia:id/search_src_text' in field Search",
                15
        );


        //Проверяем что на нашей странице больше нет найденных топиков
        waitForElementNotPresent(
                By.xpath("//*[@text='Island in Indonesia']"),
                "Find 'Island in Indonesia' topic",
                15
        );

}


//___Тут мы пишем методы/функции______________________________________________

        //Пишем метод на ожидание элемента и его клик
        private WebElement waitForElementForClick(By by, String error_message, long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, 15);
            element.click();
            return element;
        }

        //Пишем метод на клик по элементу и ввод текста
        private WebElement waitForElementSendKeys(By by, String value, String error_message, long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, 15);
            element.sendKeys(value);
            return element;
        }

        //Пишем метод на ожидание элемента на странице
        private WebElement waitForElementPresent (By by, String error_message,long timeoutInSeconds)
        {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(by)
            );
        }

        //Пишем метод очищающий строку ввода
    private WebElement waitForElementAndClear (By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    //Метод для проверки отсутвия элемента на странице
    private boolean waitForElementNotPresent (By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

}