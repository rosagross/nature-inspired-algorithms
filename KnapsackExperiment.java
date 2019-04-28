package assignment1;

import java.io.*;

public class KnapsackExperiment {

	// In the experiment we read out the items from the csv and start the HillClimb
	// or FirstCoice HillClimb algorithm with the given weightLimit (stated in the file name)
	public static void main(String[] args) {
	 
		System.out.println("...Start Problem-Solving...\n");
		/*
		 * Input from user: first argument is the filename, second argument is the algorithm,
		 * third argument is the neighbourhood setting.
		 */ 
		if(args.length != 3) {
			System.out.println("Please enter filename, type of algorithm and the mode of neighbourhood creation");
			System.exit(-1);
		}
		
		// the arguments that the user types in
		String csvName = args[0];
		String algorithm = args[1];
		String neighbourMode = args[2];
		checkInput(csvName, algorithm, neighbourMode);
		
	    Item[] items = getArrayFromCsv(csvName + ".csv");
	    String[] splitted = csvName.split("_");
	    int weightLimit = Integer.parseInt(splitted[2]);
	
	    System.out.println("limit: " + weightLimit);
	    System.out.println("Values and weights of our items: ");
		printValues(items);
		
	    //call HillClimb algorithm, with specification
	    boolean[] solvedKnapsack = KnapsackAlgorithm.solveProblem(items, weightLimit, "HillClimb", "swap");
	    
	    System.out.println("\nSolution array: ");
	    printMyArray(solvedKnapsack);
	    int solutionWeight = getArrayWeight(solvedKnapsack, items);
	    int solutionValue = KnapsackAlgorithm.getTotalValue(solvedKnapsack, items);
	    System.out.println("The final weight of the knapsack is: " +  solutionWeight);
	    System.out.println("The final value of the knapsack is: " +  solutionValue);
	}

	/**
	 * This method checks if the input from the user was correct
	 * @param csvName
	 * @param algorithm
	 * @param neighbourMode
	 */
	private static void checkInput(String csvName, String algorithm, String neighbourMode) {
		
		String fileName = "(larger_100_1000)|(knapsack_30_1000)|(cable_12_100)";
		String algorithmName = "(FirstChoice)?HillClimb";
		String neighbourModeName = "(swap)|(transposition)";
		
		if(!csvName.matches(fileName)) {
			System.out.println("The file name was not correct! Can be \"larger_100_1000\" or "
					+ "\"knapsack_30_1000\" or \"cable_12_100\"");
			System.exit(-1);
		}else if(!algorithm.matches(algorithmName)) {
			System.out.println("Wrong argument for algorithm-type! Possible is \"HillClimb\" or "
					+ "\"FirstChoiceHillClimb\"");
			System.exit(-1);

		}else if(!neighbourMode.matches(neighbourModeName)) {
			System.out.println("The argument for neighbourhood creation is not valid! Possible option are"
					+ "\"swap\" or \"transposition\"");
			System.exit(-1);

		}
	}

	/**
	 * This method reads out the data from the csv directly into an Array
	 * of items.
	 * @param csvName the name of the csv-File
	 * @return array of items
	 */
	private static Item[] getArrayFromCsv(String csvName){

		String[] csvNameSplitted = csvName.split("_");
		int nrItems = Integer.parseInt(csvNameSplitted[1]);
		Item[] items = new Item[nrItems];

		try (BufferedReader br = new BufferedReader(new FileReader(csvName))) {
		    String line;
		    int j = 0;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        // tu den ersten teil in value, den zweiten in weight
		        items[j] = new Item(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		        j++;
		    }
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist, make sure that it is in your directory.. Stop programm");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Error IOException.. Stop programm");
			System.exit(-1);
		}
		return items;
	}
  
  	/**
	 * This method prints out the values and weights of each item in the array
	 * @param items
	 */
	public static void printValues(Item[] items) {
		System.out.printf("The array contains %d items %n", items.length);
		System.out.printf("|%7s|%7s|%8s|%n", "index", "weight", "value");
		System.out.println("+-------|-------|--------+");
		for (int j = 0; j < items.length; j++) {
			System.out.printf("|%7d|%7d|%8d|%n", j, items[j].getWeight(), items[j].getValue());
		}	
	}
	
	/**
	 * This method prints out the array and gives back the weight of the array.
	 * @param itemsBool the array of the boolean items, that indicates if the item is selected or not
	 * @param items using the item-array we can calculate the total values by using the objective function.
	 */
	public static int getArrayWeight(boolean[] itemsBool, Item[] items) {
		int totalValue = 0;
		int totalWeight = 0;
		
		//System.out.println("Selected items at index: ");
		for (int j = 0; j < itemsBool.length; j++) {
			if(itemsBool[j]) {
				totalValue += items[j].getValue();
				totalWeight += items[j].getWeight();
				System.out.println(j);
			}
		}
		System.out.println("Final Value: " + totalValue);
		System.out.println("Final Weight: " + totalWeight);
		return totalWeight;
	}
	
	/**
	 * This method prints an array out of booleans as 1s and 0s
	 * @param myArray the Array that is wished to be printed out
	 */
	static void printMyArray(boolean[] myArray){
		for (int i = 0; i < myArray.length; i++) {
			if (myArray[i]) {
				System.out.print(1 + " ");
			}else{
				System.out.print(0 + " ");
			}
		}
		System.out.print("\n");
	}

}
