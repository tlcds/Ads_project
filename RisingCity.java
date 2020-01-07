import java.io.*;
import java.util.Scanner;

public class RisingCity {

	private static RedBlackTree rbt = new RedBlackTree();
	private static MinHeap mh = new MinHeap();
	public static int today = 0, countForFive = 0;

	public static void Insert(int buildingNum, int total_time) {
		Triplet triplet = new Triplet(buildingNum, 0, total_time);
		rbt.insert(triplet);
		mh.insert(triplet);
	}
	// Increase current executed_time of all triplets by 1
	// and increase the variable, countForFive, by 1
	public static void dailyUpdate() {
		mh.update();
		countForFive ++;
	}

	//Fix up the simple update above.
	public static void updateFixUp() {
		//If the building is completed in that day,
	    //print the buildingNum of it and the completion date.
	    //Then, delete the triplet of the building in the MinHeap and RedBlackTree
		if(mh.getmin().getExecuted_time() == mh.getmin().getTotal_time()) {
 			System.out.printf("(%d,%d)\n",mh.getmin().getBuildingNum(),today);
 			int completed = mh.extractMin().getBuildingNum();
 			mh.heapify();
 			rbt.delete(completed);
 			countForFive = 0;
 		 }
 		 //If there is no building completed and the countForFive = 5
 		 //heapify the minheap and reset countForFive
    	 if(countForFive == 5) {
    		 mh.heapify();
    		 countForFive = 0;
    	 }
	}

	public static void main(String[] args) throws Exception{
		//wirte output in output_file.txt
		PrintStream fileOut = new PrintStream("output_file.txt");
		System.setOut(fileOut);
		//read commands in input file
		File file = new File(args[0]);
		Scanner sc = new Scanner(file);
		String[] command;
		boolean insert,print;
		int buildingNum, total_time, buildingNum1, buildingNum2;
		// while loop to read all commands
	    while (sc.hasNextLine()) {

	    	command = sc.nextLine().split(":");
		    insert = command[1].contains("Insert");
		    print = command[1].contains("PrintBuilding");

		    while(today <= Integer.parseInt(command[0])) {
		    	 // daily update
		    	 if(mh.heapSize() > 0) {
		    		 dailyUpdate();
		    	 }
		    	 // if date matches
		    	 if(today == Integer.parseInt(command[0])) {
		    	 	// if there is a command to insert new data
			    	if(insert) {
				    	String[] par = command[1].substring(command[1].indexOf("(")+1, command[1].indexOf(")")).split(",");
				    	buildingNum = Integer.parseInt(par[0]);
				    	total_time = Integer.parseInt(par[1]);
				    	Insert(buildingNum, total_time);
				    }
				    // if there is a command to print data
				    if(print) {
				    	if(command[1].contains(",")) {
					    	String[] par = command[1].substring(command[1].indexOf("(")+1, command[1].indexOf(")")).split(",");
					    	buildingNum1 = Integer.parseInt(par[0]);
					    	buildingNum2 = Integer.parseInt(par[1]);
					    	rbt.Print(buildingNum1, buildingNum2);
				    	}else {
				    		buildingNum = Integer.parseInt(command[1].substring(command[1].indexOf("(")+1, command[1].indexOf(")")));
				    		rbt.Print(buildingNum);
				    	}
				    }
			    }
		    	updateFixUp();
		    	today++;
		    }
	    }
	    sc.close();
	    // keep constructing until completion
	    while (rbt.getroot() != null) {
	    	dailyUpdate();
	    	updateFixUp();
    		today++;
	    }
	}
}
