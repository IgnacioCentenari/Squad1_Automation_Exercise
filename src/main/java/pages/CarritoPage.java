//package pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//import java.util.List;
//
//public class CartPage extends BasePage {
//
//    // --- ELEMENTOS ESTÁTICOS ---
//    @FindBy(css = "#cart_info_table tbody tr")
//    private List<WebElement> filasProductos;
//
//    public CartPage(WebDriver driver) {
//        super(driver);
//        PageFactory.initElements(driver, this);
//    }
//
//    /**
//     * Verifica si un producto específico se encuentra en la tabla del carrito.
//     */
//    public boolean existeProductoEnCarrito(String nombreProducto) {
//        String xpathProducto = String.format(
//                "//table[@id='cart_info_table']//td[contains(@class,'cart_description')]//a[text()='%s']",
//                nombreProducto
//        );
//        return !driver.findElements(By.xpath(xpathProducto)).isEmpty();
//    }
//
//    /**
//     * Retorna la cantidad de productos distintos (filas de la tabla).
//     */
//    public int obtenerCantidadFilasProductos() {
//        return filasProductos.size();
//    }
//
//    /**
//     * Elimina un producto del carrito según su nombre haciendo clic en la 'X'.
//     */
//    public void eliminarProducto(String nombreProducto) {
//        String xpathDeleteBtn = String.format(
//                "//tr[.//td[contains(@class,'cart_description')]//a[text()='%s']]//a[@class='cart_quantity_delete']",
//                nombreProducto
//        );
//
//        WebElement btnDelete = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDeleteBtn)));
//        btnDelete.click();
//
//        // Espera explícita a que desaparezca la fila del DOM
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathDeleteBtn)));
//    }
//
//    /**
//     * Suma los precios totales de cada fila (cart_total_price) para calcular el subtotal acumulado.
//     */
//    public double calcularSubtotalAcumulado() {
//        double subtotalAcumulado = 0.0;
//
//        List<WebElement> preciosTotales = driver.findElements(By.cssSelector("p.cart_total_price"));
//
//        for (WebElement precioElement : preciosTotales) {
//            // El texto viene en formato "Rs. 500" o "Rs. 2500"
//            String textoPrecio = precioElement.getText().replaceAll("[^0-9]", "");
//            if (!textoPrecio.isEmpty()) {
//                subtotalAcumulado += Double.parseDouble(textoPrecio);
//            }
//        }
//        return subtotalAcumulado;
//    }
//
//    /**
//     * En Automation Exercise el total general coincide con la suma acumulada de las filas.
//     */
//    public double obtenerTotalGeneral() {
//        return calcularSubtotalAcumulado();
//    }
//}