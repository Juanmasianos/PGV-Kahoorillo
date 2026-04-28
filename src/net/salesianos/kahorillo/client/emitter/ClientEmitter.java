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

  public void write(String string) {
    try {
      dataOutputStream.writeUTF(string);
    } catch (Exception e) {
      System.out.println("Error al enviar datos: " + e.getMessage());
      return;
    }
  }

    public void write(String[] strings) {
    try {
        dataOutputStream.writeInt(strings.length);
        for (String s : strings) {
            dataOutputStream.writeUTF(s);
        }
    } catch (Exception e) {
      System.out.println("Error al enviar datos: " + e.getMessage());
      return;
    }
  }
}
