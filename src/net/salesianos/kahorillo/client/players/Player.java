package net.salesianos.kahorillo.client.players;

import java.util.Scanner;

import net.salesianos.kahorillo.client.emitter.ClientEmitter;
import net.salesianos.kahorillo.client.listener.ServerListener;

public class Player {

    ServerListener serverListener;
    ClientEmitter clientEmitter;
    Scanner scanner;

    public Player(ServerListener serverListener, ClientEmitter clientEmitter, Scanner scanner) {
        this.serverListener = serverListener;
        this.clientEmitter = clientEmitter;
        this.scanner = scanner;
    }

}
