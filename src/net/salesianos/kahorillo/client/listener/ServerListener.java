package net.salesianos.kahorillo.client.listener;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerListener {

  DataInputStream dataInputStream;

  public ServerListener(DataInputStream dataInputStream) {

    this.dataInputStream = dataInputStream;

  }

  public String playerType() {
    try {
      System.out.println(dataInputStream.readUTF());
      return dataInputStream.readUTF().substring(12);
    } catch (IOException e) {
      System.out.println("Error obteniendo el tipo de jugador");
      return "error";
    }
  }

  public String read() {
    try {
      return dataInputStream.readUTF();
    } catch (IOException e) {
      System.out.println("Error obteniendo datos del servidor.");
      return "error";
    }
  }

}
