package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor: Inicializa driver, espera explícita y mapeo de PageFactory
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // 1. Clic seguro: Si un modal o banner intercepta el clic, recurre a JavaScript
    protected void hacerClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            hacerClickPorJS(element);
        }
    }

    // 2. Clic forzado por JavaScript (útil para overlays o anuncios molestos)
    protected void hacerClickPorJS(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    // 3. Esperar visibilidad, limpiar e ingresar texto
    protected void escribirTexto(WebElement element, String texto) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(texto);
    }

    // 4. Obtener texto visible de un elemento
    protected String obtenerTexto(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    // 5. Verificar si un elemento es visible (no lanza excepción si no está)
    protected boolean estaVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // 6. Manejo de desplegables (Select HTML) por texto visible
    protected void seleccionarPorTexto(WebElement element, String texto) {
        wait.until(ExpectedConditions.visibilityOf(element));
        Select select = new Select(element);
        select.selectByVisibleText(texto);
    }

    // 7. Manejo de desplegables (Select HTML) por atributo 'value'
    protected void seleccionarPorValor(WebElement element, String valor) {
        wait.until(ExpectedConditions.visibilityOf(element));
        Select select = new Select(element);
        select.selectByValue(valor);
    }

    // 8. Navegación directa
    protected void navegarA(String url) {
        driver.get(url);
    }

    // 9. Obtener título de la página actual
    protected String obtenerTituloPagina() {
        return driver.getTitle();
    }

    // 10. Obtener URL actual
    protected String obtenerUrlActual() {
        return driver.getCurrentUrl();
    }

    // 11. Obtener texto por localizador By sin romper la ejecución si no se encuentra
    protected String obtenerTextoPorBy(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).getText().trim();
        } catch (TimeoutException e) {
            return "";
        }
    }
}