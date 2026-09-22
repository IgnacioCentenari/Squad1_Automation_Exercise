package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductoDetallePage extends BasePage {

    // Localizadores específicos de la vista /product_details/X
    private By campoCantidad = By.id("quantity");
    private By botonAgregarAlCarrito = By.xpath("//button[contains(@class,'cart')]");
    private By botonContinuarComprando = By.xpath("//button[contains(@class,'close-modal') or contains(text(),'Continue Shopping')]");

    public ProductoDetallePage(WebDriver driver) {
        super(driver);
    }

    public void ingresarCantidadYAgregar(int cantidad) {
        // 1. Espera a que el input de cantidad sea visible en la vista detallada
        WebElement inputCantidad = wait.until(ExpectedConditions.visibilityOfElementLocated(campoCantidad));

        // 2. Hace scroll para asegurar que no esté tapado por ningún banner
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", inputCantidad);

        // 3. Limpia y asigna la cantidad
        inputCantidad.clear();
        inputCantidad.sendKeys(String.valueOf(cantidad));

        // 4. Hace clic en el botón 'Add to cart'
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonAgregarAlCarrito));
        hacerClick(boton);

        // 5. Cierra el modal emergente si aparece
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