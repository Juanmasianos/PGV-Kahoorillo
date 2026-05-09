package net.salesianos.kahorillo.server.listener;

import java.io.DataInputStream;
import java.io.IOException;

import net.salesianos.kahorillo.utils.SecureManager;

public class ClientListener {

    DataInputStream dataInputStream;
    SecureManager secureManager;

    public ClientListener(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
        this.secureManager = new SecureManager();
    }

    public String read() throws IOException {
        String encrypted = dataInputStream.readUTF();
        return secureManager.decriptFromBase64(encrypted);
    }

    public int readInt() throws IOException {
        return dataInputStream.readInt();
    }

    public String[] readArray() throws IOException {
        int length = dataInputStream.readInt();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String encrypted = dataInputStream.readUTF();
            array[i] = secureManager.decriptFromBase64(encrypted);
        }
        return array;

    }
}
