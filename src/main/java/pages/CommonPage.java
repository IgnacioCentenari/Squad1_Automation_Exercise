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

    /**
     * Verifica si un texto aparece en algún lugar del HTML actualmente renderizado
     * en la página (útil para mensajes de confirmación/error que no tienen un
     * localizador fijo, como "ACCOUNT CREATED!" o "Email Address already exist!").
     */
    public boolean laPaginaContieneTexto(String texto) {
        String fuente = normalizarTextoPagina(driver.getPageSource());
        String buscado = normalizarTextoPagina(texto);
        return fuente.contains(buscado);
        }
    public void manejarPosiblePublicidadIntersticial() {
        try {
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
                        .until(d -> !d.getCurrentUrl().contains("google_vignette"));
                return;
            } catch (org.openqa.selenium.TimeoutException ignored) {
                // No se autocerró: intentamos el plan B (navegar directo al home).
            }
            navegarARutaRelativa("/");
        } catch (Exception e) {
            // Best-effort total: si ni el plan B pudo ejecutarse (renderer bloqueado
            // por el propio anuncio), no tumbamos el escenario acá. Dejamos que el
            // assert siguiente falle con su propio mensaje claro, en vez de un
            // stacktrace crudo de WebDriver en un punto que no es el que realmente
            // se está verificando.
        }
    }
    private String normalizarTextoPagina(String texto) {
        return texto
                .replaceAll("<[^>]+>", " ")   // colapsa cualquier tag HTML a un espacio
                .replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .toLowerCase()
                .trim();
    }
        //return driver.getPageSource().contains(texto);

}