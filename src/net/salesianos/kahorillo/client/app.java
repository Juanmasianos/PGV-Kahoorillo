package net.salesianos.kahorillo.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import net.salesianos.kahorillo.client.emitter.ClientEmitter;
import net.salesianos.kahorillo.client.listener.ServerListener;
import net.salesianos.kahorillo.client.players.Leader;
import net.salesianos.kahorillo.client.players.Player;

public class app {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("Intentando conectar...");

    try {
      Socket socket = new Socket("localhost", 6969);

      OutputStream outputStream = socket.getOutputStream();
      InputStream inputStream = socket.getInputStream();

      ClientEmitter clientEmitter = new ClientEmitter(new DataOutputStream(outputStream), scanner);
      ServerListener serverListener = new ServerListener(new DataInputStream(inputStream));

      System.out.println("Introduce tu nombre de usuario: ");
      String username = scanner.nextLine();

      clientEmitter.write(username);

      String playerType = serverListener.read().trim();

      if (playerType.equals("leader")) {
        System.out.println("Bienvenido " + username + ", como has sido el mas rapido, seras el lider. \nPrepara tus preguntas para el juego.");
        Leader leader = new Leader(serverListener, clientEmitter, scanner);
        leader.createQuestions();
        leader.startGame();

      } else if (playerType.equals("player")) {
        Player player = new Player(serverListener, clientEmitter, scanner);
        System.out.println("Bienvenido " + username + ". Espera a que el lider inicie el juego...");
        String startMessage = serverListener.read();
        if (!startMessage.equals("empezando la ronda de preguntas")) {
          System.out.println("Error: servidor no inició el juego correctamente.");
        } else {
          System.out.println("¡El juego ha comenzado! Prepárate para responder las preguntas.");
          player.playGame();
        }

      } else {
        System.out.println("Saliendo del juego...");
      }

      socket.close();
      
    } catch (UnknownHostException e) {
      System.out.println("Host desconocido, imposible acceder. \n" + e);
    } catch (IOException e) {
      System.out.println("Error en la conexion al servidor. \n" + e);
    } finally {
      try {
        scanner.close();
      } catch (Exception e) {
        System.out.println("Error al cerrar el scanner: " + e.getMessage());
      }
    }
  }
}
