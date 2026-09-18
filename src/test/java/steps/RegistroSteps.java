package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class RegistroSteps {

    @Dado("que el usuario navega a la página {string}")
    public void que_el_usuario_navega_a_la_página(String ruta) {

    }

    @Cuando("ingresa {string} en el campo {string}")
    public void ingresa_en_el_campo(String texto, String nombreCampo) {

    }

    @Cuando("hace clic en el botón {string}")
    public void hace_clic_en_el_botón(String nombreBoton) {

    }

    @Cuando("completa los campos obligatorios del formulario de registro:")
    public void completa_los_campos_obligatorios_del_formulario_de_registro(DataTable dataTable) {

    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void el_sistema_debe_mostrar_el_mensaje(String mensajeEsperado) {

    }
}