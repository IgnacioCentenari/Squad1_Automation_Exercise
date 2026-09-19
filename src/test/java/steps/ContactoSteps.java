package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import java.util.Map;

public class ContactoSteps {

    // private ContactPage contactPage = new ContactPage();

    @Cuando("completa el formulario de contacto con los siguientes datos:")
    public void completaElFormularioDeContacto(DataTable dataTable) {
        Map<String, String> datosContacto = dataTable.asMap(String.class, String.class);

        String nombre  = datosContacto.getOrDefault("Name", "");
        String email   = datosContacto.getOrDefault("Email", "");
        String asunto  = datosContacto.getOrDefault("Subject", "");
        String mensaje = datosContacto.getOrDefault("Message", "");

        // contactPage.completarFormulario(nombre, email, asunto, mensaje);
    }

    @Cuando("acepta la alerta emergente del navegador")
    public void aceptarAlertaEmergente() {
        // contactPage.aceptarAlertaJS();
    }
}