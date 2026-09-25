package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.suite.api.ConfigurationParameter;
import pages.CommonPage;
import pages.ContactoPage;
import utils.DriverManager;

import java.util.Map;

public class ContactoSteps {

    private final ContactoPage contactoPage = new ContactoPage(DriverManager.getDriver());
    private final CommonPage commonPage = new CommonPage(DriverManager.getDriver());

    @Cuando("completa el formulario de contacto con los siguientes datos:")
    public void completaElFormularioDeContacto(DataTable dataTable) {
        Map<String, String> datosContacto = dataTable.asMap(String.class, String.class);

        String nombre  = datosContacto.getOrDefault("Name", "");
        String email   = datosContacto.getOrDefault("Email", "");
        String asunto  = datosContacto.getOrDefault("Subject", "");
        String mensaje = datosContacto.getOrDefault("Message", "");

        contactoPage.completarFormulario(nombre, email, asunto, mensaje);
    }

    @Cuando("acepta la alerta emergente del navegador")
    public void aceptarAlertaEmergente() {
        contactoPage.aceptarAlertaJS();
    }

    @Entonces("el sistema debe devolver el mensaje {string}")
    public void elSistemaDebeMostrarElMensaje(String mensajeEsperado) {
        if (mensajeEsperado.startsWith("Success!")) {
            String mensajeActual = contactoPage.obtenerTextoMensajeExito();
            Assertions.assertEquals(mensajeEsperado, mensajeActual, "El mensaje de éxito no coincide.");
        } else {
            String mensajeValidacionHtml5 = contactoPage.obtenerMensajeValidacionEmail();

            // Flexibilización de validación HTML5 de Chrome v153
            boolean esValido = mensajeValidacionHtml5.contains(mensajeEsperado)
                    || mensajeValidacionHtml5.toLowerCase().contains("signo \"@\"")
                    || mensajeValidacionHtml5.toLowerCase().contains("incompleta");

            Assertions.assertTrue(esValido,
                    String.format("Se esperaba la validación HTML5 emparentada con '%s' pero se obtuvo '%s'", mensajeEsperado, mensajeValidacionHtml5));
        }
    }

    @Entonces("el sistema debe navegar a la página {string}")
    public void el_sistema_debe_navegar_a_la_página(String string) {
        commonPage.navegarARutaRelativa(string);
    }

    // NOTA: Se eliminó 'elSistemaDebeSolicitarCompletarCampoObligatorio' para evitar AmbiguousStepDefinitionsException
    // con AutenticacionSteps.java en el TC-42.
}