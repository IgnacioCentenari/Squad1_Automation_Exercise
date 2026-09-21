package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductoDetallePage extends BasePage {

    // --- ELEMENTOS DE LA VISTA DE DETALLE ---
    @FindBy(id = "quantity")
    private WebElement campoCantidad;

    @FindBy(css = "button.cart")
    private WebElement botonAgregarAlCarrito;

    @FindBy(xpath = "//button[text()='Continue Shopping']")
    private WebElement botonContinuarComprando;

    public ProductoDetallePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Setea la cantidad especificada y agrega el producto al carrito.
     */
    public void ingresarCantidadYAgregar(int cantidad) {
        WebElement inputCantidad = wait.until(ExpectedConditions.visibilityOf(campoCantidad));
        inputCantidad.clear();
        inputCantidad.sendKeys(String.valueOf(cantidad));

        wait.until(ExpectedConditions.elementToBeClickable(botonAgregarAlCarrito)).click();
        hacerClicEnContinuarComprando();
    }

    /**
     * Cierra el modal de confirmación ("Added!").
     */
    public void hacerClicEnContinuarComprando() {
        wait.until(ExpectedConditions.elementToBeClickable(botonContinuarComprando)).click();
    }
}