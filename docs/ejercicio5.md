# Ejercicio 5: Esquema de Seguridad Basado en Roles para Escalabilidad

Si el juego Kahoorillo creciera mucho, habría que implementar un sistema de roles para la seguridad. Aquí va un esquema simple:

- **Usuario (Player)**: Solo juega, envía respuestas, no puede hacer nada más.
- **Líder (Leader)**: Crea preguntas, inicia rondas, pero solo uno por partida.
- **Moderador**: Vigila los juegos, expulsa a los tramposos, revisa historial.
- **Administrador**: Controla todo, configura el servidor, ve logs.

Para la seguridad en general:
- Autenticación con usuario/contraseña encriptada y tokens JWT.
- Comunicación encriptada con TLS.
- Base de datos segura para guardar usuarios y partidas.
- Logs para saber qué hace cada uno.

Esquema de jerarquía:
```
Administrador
├── Moderador
    ├── Líder
        ├── Usuario
```

En el futuro, usar Spring Security o algo así para manejarlo fácil.