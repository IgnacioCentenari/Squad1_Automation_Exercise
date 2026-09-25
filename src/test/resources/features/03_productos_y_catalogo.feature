# language: es
Característica: Catálogo, Búsqueda y Filtros
  Como usuario de la plataforma
  Quiero explorar el catálogo
  Para visualizar y filtrar productos

  Antecedentes:
    Cuando el usuario navega a la página "/products"

  @TC-14 @Catalogo @Positivo
  Escenario: Visualización correcta de la información en el catálogo de productos
    Entonces debe visualizarse el listado de productos
    Y cada producto debe mostrar los siguientes elementos:
      | Nombre |
      | Precio |
      | Imagen |
      | Estado |

  @TC-15 @Catalogo @Busqueda
  Escenario: Búsqueda exitosa de producto coincidencia exacta
    Cuando ingreso "Blue Top" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces debe visualizarse el listado con los productos que contienen "Blue Top"

  @TC-16 @Catalogo @Busqueda
  Escenario: Búsqueda exitosa de producto coincidencia parcial "Top"
    Cuando ingreso "Top" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces debe visualizarse el listado con los productos que contienen "Top"

  @TC-17 @Catalogo @Busqueda
  Escenario: Búsqueda exitosa de producto coincidencia parcial "Jeans"
    Cuando ingreso "Jeans" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces debe visualizarse el listado con los productos que contienen "Jeans"

  @TC-18 @Catalogo @Busqueda
  Escenario: Búsqueda sin distinción de mayúsculas
    Cuando ingreso "BLUE TOP" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces debe visualizarse el listado con los productos que contienen "Blue Top"

  @TC-19 @Catalogo @Busqueda
  Escenario: Búsqueda sin distinción de minúsculas
    Cuando ingreso "blue top" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces debe visualizarse el listado con los productos que contienen "Blue Top"

  @TC-20 @Catalogo @Busqueda
  Escenario: Búsqueda sin distinción de formato mixto
    Cuando ingreso "BlUe ToP" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces debe visualizarse el listado con los productos que contienen "Blue Top"

  @TC-21 @Catalogo @Busqueda @Negativo
  Escenario: Búsqueda de un producto que no existe en el catálogo
    Cuando ingreso "ProductoInexistente12345" en el campo "Search Product"
    Y hago clic en el botón de búsqueda
    Entonces el listado de productos debe mostrarse vacío

  @TC-22 @Catalogo @Filtros
  Escenario: Filtrar productos por categoría Women - Dress
    Cuando selecciona la categoría "Women" y subcategoría "Dress"
    Entonces el sistema debe mostrar el mensaje del catalogo "Women - Dress Products"

  @TC-23 @Catalogo @Filtros
  Escenario: Filtrar productos por categoría Men - Tshirts
    Cuando selecciona la categoría "Men" y subcategoría "Tshirts"
    Entonces el sistema debe mostrar el mensaje del catalogo "Men - Tshirts Products"

  @TC-24 @Catalogo @Filtros
  Escenario: Filtrar productos por categoría Kids - Tops
    Cuando selecciona la categoría "Kids" y subcategoría "Tops & Shirts"
    Entonces el sistema debe mostrar el mensaje del catalogo "KIDS - TOPS & SHIRTS PRODUCTS"

  @TC-25 @Catalogo @Filtros
  Escenario: Filtrar productos por marca Polo
    Cuando selecciona la marca "Polo"
    Entonces el sistema debe mostrar el mensaje del catalogo "Brand - Polo Products"

  @TC-26 @Catalogo @Filtros
  Escenario: Filtrar productos por marca H&M
    Cuando selecciona la marca "H&M"
    Entonces el sistema debe mostrar el mensaje del catalogo "Brand - H&M Products"

  @TC-27 @Catalogo @Filtros
  Escenario: Filtrar productos por marca Madame
    Cuando selecciona la marca "Madame"
    Entonces el sistema debe mostrar el mensaje del catalogo "Brand - Madame Products"