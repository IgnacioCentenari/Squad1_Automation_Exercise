package steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import pages.CarritoPage;
import pages.ProductoDetallePage;
import pages.ProductosPage;
import hooks.Hooks;

public class CarritoSteps {

    private ProductosPage productosPage = new ProductosPage(Hooks.getDriver());
    private ProductoDetallePage productoDetallePage;
    private CarritoPage carritoPage = new CarritoPage(Hooks.getDriver());

    // 1. Paso genérico para agregar al carrito
    @Cuando("agrega {string} unidades del producto {string} al carrito")
    public void agregarProductoAlCarrito(String cantidad, String nombreProducto) {
        int qty = Integer.parseInt(cantidad);
        productosPage.agregarProductoDesdeCatalogo(nombreProducto, qty);
    }

    // 2. Paso específico desde la vista de catálogo
    @Cuando("agrega {string} unidades del producto {string} al carrito desde el catálogo")
    public void agregarProductoAlCarritoDesdeCatalogo(String cantidad, String nombreProducto) {
        int qty = Integer.parseInt(cantidad);
        productosPage.agregarProductoDesdeCatalogo(nombreProducto, qty);
    }

    // 3. Paso específico desde el detalle del producto
    @Cuando("ingresa a los detalles del producto {string} y agrega {string} unidades")
    public void agregarProductoDesdeDetalle(String nombreProducto, String cantidad) {
        int qty = Integer.parseInt(cantidad);
        productoDetallePage = productosPage.irAlDetalleDelProducto(nombreProducto);
        productoDetallePage.ingresarCantidadYAgregar(qty);
    }

    // 4. Paso para eliminar del carrito
    @Cuando("elimina el producto {string} del carrito")
    public void eliminarProductoDelCarrito(String nombreProducto) {
        carritoPage.eliminarProducto(nombreProducto);
    }

    // 5. Validaciones (Entonces)
    @Entonces("el carrito debe contener el producto {string}")
    public void verificarProductoEnCarrito(String nombreProducto) {
        Assertions.assertTrue(
                carritoPage.existeProductoEnCarrito(nombreProducto),
                "El producto '" + nombreProducto + "' no se encuentra en el carrito."
        );
    }

    @Entonces("el carrito debe contener {int} productos distintos")
    public void verificarCantidadProductosDistintos(Integer cantidadEsperada) {
        Assertions.assertEquals(
                cantidadEsperada.intValue(),
                carritoPage.obtenerCantidadFilasProductos(),
                "La cantidad de filas/productos en el carrito no es la esperada."
        );
    }

    @Entonces("el total general debe ser igual al subtotal acumulado")
    public void verificarTotalIgualASubtotal() {
        double subtotalCalculado = carritoPage.calcularSubtotalAcumulado();
        double totalGeneral = carritoPage.obtenerTotalGeneral();

        Assertions.assertEquals(
                subtotalCalculado,
                totalGeneral,
                0.01,
                "El total general no coincide con el subtotal acumulado."
        );
    }
}