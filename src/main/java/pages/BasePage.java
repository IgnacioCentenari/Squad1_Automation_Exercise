package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void hacerClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            hacerClickPorJS(element);
        }
    }

    protected void hacerClickPorJS(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    protected void escribirTexto(WebElement element, String texto) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(texto);
    }

    protected String obtenerTexto(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected void navegarA(String url) {
        driver.get(url);
    }

    public String obtenerUrlActual() {
        return driver.getCurrentUrl();
    }
}