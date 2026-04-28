package net.salesianos.kahorillo.server.emitter;

import java.io.DataOutputStream;

public class ServerEmitter {

    DataOutputStream dataOutputStream;

    public ServerEmitter(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void write(String string) {
        try {
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("Error al enviar datos: " + e.getMessage());
        }
    }

    public void write(String[] strings) {
        try {
            dataOutputStream.writeInt(strings.length);
            for (String s : strings) {
                dataOutputStream.writeUTF(s);
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
