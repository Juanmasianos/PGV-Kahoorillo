package net.salesianos.kahorillo.client.listener;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerListener extends Thread {

  DataInputStream dataInputStream;

  public ServerListener(DataInputStream dataInputStream) {

    this.dataInputStream = dataInputStream;

  }

  public String read() {
    try {
      return dataInputStream.readUTF();
    } catch (IOException e) {
      System.out.println("Error obteniendo datos del servidor.");
      return "error";
    }
  }

  @Override
  public void run() {
    boolean game = true;
    while (game) {
      String message = read();
      System.out.println(message);
      if (message.toLowerCase().equals("se acabo el juego")) {
        game = false;
      }
    }
  }

}
