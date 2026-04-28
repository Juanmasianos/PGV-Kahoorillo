package net.salesianos.kahorillo.client.players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void playGame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean game = true;
        while (game) {
            try {
                String question = serverListener.read();

                if (question.equals("se acabo el juego")) {
                    game = false;
                    return;
                }

                System.out.println("\n--- PREGUNTA ---");
                System.out.println(question);
                System.out.print("Tu respuesta (tienes 15s): ");

                long startTime = System.currentTimeMillis();
                String answer = "";
                boolean answered = false;

                while ((System.currentTimeMillis() - startTime) < 15000 && !answered) {
                    if (reader.ready()) {
                        answer = reader.readLine();
                        answered = true;
                    }
                    Thread.sleep(100);
                }

                if (answered) {
                    clientEmitter.write(answer);
                } else {
                    System.out.println("\n¡TIEMPO AGOTADO!");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error en el juego: " + e.getMessage());
                game = false;
            }
        }
    }
}
