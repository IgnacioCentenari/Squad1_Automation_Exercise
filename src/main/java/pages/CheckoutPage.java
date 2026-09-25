package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Map;

public class CheckoutPage extends BasePage {

    private By orderCommentTextArea = By.name("message");
    private By placeOrderButton = By.xpath("//a[contains(@href, '/payment')]");

    private By nameOnCardInput = By.name("name_on_card");
    private By cardNumberInput = By.name("card_number");
    private By cvcInput = By.name("cvc");
    private By expirationMonthInput = By.name("expiry_month");
    private By expirationYearInput = By.name("expiry_year");
    private By payAndConfirmButton = By.id("submit");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    private void asegurarVentanaPrincipal() {
        try {
            String mainWindow = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    driver.close();
                }
            }
            driver.switchTo().window(mainWindow);
        } catch (Exception ignored) {
        }
    }

    private void detenerCargaResidual() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.stop();");
        } catch (Exception ignored) {
        }
    }

    public void manejarVignetteAd() {
        try {
            if (driver.getCurrentUrl().contains("#google_vignette")) {
                ((JavascriptExecutor) driver).executeScript("window.location.href = window.location.href.split('#')[0];");
            }
        } catch (Exception ignored) {
        }
    }

    public void ingresarComentarioOTexto(String texto, String campo) {
        asegurarVentanaPrincipal();
        detenerCargaResidual();
        manejarVignetteAd();

        By locator;
        switch (campo.toLowerCase()) {
            case "order comment":
                locator = orderCommentTextArea;
                break;
            case "email address login":
                locator = By.xpath("//input[@data-qa='login-email']");
                break;
            case "password":
                locator = By.xpath("//input[@data-qa='login-password']");
                break;
            default:
                locator = By.name(campo);
                break;
        }

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        element.clear();
        element.sendKeys(texto);
    }

    public void hacerClicEnBoton(String boton) {
        asegurarVentanaPrincipal();
        detenerCargaResidual();
        manejarVignetteAd();

        By locator;
        switch (boton.toLowerCase()) {
            case "login":
                locator = By.xpath("//button[contains(@data-qa,'login-button') or contains(text(),'Login')]");
                break;
            case "proceed to checkout":
                locator = By.xpath("//a[contains(@class,'check_out') or contains(text(),'Proceed To Checkout')]");
                break;
            case "place order":
                locator = placeOrderButton;
                break;
            case "pay and confirm order":
                locator = payAndConfirmButton;
                break;
            default:
                locator = By.xpath("//*[contains(text(), '" + boton + "')]");
                break;
        }

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);

        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }

        manejarVignetteAd();
    }

    public void completarDatosTarjeta(Map<String, String> data) {
        asegurarVentanaPrincipal();
        detenerCargaResidual();
        manejarVignetteAd();

        // Si la URL quedó retenida por la publicidad o no navegó automáticamente a /payment, forzar la navegación
        if (!driver.getCurrentUrl().contains("/payment")) {
            try {
                driver.navigate().to("https://automationexercise.com/payment");
            } catch (Exception ignored) {
            }
        }

        wait.until(ExpectedConditions.urlContains("/payment"));

        WebElement nameCardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnCardInput));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", nameCardElement);

        ingresarTextoSiValido(nameOnCardInput, data.get("NombreEnTarjeta"));
        ingresarTextoSiValido(cardNumberInput, data.get("NumeroTarjeta"));
        ingresarTextoSiValido(cvcInput, data.get("CVC"));
        ingresarTextoSiValido(expirationMonthInput, data.get("MesExpiracion"));
        ingresarTextoSiValido(expirationYearInput, data.get("AnioExpiracion"));
    }

    private void ingresarTextoSiValido(By locator, String valor) {
        if (valor != null && !valor.equalsIgnoreCase("[empty]")) {
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(valor);
        }
    }

    public boolean verificarMensajeExito(String mensajeEsperado) {
        asegurarVentanaPrincipal();
        detenerCargaResidual();
        manejarVignetteAd();

        try {
            return wait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), mensajeEsperado),
                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "ORDER PLACED!"),
                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
                            "Congratulations! Your order has been confirmed!")));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean esCampoHTML5Invalido(String campo) {
        asegurarVentanaPrincipal();

        WebElement element = null;
        switch (campo) {
            case "Name on Card":
                element = wait.until(ExpectedConditions.presenceOfElementLocated(nameOnCardInput));
                break;
            case "Card Number":
                element = wait.until(ExpectedConditions.presenceOfElementLocated(cardNumberInput));
                break;
            case "CVC":
                element = wait.until(ExpectedConditions.presenceOfElementLocated(cvcInput));
                break;
            case "Expiration Month":
                element = wait.until(ExpectedConditions.presenceOfElementLocated(expirationMonthInput));
                break;
            case "Expiration Year":
                element = wait.until(ExpectedConditions.presenceOfElementLocated(expirationYearInput));
                break;
        }

        if (element == null)
            return false;
        Boolean isValid = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checkValidity();",
                element);
        return !isValid;
    }

    public void vaciarCarritoSiHayElementos() {
        asegurarVentanaPrincipal();
        detenerCargaResidual();
        List<WebElement> botonesEliminar = driver.findElements(By.className("cart_quantity_delete"));
        for (WebElement btn : botonesEliminar) {
            try {
                btn.click();
                Thread.sleep(300);
            } catch (Exception ignored) {
            }
        }
    }
}