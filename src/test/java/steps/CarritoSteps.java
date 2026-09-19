package steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class CarritoSteps {

    // private CartPage cartPage = new CartPage();

    @Cuando("agrega {string} unidades del producto {string} al carrito")
    public void agregarProductoAlCarrito(String cantidad, String nombreProducto) {
        // cartPage.agregarAlCarrito(nombreProducto, Integer.parseInt(cantidad));
    }

    @Cuando("elimina el producto {string} del carrito")
    public void eliminarProductoDelCarrito(String nombreProducto) {
        // cartPage.eliminarProducto(nombreProducto);
    }

    @Entonces("el carrito debe contener el producto {string}")
    public void verificarProductoEnCarrito(String nombreProducto) {
        // Assertions.assertTrue(cartPage.existeProductoEnCarrito(nombreProducto));
    }

    @Entonces("el carrito debe contener {int} productos distintos")
    public void verificarCantidadProductosDistintos(Integer cantidadEsperada) {
        // Assertions.assertEquals(cantidadEsperada, cartPage.obtenerCantidadFilasProductos());
    }

    @Entonces("el total general debe ser igual al subtotal acumulado")
    public void verificarTotalIgualASubtotal() {
        // double subtotalCalculado = cartPage.calcularSubtotalAcumulado();
        // double totalGeneral = cartPage.obtenerTotalGeneral();
        // Assertions.assertEquals(subtotalCalculado, totalGeneral, 0.01);
    }
}