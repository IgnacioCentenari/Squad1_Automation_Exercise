package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.AfterStep;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;


public class Hooks {

    @Before
    public void antesDeCadaEscenario() {
        DriverManager.startDriver();
    }

    @After
    public void despuesDeCadaEscenario() {
        DriverManager.quitDriver();
    }

    // Asegúrate de que el método sea public y static para poder llamarlo desde los Steps
    public static WebDriver getDriver() {
        return DriverManager.getDriver(); // O la lógica que uses para retornar el driver
    }
    @io.cucumber.java.AfterStep


    public void adjuntarEvidenciaSiFalla(io.cucumber.java.Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverManager.getDriver();
            String url = driver.getCurrentUrl();
            String html = driver.getPageSource();

            System.out.println("=== FALLO - URL actual: " + url + " ===");
            System.out.println("=== FALLO - ¿Contiene 'ACCOUNT CREATED'? " + html.contains("ACCOUNT CREATED") + " ===");
            System.out.println("=== FALLO - ¿Contiene 'Account Created' (cualquier caso)? "
                    + html.toLowerCase().contains("account created") + " ===");

            try {
                java.nio.file.Path dir = java.nio.file.Paths.get("target", "evidencia-fallos");
                java.nio.file.Files.createDirectories(dir);
                String nombreBase = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");

                byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                        .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                java.nio.file.Files.write(dir.resolve(nombreBase + ".png"), screenshot);
                java.nio.file.Files.write(dir.resolve(nombreBase + ".html"), html.getBytes(java.nio.charset.StandardCharsets.UTF_8));

                System.out.println("=== Evidencia guardada en: " + dir.toAbsolutePath() + " ===");
            } catch (java.io.IOException e) {
                System.out.println("No se pudo guardar la evidencia: " + e.getMessage());
            }
        }
    }

}
