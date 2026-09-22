package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductosPage extends CommonPage {

    public ProductosPage(WebDriver driver) {
        super(driver);
    }

    public void agregarProductoDesdeCatalogo(String nombreProducto, int cantidad) {
        String xpathTarjeta = String.format(
                "//div[contains(@class,'single-products') and .//p[contains(normalize-space(),'%s')]]",
                nombreProducto
        );

        WebElement tarjeta = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathTarjeta)));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", tarjeta);

        String xpathBotonAgregar = String.format(
                "%s//div[contains(@class,'productinfo')]//a[contains(@class,'add-to-cart')]",
                xpathTarjeta
        );

        for (int i = 0; i < cantidad; i++) {
            WebElement botonAgregar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBotonAgregar)));
            hacerClick(botonAgregar);
            cerrarModalSiEstaPresente();
        }
    }

    public ProductoDetallePage irAlDetalleDelProducto(String nombreProducto) {
        // 1. Si no estamos en la vista de catálogo, navegamos primero a /products
        if (!driver.getCurrentUrl().endsWith("/products")) {
            navegarARutaRelativa("/products");
        }

        // 2. Buscamos la tarjeta del producto especificado
        String xpathVerDetalle = String.format(
                "//div[contains(@class,'product-image-wrapper') and .//p[contains(normalize-space(),'%s')]]//a[contains(@href,'/product_details/')]",
                nombreProducto
        );

        WebElement botonVerDetalle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathVerDetalle)));

        // 3. Scroll y clic seguro
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", botonVerDetalle);

        hacerClick(botonVerDetalle);

        // 4. Esperamos a que la página de detalle termine de cargar
        wait.until(ExpectedConditions.urlContains("/product_details/"));

        return new ProductoDetallePage(driver);
    }

    private void cerrarModalSiEstaPresente() {
        try {
            By locatorBotonContinuar = By.xpath("//button[contains(@class,'close-modal') or contains(text(),'Continue Shopping')]");
            WebElement botonContinuar = wait.until(ExpectedConditions.elementToBeClickable(locatorBotonContinuar));
            hacerClick(botonContinuar);
        } catch (Exception e) {
            // Si no aparece el modal, continúa
        }
    }
}