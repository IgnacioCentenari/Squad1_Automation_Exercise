package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Página /login de automationexercise.com.
 * Contiene DOS formularios: "New User Signup!" (nombre + email) y "Login to your account".
 */
public class LoginPage extends BasePage {

    // --- Formulario de Signup ---
    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement campoNombreSignup;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement campoEmailSignup;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement botonSignup;

    // --- Formulario de Login ---
    @FindBy(css = "input[data-qa='login-email']")
    private WebElement campoEmailLogin;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement campoPassword;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement botonLogin;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Email fijo que se usa para probar el caso "cuenta ya registrada" (TC-05).
    // A este NO se le agrega sufijo: tiene que seguir siendo siempre el mismo valor
    // para que, una vez que existe en el sitio, la validación de "ya existe" funcione.
    // Público para que RegistroSteps pueda reutilizarlo al asegurar la fixture.
    public static final String EMAIL_FIXTURE_CUENTA_EXISTENTE = "user@prueba.com";

    // Emails "bien formados" (algo@algo.algo) — se usan para registrar una cuenta real.
    // Los emails deliberadamente rotos de los casos negativos (sin @, sin dominio con
    // punto, sin texto antes del @) NO matchean este patrón y quedan intactos.
    private static final java.util.regex.Pattern EMAIL_BIEN_FORMADO =
            java.util.regex.Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public void ingresarNombreSignup(String nombre) {
        escribirTextoRobusto(campoNombreSignup, nombre);
    }

    public void ingresarEmailSignup(String email) {
        escribirTextoRobusto(campoEmailSignup, hacerUnicoSiEsRegistroReal(email));
    }

    /**
     * Si el email es uno "real" destinado a crear una cuenta nueva (TC-01), le agrega
     * un sufijo único por corrida para evitar el fallo "Email Address already exist!"
     * en corridas sucesivas. El email fijo de la fixture de "cuenta ya registrada"
     * (TC-05) y los emails deliberadamente inválidos de los casos negativos
     * (TC-02/03/04) se devuelven sin modificar.
     */
    private String hacerUnicoSiEsRegistroReal(String email) {
        if (email == null || email.equals(EMAIL_FIXTURE_CUENTA_EXISTENTE)) {
            return email;
        }
        if (!EMAIL_BIEN_FORMADO.matcher(email).matches()) {
            return email;
        }
        int arroba = email.indexOf('@');
        String parteLocal = email.substring(0, arroba);
        String dominio = email.substring(arroba);
        return parteLocal + "+" + System.currentTimeMillis() + dominio;
    }

    /**
     * Escribe texto en un campo y verifica que realmente haya quedado escrito.
     * El mini-formulario de signup de automationexercise.com a veces no registra
     * el sendKeys() estándar (por banners/overlays que se reacomodan justo después
     * de cargar la página), dejando el campo vacío y disparando la validación nativa
     * "Completa este campo" al hacer clic en Signup. Si el valor no quedó escrito,
     * se reintenta seteándolo por JS y disparando los eventos input/change para que
     * el sitio lo registre igual que si lo hubiera tipeado un usuario.
     */
    private void escribirTextoRobusto(WebElement element, String texto) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(texto);

        String valorActual = element.getAttribute("value");
        if (valorActual == null || !valorActual.equals(texto)) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                            "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                    element, texto
            );
        }
    }

    public void clicSignup() {
        hacerClick(botonSignup);
    }

    /**
     * Devuelve el mensaje de validación nativo del navegador (HTML5) para el campo
     * de e-mail del mini-formulario de signup, p.ej. cuando el e-mail no tiene "@".
     * Este mensaje no aparece en el HTML de la página, solo en el tooltip del navegador.
     * Si el email era sintácticamente válido para el navegador, el signup puede haber
     * avanzado de página (el campo ya no existe); en ese caso devuelve "" en vez de
     * lanzar una excepción, para que el step de arriba pueda seguir con el chequeo de
     * texto en la página.
     */
    public String obtenerMensajeValidacionEmailSignup() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object mensaje = js.executeScript("return arguments[0].validationMessage;", campoEmailSignup);
            return mensaje != null ? mensaje.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public void ingresarEmailLogin(String email) {
        escribirTexto(campoEmailLogin, email);
    }

    public void ingresarPassword(String password) {
        escribirTexto(campoPassword, password);
    }

    public void clicLogin() {
        hacerClick(botonLogin);
    }
}