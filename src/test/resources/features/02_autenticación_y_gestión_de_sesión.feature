# language: es
Característica: Autenticación y Gestión de Sesión
  Como usuario del sitio
  Quiero poder iniciar y cerrar sesión
  Para proteger mi cuenta y acceder a secciones privadas

  Antecedentes:
    Cuando el usuario navega a la página "/login"

  @TC-06 @Autenticacion @Negativo
  Escenario: Inicio de sesión con usuario inexistente
    Cuando inicio sesión con email "noexiste2026@test.com" y contraseña "Testing123"
    Entonces el sistema debe mostrar el mensaje "Your email or password is incorrect!"

  @TC-07 @Autenticacion @Negativo
  Escenario: Inicio de sesión con contraseña incorrecta
    Cuando inicio sesión con email "nachoqa2026@test.com" y contraseña "claveErronea99"
    Entonces el sistema debe mostrar el mensaje "Your email or password is incorrect!"

  @TC-08 @Autenticacion @Negativo
  Escenario: Inicio de sesión con email y contraseña erróneos
    Cuando inicio sesión con email "usuarioinventado@test.com" y contraseña "claveInventada"
    Entonces el sistema debe mostrar el mensaje "Your email or password is incorrect!"

  @TC-09 @Autenticacion @Smoke @Positivo
  Escenario: Inicio de sesión con credenciales válidas
    Cuando inicio sesión con email "nachoqa2026@test.com" y contraseña "userQA"
    Entonces el sistema debe mostrar el mensaje "Logged in as Nacho"

  @TC-10 @Autenticacion @Negativo @ValidacionCampos
  Escenario: Intento de login dejando el campo email vacío
    Cuando ingreso "" en el campo "Email Address"
    Y ingreso "userQA" en el campo "Password"
    Y hago clic en el boton "Login"
    Entonces el sistema debe solicitar completar el campo obligatorio "Email Address"

  @TC-11 @Autenticacion @Negativo @ValidacionCampos
  Escenario: Intento de login dejando el campo password vacío
    Cuando ingreso "nachoqa2026@test.com" en el campo "Email Address"
    Y ingreso "" en el campo "Password"
    Y hago clic en el boton "Login"
    Entonces el sistema debe solicitar completar el campo obligatorio "Password"

  @TC-12 @Autenticacion @Logout
  Escenario: Cierre de sesión exitoso
    Cuando inicio sesión con email "nachoqa2026@test.com" y contraseña "userQA"
    Y hago clic en el boton "Logout"
    Entonces deberia ver que la URL contiene "/login"

  @TC-13 @Autenticacion @Logout @Persistencia
  Escenario: Verificación de destrucción de sesión tras el logout
    Cuando inicio sesión con email "nachoqa2026@test.com" y contraseña "userQA"
    Y hago clic en el boton "Logout"
    Y el usuario navega a la página "/checkout"
    Entonces deberia ver que la URL contiene "/login"