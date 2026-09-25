package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactoPage extends CommonPage {

    @FindBy(name = "name")
    private WebElement inputNombre;

    @FindBy(name = "email")
    private WebElement inputEmail;

    @FindBy(name = "subject")
    private WebElement inputAsunto;

    @FindBy(id = "message")
    private WebElement inputMensaje;

    @FindBy(name = "submit")
    private WebElement botonSubmit;

    // Selector CSS correcto para capturar el bloque de éxito
    @FindBy(css = "div.status.alert-success")
    private WebElement mensajeExitoDiv;

    @FindBy(xpath = "//a[contains(@class,'btn-success') and contains(.,'Home')]")
    private WebElement botonHomeExito;

    public ContactoPage(WebDriver driver) {
        super(driver);
    }

    public void completarFormulario(String nombre, String email, String asunto, String mensaje) {
        wait.until(ExpectedConditions.visibilityOf(inputNombre)).clear();
        if (nombre != null && !nombre.isEmpty() && !"[empty]".equalsIgnoreCase(nombre)) {
            inputNombre.sendKeys(nombre);
        }

        inputEmail.clear();
        if (email != null && !email.isEmpty() && !"[empty]".equalsIgnoreCase(email)) {
            inputEmail.sendKeys(email);
        }

        inputAsunto.clear();
        if (asunto != null && !asunto.isEmpty() && !"[empty]".equalsIgnoreCase(asunto)) {
            inputAsunto.sendKeys(asunto);
        }

        inputMensaje.clear();
        if (mensaje != null && !mensaje.isEmpty() && !"[empty]".equalsIgnoreCase(mensaje)) {
            inputMensaje.sendKeys(mensaje);
        }
    }

    public void hacerClicEnSubmit() {
        hacerClick(botonSubmit);
    }

    public void aceptarAlertaJS() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public String obtenerTextoMensajeExito() {
        // CORREGIDO: Apunta a mensajeExitoDiv (el que usa CSS Selector)
        return wait.until(ExpectedConditions.visibilityOf(mensajeExitoDiv)).getText().trim();
    }

    public void hacerClicEnBotonHomeExito() {
        hacerClick(botonHomeExito);
    }

    public String obtenerMensajeValidacionEmail() {
        return inputEmail.getAttribute("validationMessage");
    }
}