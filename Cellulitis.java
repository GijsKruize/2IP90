
// Cellulitis TEMPLATE
// Homework Assignment 3 2ip90 
/**
 * @author Gijs Kruize
 * @id 1658662
 * @author Gijs Nolet
 * @id 1679570
 * @group 64
 * @date 17-09-21
 */

import java.util.Scanner;
import java.util.ArrayList;

class Cellulitis {
  Scanner sc = new Scanner(System.in);
  String automaton;
  int L;
  int G;
  ArrayList<Integer> initPoints;
  int[] universalAutomaton = new int[8];

  void run() {

    input();

    boolean[] currentGeneration = new boolean[L + 2];
    currentGeneration = initFirstGeneration(currentGeneration);

    if (automaton.equalsIgnoreCase("A")) {
      for (int i = 0; i < G; i++) {
        draw(currentGeneration);
        currentGeneration = nextGenerationA(currentGeneration);
      }
    } else if (automaton.equalsIgnoreCase("B")) {
      for (int i = 0; i < G; i++) {
        draw(currentGeneration);
        currentGeneration = nextGenerationB(currentGeneration);
      }
    } else if (automaton.equalsIgnoreCase("U")) {
      for (int i = 0; i < G; i++) {
        draw(currentGeneration);
        currentGeneration = nextGenerationU(currentGeneration);
      }
    }
  }

  void input() {
    automaton = sc.next();
    L = sc.nextInt();
    G = sc.nextInt();
    // Creating an arraylist because we do not know the length yet.
    initPoints = new ArrayList<Integer>();

    if (sc.next().equals("init_start")) {
      while (sc.hasNext()) {
        if (sc.hasNextInt()) {
          initPoints.add(sc.nextInt());
        } else if (sc.next().equals("init_end")) {
          break;
        }
      }
    }
    if (automaton.equalsIgnoreCase("U")) {
      for (int i = 0; i < 8; i++) {
        universalAutomaton[i] = sc.nextInt();
      }
    }

  }

  boolean[] initFirstGeneration(boolean[] currentGeneration) {
    // Set every value of current generation to false
    for (int i = 0; i < currentGeneration.length; i++) {
      currentGeneration[i] = false;
    }
    // Set the cells specified in input to true.
    for (int i = 0; i < initPoints.size(); i++) {
      // For every starting point specified in the input,
      // the currentgeneration at that point must be set to true.
      currentGeneration[initPoints.get(i)] = true;
    }
    return currentGeneration;
  }

  boolean[] nextGenerationA(boolean[] generation) {
    boolean[] newGeneration = new boolean[L + 2];

    for (int i = 1; i <= L; i++) {
      if (generation[i] && (generation[i - 1] ^ generation[i + 1])) {
        newGeneration[i] = true;
      } else if (!generation[i] && (generation[i - 1] || generation[i + 1])) {
        newGeneration[i] = true;
      } else {
        newGeneration[i] = false;
      }
    }

    return newGeneration;
  }

  boolean[] nextGenerationB(boolean[] generation) {
    // Compensate for the empty row left and right added to avoid out of bounds
    // exceptions.
    boolean[] newGeneration = new boolean[L + 2];

    // Apply all rules specified for automaton
    for (int i = 1; i <= L; i++) {
      if (generation[i] && generation[i - 1] && !generation[i + 1]) {
        newGeneration[i] = true;
      } else if (!generation[i] && (generation[i - 1] ^ generation[i + 1])) {
        newGeneration[i] = true;
      } else if (generation[i] && !generation[i - 1] && !generation[i + 1]) {
        newGeneration[i] = true;
      } else if (generation[i] && generation[i + 1] && !generation[i - 1]) {
        newGeneration[i] = false;
      } else {
        newGeneration[i] = false;
      }

    }
    return newGeneration;
  }

  boolean[] nextGenerationU(boolean[] generation) {
    boolean[] newGeneration = new boolean[L + 2];
    for (int i = 1; i < L; i++) {

      // Check each possibility of 3 cells and adjust the next generation according to
      // the input
      if (!generation[i - 1] && !generation[i - 1] && !generation[i + 1]) {
        newGeneration[i] = universalAutomaton[0] == 1;
      } else if (!generation[i - 1] && !generation[i] && generation[i + 1]) {
        newGeneration[i] = universalAutomaton[1] == 1;
      } else if (!generation[i - 1] && generation[i] && !generation[i + 1]) {
        newGeneration[i] = universalAutomaton[2] == 1;
      } else if (!generation[i - 1] && generation[i] && generation[i + 1]) {
        newGeneration[i] = universalAutomaton[3] == 1;
      } else if (generation[i - 1] && !generation[i] && !generation[i + 1]) {
        newGeneration[i] = universalAutomaton[4] == 1;
      } else if (generation[i - 1] && !generation[i] && generation[i + 1]) {
        newGeneration[i] = universalAutomaton[5] == 1;
      } else if (generation[i - 1] && generation[i] && !generation[i + 1]) {
        newGeneration[i] = universalAutomaton[6] == 1;
      } else if (generation[i - 1] && generation[i] && generation[i + 1]) {
        newGeneration[i] = universalAutomaton[7] == 1;
      }
    }
    return newGeneration;
  }

  void draw(boolean[] generation) {
    String generationText = "";

    for (int i = 1; i <= L; i++) {
      if (generation[i]) {
        generationText += "*";
      } else {
        generationText += " ";
      }
    }

    System.out.println(generationText);
  }

  public static void main(String[] args) {
    new Cellulitis().run();
  }
}
