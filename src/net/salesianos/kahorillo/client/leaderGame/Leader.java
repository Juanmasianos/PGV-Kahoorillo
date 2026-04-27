package net.salesianos.kahorillo.client.leaderGame;

import java.util.ArrayList;
import java.util.Scanner;

import net.salesianos.kahorillo.client.clientMenu.LeaderMenu;
import net.salesianos.kahorillo.client.emitter.ClientEmitter;
import net.salesianos.kahorillo.client.listener.ServerListener;

public class Leader {

  ServerListener serverListener;
  ClientEmitter clientEmitter;
  Scanner scanner;
  ArrayList<String> questions;
  ArrayList<String> answers;

  public Leader(ServerListener serverListener, ClientEmitter clientEmitter, Scanner scanner) {

    this.serverListener = serverListener;
    this.clientEmitter = clientEmitter;
    this.scanner = scanner;
    this.questions = new ArrayList<>();
    this.answers = new ArrayList<>();

  }

  public void createQuestions() {

    boolean anotherQuestion = true;
    int iterator = 1;

    do {
      System.out.println("Introduce la " + iterator + "ª pregunta: ");
      String question = scanner.nextLine();
      this.questions.add(question);
      LeaderMenu.showQuestionsMenu();
      String option = scanner.nextLine();
      boolean correctOption = true;
      do {
        switch (option) {
          case "1":
            break;
          case "2":
            removeQuestion(scanner);
            break;
          case "3":
            anotherQuestion = false;
            break;
          default:
            System.out.println("Opción no válida");
            correctOption = false;
        }
      } while (!correctOption);
      iterator++;
    } while (anotherQuestion);

  }

  private void removeQuestion(Scanner scanner) {

    System.out.println("Introduce el número de la pregunta a eliminar: ");
    String questionNumber = scanner.nextLine();
    int questionIndex = Integer.parseInt(questionNumber) - 1;
    this.questions.remove(questionIndex);
  }

}
