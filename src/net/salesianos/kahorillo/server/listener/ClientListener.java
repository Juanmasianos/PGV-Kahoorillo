package net.salesianos.kahorillo.server.listener;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientListener {

    DataInputStream dataInputStream;

    public ClientListener(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public String read() throws IOException {
        return dataInputStream.readUTF();
    }

    public int readInt() throws IOException {
        return dataInputStream.readInt();
    }

    public String[] readArray() throws IOException {
        int length = dataInputStream.readInt();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            array[i] = dataInputStream.readUTF();
        }
        return array;

    }
}
