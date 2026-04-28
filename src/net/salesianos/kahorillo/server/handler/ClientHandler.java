package net.salesianos.kahorillo.server.handler;

import java.io.DataInputStream;
import java.net.Socket;

import net.salesianos.kahorillo.server.manager.GameManager;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private String username;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.username = new DataInputStream(clientSocket.getInputStream()).readUTF();
        } catch (Exception e) {
            System.out.println("Error al leer nombre de usuario: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        
    }

    public String getUsername() {
        return username;
    }
}
