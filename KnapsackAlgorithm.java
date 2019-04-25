import java.io.*;
import java.util.*;

public class KnapsackAlgorithm{

  public static boolean[] solveProblem(Item[] items, int weightLimit){
    boolean[] randomSelectedItems = initializeRandom(items.length);
    printMyArray(randomSelectedItems);

    //create neighbourhood
    boolean[][] neighbourhood = swapNeighbourhood(randomSelectedItems);

    // call HillClimb or FirstCoice HillClimb

    printMyArray(neighbourhood[2]);
    return neighbourhood[0];
  }

  // this method should implement the hillclimb algorithm

  /**
   * This method should implement the hillclimb algorithm.
   * @param items the array that contains the items
   * @param neighbourhood the array that contains all neighbours of the initial assignment
   * @param weightLimit the constraint given by the weightLimit on the csv-file name
   * @return the best solution that the algorithm could find.
   */
  private static boolean[] HillClimb(Item[] items, boolean[][] neighbourhood, int weightLimit){

    // compare the outcome of all neighbours with our start-assignment
    // therefore we need to compute the weight with the objective funciton,
    // check if the solution is feasible and if it is better than the original
    // assignment.

    for (int i = 0; i < neighbourhood.length; i++ ) {
      for (int j = 0; j < neighbourhood[i].length; j++) {

        totalValue += neighbourhood[i].getValue();
        totalWeight += neighbourhood[i].getWeight();
      }

      if (totalWeight <= weightLimit && totalValue > ) {

      }
    }


  }

  //initialises a random array that is our strating point for the algorithm
  private static boolean[] initializeRandom(int len){
    Random random = new Random(); //java.util.Random
    boolean[] arr = new boolean[len];
    for(int i = 0; i < len; i++) {
      arr[i] = random.nextBoolean();
    }
    return arr;
  }

  /**
   * This method prints an array out of booleans as 1s and 0s
   */
  private static void printMyArray(boolean[] myArray){
    for (int i = 0; i < myArray.length; i++) {
      if (myArray[i]) {
        System.out.print(1 + " ");
      }else{
        System.out.print(0 + " ");
      }
    }
  }

  /**
   * This method prints an array out of Items
   */
  public static void printMyArray(Item[] myArray){
    for (int i = 0; i < myArray.length; i++) {
      System.out.println("weight: " + myArray[i].getWeight());
      System.out.println("value: " + myArray[i].getValue());
    }
  }

  /**
   * This method implements the swapNeighbourhood algorithm. It creates a
   * rather small neighbourhood.
   */
  public static boolean[][] swapNeighbourhood(boolean[] items) {
        boolean[][] neighbourhood = new boolean[items.length+1][items.length];
        neighbourhood[0][] = items;
        for (int i = 1; i < items.length; i++) {
            for (int j = 0; j < items.length; j++) {
                neighbourhood[i][j] = items[j];
                if (i == 1 && j == 0) {
                    neighbourhood[i][j] = items[items.length - 1];
                } else if (i == 1 && j == items.length - 1) {
                    neighbourhood[i][j] = items[0];
                } else if (j == i) {
                    neighbourhood[i][j] = items[j - 1];
                    neighbourhood[i][j-1] = items[j];
                }
            }

        }
        return neighbourhood;
    }
}
