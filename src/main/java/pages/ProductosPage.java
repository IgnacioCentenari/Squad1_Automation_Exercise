package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.util.List;

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
        if (!driver.getCurrentUrl().endsWith("/products")) {
            navegarARutaRelativa("/products");
        }

        String xpathVerDetalle = String.format(
                "//div[contains(@class,'product-image-wrapper') and .//p[contains(normalize-space(),'%s')]]//a[contains(@href,'/product_details/')]",
                nombreProducto
        );

        WebElement botonVerDetalle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathVerDetalle)));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", botonVerDetalle);

        hacerClick(botonVerDetalle);

        wait.until(ExpectedConditions.urlContains("/product_details/"));

        return new ProductoDetallePage(driver);
    }

    private void cerrarModalSiEstaPresente() {
        try {
            By locatorBotonContinuar = By.xpath("//button[contains(@class,'close-modal') or contains(text(),'Continue Shopping')]");
            WebElement botonContinuar = wait.until(ExpectedConditions.elementToBeClickable(locatorBotonContinuar));
            hacerClick(botonContinuar);
        } catch (Exception e) {
            // Si el modal no se despliega, continúa
        }
    }

    // --- ELEMENTOS DE CATÁLOGO ---

    @FindBy(xpath = "//div[@class='features_items']")
    private WebElement contenedorProductos;

    @FindBy(xpath = "//div[@class='product-image-wrapper']")
    private List<WebElement> listaTarjetasProductos;

    public boolean esListadoProductosVisible() {
        return wait.until(ExpectedConditions.visibilityOf(contenedorProductos)).isDisplayed();
    }

    public boolean validarElementosProductos(List<String> elementosEsperados) {
        if (listaTarjetasProductos.isEmpty()) {
            return false;
        }

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

    @FindBy(xpath = "//div[@class='features_items']//div[@class='productinfo text-center']/p")
    private List<WebElement> nombresProductosVisibles;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='product-image-wrapper']")
    private List<WebElement> tarjetasProductos;

    public void buscarProducto(String texto) {
        wait.until(ExpectedConditions.visibilityOf(inputBuscarProducto)).clear();
        inputBuscarProducto.sendKeys(texto);
        wait.until(ExpectedConditions.elementToBeClickable(botonBuscar)).click();
    }

    // Es necesario implementarlo de esta manera para evitar la interferencia con un anuncio pop up
    public void hacerClickBotonBusqueda() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", botonBuscar);
        js.executeScript("arguments[0].click();", botonBuscar);
    }
    }

    public boolean todosLosProductosContienen(String textoEsperado) {
        if (tarjetasProductos.isEmpty()) {
            return false;
        }

        String textoBusqueda = textoEsperado.toLowerCase();

        for (WebElement producto : nombresProductosVisibles) {
            if (producto.isDisplayed()) {
                String nombreActual = producto.getText().toLowerCase();
                if (!nombreActual.contains(textoBusqueda)) {
                    return false;
                }
            }
            }
        }
        return true;
    }

    public int obtenerCantidadProductos() {
        return tarjetasProductos.size();
    }

    // --- FILTROS ---

    public void seleccionarCategoriaYSubcategoria(String categoria, String subcategoria) {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));

        // Búsqueda y clic en la categoría
        By catLocator = By.xpath("//div[@id='accordian']//a[normalize-space()='" + categoria + "']");
        WebElement catElement = wait.until(ExpectedConditions.elementToBeClickable(catLocator));
        js.executeScript("arguments[0].scrollIntoView(true);", catElement);
        js.executeScript("arguments[0].click();", catElement);

        // Búsqueda y clic en la subcategoría
        By subcatLocator = By.xpath("//div[@id='accordian']//a[normalize-space()='" + subcategoria + "']");
        WebElement subcatElement = wait.until(ExpectedConditions.elementToBeClickable(subcatLocator));
        js.executeScript("arguments[0].scrollIntoView(true);", subcatElement);
        js.executeScript("arguments[0].click();", subcatElement);
    }

        // Busqueda y clic en la categoría
        By catLocator = By.xpath("//div[@id='accordian']//a[normalize-space()='" + categoria + "']");
        WebElement catElement = wait.until(
                ExpectedConditions.elementToBeClickable(catLocator)
        );
        //Para evitar el pop up
        js.executeScript("arguments[0].scrollIntoView(true);", catElement);
        js.executeScript("arguments[0].click();", catElement);

        // 2. Esperar a que la subcategoría aparezca (usando un XPath flexible que busca en el panel lateral
        // por si el ID del contenedor colapsable difiere en mayúsculas/minúsculas, ej: 'kids' vs 'Kids')
        By subCatLocator = By.xpath("//div[contains(@id, '" + categoria + "')]//a[normalize-space()='" + subcategoria + "']");

        WebElement subCatElement = wait.until(
                ExpectedConditions.elementToBeClickable(subCatLocator)
        );


        js.executeScript("arguments[0].scrollIntoView(true);", subCatElement);
        js.executeScript("arguments[0].click();", subCatElement);
    }

    public void seleccionarMarca(String marca) {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

        // XPath robusto usando normalize-space para buscar el enlace o texto de la marca

        By marcaLocator = By.xpath("//div[@class='brands-name']//a[contains(normalize-space(), '" + marca + "')]");

        // 1. Esperar a que el elemento sea clickeable
        WebElement marcaElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(marcaLocator));

        // 2. Hacer scroll para sacarlo de abajo de cualquier anuncio flotante (podría no ser necesario)
        js.executeScript("arguments[0].scrollIntoView(true);", marcaElement);

        // 3. Forzar el clic por JavaScript para evitar intercepciones
        js.executeScript("arguments[0].click();", marcaElement);
    }

    @FindBy(xpath = "//h2[@class='title text-center']")
    private WebElement tituloCategoria;

    public String obtenerTextoTituloCategoria() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(tituloCategoria));

        return tituloCategoria.getText();
    }
}