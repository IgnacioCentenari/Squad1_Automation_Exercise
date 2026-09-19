# language: es
Característica: Registro de Usuarios
  Como un nuevo visitante de la plataforma
  Quiero registrarme creando una cuenta
  Para acceder a las funcionalidades completas

  Antecedentes:
    Dado que el usuario navega a la página "/login"

  @TC-01 @Registro @Smoke @Positivo
  Escenario: Registro exitoso con datos válidos y verificación de campos obligatorios
    Cuando ingresa "user" en el campo "Name"
    Y ingresa "user_nuevo@prueba.com" en el campo "Email Address Signup"
    Y hace clic en el botón "Signup"
    Y completa los campos obligatorios del formulario de registro:
      | Titulo         | Mr.             |
      | Contrasena     | userQA          |
      | DiaNacimiento  | 13              |
      | MesNacimiento  | September       |
      | AnioNacimiento | 2021            |
      | Nombre         | user            |
      | Apellido       | Test            |
      | Direccion      | Calle Falsa 123 |
      | Pais           | United States   |
      | Estado         | California      |
      | Ciudad         | Los Angeles     |
      | CodigoPostal   | 90210           |
      | Teléfono       | 1122334455      |
    Y hace clic en el botón "Create Account"
    Entonces el sistema debe mostrar el mensaje "ACCOUNT CREATED!"
    Cuando hace clic en el botón "Continue"
    Entonces el sistema debe mostrar el mensaje "Logged in as user"

  @TC-02 @Registro @Negativo @ValidacionCampos
  Escenario: Validación de e-mail sin arroba
    Cuando ingresa "user" en el campo "Name"
    Y ingresa "user_sin_arroba" en el campo "Email Address Signup"
    Y hace clic en el botón "Signup"
    Entonces el sistema debe mostrar el mensaje "Incluye un símbolo @ en la dirección de correo."

  @TC-03 @Registro @Negativo @ValidacionCampos
  Escenario: Validación de e-mail incompleto tras arroba
    Cuando ingresa "user" en el campo "Name"
    Y ingresa "user@dominio" en el campo "Email Address Signup"
    Y hace clic en el botón "Signup"
    Entonces el sistema debe mostrar el mensaje "Introduce un texto después del signo @."

  @TC-04 @Registro @Negativo @ValidacionCampos
  Escenario: Validación de e-mail sin texto previo a arroba
    Cuando ingresa "user" en el campo "Name"
    Y ingresa "@prueba.com" en el campo "Email Address Signup"
    Y hace clic en el botón "Signup"
    Entonces el sistema debe mostrar el mensaje "Introduce un texto antes del signo @."

  @TC-05 @Registro @Negativo @UsuarioExistente
  Escenario: Intentar registrar un usuario con un e-mail previamente registrado
    Cuando ingresa "user" en el campo "Name"
    Y ingresa "user@prueba.com" en el campo "Email Address Signup"
    Y hace clic en el botón "Signup"
    Entonces el sistema debe mostrar el mensaje "Email Address already exist!"