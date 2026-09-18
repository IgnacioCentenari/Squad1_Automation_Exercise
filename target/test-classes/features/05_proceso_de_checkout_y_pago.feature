# language: es
Característica: Proceso de Checkout y Pago
  Como un usuario registrado
  Quiero realizar el checkout y pagar con tarjeta
  Para finalizar la orden y obtener la factura

  Antecedentes:
    Dado que el usuario navega a la página "/login"
    Y ingresa "user@prueba.com" en el campo "Email Address Login"
    Y ingresa "userQA" en el campo "Password"
    Y hace clic en el botón "Login"

  @TC-32 @Checkout @Pago @Smoke @Positivo
  Escenario: Proceso de checkout y pago con tarjeta de crédito exitoso
    Dado que agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Cuando hace clic en el botón "Proceed To Checkout"
    Y ingresa "Por favor entregar en horario de oficina" en el campo "Order Comment"
    Y hace clic en el botón "Place Order"
    Y ingresa "QA User" en el campo "Name on Card"
    Y ingresa "4111111111111111" en el campo "Card Number"
    Y ingresa "311" en el campo "CVC"
    Y ingresa "12" en el campo "Expiration Month"
    Y ingresa "2028" en el campo "Expiration Year"
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe mostrar el mensaje "Your order has been placed successfully!"

  @TC-33 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo NombreEnTarjeta
    Dado que agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Cuando hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y ingresa "" en el campo "Name on Card"
    Y ingresa "4111111111111111" en el campo "Card Number"
    Y ingresa "311" en el campo "CVC"
    Y ingresa "12" en el campo "Expiration Month"
    Y ingresa "2028" en el campo "Expiration Year"
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Name on Card"

  @TC-34 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo NumeroTarjeta
    Dado que agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Cuando hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y ingresa "QA User" en el campo "Name on Card"
    Y ingresa "" en el campo "Card Number"
    Y ingresa "311" en el campo "CVC"
    Y ingresa "12" en el campo "Expiration Month"
    Y ingresa "2028" en el campo "Expiration Year"
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Card Number"

  @TC-35 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo CVC
    Dado que agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Cuando hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y ingresa "QA User" en el campo "Name on Card"
    Y ingresa "4111111111111111" en el campo "Card Number"
    Y ingresa "" en el campo "CVC"
    Y ingresa "12" en el campo "Expiration Month"
    Y ingresa "2028" en el campo "Expiration Year"
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "CVC"

  @TC-36 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo MesExpiracion
    Dado que agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Cuando hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y ingresa "QA User" en el campo "Name on Card"
    Y ingresa "4111111111111111" en el campo "Card Number"
    Y ingresa "311" en el campo "CVC"
    Y ingresa "" en el campo "Expiration Month"
    Y ingresa "2028" en el campo "Expiration Year"
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Expiration Month"

  @TC-37 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo AnioExpiracion
    Dado que agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Cuando hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y ingresa "QA User" en el campo "Name on Card"
    Y ingresa "4111111111111111" en el campo "Card Number"
    Y ingresa "311" en el campo "CVC"
    Y ingresa "12" en el campo "Expiration Month"
    Y ingresa "" en el campo "Expiration Year"
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Expiration Year"

  @TC-38 @Checkout @Negativo @CarritoVacio
  Escenario: Intentar realizar checkout sin productos en el carrito
    Dado que el usuario navega a la página "/view_cart"
    Entonces el sistema debe mostrar el mensaje "Cart is empty!"