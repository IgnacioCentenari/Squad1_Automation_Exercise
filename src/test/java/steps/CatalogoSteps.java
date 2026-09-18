package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import java.util.List;

public class CatalogoSteps {

    // Instancia de tus Page Objects (ej. CatalogPage catalogPage = new CatalogPage();)

    @Dado("que el usuario navega a la página {string}")
    @Cuando("el usuario navega a la página {string}")
    public void navegarAPagina(String ruta) {
        // driver.get(baseUrl + ruta);
    }

    @Cuando("ingresa {string} en el campo {string}")
    public void ingresarTextoEnCampo(String texto, String nombreCampo) {
        // catalogPage.completarCampo(nombreCampo, texto);
    }

    @Cuando("hace clic en el botón {string}")
    public void hacerClicEnBoton(String nombreBoton) {
        // catalogPage.hacerClicEn(nombreBoton);
    }

    @Entonces("debe visualizarse el listado de productos")
    public void verificarListadoProductosPresente() {
        // Assertions.assertTrue(catalogPage.esListadoProductosVisible());
    }

    @Entonces("cada producto debe mostrar los siguientes elementos:")
    public void verificarElementosPorProducto(DataTable dataTable) {
        List<String> elementosEsperados = dataTable.asList(String.class);
        // Assertions.assertTrue(catalogPage.validarElementosProductos(elementosEsperados));
    }

    @Entonces("debe visualizarse el listado con los productos que contienen {string}")
    public void verificarProductosContienenTexto(String textoEsperado) {
        // Assertions.assertTrue(catalogPage.todosLosProductosContienen(textoEsperado));
    }

    @Entonces("el listado de productos debe mostrarse vacío")
    public void verificarListadoVacio() {
        // Assertions.assertEquals(0, catalogPage.obtenerCantidadProductos());
    }

    @Cuando("selecciona la categoría {string} y subcategoría {string}")
    public void seleccionarCategoriaYSubcategoria(String categoria, String subcategoria) {
        // catalogPage.seleccionarCategoriaYSubcategoria(categoria, subcategoria);
    }

    @Cuando("selecciona la marca {string}")
    public void seleccionarMarca(String marca) {
        // catalogPage.seleccionarMarca(marca);
    }

    @Entonces("el sistema debe mostrar el mensaje {string}")
    public void verificarMensajeSistema(String mensajeEsperado) {
        // Assertions.assertEquals(mensajeEsperado, catalogPage.obtenerTituloSeccion());
    }
}