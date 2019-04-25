
import java.io.*;
import java.util.*;

public class KnapsackExperiment {

  // In the experiment we read out the items from the csv and start the HillClimb
  // or FirstCoice HillClimb algorithm with the given weightLimit (stated in the file name)
	public static void main(String[] args) {

    String csvName = "knapsack_30_1000";
    Item[] items = getArrayFromCsv("knapsack_30_1000.csv");
    String[] splitted = csvName.split("_");
    int weightLimit = Integer.parseInt(splitted[2]);

    System.out.println("limit: " + weightLimit);
    //KnapsackAlgorithm.printMyArray(items);

    //call HillClimb algorithm
    boolean[] solvedKnapsack = KnapsackAlgorithm.solveProblem(items, weightLimit);


    // TODO:
    // ASSURE FEASABILITY

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
			System.out.println("The file doesn't exist.. Stop programm");
      System.exit(-1);
		} catch (IOException e) {
      System.out.println("Error IOException.. Stop programm");
      System.exit(-1);
		}
    return items;
  }

}
