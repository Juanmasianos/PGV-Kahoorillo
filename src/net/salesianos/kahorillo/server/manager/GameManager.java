package net.salesianos.kahorillo.server.manager;

import java.util.HashMap;
import java.util.Map;

import net.salesianos.kahorillo.server.handler.ClientHandler;

public class GameManager {

    private String[] questions;
    private String[] answers;
    private ClientHandler leaderHandler;
    private Map<String, ClientHandler> players;
    private ScoreBoard scoreBoard;
    private boolean gameStarted = false;
    private boolean gameRunning = false;
    private int minPlayers = 2;
    private int playersFinished = 0;

    public GameManager() {
        this.players = new HashMap<>();
        this.scoreBoard = new ScoreBoard();
    }

    public String assignType() {
        if (leaderHandler == null) {
            return "leader";
        } else {
            return "player";
        }
    }

    public void setLeader(ClientHandler handler) {
        this.leaderHandler = handler;
        System.out.println("Líder asignado: " + handler.getUsername());
    }

    public void addPlayer(ClientHandler handler, String username) {
        players.put(username, handler);
        System.out.println("Jugador registrado: " + username + " (Total: " + players.size() + ")");
    }

    public void setQuestions(String[] questions, String[] answers) {
        this.questions = questions;
        this.answers = answers;
        System.out.println("Preguntas establecidas: " + questions.length + " preguntas");
    }

    public String[] getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void waitForPlayers() throws InterruptedException {
        while (players.size() < minPlayers) {
            System.out.println("Esperando jugadores... (" + players.size() + "/" + minPlayers + ")");
            Thread.sleep(5000);
        }
        System.out.println("Suficientes jugadores conectados. El líder puede comenzar el juego.");
        return;
    }

    public void startGame() {
        gameRunning = true;
        gameStarted = true;
        System.out.println("¡Juego iniciado!");
    }

    public void waitForGameStart() throws InterruptedException {
        while (!gameStarted) {
            Thread.sleep(500);
        }
    }

    public int recordPlayerAnswers(String username, String[] playerAnswers) {
        if (answers == null || playerAnswers == null) {
            System.out.println("Error: preguntas o respuestas no inicializadas");
            return 0;
        }
        int score = 0;
        for (int i = 0; i < playerAnswers.length && i < answers.length; i++) {
            if (playerAnswers[i] != null && playerAnswers[i].toLowerCase().equals(answers[i].toLowerCase())) {
                score++;
            }
        }
        scoreBoard.recordScore(username, score);
        return score;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void endGame() {
        gameRunning = false;
        scoreBoard.printRanking();
    }

    public int getPlayerCount() {
        return players.size();
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void removePlayer(String username) {
        if (players.containsKey(username)) {
            players.remove(username);
            System.out.println("Jugador " + username + " removido. Jugadores activos: " + players.size());
        }
    }

    public synchronized boolean playerFinished() {
        playersFinished++;
        int registeredPlayers = getPlayerCount() + (leaderHandler != null ? 1 : 0);
        System.out.println("Jugador terminó " + playersFinished + "/" + (registeredPlayers - 1));
        return playersFinished >= (registeredPlayers - 1);
    }

    public void removeLeader() {
        if (leaderHandler != null) {
            System.out.println("Líder desconectado. Juego cancelado.");
            leaderHandler = null;
            gameRunning = false;
            gameStarted = false;
        }
    }
    
}
