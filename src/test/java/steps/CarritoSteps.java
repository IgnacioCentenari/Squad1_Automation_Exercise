package steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class CarritoSteps {

    // Instancias de Page Objects (ej. CartPage cartPage = new CartPage();)

    @Dado("que el usuario navega a la página {string}")
    @Dado("el usuario navega a la página {string}")
    @Cuando("el usuario navega a la página {string}")
    public void navegarAPagina(String ruta) {
        // driver.get(baseUrl + ruta);
    }

    @Cuando("selecciona la marca {string}")
    public void seleccionarMarca(String marca) {
        // catalogPage.seleccionarMarca(marca);
    }

    @Dado("que agrega {string} unidades del producto {string} al carrito")
    @Cuando("agrega {string} unidades del producto {string} al carrito")
    public void agregarProductoAlCarrito(String cantidad, String nombreProducto) {
        // catalogPage.agregarAlCarrito(nombreProducto, Integer.parseInt(cantidad));
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

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void verificarMensajeSistema(String mensajeEsperado) {
        // Assertions.assertEquals(mensajeEsperado, cartPage.obtenerMensajeEstado());
    }
}