package steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class CheckoutSteps {

    // Instancias de Page Objects (ej. CheckoutPage checkoutPage = new CheckoutPage();)

    @Dado("que el usuario navega a la página {string}")
    @Dado("el usuario navega a la página {string}")
    @Cuando("el usuario navega a la página {string}")
    public void navegarAPagina(String ruta) {
        // driver.get(baseUrl + ruta);
    }

    @Dado("ingresa {string} en el campo {string}")
    @Cuando("ingresa {string} en el campo {string}")
    public void ingresarTextoEnCampo(String texto, String nombreCampo) {
        // checkoutPage.completarCampo(nombreCampo, texto);
    }

    @Dado("hace clic en el botón {string}")
    @Cuando("hace clic en el botón {string}")
    public void hacerClicEnBoton(String nombreBoton) {
        // checkoutPage.hacerClicEnBoton(nombreBoton);
    }

    @Dado("que agrega {string} unidades del producto {string} al carrito")
    public void agregarProductoAlCarrito(String cantidad, String nombreProducto) {
        // catalogPage.agregarAlCarrito(nombreProducto, Integer.parseInt(cantidad));
    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void verificarMensajeSistema(String mensajeEsperado) {
        // Assertions.assertTrue(checkoutPage.obtenerMensajeConfirmacion().contains(mensajeEsperado));
    }

    @Entonces("el sistema debe solicitar completar el campo obligatorio {string}")
    public void verificarCampoObligatorio(String nombreCampo) {
        // Assertions.assertTrue(checkoutPage.esCampoRequeridoActivado(nombreCampo));
    }
}