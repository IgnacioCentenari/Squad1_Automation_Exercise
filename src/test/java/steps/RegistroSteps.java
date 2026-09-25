package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import pages.*;
import utils.DriverManager;

import java.util.Map;

public class RegistroSteps {

    private final CommonPage commonPage = new CommonPage(DriverManager.getDriver());
    private final LoginPage loginPage = new LoginPage(DriverManager.getDriver());
    private final RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
    private final CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
    private final ContactoPage contactoPage = new ContactoPage(DriverManager.getDriver());

    // Recuerda el último email ingresado en el mini-formulario de signup, para saber
    // si el "Signup" que se acaba de clickear corresponde a la fixture de TC-05.
    private String ultimoEmailSignupIngresado = "";

    @Cuando("completa los campos obligatorios del formulario de registro:")
    public void completaLosCamposObligatoriosDelFormularioDeRegistro(DataTable dataTable) {
        Map<String, String> datos = dataTable.asMap(String.class, String.class);
        registerPage.completarFormularioRegistro(datos);
    }

    @Cuando("que el usuario navega a la página {string}")
    public void que_el_usuario_navega_a_la_página(String ruta) {
        commonPage.navegarARutaRelativa(ruta);
    }

    @Cuando("ingresa {string} en el campo {string}")
    public void ingresa_en_el_campo(String valor, String nombreCampo) {
        switch (nombreCampo) {
            case "Name" -> loginPage.ingresarNombreSignup(valor);
            case "Email Address Signup" -> {
                ultimoEmailSignupIngresado = valor;
                loginPage.ingresarEmailSignup(valor);
            }
            default -> commonPage.ingresarTextoEnCampo(nombreCampo, valor);
        }
    }

    @Cuando("hace clic en el botón {string}")
    public void hace_clic_en_el_botón(String nombreBoton) {
        switch (nombreBoton) {
            case "Signup" -> {
                loginPage.clicSignup();
                asegurarFixtureCuentaExistenteSiCorresponde();
            }
            case "Create Account" -> registerPage.clicCreateAccount();
            case "Continue" -> {
                registerPage.clicContinue();
                commonPage.manejarPosiblePublicidadIntersticial();
            }
            // Integración de casos provenientes de Checkout:
            case "Login", "Proceed To Checkout", "Place Order", "Pay and Confirm Order" -> {
                checkoutPage.hacerClicEnBoton(nombreBoton);
            }
            // Integración del botón Home post-contacto:
            case "Home" -> {
                contactoPage.hacerClicEnBotonHomeExito();
            }
            default -> commonPage.hacerClicEnBotonPorTexto(nombreBoton);
        }
    }

    /**
     * TC-05 necesita que "user@prueba.com" ya exista como cuenta registrada para poder
     * verificar el mensaje "Email Address already exist!". La primera vez que corre la
     * suite en un ambiente nuevo, esa cuenta todavía no existe: el signup avanza
     * normalmente a "ENTER ACCOUNT INFORMATION" en vez de mostrar el error.
     * En ese caso, se completa un registro mínimo para crear la cuenta ahí mismo, se
     * cierra sesión y se repite el intento de signup con el mismo email — así el
     * chequeo del mensaje que viene después encuentra la condición real, incluso en
     * la primera corrida en un ambiente limpio.
     */
    private void asegurarFixtureCuentaExistenteSiCorresponde() {
        if (!LoginPage.EMAIL_FIXTURE_CUENTA_EXISTENTE.equals(ultimoEmailSignupIngresado)) {
            return;
        }
        if (!registerPage.estaEnPaginaDeCuenta()) {
            return;
        }

        Map<String, String> datosMinimos = Map.ofEntries(
                Map.entry("Titulo", "Mr."),
                Map.entry("Contrasena", "userQA"),
                Map.entry("DiaNacimiento", "1"),
                Map.entry("MesNacimiento", "January"),
                Map.entry("AnioNacimiento", "1990"),
                Map.entry("Nombre", "user"),
                Map.entry("Apellido", "Test"),
                Map.entry("Direccion", "Calle Falsa 123"),
                Map.entry("Pais", "United States"),
                Map.entry("Estado", "California"),
                Map.entry("Ciudad", "Los Angeles"),
                Map.entry("CodigoPostal", "90210"),
                Map.entry("Teléfono", "1122334455")
        );
        registerPage.completarFormularioRegistro(datosMinimos);
        registerPage.clicCreateAccount();
        registerPage.clicContinue();
        commonPage.hacerClicEnBotonPorTexto("Logout");

        commonPage.navegarARutaRelativa("/login");
        loginPage.ingresarNombreSignup("user");
        loginPage.ingresarEmailSignup(LoginPage.EMAIL_FIXTURE_CUENTA_EXISTENTE);
        loginPage.clicSignup();
    }

    @Entonces("debe mostrar el mensaje {string}")
    public void el_sistema_debe_mostrar_el_mensaje(String mensajeEsperado) {
        // Los mensajes de validación de e-mail (TC-02/03/04) los muestra el propio
        // navegador (validación HTML5 nativa del input), no quedan en el HTML de la página.
        // Se compara de forma tolerante (sin mayúsculas/acentos exactos ni signos de puntuación
        // al final) porque el texto exacto de Chrome puede variar levemente entre versiones.
//        String mensajeValidacionNavegador = loginPage.obtenerMensajeValidacionEmailSignup();
//        if (coincidenAproximadamente(mensajeValidacionNavegador, mensajeEsperado)) {
//            return;
//        }
        String mensajeValidacionNavegador = "";
        if (loginPage.obtenerUrlActual().contains("/login")) {
            mensajeValidacionNavegador = loginPage.obtenerMensajeValidacionEmailSignup();
        }
        if (coincidenAproximadamente(mensajeValidacionNavegador, mensajeEsperado)) {
            return;
        }
        Assertions.assertTrue(
                esperarQueLaPaginaContengaTexto(mensajeEsperado, java.time.Duration.ofSeconds(10)),
                "No se encontró el mensaje esperado \"" + mensajeEsperado + "\" en la página "
                        + "(ni como texto visible ni como mensaje de validación del navegador"
                        + (mensajeValidacionNavegador == null || mensajeValidacionNavegador.isEmpty()
                                ? ""
                                : ", que en este caso fue: \"" + mensajeValidacionNavegador + "\"")
                        + ")."
        );
    }

    /**
     * commonPage.laPaginaContieneTexto() lee el HTML al instante, sin esperar. Eso alcanza
     * para las validaciones nativas del navegador (síncronas), pero no para mensajes que
     * aparecen después de un submit real con redirect (como "ACCOUNT CREATED!" o
     * "Logged in as..."), donde la página nueva puede tardar un momento en cargar.
     * Este método reintenta el chequeo con un timeout en vez de fallar en el primer intento.
     */
    private boolean esperarQueLaPaginaContengaTexto(String texto, java.time.Duration timeout) {
        long limite = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < limite) {
            if (commonPage.laPaginaContieneTexto(texto)) {
                return true;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return commonPage.laPaginaContieneTexto(texto);
    }

    private boolean coincidenAproximadamente(String actual, String esperado) {
        if (actual == null || actual.isEmpty() || esperado == null) {
            return false;
        }
        String actualNormalizado = normalizar(actual);
        String esperadoNormalizado = normalizar(esperado);
        return actualNormalizado.contains(esperadoNormalizado) || esperadoNormalizado.contains(actualNormalizado);
    }

    private String normalizar(String texto) {
        return texto.toLowerCase()
                .trim()
                .replaceAll("[.!]+$", "")
                .replaceAll("['\"]", "");
    }
}
