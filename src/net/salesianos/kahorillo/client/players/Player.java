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

    public Player(ServerListener serverListener, ClientEmitter clientEmitter, Scanner scanner) {

        this.serverListener = serverListener;
        this.clientEmitter = clientEmitter;

    }

    public void playGame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            boolean game = true;
            while (game) {
                try {
                    String question = serverListener.read();

                    if (question.equals("se acabo el juego")) {
                        game = false;
                        break;
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
                        clientEmitter.write("");
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println("Error en el juego: " + e.getMessage());
                    game = false;
                }
            }
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar BufferedReader: " + e.getMessage());
            }
        }
    }
}
