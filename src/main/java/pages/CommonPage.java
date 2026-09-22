package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CommonPage extends BasePage {

    private static final String BASE_URL = "https://automationexercise.com";

    public CommonPage(WebDriver driver) {
        super(driver);
    }

    public void navegarARutaRelativa(String ruta) {
        String urlCompleta = ruta.startsWith("http") ? ruta : BASE_URL + (ruta.startsWith("/") ? ruta : "/" + ruta);
        super.navegarA(urlCompleta);
    }

    public void ingresarTextoEnCampo(String nombreCampo, String texto) {
        By localizador = By.xpath(String.format(
                "//input[@name='%1$s' or @id='%1$s' or @placeholder='%1$s'] | //textarea[@name='%1$s' or @id='%1$s']",
                nombreCampo
        ));
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(localizador));
        escribirTexto(campo, texto);
    }

    public void hacerClicEnBotonPorTexto(String textoBoton) {
        By localizador = By.xpath(String.format(
                "//button[contains(normalize-space(),'%1$s')] | //a[contains(normalize-space(),'%1$s')] | //input[@type='submit' and @value='%1$s']",
                textoBoton
        ));
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(localizador));
        hacerClick(boton);
    }
}