/**
 * Created by Dina_Abdykasheva on 6/8/2017.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class GMailTest {

    private WebDriver driver;

    @BeforeClass(description = "StartBrowser")
    private void startBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(description = "isLoginSuccessfulTest")
    public void isLoginSuccessfulTest () {
        driver.get("https://www.google.com/gmail");
        driver.findElement(By.name("identifier")).sendKeys("test.da.10062017");
        driver.findElement(By.id("identifierNext")).click();
        driver.findElement(By.name("password")).sendKeys("testtest01");
        WebElement element = driver.findElement(By.id("passwordNext"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);
        Assert.assertTrue(driver.findElement(By.xpath(".//span [@class='gb_8a gbii']")).isDisplayed(), "Login isn't successful");
    }

    @Test(description = "isMailSavedToDraft", dependsOnMethods = "isLoginSuccessfulTest")
    public void isMailSavedToDraft() {
        driver.findElement(By.xpath(".//div[@class='T-I J-J5-Ji T-I-KE L3']")).click();
        driver.switchTo().activeElement();
        driver.findElement(By.xpath(".//textarea[@aria-label='Кому']")).sendKeys("dina_abdykasheva@mail.ru");
        driver.findElement(By.name("subjectbox")).sendKeys("mentoring task");
        driver.findElement(By.xpath(".//div[@role='textbox']")).sendKeys("body text");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//span[@class = 'oG aOy']")));
        driver.findElement(By.xpath(".//img[@alt='Закрыть']")).click();
        driver.findElement(By.xpath(".//a[contains(text(), 'Черновики')]")).click();
        Assert.assertTrue(driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).isDisplayed(), "Mail wasn't saved to draft");
    }

    @Test(description = "isSavedDraftMailReceiverValidTest", priority = 2, dependsOnMethods = "isMailSavedToDraft")
    public void isSavedDraftMailReceiverValidTest () {
        driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).click();
        driver.switchTo().activeElement();
        String receiver = driver.findElement(By.xpath(".//span[@class='vN bfK a3q']")).getAttribute("email");
        Assert.assertEquals(receiver, "dina_abdykasheva@mail.ru", "Receiver is invalid");
        String subject = driver.findElement(By.name("subjectbox")).getAttribute("value");
        Assert.assertEquals(subject, "mentoring task", "Subject is invalid");
        String body = driver.findElement(By.xpath(".//div[@role='textbox']")).getText();
        Assert.assertEquals(body, "body text", "Body is invalid");
    }

    @Test(description = "isSavedDraftMailSubjectValidTest", dependsOnMethods = "isSavedDraftMailReceiverValidTest")
    public void isSavedDraftMailSubjectValidTest () {
        String subject = driver.findElement(By.name("subjectbox")).getAttribute("value");
        Assert.assertEquals(subject, "mentoring task", "Subject is invalid");
    }

    @Test(description = "isSavedDraftMailBodyValidTest", dependsOnMethods = "isSavedDraftMailReceiverValidTest")
    public void isSavedDraftMailBodyValidTest () {
        String body = driver.findElement(By.xpath(".//div[@role='textbox']")).getText();
        Assert.assertEquals(body, "body text", "Body is invalid");
    }

    @Test(description = "isMailSentTest", priority = 4, dependsOnMethods = {"isSavedDraftMailReceiverValidTest", "isSavedDraftMailSubjectValidTest", "isSavedDraftMailBodyValidTest"})
    public void isMailSentTest() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(text(), 'Отправить')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(text(), 'Письмо отправлено')]")));
        driver.findElement(By.xpath(".//a[contains(text(), 'Отправленные')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//span[contains(text(), 'mentoring task')]")));
        Assert.assertTrue(driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).isDisplayed(), "Mail wasn't sent");
    }

    @Test(description = "isMailDeletedFromDraftsAfterSent", dependsOnMethods = "isMailSentTest")
    public void isMailDeletedFromDraftsAfterSent() {
        driver.findElement(By.xpath(".//a[contains(text(), 'Черновики')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//span[contains(text(), 'mentoring task')]")));
        Assert.assertFalse(driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).isDisplayed(), "Mail wasn't deleted from drafts");
    }

    @Test(description = "exitGMail", dependsOnMethods = "isMailDeletedFromDraftsAfterSent")
    public void exitGMail() {
        driver.findElement(By.xpath(".//span [@class='gb_8a gbii']")).click();
        driver.switchTo().activeElement();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath((".//a[contains(text(), 'Выйти')]")))).click();
        Assert.assertTrue(driver.findElement(By.xpath(".//div[@class= 'bdf4dc']")).isDisplayed(), "User wasn't logged off");
    }

    @AfterClass
    public void closeDriver() {
        driver.close();
    }


}
