# Ejercicio 2: Creación de Clase de Encriptación y Desencriptación

Para este ejercicio, tuve que crear una clase llamada SecureManager que se encargara de encriptar y desencriptar la información. Usé AES, que es un algoritmo de encriptación estándar, con una clave de 128 bits. La clase tiene un constructor que usa una clave fija por defecto para que tanto el cliente como el servidor puedan usar la misma sin problemas.

Los métodos principales son encript, que toma un texto y lo convierte en bytes encriptados, y decript, que hace lo contrario. También agregué versiones que usan Base64 para poder enviar los datos como strings, ya que el protocolo usa writeUTF. Por ejemplo, encriptToBase64 encripta y codifica en Base64, y decriptFromBase64 decodifica y desencripta.

Al final, la clase maneja todo lo de la encriptación de forma automática, y es fácil de usar en las otras clases. Probé que compilara bien y que no diera errores.