package steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;

public class AuthSteps {

    private WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    @Cuando("inicio sesión con email {string} y contraseña {string}")
    public void inicioSesion(String email, String password) {

        WebDriver driver = getDriver();
        WebDriverWait wait = getWait();

        WebElement campoEmail = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[data-qa='login-email']")
                )
        );

        WebElement campoPassword = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[data-qa='login-password']")
                )
        );

        WebElement botonLogin = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button[data-qa='login-button']")
                )
        );

        campoEmail.clear();
        campoEmail.sendKeys(email);

        campoPassword.clear();
        campoPassword.sendKeys(password);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                botonLogin
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                botonLogin
        );
    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void elSistemaDebeMostrarElMensaje(String mensajeEsperado) {

        WebDriverWait wait = getWait();

        if (mensajeEsperado.equals("Logged in as Nacho")) {

            By localizador = By.xpath(
                    "//a[.//b[normalize-space()='Nacho']]"
            );

            WebElement elemento = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(localizador)
            );

            String textoActual = elemento.getText().trim();

            Assertions.assertTrue(
                    textoActual.contains("Logged in as")
                            && textoActual.contains("Nacho"),
                    "El mensaje encontrado no coincide. Texto actual: ["
                            + textoActual + "]"
            );

        } else {

            By localizador = By.xpath(
                    "//*[contains(normalize-space(.),\""
                            + mensajeEsperado
                            + "\")]"
            );

            WebElement elemento = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(localizador)
            );

            Assertions.assertTrue(
                    elemento.getText().contains(mensajeEsperado),
                    "No se encontró el mensaje esperado: ["
                            + mensajeEsperado + "]"
            );
        }
    }

    @Entonces("el sistema debe solicitar completar el campo obligatorio {string}")
    public void elSistemaDebeSolicitarCompletarElCampoObligatorio(String nombreCampo) {

        By localizador;

        switch (nombreCampo) {

            case "Email Address":
                localizador = By.cssSelector(
                        "input[data-qa='login-email']"
                );
                break;

            case "Password":
                localizador = By.cssSelector(
                        "input[data-qa='login-password']"
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Campo de login no soportado: " + nombreCampo
                );
        }

        WebElement campo = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(localizador)
        );

        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        Boolean campoValido = (Boolean) js.executeScript(
                "return arguments[0].validity.valid;",
                campo
        );

        String mensajeValidacion = (String) js.executeScript(
                "return arguments[0].validationMessage;",
                campo
        );

        Assertions.assertFalse(
                campoValido,
                "El campo [" + nombreCampo + "] debería ser inválido."
        );

        Assertions.assertFalse(
                mensajeValidacion == null || mensajeValidacion.isBlank(),
                "El navegador no mostró una validación para el campo ["
                        + nombreCampo + "]."
        );
    }
}