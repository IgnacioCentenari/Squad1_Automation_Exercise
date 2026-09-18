package steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class AuthSteps {

    // Instancia de tus Page Objects (ejemplo: LoginPage loginPage = new LoginPage();)

    @Dado("que el usuario navega a la página {string}")
    @Cuando("el usuario navega a la página {string}")
    public void navegarAPagina(String ruta) {
        // Lógica Selenium: driver.get(baseUrl + ruta);
    }

    @Dado("que ingresa {string} en el campo {string}")
    @Dado("ingresa {string} en el campo {string}")
    @Cuando("ingresa {string} en el campo {string}")
    public void ingresarTextoEnCampo(String valor, String nombreCampo) {
        // Lógica para escribir en el input según el locator de 'nombreCampo'
        // loginPage.completarCampo(nombreCampo, valor);
    }

    @Dado("hace clic en el botón {string}")
    @Cuando("hace clic en el botón {string}")
    public void hacerClicEnBoton(String nombreBoton) {
        // Lógica para hacer click en Login / Logout
        // loginPage.hacerClicEn(nombreBoton);
    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void verificarMensaje(String mensajeEsperado) {
        // String mensajeObtenido = loginPage.obtenerMensaje();
        // Assertions.assertEquals(mensajeEsperado, mensajeObtenido);
    }

    @Entonces("el sistema debe solicitar completar el campo obligatorio {string}")
    public void verificarCampoObligatorio(String nombreCampo) {
        // Validar mensaje de validación HTML5 (validationMessage) o alerta personalizada
        // Assertions.assertTrue(loginPage.esCampoRequerido(nombreCampo));
    }

    @Entonces("el sistema debe navegar a la página {string}")
    public void verificarRedireccion(String rutaEsperada) {
        // String urlActual = driver.getCurrentUrl();
        // Assertions.assertTrue(urlActual.endsWith(rutaEsperada));
    }
}