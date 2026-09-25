package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.junit.jupiter.api.Assertions;
import pages.CheckoutPage;
import utils.DriverManager;

import java.util.Map;

public class CheckoutSteps {

    private CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());

    @Cuando("se ingresa {string} en el campo {string}")
    public void ingresarComentario(String texto, String campo) {
        checkoutPage.ingresarComentarioOTexto(texto, campo);
    }



    @Cuando("completa los datos de la tarjeta con los siguientes valores:")
    public void completarDatosTarjeta(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        checkoutPage.completarDatosTarjeta(data);
    }

    @Entonces("se muestra el mensaje {string}")
    public void verificarMensajeExito(String mensajeEsperado) {
        // En caso de estar validando "Cart is empty!", aseguramos vaciar residuales de pruebas previas
        if ("Cart is empty!".equalsIgnoreCase(mensajeEsperado)) {
            checkoutPage.vaciarCarritoSiHayElementos();
        }

        boolean visible = checkoutPage.verificarMensajeExito(mensajeEsperado);
        Assertions.assertTrue(visible, "El mensaje esperado no fue visible: " + mensajeEsperado);
    }

    @Entonces("la validación debe solicitar completar el campo obligatorio {string}")
    public void validarCampoObligatorioHTML5(String campo) {
        boolean esInvalido = checkoutPage.esCampoHTML5Invalido(campo);
        Assertions.assertTrue(esInvalido,
                "El campo " + campo + " debería ser obligatorio pero fue marcado como válido.");
    }
}