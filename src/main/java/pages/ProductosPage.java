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

    // --- Identificador para el contenedor de los productos y cada tarjeta individual---

    @FindBy(xpath = "//div[@class='features_items']")
    private  WebElement contenedorProductos;

    @FindBy(xpath = "//div[@class='product-image-wrapper']")
    private  java.util.List<WebElement> listaTarjetasProductos;

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

        // Validamos el primer producto (o podemos recorrerlos todos) como muestra representativa
        WebElement primerProducto = listaTarjetasProductos.get(0);

        for (String elemento : elementosEsperados) {
            switch (elemento.toLowerCase()) {
                case "nombre":
                    if (!primerProducto.findElement(By.xpath(".//p")).isDisplayed()) return false;
                    break;
                case "precio":
                    if (!primerProducto.findElement(By.xpath(".//h2")).isDisplayed()) return false;
                    break;
                case "imagen":
                    if (!primerProducto.findElement(By.xpath(".//img")).isDisplayed()) return false;
                    break;
                case "estado":
                    // El "estado" suele ser el botón de "Add to cart" o visualización de stock
                    if (!primerProducto.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).isDisplayed()) return false;
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

    // Tarjetas completas para contar cuántos productos hay (útil para el caso vacío)
    @FindBy(xpath = "//div[@class='features_items']//div[@class='product-image-wrapper']")
    private java.util.List<WebElement> tarjetasProductos;


    // --- ACCIONES ---

    /**
     * Ingresa el texto en el buscador y hace clic en la lupa.
     */
//    public void buscarProducto(String texto) {
//        wait.until(ExpectedConditions.visibilityOf(inputBuscarProducto)).clear();
//        inputBuscarProducto.sendKeys(texto);
//        wait.until(ExpectedConditions.elementToBeClickable(botonBuscar)).click();
//    }


    //Es necesario implementarlo de esta manera para evitar la interferencia con un anuncion pop up
    public void hacerClickBotonBusqueda() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        // Hace scroll para asegurar que el elemento esté en la vista
        js.executeScript("arguments[0].scrollIntoView(true);", botonBuscar);
        // Fuerza el clic ignorando si un iframe o anuncio lo está tapando
        js.executeScript("arguments[0].click();", botonBuscar);
    }

    // --- Validaciones de búsqueda ---

    /**
     * Verifica que CADA uno de los productos mostrados en pantalla
     * contenga el texto buscado (ignorando mayúsculas/minúsculas para mayor seguridad).
     */
    public boolean todosLosProductosContienen(String textoEsperado) {
        if (tarjetasProductos.isEmpty()) {
            return false; // Si no hay productos, no contiene nada
        }

        String textoBusqueda = textoEsperado.toLowerCase();

        for (WebElement producto : nombresProductosVisibles) {
            // Solo evaluamos los elementos que estén visibles en el DOM
            if (producto.isDisplayed()) {
                String nombreActual = producto.getText().toLowerCase();
                if (!nombreActual.contains(textoBusqueda)) {
                    return false;
                }
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


    //FILTROS

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
        WebElement marcaCheckbox = driver.findElement(By.xpath("//label[contains(., '" + marca + "')]//input | //span[text()='" + marca + "']"));
        marcaCheckbox.click();
    }
}