package assignment1;

import java.io.*;

public class KnapsackExperiment {

	/**
	 * In the experiment we read out the items from the csv and start the HillClimb
	 * or FirstCoice HillClimb algorithm with the given weightLimit (stated in the file name).
	 * In the README.txt file you can find the directions on how to execute the programm.
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
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
		
		// read out the csv-file
	    Item[] items = getArrayFromCsv(csvName + ".csv");
	    String[] splitted = csvName.split("_");
	    int weightLimit = Integer.parseInt(splitted[2]);
	    
	    System.out.println("Values and weights of our items: ");
	    System.out.println("The limit of the knapsack is: " + weightLimit);
		printValues(items);
	    
	    // call HillClimb or FirstCoice HillClimb, default is HillClimb
 		boolean firstChoice = false;
 		if(algorithm.equals("FirstChoiceHillClimb")) {
 			firstChoice = true;
 		}
 		
 		boolean[] solvedKnapsack = KnapsackAlgorithm.hillClimb(items, weightLimit, firstChoice, neighbourMode);
	    
	    System.out.println("\nSolution array: ");
	    printMyArray(solvedKnapsack, items);
	    long endTime = System.nanoTime();
	    System.out.println("Execution time: " + (endTime - startTime)/1000000);
 	}

	/**
	 * This method checks if the input from the user was correct
	 * @param csvName the name of the csv-file
	 * @param algorithm the algorithm type (HillClimb or FirstChoiceHillClimb)
	 * @param neighbourMode the type of algorithm that creates the neighbourhood
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
	 * @param items the array that contains the items
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
	 * This method gives back the weight of the array.
	 * @param itemsBool the array of the boolean items, that indicates if the item is selected or not
	 * @param items using the item-array we can calculate the total values by using the objective function.
	 */
	public static int getArrayWeight(boolean[] itemsBool, Item[] items) {
		int totalWeight = 0;
		for (int j = 0; j < itemsBool.length; j++) {
			if(itemsBool[j]) {
				totalWeight += items[j].getWeight();
			}
		}
		return totalWeight;
	}
	
	/**
	 * This method prints an array out of booleans as 1s and 0s. It also prints the total weight
	 * and total Value of the array
	 * @param myArray the Array that is wished to be printed out
	 */
	static void printMyArray(boolean[] myArray, Item[] items){
		int totalValue = 0;
		int totalWeight = 0;
		for (int i = 0; i < myArray.length; i++) {
			if (myArray[i]) {
				System.out.print(1 + " ");
				totalValue += items[i].getValue();
				totalWeight += items[i].getWeight();
			}else{
				System.out.print(0 + " ");
			}
		}
		System.out.println("\nFinal Value: " + totalValue);
		System.out.println("Final Weight: " + totalWeight);
		System.out.print("\n");
	}
}
