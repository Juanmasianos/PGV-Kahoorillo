# Kahorillo 🎮

Kahorillo es una plataforma de juegos de preguntas y respuestas en tiempo real basada en una arquitectura **Cliente-Servidor**. Utiliza Sockets de Java para permitir que un "Líder" cree una ronda de preguntas y que varios "Jugadores" compitan simultáneamente por la mejor puntuación.

## Características

* **Asignación Automática:** El primer usuario en conectarse es designado como **Líder**.
* **Gestión Dinámica:** El Líder define las preguntas y respuestas antes de iniciar la partida.
* **Multijugador:** Soporta múltiples conexiones simultáneas (mínimo 2 jugadores para iniciar).
* **Sistema de Puntuación:** Validación en tiempo real de respuestas y ranking final al terminar la partida.

## Tecnologías utilizadas

* **Lenguaje:** Java 17+
* **Comunicación:** Java Sockets (TCP/IP)
* **Concurrencia:** Hilos (Threads) para el manejo de múltiples clientes.

## Requisitos

* Tener instalado el **JDK 17** o superior.
* Acceso a una red local o uso de `localhost`.
