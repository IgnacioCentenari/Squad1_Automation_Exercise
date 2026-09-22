package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class Hooks {

    @Before
    public void setUp() {
        DriverManager.getDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // Método estático para exponer el driver que pide CarritoSteps
    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}