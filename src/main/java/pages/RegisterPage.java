package pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;

/**
 * Página "ENTER ACCOUNT INFORMATION" de automationexercise.com,
 * a la que se llega luego de completar el mini-formulario de Signup.
 */
public class RegisterPage extends BasePage {

    @FindBy(id = "id_gender1")
    private WebElement tituloMr;

    @FindBy(id = "id_gender2")
    private WebElement tituloMrs;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "days")
    private WebElement dia;

    @FindBy(id = "months")
    private WebElement mes;

    @FindBy(id = "years")
    private WebElement anio;

    @FindBy(id = "first_name")
    private WebElement nombre;

    @FindBy(id = "last_name")
    private WebElement apellido;

    @FindBy(id = "address1")
    private WebElement direccion;

    @FindBy(id = "country")
    private WebElement pais;

    @FindBy(id = "state")
    private WebElement estado;

    @FindBy(id = "city")
    private WebElement ciudad;

    @FindBy(id = "zipcode")
    private WebElement codigoPostal;

    @FindBy(id = "mobile_number")
    private WebElement telefono;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement botonCreateAccount;

    @FindBy(css = "a[data-qa='continue-button']")
    private WebElement botonContinue;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Completa los campos obligatorios del formulario a partir del DataTable del feature.
     * Claves esperadas: Titulo, Contrasena, DiaNacimiento, MesNacimiento, AnioNacimiento,
     * Nombre, Apellido, Direccion, Pais, Estado, Ciudad, CodigoPostal, Teléfono.
     */
    public void completarFormularioRegistro(Map<String, String> datos) {
        seleccionarTitulo(datos.get("Titulo"));
        escribirSiPresente(password, datos.get("Contrasena"));
        seleccionarPorTextoSiPresente(dia, datos.get("DiaNacimiento"));
        seleccionarPorTextoSiPresente(mes, datos.get("MesNacimiento"));
        seleccionarPorTextoSiPresente(anio, datos.get("AnioNacimiento"));
        escribirSiPresente(nombre, datos.get("Nombre"));
        escribirSiPresente(apellido, datos.get("Apellido"));
        escribirSiPresente(direccion, datos.get("Direccion"));
        seleccionarPorTextoSiPresente(pais, datos.get("Pais"));
        escribirSiPresente(estado, datos.get("Estado"));
        escribirSiPresente(ciudad, datos.get("Ciudad"));
        escribirSiPresente(codigoPostal, datos.get("CodigoPostal"));
        escribirSiPresente(telefono, datos.get("Teléfono"));
    }

    private void seleccionarTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return;
        }
        if (titulo.trim().equalsIgnoreCase("Mr.")) {
            hacerClick(tituloMr);
        } else if (titulo.trim().equalsIgnoreCase("Mrs.")) {
            hacerClick(tituloMrs);
        }
    }

    private void escribirSiPresente(WebElement campo, String valor) {
        if (valor == null || valor.isEmpty()) {
            return;
        }
        escribirTextoRobusto(campo, valor);
    }

    /**
     * Igual que escribirTexto de BasePage, pero verifica que el valor haya quedado
     * realmente escrito y, si no, lo setea por JS disparando los eventos input/change.
     * Ver el mismo mecanismo en LoginPage.escribirTextoRobusto para más contexto.
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

    private void seleccionarPorTextoSiPresente(WebElement select, String valor) {
        if (valor == null || valor.isEmpty()) {
            return;
        }
        seleccionarPorTexto(select, valor);
    }

    public void clicCreateAccount() {
        hacerClick(botonCreateAccount);
    }

    public void clicContinue() {

        hacerClick(botonContinue);

    }

    /**
     * Indica si ya estamos en la página "ENTER ACCOUNT INFORMATION" (o sea, el signup
     * avanzó de página en vez de quedarse en /login). Usa una espera corta y no lanza
     * excepción si no la encuentra — pensado para chequear sin frenar el escenario.
     */
    public boolean estaEnPaginaDeCuenta() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOf(tituloMr));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}