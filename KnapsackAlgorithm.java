package assignment1;

import java.util.*;

/**
 * This class contains all methods that are necessary to execute the Hillclimb algorithm.
 * @author Rosa, Tula, Emilia
 *
 */
public class KnapsackAlgorithm{

	/**
	 * This method implements the hillclimb algorithm.
	 * compare the outcome of all neighbours with our start-assignment 
	 * therefore we need to compute the weight with the objective funciton,
	 * check if the solution is feasible and if it is better than the original
	 * assignment. We create a new neighbourhood of the best array in the neighbourhood.
	 * We stop if there is no better array in the current neighbourhood.
	 * @param items the array that contains the items
	 * @param neighbourhood the array that contains all neighbours of the initial assignment
	 * @param weightLimit the constraint given by the weightLimit on the csv-file name
	 * @param firstChoice if this is true we use the firstChoice algorithm
	 * @return the best solution that the algorithm could find.
   	 */
	static boolean[] hillClimb(Item[] items, int weightLimit, boolean firstChoice, String neighbourMode){
		
		boolean[] currentState;		
		// assure that the array that we start with, is not infeasable
		do {
			currentState = initializeRandom(items.length);
		}while(KnapsackExperiment.getArrayWeight(currentState, items) > weightLimit 
				| (KnapsackExperiment.getArrayWeight(currentState, items) == 0));
		
		int currentValue = 0;
		int bestValue = getTotalValue(currentState, items);
		boolean[][] neighbourhood = null;

		do {
			// do swap- or transposition neighbourhood
			if(neighbourMode.equals("swap")) {
				neighbourhood = swapNeighbourhood(currentState);
			}else {
				neighbourhood = transposNeighbourhood(currentState);
			}
			
			currentValue = getTotalValue(currentState, items);
			
			for (int i = 0; i < neighbourhood.length; i++) {
			    
				int totalValue = 0;
				int totalWeight = 0;
				
				// iterates through the created neighbourhood to find a better assignment
				for (int j = 0; j < neighbourhood[i].length; j++) {

					if(neighbourhood[i][j]) {
						totalValue += items[j].getValue();
						totalWeight += items[j].getWeight();
					}
				}
				if ((totalWeight <= weightLimit) && (totalValue > currentValue)) {
					bestValue = totalValue;
					currentState = neighbourhood[i];
					if(firstChoice) {
						break;
					}
				}
			}
		}while(bestValue != currentValue);
		return currentState;
	}
	
	static int getTotalValue(boolean[] currentState, Item[] items) {
		int totalValue = 0;
		for (int j = 0; j < currentState.length; j++) {

			if(currentState[j]) {
				totalValue += items[j].getValue();
			}
		}
		return totalValue;
	}

	/**
	 * Initialises a random array that is our strating point for the algorithm
	 * @param len the length of the random array, depends on how many items we have
	 * @return the boolean array that represents the selection of items
	 */
	private static boolean[] initializeRandom(int len){		
		Random random = new Random();
		boolean[] arr = new boolean[len];
		for(int i = 0; i < len; i++) {
			// we assign the probability not with 0.5 because it would be too costly
			// to generate a lot of new arrays
			arr[i] = (random.nextFloat() < 0.15);
		}
		
		return arr;
	}

	/**
	 * This method implements the swapNeighbourhood algorithm. It creates a
	 * rather small neighbourhood. For each assignment the neighbourhood is
	 * as large as the number of items. It selects one variable and swap value with left neighbour.
	 * @param items the array that is the base of the neighbourhood
	 * @return the neighbourhood array
	 */
	public static boolean[][] swapNeighbourhood(boolean[] items) {
		boolean[][] neighbourhood = new boolean[items.length][items.length];
		System.out.println("swap");
		System.out.println("\nlength: " + items.length);
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items.length; j++) {
                neighbourhood[i][j] = items[j];
                if (i == 0 && j == 0) {
                    neighbourhood[i][j] = items[items.length - 1]; // at the beginning of the array we swap with the last item
                    neighbourhood[i][j] = items[0];
                
                } else if (j == i) { // swap with the left neighbour
                    neighbourhood[i][j] = items[j-1];
                    neighbourhood[i][j-1] = items[j];
                }
            }
        }
        return neighbourhood;
    }
	
	/**
	 * This method implements the Transposition-Neighbourhood algorithm. It creates a
	 * rather small neighbourhood. It swaps values of two arbitrary variables. The order of neighbours
	 * is systematic.
	 * @param items the array that is the base of the neighbourhood
	 * @return the neighbourhood array
	 */
	public static boolean[][] transposNeighbourhood(boolean[] items) {
		boolean[][] neighbourhood = new boolean[(items.length)*(items.length - 1)/2][items.length];
		int index = 0;

		for (int j = 0; j < items.length; j++) {
			for (int k = j + 1; k < items.length - 1 ; k++) {
				for (int i = 0; i < items.length; i++) {
					neighbourhood[index][i] = items[i];
				}
				neighbourhood[index][j] = items[k];
				neighbourhood[index][k] = items[j];
				index++; 
			}
		}
		return neighbourhood;
	}
			
}

