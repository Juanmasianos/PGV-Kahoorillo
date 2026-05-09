# Ejercicio 3: Modificación de Aplicaciones Cliente y Servidor para Encriptación

## Descripción
Se modificaron las clases de emisión y recepción de mensajes en el cliente y servidor para integrar la encriptación/desencriptación automática de todos los datos transmitidos. Esto asegura que la información intercambiada esté protegida durante el tránsito.

## Modificaciones en el Cliente

### ClientEmitter.java
- Se agregó una instancia de `SecureManager` en el constructor.
- El método `write(String string)` ahora encripta el mensaje con `encriptToBase64()` antes de enviarlo.
- El método `write(String[] strings)` encripta cada elemento del array antes de enviarlo.
- Los números (como la longitud del array) no se encriptan, ya que son datos de control.

### ServerListener.java
- Se agregó una instancia de `SecureManager` en el constructor.
- El método `read()` ahora lee el mensaje encriptado y lo desencripta con `decriptFromBase64()` antes de devolverlo.
- El hilo de escucha (`run()`) procesa mensajes desencriptados.

## Modificaciones en el Servidor

### ServerEmitter.java
- Se agregó una instancia de `SecureManager` en el constructor.
- El método `write(String string)` encripta el mensaje antes de enviarlo.
- El método `write(String[] strings)` encripta cada elemento del array.
- El método `writeInt(int num)` no encripta, ya que es un entero.

### ClientListener.java
- Se agregó una instancia de `SecureManager` en el constructor.
- El método `read()` desencripta el mensaje recibido.
- El método `readArray()` desencripta cada elemento del array.
- El método `readInt()` no desencripta, ya que lee un entero.

## Integración
- Todas las clases de comunicación ahora usan la misma clave predeterminada, asegurando compatibilidad.
- La encriptación es transparente para el resto de la aplicación: los métodos `write()` y `read()` manejan la encriptación internamente.
- Se mantiene la compatibilidad con el protocolo existente (DataInputStream/DataOutputStream), pero con datos encriptados.

## Pruebas
- Se compiló el código sin errores.
- La comunicación entre cliente y servidor funciona con encriptación activa.
- Los mensajes se encriptan antes de enviar y se desencriptan al recibir, manteniendo la funcionalidad del juego.