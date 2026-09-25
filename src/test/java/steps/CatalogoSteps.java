package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.junit.jupiter.api.Assertions;
import pages.ProductosPage;

import java.util.List;
import hooks.Hooks;

public class CatalogoSteps {

    //private ProductosPage productosPage = new ProductosPage(DriverManager.getDriver());
    // Declaramos la variable sin instanciarla todavía
    ProductosPage productosPage = new ProductosPage(Hooks.getDriver());

    // Mét odo privado para obtener la instancia de forma segura
//    private ProductosPage getProductosPage() {
//        if (productosPage == null) {
//            productosPage = new ProductosPage(DriverManager.getDriver());
//        }
//        return productosPage;
//    }

    @Entonces("debe visualizarse el listado de productos")
    public void verificarListadoProductosPresente() {
        Assertions.assertTrue(productosPage.esListadoProductosVisible());
    }

    @Entonces("cada producto debe mostrar los siguientes elementos:")
    public void verificarElementosPorProducto(DataTable dataTable) {
        List<String> elementosEsperados = dataTable.asList(String.class);
        Assertions.assertTrue(productosPage.validarElementosProductos(elementosEsperados));
    }

    @Entonces("debe visualizarse el listado con los productos que contienen {string}")
    public void verificarProductosContienenTexto(String textoEsperado) {
        boolean resultado = productosPage.todosLosProductosContienen(textoEsperado);
        Assertions.assertTrue(resultado,
                String.format("Error: Se encontraron productos en la lista que no contienen la palabra '%s'.", textoEsperado));
    }

    @Entonces("el listado de productos debe mostrarse vacío")
    public void verificarListadoVacio() {
        Assertions.assertEquals(0, productosPage.obtenerCantidadProductos(),
                "Se esperaba que el listado estuviera vacío, pero se encontraron productos.");
    }

    @Cuando("selecciona la categoría {string} y subcategoría {string}")
    public void seleccionarCategoriaYSubcategoria(String categoria, String subcategoria) {
        productosPage.seleccionarCategoriaYSubcategoria(categoria, subcategoria);
    }

    @Cuando("selecciona la marca {string}")
    public void seleccionarMarca(String marca) {
        productosPage.seleccionarMarca(marca);
    }

    @Y("hago clic en el botón de búsqueda")
    public void haceClicEnElBotonDeBusquedaDelCatalogo() {
        productosPage.hacerClickBotonBusqueda();
    }

    @Entonces("el sistema debe mostrar el mensaje del catalogo {string}")
    public void elSistemaDebeMostrarElMensaje(String mensajeEsperado) {
        String textoActual = productosPage.obtenerTextoTituloCategoria();

        // Normalizamos: pasamos a minúsculas y reemplazamos cualquier espacio doble o múltiple por uno solo
        String textoActualNormalizado = textoActual.toLowerCase().replaceAll("\\s+", " ");
        String mensajeEsperadoNormalizado = mensajeEsperado.toLowerCase().replaceAll("\\s+", " ");

        Assertions.assertTrue(
                textoActualNormalizado.contains(mensajeEsperadoNormalizado),
                "Se esperaba que el título contuviera '" + mensajeEsperado + "', pero se obtuvo '" + textoActual + "'"
        );
    }
}