# language: es
Característica: Verificación básica de la aplicación

  Escenario: Navegar a la sección de productos
    Dado que me encuentro en la pagina "/"
    Cuando hago clic en el boton "Products"
    Entonces deberia ver que la URL contiene "/products"