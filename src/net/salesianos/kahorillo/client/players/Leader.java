package net.salesianos.kahorillo.client.players;

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
    this.questions = new ArrayList<String>();
    this.answers = new ArrayList<String>();

  }

  public void createQuestions() {

    boolean anotherQuestion = true;
    int iterator = 1;

    do {
      addQuestion(iterator);
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

    clientEmitter.write(questions.toArray(new String[0]));
    clientEmitter.write(answers.toArray(new String[0]));

  }

  public void startGame() {
    while (!serverListener.read().equals("preguntas recibidas")) {
    }
    System.out.println("Preguntas enviadas correctamente, esperando a los jugadores...");
    while (!serverListener.read().equals("jugadores listos")) {
    }
    System.out.println("Todos los jugadores están listos, comienza el juego escribiendo \"start\"");
    while (!scanner.nextLine().toLowerCase().equals("start")) {
      System.out.println("Valor no válido, escribe \"start\" para comenzar el juego.");
    }
    clientEmitter.write("start");
  }

  private void addQuestion(int iterator) {
      System.out.println("Introduce la " + iterator + "ª pregunta: ");
      String question = scanner.nextLine();
      this.questions.add(question);
      System.out.println("Introduce su respuesta: ");
      String answer = scanner.nextLine().toLowerCase();
      this.answers.add(answer);
  }

  private void removeQuestion(Scanner scanner) {

    System.out.println("Introduce el número de la pregunta a eliminar: ");
    String questionNumber = scanner.nextLine();
    int questionIndex = Integer.parseInt(questionNumber) - 1;
    this.questions.remove(questionIndex);
  }

}
