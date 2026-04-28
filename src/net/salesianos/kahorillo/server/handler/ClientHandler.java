package net.salesianos.kahorillo.server.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import net.salesianos.kahorillo.server.emitter.ServerEmitter;
import net.salesianos.kahorillo.server.listener.ClientListener;
import net.salesianos.kahorillo.server.manager.GameManager;

public class ClientHandler extends Thread {

    private Socket clientSocket;
    private ServerEmitter serverEmitter;
    private ClientListener clientListener;
    private GameManager gameManager;
    private String username;
    private String clientType;
    private boolean alreadyRemoved = false;

    public ClientHandler(Socket clientSocket, GameManager gameManager) {
        this.clientSocket = clientSocket;
        this.gameManager = gameManager;

        try {
            OutputStream outputStream = this.clientSocket.getOutputStream();
            InputStream inputStream = this.clientSocket.getInputStream();
            this.serverEmitter = new ServerEmitter(new DataOutputStream(outputStream));
            this.clientListener = new ClientListener(new DataInputStream(inputStream));
        } catch (IOException e) {
            System.out.println("Error al crear streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            this.username = clientListener.read();

            System.out.println("Usuario conectado: " + username);

            this.clientType = gameManager.assignType();

            serverEmitter.write(clientType);

            if (clientType.equals("leader")) {
                handleLeader();
            } else {
                handlePlayer();
            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar socket: " + e.getMessage());
        }
        handleDisconnection();
    }

    private void handleLeader() throws IOException {
        try {
            System.out.println("Líder " + username + " conectado");
            gameManager.setLeader(this);

            String[] questions = clientListener.readArray();
            String[] answers = clientListener.readArray();

            gameManager.setQuestions(questions, answers);

            serverEmitter.write("preguntas recibidas");

            System.out.println("Preguntas recibidas del líder: " + questions.length);

            gameManager.waitForPlayers();

            Thread.sleep(200);

            serverEmitter.write("jugadores listos");

            System.out.println("Esperando señal de inicio ('start')...");
            String signal = "";

            while (true) {
                signal = clientListener.read(); 
                if (signal != null) {
                    signal = signal.trim().toLowerCase(); 
                    if (signal.trim().equals("start")) {
                        System.out.println("¡Señal 'start' recibida correctamente!");
                        gameManager.startGame();
                        break;
                    } else if (!signal.isEmpty()) {
                        System.out.println("Basura ignorada en el buffer: [" + signal + "]");
                    } else {
                        System.out.println("Cadena vacía recibida, esperando 'start'...");
                    }
                }
            }

            while (gameManager.isGameRunning()) {
                Thread.sleep(1000);
            }

            gameManager.endGame();

            serverEmitter.write(gameManager.getScoreBoard().getRankingString());

        } catch (InterruptedException e) {
            System.out.println("Error en manejo de líder: " + e.getMessage());
            gameManager.removeLeader();
            alreadyRemoved = true;
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO LÍDER: " + e.getMessage());
            e.printStackTrace();
            gameManager.removeLeader();
            alreadyRemoved = true;
        }
    }

    private void handlePlayer() throws IOException {
        try {
            System.out.println("Jugador " + username + " conectado");
            gameManager.addPlayer(this, username);

            gameManager.waitForGameStart();
            System.out.println("Enviando preguntas a: " + username);

            String[] questions = gameManager.getQuestions();
            serverEmitter.write("empezando la ronda de preguntas");

            String[] playerAnswers = new String[questions.length];

            for (int i = 0; i < questions.length; i++) {
                serverEmitter.write("PREGUNTA " + (i + 1) + ": " + questions[i]);
                clientSocket.setSoTimeout(15000);
                try {
                    playerAnswers[i] = clientListener.read();
                    System.out.println("Respuesta de " + username + ": " + playerAnswers[i]);
                } catch (SocketTimeoutException e) {
                    playerAnswers[i] = "SIN RESPUESTA";
                } finally {
                    clientSocket.setSoTimeout(0);
                }
            }

            int score = gameManager.recordPlayerAnswers(username, playerAnswers);

            serverEmitter.write("score:" + score);

            System.out.println("Jugador " + username + " terminó con puntuación: " + score);

            serverEmitter.write("se acabo el juego");

            if (gameManager.playerFinished()) {
                gameManager.endGame();
            }

            while (gameManager.isGameRunning()) {
                Thread.sleep(100);
            }

            System.out.println("Cerrando conexión con jugador: " + username);

        } catch (InterruptedException | SocketException e) {
            System.out.println("Error en manejo de jugador " + username + ": " + e.getMessage());
            gameManager.removePlayer(username);
            alreadyRemoved = true;
        }
    }

    public void sendMessage(String message) throws IOException {
        serverEmitter.write(message);
    }

    public String getUsername() {
        return username;
    }

    public String getClientType() {
        return clientType;
    }

    private void handleDisconnection() {
        if (!alreadyRemoved && username != null && clientType != null) {
            if (clientType.equals("leader")) {
                gameManager.removeLeader();
            } else {
                gameManager.removePlayer(username);
            }
            System.out.println("Cliente " + username + " ha sido removido del servidor");
        }
    }
}
