package net.salesianos.kahorillo.server.listener;

import java.io.DataInputStream;

public class ClientListener {

    DataInputStream dataInputStream;

    public ClientListener(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public String read() {
        try {
            return dataInputStream.readUTF();
        } catch (Exception e) {
            System.out.println("Error al leer datos del cliente: " + e.getMessage());
            return "error";
        }
    }

    public int readInt() {
        try {
            return dataInputStream.readInt();
        } catch (Exception e) {
            System.out.println("Error al leer entero del cliente: " + e.getMessage());
            return -1;
        }
    }

    public String[] readArray() {
        try {
            int length = dataInputStream.readInt();
            String[] array = new String[length];
            for (int i = 0; i < length; i++) {
                array[i] = dataInputStream.readUTF();
            }
            return array;
        } catch (Exception e) {
            System.out.println("Error al leer array del cliente: " + e.getMessage());
            return new String[0];
        }
    }
}
