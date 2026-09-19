package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import java.util.Map;

public class RegistroSteps {

    // private RegisterPage registerPage = new RegisterPage();

    @Cuando("completa los campos obligatorios del formulario de registro:")
    public void completaLosCamposObligatoriosDelFormularioDeRegistro(DataTable dataTable) {
        Map<String, String> datos = dataTable.asMap(String.class, String.class);

        // Mapeo limpio a tu Page Object
        // registerPage.completarFormularioRegistro(datos);
    }
}