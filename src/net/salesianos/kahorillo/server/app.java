package net.salesianos.kahorillo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import net.salesianos.kahorillo.server.handler.ClientHandler;
import net.salesianos.kahorillo.server.manager.GameManager;

public class app {
  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(6969, 10);
      System.out.println("Servidor iniciado en puerto 6969...");

      GameManager gameManager = new GameManager();
      
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Nueva conexión recibida");
        
        ClientHandler clientHandler = new ClientHandler(socket, gameManager);
        clientHandler.start();
      }

    } catch (IOException e) {
      System.out.println("Error al iniciar el servidor: " + e.getMessage());
    } finally {
      if (serverSocket != null) {
        try {
          serverSocket.close();
        } catch (IOException e) {
          System.out.println("Error al cerrar serverSocket: " + e.getMessage());
        }
      }
    }
  }
}
