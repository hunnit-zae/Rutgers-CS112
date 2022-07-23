package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return 
	 */
	public TNode makeList(int[] trainStations, int[] busStops, int[] locations) {

		TNode walkingNode = new TNode(0, null, null);
		TNode busNode = new TNode(0, null, walkingNode);
		trainZero = new TNode(0, null, busNode);
		TNode trainNode = trainZero;
		int cnt1 = 0;
		int cnt2 = 0;

        for (int i = 0; i <locations.length; i++) 
		{
            walkingNode.setNext(new TNode(locations[i], null, null));
			walkingNode = walkingNode.getNext();
			if (cnt2 < busStops.length)
			{
				if(locations[i] == busStops[cnt2])
				{
					busNode.setNext(new TNode(busStops[cnt2], null, walkingNode));
					busNode = busNode.getNext();
					cnt2++;
				}
			}

			if (cnt1 < trainStations.length)
			{
				if(locations[i] == trainStations[cnt1])
				{
					trainNode.setNext(new TNode(trainStations[cnt1], null, busNode));
					trainNode = trainNode.getNext();
					cnt1++;
				}
			}
        }
		return trainNode;
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {

		TNode trainNode = trainZero.getNext();
		TNode trainNode2 = trainZero;

        while (trainNode != null)
		{
            if (trainNode.getLocation() == station) 
			{
                trainNode2.setNext(trainNode.getNext());
            }

			trainNode2 = trainNode;
			trainNode = trainNode.getNext();
        }
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
		
	    TNode busnNode = trainZero.getDown();
		TNode walkingNode = trainZero.getDown().getDown();

		while (walkingNode.getLocation() != busStop) {
			walkingNode = walkingNode.getNext();
		}

		while (true) {
			if (busStop == busnNode.getLocation()) 
			{ 
				return;

			} 
			else if (busStop > busnNode.getLocation()) 
			{
				if (busnNode.getNext() == null) { 
					busnNode.setNext(new TNode(busStop, null, walkingNode)); 
					return;
				} 
				else if (busStop > busnNode.getNext().getLocation()) 
				{
					busnNode = busnNode.getNext();
				} 
				else if (busStop == busnNode.getNext().getLocation()) 
				{ 
					return;
				} 
				else 
				{
					TNode newNode = new TNode(busStop, null, walkingNode);
					newNode.setNext(busnNode.getNext());
					busnNode.setNext(newNode);
					return;
				}
			}
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		
		ArrayList<TNode> Path = new ArrayList<>();
        Path.add(trainZero);
        TNode bestlocation = trainZero;

        while(bestlocation != null && bestlocation.getLocation() != destination) 
		{
            if(bestlocation.getNext() != null && bestlocation.getNext().getLocation() <= destination)
			{
                bestlocation = bestlocation.getNext();
            }
            else
			{
                bestlocation = bestlocation.getDown();    
            }
            
            Path.add(bestlocation);
        }
        while(bestlocation.getDown() != null) 
		{
            bestlocation = bestlocation.getDown();
            Path.add(bestlocation);
        }
	    
	    return Path;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {

		TNode walkingNode = new TNode(0, null, null);
		TNode busNode = new TNode(0, null, walkingNode);
		TNode trainNode = new TNode(0, null, busNode);
		TNode trainNode2 = trainNode;

		TNode walkingnode2 = trainZero.getDown().getDown().getNext();
		TNode busNode2 = trainZero.getDown().getNext();
		TNode traincopyNode = trainZero.getNext();
		
		while (walkingnode2 != null) 
		{
			walkingNode.setNext(new TNode(walkingnode2.getLocation(), null, null));
			walkingNode = walkingNode.getNext();
			
			if (busNode2 != null && walkingnode2.getLocation() == busNode2.getLocation()) 
			{
				busNode.setNext(new TNode(busNode2.getLocation(), null, walkingNode)); 
				busNode2 = busNode2.getNext();
				busNode = busNode.getNext();
			}

			if (traincopyNode != null && walkingnode2.getLocation() == traincopyNode.getLocation()) {
				trainNode.setNext(new TNode(traincopyNode.getLocation(), null, busNode)); 
				traincopyNode = traincopyNode.getNext();
				trainNode = trainNode.getNext();
			}
			walkingnode2 = walkingnode2.getNext();
		}
		return trainNode2;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		
		TNode scooterNode = new TNode(0, null, null);
		TNode walkingNode = trainZero.getDown().getDown();
		TNode busNode = trainZero.getDown();
		busNode.setDown(scooterNode);
		scooterNode.setDown(walkingNode);
		
		int index = 0;
		busNode = busNode.getNext();

		while (walkingNode != null) 
		{
			if (index == scooterStops.length) 
			{
				return;
			}

			if (scooterStops[index] == walkingNode.getLocation()) 
			{
				scooterNode.setNext(new TNode(scooterStops[index], null, walkingNode));
				scooterNode = scooterNode.getNext();
				if (busNode != null) 
				{
					if (scooterStops[index] == busNode.getLocation()) 
					{
						busNode.setDown(scooterNode);
					}
				}
				++index;
			}
			if (walkingNode.getLocation() == busNode.getLocation() && busNode.getNext() != null) 
			{
				busNode = busNode.getNext();
			}
			walkingNode = walkingNode.getNext();
		}	
	}


	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
