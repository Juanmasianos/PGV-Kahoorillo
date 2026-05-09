package net.salesianos.kahorillo.client.emitter;

import java.io.DataOutputStream;
import java.util.Scanner;

import net.salesianos.kahorillo.utils.SecureManager;

public class ClientEmitter {

  DataOutputStream dataOutputStream;
  Scanner scanner;
  SecureManager secureManager;

  public ClientEmitter(DataOutputStream dataOutputStream, Scanner scanner) {

    this.dataOutputStream = dataOutputStream;
    this.scanner = scanner;
    this.secureManager = new SecureManager();

  }

  public void write(String string) {
    try {
      String encrypted = secureManager.encriptToBase64(string);
      dataOutputStream.writeUTF(encrypted);
      dataOutputStream.flush();
    } catch (Exception e) {
      System.out.println("Error al enviar datos: " + e.getMessage());
      return;
    }
  }

  public void write(String[] strings) {
    try {
      dataOutputStream.writeInt(strings.length);
      for (String s : strings) {
        String encrypted = secureManager.encriptToBase64(s);
        dataOutputStream.writeUTF(encrypted);
        dataOutputStream.flush();
      }
    } catch (Exception e) {
      System.out.println("Error al enviar datos: " + e.getMessage());
      return;
    }
  }
}
