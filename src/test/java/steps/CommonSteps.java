package steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import pages.CommonPage;
import utils.DriverManager;

public class CommonSteps {

    private CommonPage commonPage = new CommonPage(DriverManager.getDriver());

    @Cuando("el usuario navega a la página {string}")
    public void queMeEncuentroEnLaPagina(String ruta) {
        commonPage.navegarARutaRelativa(ruta);
    }

    @Cuando("ingreso {string} en el campo {string}")
    public void ingresoTextoEnElCampo(String texto, String nombreCampo) {
        commonPage.ingresarTextoEnCampo(nombreCampo, texto);
    }

    @Cuando("hago clic en el boton {string}")
    public void hagoClicEnElBoton(String textoBoton) {
        commonPage.hacerClicEnBotonPorTexto(textoBoton);
    }

    @Entonces("deberia ver que la URL contiene {string}")
    public void deberiaVerQueLaUrlContiene(String urlEsperada) {
        String urlActual = commonPage.obtenerUrlActual();
        Assertions.assertTrue(urlActual.contains(urlEsperada),
                "La URL actual [" + urlActual + "] no contiene la subcadena esperada [" + urlEsperada + "]");
    }


}