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
import net.salesianos.kahorillo.client.leaderGame.Leader;
import net.salesianos.kahorillo.client.listener.ServerListener;

public class app {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("Intentando conectar...");

    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    try {
      socket = new Socket("localhost", 6969);

      OutputStream outputStream = socket.getOutputStream();

      InputStream inputStream = socket.getInputStream();

      ClientEmitter clientEmitter = new ClientEmitter(new DataOutputStream(outputStream), scanner);

      ServerListener serverListener = new ServerListener(new DataInputStream(inputStream));

      boolean game = true;
      while (game) {

        if (serverListener.playerType() == "leader") {

          Leader leader = new Leader(serverListener, clientEmitter);
          leader.createQuestions();

        } else if (serverListener.playerType() == "error") {
          game = false;
          System.out.println("Saliendo del juego...");
        } else {



        }

      }

    } catch (UnknownHostException e) {
      System.out.println("Host desconocido, imposible acceder. \n" + e);
    } catch (IOException e) {
      System.out.println("Error en la conexion al servidor. \n" + e);
    }
  }
}
