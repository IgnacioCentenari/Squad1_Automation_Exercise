# language: es
Característica: Gestión del Carrito de Compras
  Como cliente de la plataforma
  Quiero gestionar artículos en el carrito
  Para revisar montos y cantidades antes de comprar

  Antecedentes:
    Dado que el usuario navega a la página "/products"

  @TC-28 @Carrito @Positivo
  Escenario: Agregar un solo producto al carrito y verificar subtotal
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Entonces el carrito debe contener el producto "Blue Top"
    Y el total general debe ser igual al subtotal acumulado

  @TC-29 @Carrito @Positivo @AdicionProductos
  Escenario: Agregar múltiples productos con diferentes cantidades y verificar la suma total
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y agrega "2" unidades del producto "Men Tshirt" al carrito
    Y el usuario navega a la página "/view_cart"
    Entonces el carrito debe contener 2 productos distintos
    Y el total general debe ser igual al subtotal acumulado

  @TC-30 @Carrito @Modificacion @CarritoVacio
  Escenario: Eliminar un producto del carrito, verificar la actualización del total y estado vacío
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y elimina el producto "Blue Top" del carrito
    Entonces el sistema debe mostrar el mensaje "Cart is empty!"

  @TC-31 @Carrito @Persistencia
  Escenario: Verificación de la persistencia del carrito al navegar a otras secciones
    Cuando agrega "1" unidades del producto "Men Tshirt" al carrito
    Y el usuario navega a la página "/contact_us"
    Y el usuario navega a la página "/view_cart"
    Entonces el carrito debe contener el producto "Men Tshirt"