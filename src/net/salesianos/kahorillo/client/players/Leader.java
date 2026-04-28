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

    while (anotherQuestion) {
      LeaderMenu.showQuestionsMenu();
      String option = scanner.nextLine();
      
      switch (option) {
        case "1":
          addQuestion(iterator);
          iterator++;
          break;
        case "2":
          removeQuestion(scanner);
          break;
        case "3":
          anotherQuestion = false;
          break;
        default:
          System.out.println("Opción no válida");
      }
    }

    clientEmitter.write(questions.toArray(new String[0]));
    clientEmitter.write(answers.toArray(new String[0]));

  }

  public void startGame() {
    String response = serverListener.read();
    if (!response.equals("preguntas recibidas")) {
      System.out.println("Error: servidor no confirmó recepción de preguntas");
      return;
    }
    System.out.println("Preguntas enviadas correctamente, esperando a los jugadores...");
    
    response = serverListener.read();
    if (!response.contains("jugadores listos")) {
        System.out.println("Error: servidor no confirmó jugadores listos");
        return;
    }
    System.out.println("Server dice: " + response);
    
    String start = "";
    while (!start.equalsIgnoreCase("start")) {
        System.out.print("Escribe 'start' para comenzar: ");
        start = scanner.nextLine().trim();
        if (!start.equalsIgnoreCase("start") && !start.isEmpty()) {
            System.out.println("Comando no reconocido. Escribe 'start'.");
        }
    }

    System.out.println("enviando 'start'...");
    clientEmitter.write("start");

    System.out.println("Juego iniciado. Esperando a que terminen los jugadores...");

    String finalResult = serverListener.read();
    System.out.println("\n" + finalResult);

    System.out.println("El juego ha finalizado. Gracias por ser el líder.");

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
    try {
      String questionNumber = scanner.nextLine();
      int questionIndex = Integer.parseInt(questionNumber) - 1;
      if (questionIndex >= 0 && questionIndex < this.questions.size()) {
        System.out.println("Pregunta eliminada: " + this.questions.get(questionIndex));
        this.questions.remove(questionIndex);
        this.answers.remove(questionIndex);
      } else {
        System.out.println("Índice fuera de rango. Tienes " + this.questions.size() + " preguntas.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Error: debes escribir un número válido.");
    }
  }

}
