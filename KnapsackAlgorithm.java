package assignment1;

import java.util.*;

public class KnapsackAlgorithm{

	/**
	 * This method gives an overview about the process of finding a solution by using the 
	 * algorithm. 
	 * First a random boolean array is created, with which we can start.
	 * Then a neighbourhood is generated, either with swap- or transposition-neighbourhood.
	 * Then the total value and weight is calculated with the objective function and compared to the
	 * currently best solution.
	 * @param items the array that contains the items with value and weight
	 * @param weightLimit the constraint given by the weightLimit on the csv-file name
	 * @param algorithm specified by the user, should be firstChoice or normal hillClimb
	 * @param neighbourMode specified by the user, can be swap or transposition neighbourhoods
	 * @return the array with the best solution computed by the algorithm
	 */
	public static boolean[] solveProblem(Item[] items, int weightLimit, String algorithm, String neighbourMode){

		// call HillClimb or FirstCoice HillClimb, default is HillClimb
		boolean firstChoice = false;
		if(algorithm.equals("FirstChoiceHillClimb")) {
			firstChoice = true;
		}
		
		boolean[] bestSolution = hillClimb(items, weightLimit, firstChoice, neighbourMode);
		
		return bestSolution;
	}
	
	/**
	 * This method should implement the hillclimb algorithm.
	 * @param items the array that contains the items
	 * @param neighbourhood the array that contains all neighbours of the initial assignment
	 * @param weightLimit the constraint given by the weightLimit on the csv-file name
	 * @param firstChoice if this is true we use the firstChoice algorithm
	 * @return the best solution that the algorithm could find.
   	 */
	private static boolean[] hillClimb(Item[] items, int weightLimit, boolean firstChoice, String neighbourMode){
	  
		// compare the outcome of all neighbours with our start-assignment
	    // therefore we need to compute the weight with the objective funciton,
	    // check if the solution is feasible and if it is better than the original
	    // assignment. We create a new neighbourhood of the best array in the neighbourhood.
		// We stop if there is no better array in the current neighbourhood.
		
		boolean[] currentState;		
		// assure that the array that we start with, is not infeasable
		do {
			currentState = initializeRandom(items.length);
		}while(KnapsackExperiment.getArrayWeight(currentState, items) > weightLimit 
				&& (KnapsackExperiment.getArrayWeight(currentState, items) == 0));
		System.out.println("Initial Array: ");
		KnapsackExperiment.printMyArray(currentState);
		
		int currentValue = 0;
		int bestValue = getTotalValue(currentState, items);
		boolean[][] neighbourhood = null;
		
		int count = 0;

		do {
			count++;
			System.out.printf("Neighbourhood creation nr. %d", count);
			
			// do swap- or transposition neighbourhood
			if(neighbourMode.equals("swap")) {
				neighbourhood = swapNeighbourhood(currentState);
			}else {
				//neighbourhood = transpositionNeighbourhood(randomSelectedItems);
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
						//System.out.printf("|%7d|%7d|%n", items[j].getWeight(), items[j].getValue());
					}
				}
				//System.out.println("+-------|-------+");
				//System.out.printf("|%7d|%7d|%n", totalWeight, totalValue);

				if ((totalWeight <= weightLimit) && (totalValue > currentValue)) {
					bestValue = totalValue;
					currentState = neighbourhood[i];
					if(firstChoice) {
						break;
					}
				}else if(totalWeight > weightLimit) {
					System.out.printf("The solution of neighbour %d is infeasable!%n", i);
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
			arr[i] = (random.nextFloat() < 0.08);
		}
		
		return arr;
	}

	/**
	 * This method implements the swapNeighbourhood algorithm. It creates a
	 * rather small neighbourhood.
	 */
	public static boolean[][] swapNeighbourhood(boolean[] items) {
		boolean[][] neighbourhood = new boolean[items.length][items.length];
		System.out.println("swap");
		KnapsackExperiment.printMyArray(items);
		System.out.println("\nlength: " + items.length);
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items.length; j++) {
                neighbourhood[i][j] = items[j];
                if (i == 0 && j == 0) {
                    neighbourhood[i][j] = items[items.length - 1];
                } else if (i == 0 && j == items.length - 1) {
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

