package hw1;
/*
 * Main Class: DynamicQueue.java
 * Date last modified: 3/2/2018 @ 10:46PM EST
 * Version: 1.0
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import predefined_classes.DynamicNode;

public class DynamicQueue {

  private DynamicNode front, rear;
  //declaring these here at class scope allow for us to easily modify the queue size and queue count
  private static int N = 4;
  private static int QUEUESIZE = 4;

  public DynamicQueue() {
    front = rear = null;
  }
  
  //prints the queue from right to left i.e. rear is the rightmost, front is the leftmost
  public void printQueue() {
    if (this.rear == null) { //in the event you try to print an empty queue
      System.out.println("Empty");
      return;
    }
    StringBuilder queuePrint = new StringBuilder();
    DynamicNode queueCount = this.rear; //start at the rear, and work our way from there
    queuePrint.append(queueCount.getInfo().toString());
    while (queueCount.getNext() != null) { //loop until we reach the end of the queue
      queueCount = queueCount.getNext();
      queuePrint.append(">-" + queueCount.getInfo().toString()); //StringBuilder is created from left to right
    }
    queuePrint.reverse(); //reverse so we can get right to left
    System.out.println(queuePrint.toString());
  }

  //main method where queues are created and modified
  public static void main(String[] args) {

    Scanner inputTxt = null; //scanner for the file
    Character value = null; //stores the current value, based on the file
    Integer index = null; //stores the index of the current queue, based on the file
    DynamicNode nodeCounter = null; //reference to a position in the queue
    DynamicNode prevCounter = null; //reference to the position behind nodeCounter
    boolean isEqual; //boolean to determine whether a value is matched or not
    int nodeCount; //used to count our "index" in the queue

    DynamicQueue[] queues = new DynamicQueue[N]; //creates N amount of queues
    for (int count = 0; count < N; count++) { //initializes each queue
      queues[count] = new DynamicQueue();
    }

    try { //just to make sure the file is found
      inputTxt = new Scanner(new File("src/data/data.txt"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
      System.exit(1);
    }

    try {

      while (inputTxt.hasNextLine()) {//loop through while lines still exist in the file
        nodeCounter = null; //clear the pointers for the next iteration
        prevCounter = null;
        nodeCount = 0; //clear the counter as well
        isEqual = false; //assume that the values aren't equal
        value = inputTxt.next().charAt(0); //in the txt file, the value is the first char
        index = inputTxt.nextInt(); //in the txt file, there should be only one int per line
        System.out.print("Read key " + value + " for queue "+index+". ");

        if (queues[index].rear == null) { //special case where the queue is empty
          queues[index].rear = new DynamicNode(value, null); //so create a rear
          queues[index].front = queues[index].rear; //in a queue of size 1, the rear is the front
          System.out.println("Inserting " + value + " in rear. Q" + index + ": " + value);
        } else {
          nodeCounter = queues[index].rear; //always start from the rear
          isEqual = nodeCounter.getInfo().equals(value); //check if the rear has the value we have
          while (nodeCounter.getNext() != null && isEqual == false) {
            prevCounter = nodeCounter;
            nodeCounter = nodeCounter.getNext();
            isEqual = nodeCounter.getInfo().equals(value); //check again for the next value
            nodeCount++;
          }
          if (isEqual) { // go here if the loop was broken because the value was found
            if (queues[index].rear.getInfo().equals(value)) {
              // do nothing, because that means the first element is already the repeat
              System.out.print(value + " is already rear. Q" + index + ": ");
              queues[index].printQueue();
            } else { //move the node with the repeat value from it's current position to the rear
              prevCounter.setNext(nodeCounter.getNext());
              nodeCounter.setNext(queues[index].rear);
              queues[index].rear = nodeCounter;
              System.out.print("Moving " + value + " to rear. Q" + index + ": ");
              queues[index].printQueue();
            }
          } else { // go here if the loop was broken because we reached the end
            DynamicNode newNode = new DynamicNode(value, null); //create a new node to add to the queue
            if (nodeCount < QUEUESIZE - 1) { // if the queue isn't full yet, simply add it to the rear
              queues[index].front = nodeCounter; //where ever the counter is, that must be the front
              newNode.setNext(queues[index].rear);
              queues[index].rear = newNode;
              System.out.print("Inserting " + value + " in rear. Q" + index + ": ");
              queues[index].printQueue();
            } else { // otherwise the queue is full, so remove the front and then add the new node to rear
              prevCounter.setNext(null); //essentially isolates the current node, thus "removing" it
              queues[index].front = prevCounter; //now the previous node is the front of the queue
              newNode.setNext(queues[index].rear);
              queues[index].rear = newNode;
              System.out.print("Q is full, removing front. Inserting " + value + " in rear. Q" + index + ": ");
              queues[index].printQueue();
            }
          }
        }
      }
    } catch (Exception e) { //in the event something went wrong with the file reading
      System.out.println("Something went wrong");
      System.exit(1);
    }

    System.out.println(); //empty line to print space
    System.out.println("..Final Queues..");
    nodeCount = 0; //reuse old variables vs. making new ones
    for (DynamicQueue q : queues) {
      System.out.print("Q" + nodeCount + ": ");
      q.printQueue();
      nodeCount++;
    }
  }
}
