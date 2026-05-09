package net.salesianos.kahorillo.client.listener;

import java.io.DataInputStream;
import java.io.IOException;

import net.salesianos.kahorillo.utils.SecureManager;

public class ServerListener extends Thread {

  DataInputStream dataInputStream;
  SecureManager secureManager;

  public ServerListener(DataInputStream dataInputStream) {

    this.dataInputStream = dataInputStream;
    this.secureManager = new SecureManager();

  }

  public String read() {
    try {
      String encrypted = dataInputStream.readUTF();
      return secureManager.decriptFromBase64(encrypted);
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
