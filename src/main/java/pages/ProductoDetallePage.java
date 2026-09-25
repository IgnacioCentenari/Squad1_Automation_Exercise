package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductoDetallePage extends BasePage {

    private final By campoCantidad = By.id("quantity");
    private final By botonAgregarAlCarrito = By.xpath("//button[contains(@class,'cart')]");
    private final By botonContinuarComprando = By.xpath("//button[contains(@class,'close-modal') or contains(text(),'Continue Shopping')]");

    public ProductoDetallePage(WebDriver driver) {
        super(driver);
    }

    public void ingresarCantidadYAgregar(int cantidad) {
        WebElement inputCantidad = wait.until(ExpectedConditions.visibilityOfElementLocated(campoCantidad));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", inputCantidad);

        inputCantidad.clear();
        inputCantidad.sendKeys(String.valueOf(cantidad));

        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonAgregarAlCarrito));
        hacerClick(boton);

        cerrarModalSiEstaPresente();
    }

    private void cerrarModalSiEstaPresente() {
        try {
            WebElement botonContinuar = wait.until(ExpectedConditions.elementToBeClickable(botonContinuarComprando));
            hacerClick(botonContinuar);
        } catch (Exception e) {
            // Continúa si el modal no intercepta la pantalla
        }
    }
}