/**
 * Created by Dina_Abdykasheva on 6/8/2017.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com/gmail");

        driver.findElement(By.name("identifier")).sendKeys("test.da.10062017");

        driver.findElement(By.id("identifierNext")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.name("password")).sendKeys("testtest01");

        driver.findElement(By.id("passwordNext")).click();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        Assert.assertTrue(driver.findElement(By.xpath(".//span [@class='gb_8a gbii']")).isDisplayed());

        driver.findElement(By.xpath(".//div[@class='T-I J-J5-Ji T-I-KE L3']")).click();

        driver.findElement(By.xpath(".//div[@class='AD']"));

        driver.switchTo().activeElement();

        driver.findElement(By.xpath(".//textarea[@aria-label='Кому']")).sendKeys("dina_abdykasheva@mail.ru");

        driver.findElement(By.name("subjectbox")).sendKeys("mentoring task");

        driver.findElement(By.xpath(".//div[@role='textbox']")).sendKeys("body text");

        driver.findElement(By.xpath(".//img[@alt='Закрыть']")).click();

        driver.findElement(By.xpath(".//a[contains(text(), 'Черновики')]")).click();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).click();

        driver.switchTo().activeElement();

        String s = driver.findElement(By.xpath(".//span[@class='vN bfK a3q']")).getAttribute("email");

        Assert.assertEquals(s, "dina_abdykasheva@mail.ru");

        String d = driver.findElement(By.name("subjectbox")).getAttribute("value");

        Assert.assertEquals(d, "mentoring task");

        String e = driver.findElement(By.xpath(".//div[@role='textbox']")).getText();

        Assert.assertEquals(e, "body text");

        driver.findElement(By.xpath(".//div[@class='T-I J-J5-Ji aoO T-I-atl L3']")).click();

        driver.findElement(By.xpath(".//a[@href='https://mail.google.com/mail/u/0/#drafts']")).click();

        Assert.assertFalse(driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).isDisplayed());

        driver.findElement(By.xpath(".//a[contains(text(), 'Отправленные')]")).click();

        Assert.assertTrue(driver.findElement(By.xpath(".//span[contains(text(), 'mentoring task')]")).isDisplayed());

        driver.findElement(By.xpath(".//span [@class='gb_8a gbii']")).click();

        driver.switchTo().activeElement();

        driver.findElement(By.xpath(".//a[contains(text(), 'Выход')]")).click();

        driver.close();
    }
}
