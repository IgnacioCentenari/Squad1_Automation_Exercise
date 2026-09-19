# language: es
Característica: Formulario de Contacto
  Como usuario de la plataforma
  Quiero enviar mensajes al equipo de soporte
  Para realizar consultas o enviar comentarios

  Antecedentes:
    Dado que el usuario navega a la página "/contact_us"

  @TC-39 @Contacto @Positivo @PopUpJS
  Escenario: Envío exitoso del formulario de contacto y retorno al inicio
    Cuando completa el formulario de contacto con los siguientes datos:
      | Name    | user                                               |
      | Email   | user@prueba.com                                    |
      | Subject | Consulta de soporte QA                             |
      | Message | Estimados, solicito información sobre el estado... |
    Y hace clic en el botón "Submit"
    Y acepta la alerta emergente del navegador
    Entonces el sistema debe mostrar el mensaje "Success! Your details have been submitted successfully."
    Cuando hace clic en el botón "Home"
    Entonces el sistema debe navegar a la página "/"

  @TC-40 @Contacto @Negativo @ValidacionEmail
  Escenario: Formulario de contacto con formato de email incompleto (sin dominio)
    Cuando completa el formulario de contacto con los siguientes datos:
      | Name    | user               |
      | Email   | user@              |
      | Subject | Consulta de prueba |
      | Message | Mensaje de prueba. |
    Y hace clic en el botón "Submit"
    Entonces el sistema debe mostrar el mensaje "Introduce un texto después del signo @."

  @TC-41 @Contacto @Negativo @ValidacionEmail
  Escenario: Formulario de contacto con formato de email incompleto (sin usuario)
    Cuando completa el formulario de contacto con los siguientes datos:
      | Name    | user               |
      | Email   | @prueba            |
      | Subject | Consulta de prueba |
      | Message | Mensaje de prueba. |
    Y hace clic en el botón "Submit"
    Entonces el sistema debe mostrar el mensaje "Introduce un texto antes del signo @."

  @TC-42 @Contacto @Negativo @ValidacionEmail
  Escenario: Formulario de contacto con el campo email vacío
    Cuando completa el formulario de contacto con los siguientes datos:
      | Name    | user               |
      | Email   |                    |
      | Subject | Consulta de prueba |
      | Message | Mensaje de prueba. |
    Y hace clic en el botón "Submit"
    Entonces el sistema debe solicitar completar el campo obligatorio "Email Contact"