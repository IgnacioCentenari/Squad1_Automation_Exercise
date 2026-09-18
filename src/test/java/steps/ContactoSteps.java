package steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class ContactoSteps {

    // Instancias de Page Objects (ej. ContactPage contactPage = new ContactPage();)

    @Dado("que el usuario navega a la página {string}")
    @Dado("el usuario navega a la página {string}")
    @Cuando("el usuario navega a la página {string}")
    public void navegarAPagina(String ruta) {
        // driver.get(baseUrl + ruta);
    }

    @Dado("ingresa {string} en el campo {string}")
    @Cuando("ingresa {string} en el campo {string}")
    public void ingresarTextoEnCampo(String texto, String nombreCampo) {
        // contactPage.completarCampo(nombreCampo, texto);
    }

    @Dado("hace clic en el botón {string}")
    @Cuando("hace clic en el botón {string}")
    public void hacerClicEnBoton(String nombreBoton) {
        // contactPage.hacerClicEnBoton(nombreBoton);
    }

    @Cuando("acepta la alerta emergente del navegador")
    public void aceptarAlertaEmergente() {
        // contactPage.aceptarAlertaJS();
    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void verificarMensajeSistema(String mensajeEsperado) {
        // Assertions.assertTrue(contactPage.obtenerMensajeÉxitoOMensajeHtml5().contains(mensajeEsperado));
    }

    @Entonces("el sistema debe navegar a la página {string}")
    public void verificarNavegacionAPagina(String rutaEsperada) {
        // Assertions.assertTrue(driver.getCurrentUrl().endsWith(rutaEsperada));
    }

    @Entonces("el sistema debe solicitar completar el campo obligatorio {string}")
    public void verificarCampoObligatorio(String nombreCampo) {
        // Assertions.assertTrue(contactPage.esCampoRequeridoValido(nombreCampo));
    }
}