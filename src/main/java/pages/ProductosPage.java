package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductosPage extends CommonPage {

    public ProductosPage(WebDriver driver) {
        super(driver);
    }

    public void agregarProductoDesdeCatalogo(String nombreProducto, int cantidad) {
        String xpathTarjeta = String.format(
                "//div[contains(@class,'single-products') and .//p[contains(normalize-space(),'%s')]]",
                nombreProducto);

        WebElement tarjeta = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathTarjeta)));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", tarjeta);

        String xpathBotonAgregar = String.format(
                "%s//div[contains(@class,'productinfo')]//a[contains(@class,'add-to-cart')]",
                xpathTarjeta);

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
                nombreProducto);

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
            By locatorBotonContinuar = By
                    .xpath("//button[contains(@class,'close-modal') or contains(text(),'Continue Shopping')]");
            WebElement botonContinuar = wait.until(ExpectedConditions.elementToBeClickable(locatorBotonContinuar));
            hacerClick(botonContinuar);
        } catch (Exception e) {
            // Si no aparece el modal, continúa
        }
    }

    // --- Identificador para el contenedor de los productos y cada tarjeta
    // individual---

    @FindBy(xpath = "//div[@class='features_items']")
    private WebElement contenedorProductos;

    @FindBy(xpath = "//div[@class='product-image-wrapper']")
    private java.util.List<WebElement> listaTarjetasProductos;

    /**
     * Los productos son visibles (aparece el contenedor).
     */
    public boolean esListadoProductosVisible() {
        return wait.until(ExpectedConditions.visibilityOf(contenedorProductos)).isDisplayed();
    }

    /**
     * Valida que los elementos especificados (Nombre, Precio, Imagen, Estado)
     * estén presentes en los productos del catálogo.
     * Lo hacemos solamente para el primero por una cuestión de simplicidad
     */
    public boolean validarElementosProductos(java.util.List<String> elementosEsperados) {
        // Aseguramos que hay productos listados
        if (listaTarjetasProductos.isEmpty()) {
            return false;
        }

        // Validamos el primer producto (o podemos recorrerlos todos) como muestra
        // representativa
        WebElement primerProducto = listaTarjetasProductos.get(0);

        for (String elemento : elementosEsperados) {
            switch (elemento.toLowerCase()) {
                case "nombre":
                    if (!primerProducto.findElement(By.xpath(".//p")).isDisplayed())
                        return false;
                    break;
                case "precio":
                    if (!primerProducto.findElement(By.xpath(".//h2")).isDisplayed())
                        return false;
                    break;
                case "imagen":
                    if (!primerProducto.findElement(By.xpath(".//img")).isDisplayed())
                        return false;
                    break;
                case "estado":
                    // El "estado" suele ser el botón de "Add to cart" o visualización de stock
                    if (!primerProducto.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).isDisplayed())
                        return false;
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    // --- ELEMENTOS DE BÚSQUEDA ---
    @FindBy(id = "search_product")
    private WebElement inputBuscarProducto;

    @FindBy(id = "submit_search")
    private WebElement botonBuscar;

    // Lista con los textos (nombres) de los productos que aparecen en pantalla
    @FindBy(xpath = "//div[@class='features_items']//div[@class='productinfo text-center']/p")
    private java.util.List<WebElement> nombresProductosVisibles;

    // Tarjetas completas para contar cuántos productos hay (útil para el caso
    // vacío)
    @FindBy(xpath = "//div[@class='features_items']//div[@class='product-image-wrapper']")
    private java.util.List<WebElement> tarjetasProductos;

    // --- ACCIONES ---

    /**
     * Ingresa el texto en el buscador y hace clic en la lupa.
     */
    public void buscarProducto(String texto) {
        wait.until(ExpectedConditions.visibilityOf(inputBuscarProducto)).clear();
        inputBuscarProducto.sendKeys(texto);
        wait.until(ExpectedConditions.elementToBeClickable(botonBuscar)).click();
    }

    // --- Validaciones de búsqueda ---

    /**
     * Verifica que CADA uno de los productos mostrados en pantalla
     * contenga el texto buscado (ignorando mayúsculas/minúsculas para mayor
     * seguridad).
     */
    public boolean todosLosProductosContienen(String textoEsperado) {
        if (tarjetasProductos.isEmpty()) {
            return false; // Si no hay productos, no contiene nada
        }

        String textoBusqueda = textoEsperado.toLowerCase();

        for (WebElement producto : nombresProductosVisibles) {
            String nombreActual = producto.getText().toLowerCase();
            if (!nombreActual.contains(textoBusqueda)) {
                return false; // Si al menos uno no coincide, la validación falla
            }
        }
        return true; // Todos coinciden
    }

    /**
     * Devuelve la cantidad de productos visibles en el catálogo.
     * (Ideal para verificar el escenario negativo donde la lista debe estar vacía).
     */
    public int obtenerCantidadProductos() {
        return tarjetasProductos.size();
    }

    // FILTROS

    // Seleccionar categoría y subcategoría
    public void seleccionarCategoriaYSubcategoria(String categoria, String subcategoria) {
        // Ejemplo genérico con XPath dinámico para menús laterales
        WebElement catElement = driver.findElement(By.xpath("//a[contains(text(), '" + categoria + "')]"));
        catElement.click();

        WebElement subCatElement = driver.findElement(By.xpath("//a[contains(text(), '" + subcategoria + "')]"));
        subCatElement.click();
    }

    // Seleccionar marca
    public void seleccionarMarca(String marca) {
        WebElement marcaCheckbox = driver
                .findElement(By.xpath("//label[contains(., '" + marca + "')]//input | //span[text()='" + marca + "']"));
        marcaCheckbox.click();
    }
}