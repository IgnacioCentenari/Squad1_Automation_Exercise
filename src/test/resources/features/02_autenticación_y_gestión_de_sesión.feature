# language: es
Característica: Autenticación y Gestión de Sesión
  Como usuario del sitio
  Quiero poder iniciar y cerrar sesión
  Para proteger mi cuenta y acceder a secciones privadas

  Antecedentes:
    Dado que el usuario navega a la página "/login"

  @TC-06 @Autenticacion @Negativo
  Escenario: Inicio de sesión con usuario inexistente
    Cuando ingresa "noexiste2026@test.com" en el campo "Email Address Login"
    Y ingresa "Testing123" en el campo "Password"
    Y hace clic en el botón "Login"
    Entonces el sistema debe mostrar el mensaje "Your email or password is incorrect!"

  @TC-07 @Autenticacion @Negativo
  Escenario: Inicio de sesión con contraseña incorrecta
    Cuando ingresa "testqa2026@test.com" en el campo "Email Address Login"
    Y ingresa "claveErronea99" en el campo "Password"
    Y hace clic en el botón "Login"
    Entonces el sistema debe mostrar el mensaje "Your email or password is incorrect!"

  @TC-08 @Autenticacion @Negativo
  Escenario: Inicio de sesión con email y contraseña erróneos
    Cuando ingresa "usuarioinventado@test.com" en el campo "Email Address Login"
    Y ingresa "claveInventada" en el campo "Password"
    Y hace clic en el botón "Login"
    Entonces el sistema debe mostrar el mensaje "Your email or password is incorrect!"

  @TC-09 @Autenticacion @Smoke @Positivo
  Escenario: Inicio de sesión con credenciales válidas
    Cuando ingresa "user@prueba.com" en el campo "Email Address Login"
    Y ingresa "userQA" en el campo "Password"
    Y hace clic en el botón "Login"
    Entonces el sistema debe mostrar el mensaje "Logged in as user"

  @TC-10 @Autenticacion @Negativo @ValidacionCampos
  Escenario: Intento de login dejando el campo email vacío
    Cuando ingresa "" en el campo "Email Address Login"
    Y ingresa "userQA" en el campo "Password"
    Y hace clic en el botón "Login"
    Entonces el sistema debe solicitar completar el campo obligatorio "Email Address Login"

  @TC-11 @Autenticacion @Negativo @ValidacionCampos
  Escenario: Intento de login dejando el campo password vacío
    Cuando ingresa "user@prueba.com" en el campo "Email Address Login"
    Y ingresa "" en el campo "Password"
    Y hace clic en el botón "Login"
    Entonces el sistema debe solicitar completar el campo obligatorio "Password"

  @TC-12 @Autenticacion @Logout
  Escenario: Cierre de sesión exitoso
    Dado que ingresa "user@prueba.com" en el campo "Email Address Login"
    Y ingresa "userQA" en el campo "Password"
    Y hace clic en el botón "Login"
    Cuando hace clic en el botón "Logout"
    Entonces el sistema debe navegar a la página "/login"

  @TC-13 @Autenticacion @Logout @Persistencia
  Escenario: Verificación de destrucción de sesión tras el logout
    Dado que ingresa "user@prueba.com" en el campo "Email Address Login"
    Y ingresa "userQA" en el campo "Password"
    Y hace clic en el botón "Login"
    Y hace clic en el botón "Logout"
    Cuando el usuario navega a la página "/checkout"
    Entonces el sistema debe navegar a la página "/login"