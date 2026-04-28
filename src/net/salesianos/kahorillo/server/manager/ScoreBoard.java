package net.salesianos.kahorillo.server.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoard {

    private Map<String, Integer> scores;

    public ScoreBoard() {
        this.scores = new HashMap<>();
    }

    public void recordScore(String username, int score) {
        scores.put(username, score);
        System.out.println("Puntuación registrada - " + username + ": " + score);
    }

    public int getScore(String username) {
        return scores.getOrDefault(username, 0);
    }

    public List<Map.Entry<String, Integer>> getRanking() {
        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(scores.entrySet());
        Collections.sort(ranking, (a, b) -> b.getValue().compareTo(a.getValue()));
        return ranking;
    }

    public void printRanking() {
        System.out.println("\n\nRANKING FINAL \n");
        List<Map.Entry<String, Integer>> ranking = getRanking();
        int position = 1;
        for (Map.Entry<String, Integer> entry : ranking) {
            System.out.println(position + ". " + entry.getKey() + " - " + entry.getValue() + " puntos");
            position++;
        }
        System.out.println("\n");
    }

    public String getRankingString() {
        StringBuilder sb = new StringBuilder("\n\n RANKING FINAL \n");
        List<Map.Entry<String, Integer>> ranking = getRanking();
        int pos = 1;
        for (Map.Entry<String, Integer> entry : ranking) {
            sb.append(pos + ". " + entry.getKey() + " (" + entry.getValue() + " pts)\n");
            pos++;
        }
        return sb.toString();
    }

    public int getPlayerCount() {
        return scores.size();
    }
}
