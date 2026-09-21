package steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class CommonSteps {

    // private CommonPage commonPage = new CommonPage();


    @Cuando("el usuario navega a la página {string}")
    public void navegarAPagina(String ruta) {
        // commonPage.navegarA(ruta);
    }

    @Cuando("ingresa {string} en el campo {string}")
    public void ingresarTextoEnCampo(String valor, String nombreCampo) {
        // commonPage.ingresarTexto(nombreCampo, valor);
    }

    @Cuando("hace clic en el botón {string}")
    public void hacerClicEnBoton(String nombreBoton) {
        // commonPage.hacerClicEnBoton(nombreBoton);
    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void verificarMensaje(String mensajeEsperado) {
        // Assertions.assertTrue(commonPage.obtenerMensajeVisible().contains(mensajeEsperado));
    }

    @Entonces("el sistema debe navegar a la página {string}")
    public void verificarRedireccion(String rutaEsperada) {
        // Assertions.assertTrue(commonPage.obtenerUrlActual().endsWith(rutaEsperada));
    }

    @Entonces("el sistema debe solicitar completar el campo obligatorio {string}")
    public void verificarCampoObligatorio(String nombreCampo) {
        // Assertions.assertTrue(commonPage.esCampoRequeridoValido(nombreCampo));
    }
}