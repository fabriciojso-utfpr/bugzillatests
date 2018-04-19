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
import org.openqa.selenium.support.ui.Select;

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
    public void testPesquisaAvancadaTotalDeItensEncontrada() {
        driver.get("https://landfill.bugzilla.org/bugzilla-5.0-branch/query.cgi?format=advanced");
        WebElement inputDeBusca = driver.findElement(By.xpath("//*[@id=\"short_desc\"]"));
        WebElement btSubmit = driver.findElement(By.xpath("//*[@id=\"Search_top\"]"));

        inputDeBusca.sendKeys("it ain't accepting the fields of username and password");
        btSubmit.submit();

        WebElement totalDeBugs = driver.findElement(By.xpath("//*[@id=\"bugzilla-body\"]/span"));

        assertEquals("2 bugs found.", totalDeBugs.getText());
    }

    @Test
    public void testPesquisaAvancadaOrdenacaoPorId() {
        driver.get("https://landfill.bugzilla.org/bugzilla-5.0-branch/query.cgi?format=advanced");
        WebElement inputDeBusca = driver.findElement(By.xpath("//*[@id=\"short_desc\"]"));
        WebElement btSubmit = driver.findElement(By.xpath("//*[@id=\"Search_top\"]"));

        inputDeBusca.sendKeys("it ain't accepting the fields of username and password");
        btSubmit.submit();

        WebElement ID = driver.findElement(By.cssSelector("#bugzilla-body > table > tbody > tr.bz_buglist_header.bz_first_buglist_header > th.first-child > a"));
        ID.click();

        WebElement idDoPrimeiroItem = driver.findElement(By.cssSelector("td.first-child.bz_id_column > a"));
        assertEquals("46907", idDoPrimeiroItem.getText());
    }

    @Test
    public void testPesquisaAvancadaSelecionandoUmProduto() {
        driver.get("https://landfill.bugzilla.org/bugzilla-5.0-branch/query.cgi?format=advanced");
        WebElement inputDeBusca = driver.findElement(By.xpath("//*[@id=\"short_desc\"]"));
        WebElement btSubmit = driver.findElement(By.xpath("//*[@id=\"Search_top\"]"));

        inputDeBusca.sendKeys("username");

        Select selectProduto = new Select(driver.findElement(By.id("product")));
        selectProduto.selectByVisibleText("WorldControl");
        btSubmit.submit();

        WebElement textResult = driver.findElement(By.xpath("//*[@id=\"bugzilla-body\"]/span[1]"));
        assertEquals("15 bugs found.", textResult.getText());

    }

}
