package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import java.util.Map;

public class CheckoutSteps {

    // private PaymentPage paymentPage = new PaymentPage();

    @Cuando("completa los datos de la tarjeta con los siguientes valores:")
    public void completaLosDatosDeLaTarjeta(DataTable dataTable) {
        Map<String, String> datosTarjeta = dataTable.asMap(String.class, String.class);

        String nombre = datosTarjeta.getOrDefault("NombreEnTarjeta", "");
        String numero = datosTarjeta.getOrDefault("NumeroTarjeta", "");
        String cvc    = datosTarjeta.getOrDefault("CVC", "");
        String mes    = datosTarjeta.getOrDefault("MesExpiracion", "");
        String anio   = datosTarjeta.getOrDefault("AnioExpiracion", "");

        // paymentPage.completarFormularioPago(nombre, numero, cvc, mes, anio);
    }
}