package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductosPage extends BasePage {

    // --- ELEMENTOS ESTÁTICOS ---
    @FindBy(xpath = "//button[text()='Continue Shopping']")
    private WebElement botonContinuarComprando;

    public ProductosPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Agrega N unidades de un producto directamente desde el catálogo flotante (overlay).
     */
    public void agregarProductoDesdeCatalogo(String nombreProducto, int cantidad) {
        String xpathTarjetaProducto = String.format(
                "//div[@class='single-products'][.//p[text()='%s']]",
                nombreProducto
        );

        String xpathBotonAgregarAlCarrito = String.format(
                "//div[@class='product-overlay'][.//p[text()='%s']]//a[contains(@class,'add-to-cart')]",
                nombreProducto
        );

        for (int i = 0; i < cantidad; i++) {
            // Hover sobre la tarjeta del producto
            WebElement tarjetaProducto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathTarjetaProducto)));
            Actions acciones = new Actions(driver);
            acciones.moveToElement(tarjetaProducto).perform();

            // Clic en el botón del overlay
            WebElement botonAgregar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBotonAgregarAlCarrito)));
            botonAgregar.click();

            // Cerrar el modal de confirmación
            hacerClicEnContinuarComprando();
        }
    }

    /**
     * Navega a la vista de detalle del producto y devuelve una instancia de ProductoDetallePage.
     */
    public ProductoDetallePage irAlDetalleDelProducto(String nombreProducto) {
        String xpathVerProducto = String.format(
                "//div[@class='single-products'][.//p[text()='%s']]/following-sibling::div[@class='choose']//a",
                nombreProducto
        );

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVerProducto))).click();
        return new ProductoDetallePage(driver);
    }

    /**
     * Cierra el modal de confirmación ("Added!").
     */
    public void hacerClicEnContinuarComprando() {
        wait.until(ExpectedConditions.elementToBeClickable(botonContinuarComprando)).click();
    }
}