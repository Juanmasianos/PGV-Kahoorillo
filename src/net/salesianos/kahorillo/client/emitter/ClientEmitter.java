package net.salesianos.kahorillo.client.emitter;

import java.io.DataOutputStream;
import java.util.Scanner;

public class ClientEmitter {

  DataOutputStream dataOutputStream;
  Scanner scanner;

  public ClientEmitter(DataOutputStream dataOutputStream, Scanner scanner) {

    this.dataOutputStream = dataOutputStream;
    this.scanner = scanner;

  }

  public void run() {
    try {
      System.out.println("Introduce tu nombre de usuario: ");
      String username = scanner.nextLine();

      dataOutputStream.writeUTF(username);

    } catch (Exception e) {
      System.out.println("Error al recibir datos: " + e.getMessage());
      return;
    }
  }

}
