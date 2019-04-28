# nature-inspired-algorithms

README - Knapsack problem


Our implementation of the knapsack problem consists of three classes: KnapsackAlgorithm, KnapsackExperiment and Item. KnapsackAlgorithm implements First Choice Hill Climbing and Hill Climbing for the given problem. KnapsackExperiment allows for testing the algorithm on given csv files. This is the class you need to execute in order to run the program. Item generates an object that holds information about the weight and values of possible entities that can be put into our knapsack.



The csv file that is employed as input is read in the main method of KnapsackExperiment. It holds information on the weight limit that is given and on weight-value-pairs for each item. Employing the method getArrayfromcsv(), an object of the class Item is generated from the weight-value-pairs. Note that the program requires that the csv file can be found in the remote path of where the program runs. 

Additionally, the user inputs whether she intends to employ Hill Climbing or First Choice Hill Climbing and Swap Neighbourhood or Transposition Neighbourhood. 
Furthermore, the main method checks if the input was feasible. Feasible inputs are the following strings: „FirstChoiceHillClimb“, „HillClimb“, „swap“, „transposition“ and the file names „larger_100_1000“, „knapsack_30_1000“ and „cable_12_100“.

The method hillClimb() from the class KnapsackAlgorithm is called to generate a solution for the problem. This method takes the Item-array and the weight limit which were both given by the csv file name. Also, it takes into account the user input that holds information about the algorithm and its neighbourhood. 

In hillClimb(), there is a Boolean-array representation of the items coding 0 if the item is absent and 1 if it is present in the knapsack. The method initializeRandom() generates a random state of this representation. Its feasibility is checked via a loop so that a useful initialisation is guaranteed. Next, a neighbourhood of the state is created. Depending on the user input, it will either be swapNeighbourhood() which is the smaller alternative containing as many elements as there are items, or transposNeighbourhood() containing ((n - 1) 2) elements. The neighbourhoods are realised as 2-dimensional-Boolean-arrays.

To start improving the value of the state, we first evaluate the randomly generated start state by using the method getTotalValue(). Then, the algorithm looks through the neighbourhood to see if any assignment can be found that results in a better value. In case of Hill Climbing it checks the whole neighbourhood, whereas in First Choice Hill Climbing the method finishes as soon as it found some improvement. We generate new neighbourhoods and look at them as long as there is an improvement. 

The result is printed via printMyArray() in KnapsackExperiment. The weight of our solved knapsack is also put out.


 


