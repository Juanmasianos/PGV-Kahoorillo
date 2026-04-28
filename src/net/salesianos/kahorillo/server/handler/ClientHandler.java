package net.salesianos.kahorillo.server.handler;

import java.net.Socket;

import net.salesianos.kahorillo.server.manager.GameManager;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        
    }
}
