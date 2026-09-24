package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public boolean todosLosProductosContienen(String textoEsperado) {
        if (tarjetasProductos.isEmpty()) {
            return false;
        }

        String textoBusqueda = textoEsperado.toLowerCase();

        for (WebElement producto : nombresProductosVisibles) {
            String nombreActual = producto.getText().toLowerCase();
            if (!nombreActual.contains(textoBusqueda)) {
                return false;
            }
        }
        return true;
    }

    public int obtenerCantidadProductos() {
        return tarjetasProductos.size();
    }

    // --- FILTROS ---

    public void seleccionarCategoriaYSubcategoria(String categoria, String subcategoria) {
        WebElement catElement = driver.findElement(By.xpath("//a[contains(text(), '" + categoria + "')]"));
        catElement.click();

        WebElement subCatElement = driver.findElement(By.xpath("//a[contains(text(), '" + subcategoria + "')]"));
        subCatElement.click();
    }

    public void seleccionarMarca(String marca) {
        WebElement marcaCheckbox = driver.findElement(By.xpath("//label[contains(., '" + marca + "')]//input | //span[text()='" + marca + "']"));
        marcaCheckbox.click();
    }
}