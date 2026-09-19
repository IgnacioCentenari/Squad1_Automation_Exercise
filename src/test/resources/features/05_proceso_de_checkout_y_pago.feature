# language: es
Característica: Proceso de Checkout y Pago
  Como un usuario registrado
  Quiero realizar el checkout y pagar con tarjeta
  Para finalizar la orden y obtener la factura

  Antecedentes:
    Dado que el usuario navega a la página "/login"
    Cuando ingresa "user@prueba.com" en el campo "Email Address Login"
    Y ingresa "userQA" en el campo "Password"
    Y hace clic en el botón "Login"

  @TC-32 @Checkout @Pago @Smoke @Positivo
  Escenario: Proceso de checkout y pago con tarjeta de crédito exitoso
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y hace clic en el botón "Proceed To Checkout"
    Y ingresa "Por favor entregar en horario de oficina" en el campo "Order Comment"
    Y hace clic en el botón "Place Order"
    Y completa los datos de la tarjeta con los siguientes valores:
      | NombreEnTarjeta | QA User          |
      | NumeroTarjeta   | 4111111111111111 |
      | CVC             | 311              |
      | MesExpiracion   | 12               |
      | AnioExpiracion  | 2028             |
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe mostrar el mensaje "Your order has been placed successfully!"

  @TC-33 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo NombreEnTarjeta
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y completa los datos de la tarjeta con los siguientes valores:
      | NombreEnTarjeta |                  |
      | NumeroTarjeta   | 4111111111111111 |
      | CVC             | 311              |
      | MesExpiracion   | 12               |
      | AnioExpiracion  | 2028             |
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Name on Card"

  @TC-34 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo NumeroTarjeta
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y completa los datos de la tarjeta con los siguientes valores:
      | NombreEnTarjeta | QA User |
      | NumeroTarjeta   |         |
      | CVC             | 311     |
      | MesExpiracion   | 12      |
      | AnioExpiracion  | 2028    |
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Card Number"

  @TC-35 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo CVC
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y completa los datos de la tarjeta con los siguientes valores:
      | NombreEnTarjeta | QA User          |
      | NumeroTarjeta   | 4111111111111111 |
      | CVC             |                  |
      | MesExpiracion   | 12               |
      | AnioExpiracion  | 2028             |
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "CVC"

  @TC-36 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo MesExpiracion
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y completa los datos de la tarjeta con los siguientes valores:
      | NombreEnTarjeta | QA User          |
      | NumeroTarjeta   | 4111111111111111 |
      | CVC             | 311              |
      | MesExpiracion   |                  |
      | AnioExpiracion  | 2028             |
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Expiration Month"

  @TC-37 @Pago @Negativo @ValidacionCampos
  Escenario: Validación omitiendo el campo AnioExpiracion
    Cuando agrega "1" unidades del producto "Blue Top" al carrito
    Y el usuario navega a la página "/view_cart"
    Y hace clic en el botón "Proceed To Checkout"
    Y hace clic en el botón "Place Order"
    Y completa los datos de la tarjeta con los siguientes valores:
      | NombreEnTarjeta | QA User          |
      | NumeroTarjeta   | 4111111111111111 |
      | CVC             | 311              |
      | MesExpiracion   | 12               |
      | AnioExpiracion  |                  |
    Y hace clic en el botón "Pay and Confirm Order"
    Entonces el sistema debe solicitar completar el campo obligatorio "Expiration Year"

  @TC-38 @Checkout @Negativo @CarritoVacio
  Escenario: Intentar realizar checkout sin productos en el carrito
    Cuando el usuario navega a la página "/view_cart"
    Entonces el sistema debe mostrar el mensaje "Cart is empty!"