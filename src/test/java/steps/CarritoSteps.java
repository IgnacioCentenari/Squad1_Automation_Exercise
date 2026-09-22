package steps;

import hooks.Hooks;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import pages.CarritoPage;
import pages.ProductoDetallePage;
import pages.ProductosPage;

public class CarritoSteps {

    private ProductosPage productosPage;
    private ProductoDetallePage productoDetallePage;
    private CarritoPage carritoPage;

    // Métodos auxiliares para obtener las instancias de las páginas con el driver activo
    private ProductosPage getProductosPage() {
        if (productosPage == null) {
            productosPage = new ProductosPage(Hooks.getDriver());
        }
        return productosPage;
    }

    private CarritoPage getCarritoPage() {
        if (carritoPage == null) {
            carritoPage = new CarritoPage(Hooks.getDriver());
        }
        return carritoPage;
    }

    // 1. Paso genérico para agregar al carrito
    @Cuando("agrega {string} unidades del producto {string} al carrito")
    public void agregarProductoAlCarrito(String cantidad, String nombreProducto) {
        int qty = Integer.parseInt(cantidad);
        getProductosPage().agregarProductoDesdeCatalogo(nombreProducto, qty);
    }

    // 2. Paso específico desde la vista de catálogo
    @Cuando("agrega {string} unidades del producto {string} al carrito desde el catálogo")
    public void agregarProductoAlCarritoDesdeCatalogo(String cantidad, String nombreProducto) {
        int qty = Integer.parseInt(cantidad);
        getProductosPage().agregarProductoDesdeCatalogo(nombreProducto, qty);
    }

    // 3. Paso específico desde el detalle del producto
    @Cuando("ingresa a los detalles del producto {string} y agrega {string} unidades")
    public void agregarProductoDesdeDetalle(String nombreProducto, String cantidad) {
        int qty = Integer.parseInt(cantidad);
        productoDetallePage = getProductosPage().irAlDetalleDelProducto(nombreProducto);
        productoDetallePage.ingresarCantidadYAgregar(qty);
    }

    // 4. Paso para eliminar del carrito
    @Cuando("elimina el producto {string} del carrito")
    public void eliminarProductoDelCarrito(String nombreProducto) {
        getCarritoPage().eliminarProducto(nombreProducto);
    }

    // 5. Validaciones (Entonces)
    @Entonces("el carrito debe contener el producto {string}")
    public void verificarProductoEnCarrito(String nombreProducto) {
        Assertions.assertTrue(
                getCarritoPage().existeProductoEnCarrito(nombreProducto),
                "El producto '" + nombreProducto + "' no se encuentra en el carrito."
        );
    }

    @Entonces("el carrito debe contener {int} productos distintos")
    public void verificarCantidadProductosDistintos(Integer cantidadEsperada) {
        Assertions.assertEquals(
                cantidadEsperada.intValue(),
                getCarritoPage().obtenerCantidadFilasProductos(),
                "La cantidad de filas/productos en el carrito no es la esperada."
        );
    }

    @Entonces("el total general debe ser igual al subtotal acumulado")
    public void verificarTotalIgualASubtotal() {
        double subtotalCalculado = getCarritoPage().calcularSubtotalAcumulado();
        double totalGeneral = getCarritoPage().obtenerTotalGeneral();

        Assertions.assertEquals(
                subtotalCalculado,
                totalGeneral,
                0.01,
                "El total general no coincide con el subtotal acumulado."
        );
    }

    // 6. Paso para validar mensajes en pantalla (Ej: "Cart is empty!")
    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void elSistemaDebeMostrarElMensaje(String mensajeEsperado) {
        boolean estaVisible = getCarritoPage().estaMensajeCarritoVacioVisible(mensajeEsperado);
        Assertions.assertTrue(
                estaVisible,
                "El mensaje esperado '" + mensajeEsperado + "' no se mostró en la pantalla."
        );
    }
}