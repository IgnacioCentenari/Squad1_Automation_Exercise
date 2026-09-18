# language: es
Característica: Formulario de Contacto
  Como usuario de la plataforma
  Quiero enviar mensajes al equipo de soporte
  Para realizar consultas o enviar comentarios

  Antecedentes:
    Dado que el usuario navega a la página "/contact_us"

  @TC-39 @Contacto @Positivo @PopUpJS
  Escenario: Envío exitoso del formulario de contacto y retorno al inicio
    Cuando ingresa "user" en el campo "Name Contact"
    Y ingresa "user@prueba.com" en el campo "Email Contact"
    Y ingresa "Consulta de soporte QA" en el campo "Subject"
    Y ingresa "Estimados, solicito información sobre el estado del pedido." en el campo "Message"
    Y hace clic en el botón "Submit"
    Y acepta la alerta emergente del navegador
    Entonces el sistema debe mostrar el mensaje "Success! Your details have been submitted successfully."
    Cuando hace clic en el botón "Home"
    Entonces el sistema debe navegar a la página "/"

  @TC-40 @Contacto @Negativo @ValidacionEmail
  Escenario: Formulario de contacto con formato de email "user@"
    Cuando ingresa "user" en el campo "Name Contact"
    Y ingresa "user@" en el campo "Email Contact"
    Y ingresa "Consulta de prueba" en el campo "Subject"
    Y ingresa "Mensaje de prueba." en el campo "Message"
    Y hace clic en el botón "Submit"
    Entonces el sistema debe mostrar el mensaje "Introduce un texto después del signo "@"."

  @TC-41 @Contacto @Negativo @ValidacionEmail
  Escenario: Formulario de contacto con formato de email "@prueba"
    Cuando ingresa "user" en el campo "Name Contact"
    Y ingresa "@prueba" en el campo "Email Contact"
    Y ingresa "Consulta de prueba" en el campo "Subject"
    Y ingresa "Mensaje de prueba." en el campo "Message"
    Y hace clic en el botón "Submit"
    Entonces el sistema debe mostrar el mensaje "Introduce un texto antes del signo "@"."

  @TC-42 @Contacto @Negativo @ValidacionEmail
  Escenario: Formulario de contacto con el campo email vacío
    Cuando ingresa "user" en el campo "Name Contact"
    Y ingresa "" en el campo "Email Contact"
    Y ingresa "Consulta de prueba" en el campo "Subject"
    Y ingresa "Mensaje de prueba." en el campo "Message"
    Y hace clic en el botón "Submit"
    Entonces el sistema debe solicitar completar el campo obligatorio "Email Contact"