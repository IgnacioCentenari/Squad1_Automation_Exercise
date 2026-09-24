package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverManager {

    private static WebDriver driver;
    public static final String BASE_URL = "https://automationexercise.com";

    private DriverManager() {
    }

    public static void startDriver() {
        if (driver != null) {
            return;
        }

        String browser = System.getProperty("browser", "chrome").toLowerCase();
        // Detecta si viene la propiedad System o si está corriendo en GitHub Actions / CI
        boolean isCI = System.getenv("CI") != null;
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false")) || isCI;

        switch (browser) {
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                } else {
                    options.addArguments("--start-maximized");
                }
                options.addArguments("--remote-allow-origins=*");
                driver = new EdgeDriver(options);
            }
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();

                // Flags requeridos para Linux / GitHub Actions
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                } else {
                    options.addArguments("--start-maximized");
                }

                // Preferencias de autocompletado y contraseñas
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("autofill.profile_enabled", false);
                prefs.put("autofill.address_enabled", false);
                prefs.put("autofill.credit_card_enabled", false);
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");

                driver = new ChromeDriver(options);
            }
            default -> throw new IllegalArgumentException("Browser no soportado: " + browser);
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            startDriver();
        }
        return driver;
    }

    public static void openBaseUrl() {
        getDriver().get(BASE_URL);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}