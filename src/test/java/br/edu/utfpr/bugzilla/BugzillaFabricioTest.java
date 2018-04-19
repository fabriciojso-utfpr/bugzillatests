package br.edu.utfpr.bugzilla;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BugzillaFabricioTest {

    /**
     * Vc precisa identificar onde estah o chromedriver. Baixar de:
     * https://sites.google.com/a/chromium.org/chromedriver/downloads
     *
     * Versão utilizada do chromedriver: 2.35.528139
     */
    private static String CHROMEDRIVER_LOCATION = "chromedriver";
    
    private static int scId = 0;

    WebDriver driver;
    
    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_LOCATION);
    }
    
    @Before
    public void before() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //Opcao headless para MacOS e Linux
        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("window-size=1200x600");
        chromeOptions.addArguments("start-maximized");
        
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @After
    public void after() {
        driver.close();
    }
    
    @Test
    public void testAcessaFAQ() {
        driver.get("https://ration.io/");
        WebElement faqLink = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/a[1]"));
        faqLink.click();
        
        WebElement h1 = driver.findElement(By.tagName("h1"));
        assertEquals("FAQ", h1.getText());
    }
   
}
