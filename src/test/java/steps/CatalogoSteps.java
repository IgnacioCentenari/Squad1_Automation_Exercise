package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import java.util.List;

public class CatalogoSteps {

    // private CatalogPage catalogPage = new CatalogPage();

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
}