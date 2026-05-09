package net.salesianos.kahorillo.server.emitter;

import java.io.DataOutputStream;

import net.salesianos.kahorillo.utils.SecureManager;

public class ServerEmitter {

    DataOutputStream dataOutputStream;
    SecureManager secureManager;

    public ServerEmitter(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.secureManager = new SecureManager();
    }

    public void write(String string) {
        try {
            String encrypted = secureManager.encriptToBase64(string);
            dataOutputStream.writeUTF(encrypted);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("Error al enviar datos: " + e.getMessage());
        }
    }

    public void write(String[] strings) {
        try {
            dataOutputStream.writeInt(strings.length);
            for (String s : strings) {
                String encrypted = secureManager.encriptToBase64(s);
                dataOutputStream.writeUTF(encrypted);
            }
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("Error al enviar datos: " + e.getMessage());
        }
    }

    public void writeInt(int num) {
        try {
            dataOutputStream.writeInt(num);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("Error al enviar entero: " + e.getMessage());
        }
    }
}
