package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CarritoPage extends BasePage {

    public CarritoPage(WebDriver driver) {
        super(driver);
    }

    public void eliminarProducto(String nombreProducto) {
        String xpathEliminar = String.format(
                "//tr[contains(@id,'product-') and .//a[contains(normalize-space(),'%s')]]//a[contains(@class,'cart_quantity_delete')]",
                nombreProducto
        );
        WebElement botonEliminar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathEliminar)));
        hacerClick(botonEliminar);
    }

    public boolean existeProductoEnCarrito(String nombreProducto) {
        String xpathProducto = String.format(
                "//tr[contains(@id,'product-')]//td[contains(@class,'cart_description')]//a[contains(normalize-space(),'%s')]",
                nombreProducto
        );
        List<WebElement> elementos = driver.findElements(By.xpath(xpathProducto));
        return !elementos.isEmpty() && elementos.get(0).isDisplayed();
    }

    public int obtenerCantidadFilasProductos() {
        By locatorFilas = By.xpath("//tr[contains(@id,'product-')]");
        List<WebElement> filas = driver.findElements(locatorFilas);
        return filas.size();
    }

    public double calcularSubtotalAcumulado() {
        By locatorTotalesFila = By.xpath("//tr[contains(@id,'product-')]//td[contains(@class,'cart_total')]/p");
        List<WebElement> preciosTotales = driver.findElements(locatorTotalesFila);

        double sumaSubtotales = 0.0;
        for (WebElement elementoPrecio : preciosTotales) {
            String textoPrecio = elementoPrecio.getText().replaceAll("[^0-9.]", "");
            if (!textoPrecio.isEmpty()) {
                sumaSubtotales += Double.parseDouble(textoPrecio);
            }
        }
        return sumaSubtotales;
    }

    public double obtenerTotalGeneral() {
        // En Automation Exercise el subtotal acumulado equivale al total mostrado en la tabla
        return calcularSubtotalAcumulado();
    }

    public boolean estaMensajeCarritoVacioVisible(String mensajeEsperado) {
        // Localizador del contenedor del carrito vacío en Automation Exercise
        By locatorMensajeVacio = By.xpath("//span[@id='empty_cart']//b | //span[@id='empty_cart']");

        // Espera explícita hasta que el mensaje sea visible tras la eliminación AJAX
        WebElement elementoMensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(locatorMensajeVacio));

        String textoObtenido = elementoMensaje.getText().trim();
        return textoObtenido.equalsIgnoreCase(mensajeEsperado) || textoObtenido.contains(mensajeEsperado);
    }
}