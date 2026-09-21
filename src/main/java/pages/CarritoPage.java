package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CarritoPage extends BasePage {

    // --- ELEMENTOS ESTÁTICOS ---
    @FindBy(css = "#cart_info_table tbody tr")
    private List<WebElement> filasProductos;

    public CarritoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifica si un producto existe dentro de la tabla del carrito.
     */
    public boolean existeProductoEnCarrito(String nombreProducto) {
        String xpathProducto = String.format(
                "//table[@id='cart_info_table']//td[contains(@class,'cart_description')]//a[text()='%s']",
                nombreProducto
        );
        return !driver.findElements(By.xpath(xpathProducto)).isEmpty();
    }

    /**
     * Retorna la cantidad de ítems/filas distintas en la tabla del carrito.
     */
    public int obtenerCantidadFilasProductos() {
        return filasProductos.size();
    }

    /**
     * Elimina un producto de la tabla haciendo clic en la 'X'.
     */
    public void eliminarProducto(String nombreProducto) {
        String xpathBotonEliminar = String.format(
                "//tr[.//td[contains(@class,'cart_description')]//a[text()='%s']]//a[@class='cart_quantity_delete']",
                nombreProducto
        );

        WebElement botonEliminar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBotonEliminar)));
        botonEliminar.click();

        // Espera a que el elemento se elimine del DOM
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathBotonEliminar)));
    }

    /**
     * Suma los valores de la columna de totales de cada fila.
     */
    public double calcularSubtotalAcumulado() {
        double subtotalAcumulado = 0.0;
        List<WebElement> preciosTotales = driver.findElements(By.cssSelector("p.cart_total_price"));

        for (WebElement elementoPrecio : preciosTotales) {
            String textoPrecio = elementoPrecio.getText().replaceAll("[^0-9]", "");
            if (!textoPrecio.isEmpty()) {
                subtotalAcumulado += Double.parseDouble(textoPrecio);
            }
        }
        return subtotalAcumulado;
    }

    /**
     * Retorna el monto total general calculado.
     */
    public double obtenerTotalGeneral() {
        return calcularSubtotalAcumulado();
    }
}