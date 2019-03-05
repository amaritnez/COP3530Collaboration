package hw2;
/*
 * Class: hw2.java
 * Date last modified: 4/18/2018 11:33PM
 * Version 1.0
 */
public class hw2 {

  /*
   * Definitions of the parameters 
   * 1) heap: the array where the heap (sweeping window) is implemented 
   * 2) newEle: the new element to insert 
   * 3) pos: where to insert the new element initially. 
   * note it does not mean newEle is going to stay at pos after this function 
   * 4) increment 
   * a) true: insert newEle, that is all 
   * b) false: insert newEle, then remove the root
   */
  static void insertAt(int[] heap, int newEle, int pos, boolean increment) {

    int child = pos; //initialize the child pointer to the position 
    int parent, larger, temp; //initialize the rest of the vars for the rest of the method
    heap[pos] = newEle; //place the new element where ever pos tells it too
    parent = (pos - 1) / 2; //calculate the value of the parent pointer relative to pos, based on heap
    
    if (increment) { //start case a. here

      /* loop through the heap to make sure it meets the requirement of parent >= children */
      while (child > 0 && heap[parent] < heap[child]) { 
        heap[child] = heap[parent];// heap exchange parent and child
        heap[parent] = newEle; //add the new element to the parent
        child = parent; // update parent and child
        parent = (child - 1) / 2; //update parent pointer
      }
    } else { //start case b. here
      
      heap[heap.length - 1] = newEle; //place the new element at the end of the heap
      /* loop through the heap to make sure it meets the requirement of parent >= children */
      while (child > 0 && heap[parent] < heap[child]) {
        heap[child] = heap[parent];// heap exchange parent and child
        heap[parent] = newEle; // update parent and child
        child = parent; // update parent and child
        parent = (child - 1) / 2; //update parent pointer
      }
      
      temp = heap[heap.length - 1]; //store the last value of the heap here temporarily
      heap[heap.length - 1] = heap[0]; // the root (largest) element is at 0
      heap[0] = temp; // now the root does not have the largest element
      // note the range has reduced to (0, k-1)

      // special case handling:
      // decide the first larger child of the element at heap[0]
      if (heap.length - 2 == 0) { //do minus 2 since the last value technically isn't in the heap
        return; // only one node
      }
      if (heap.length - 2 == 1)  { // only two nodes: root and its child
        larger = 1; // the larger child is at position 1
      } else { //more than two nodes
        if (heap[1] > heap[2]) {
          larger = 1; //start at left
        } else {
          larger = 2; //start at right
        }
      }
      // bubble-down heap[0] – note: range was reduced to (0, k-1)
      parent = 0; // begin with the root of the entire heap
      while (heap[parent] < heap[larger]) {
        temp = heap[parent]; // swap heap[parent]
        heap[parent] = heap[larger]; // and its larger
        heap[larger] = temp; // child
        parent = larger; // update parent
        child = (2 * parent) + 1; // update child
        if (child > heap.length - 2) {
          break; // no child
        } else if (child + 1 > heap.length - 2) {
          larger = child; // only a left child (since almost complete tree)
        } else { // two children; picks the greatest
          if (heap[child + 1] > heap[child]) {
            larger = child + 1;
          } else {
            larger = child;
          }
        }
      }
    }
  }

  /*
   * get the smallest k elements in array x in O(nlogk) time, where n is the
   * size of array x.
   * 
   * It is supposed to return an array, containing the smallest k elements in
   * the increasing order.
   */
  static int[] getSmallestK(int x[], int k) {

    if (k > x.length)
      return null;

    int[] result = new int[k + 1];

    // use the first k elements in x to construct an
    // almost complete binary tree, where parent >= children
    result[0] = x[0];
    for (int i = 1; i < k; i++) {
      insertAt(result, x[i], i, true);
    }

    System.out.print("Original heap: ");
    for (int i = 0; i < k; i++) {
      System.out.print(result[i] + " ");
    }
    System.out.println();

    // for each element in the rest of array x,
    // insert it in the almost complete binary tree, and then
    // remove the root in the tree.
    for (int i = k; i < x.length; i++) {
      insertAt(result, x[i], k, false);
    }

    // now the first k elements in result are the smallest k elements in x
    System.out.print("Resulting heap: ");
    for (int i = 0; i < k; i++) {
      System.out.print(result[i] + " ");
    }
    System.out.println();

    
    int larger, parent, child, temp; //var initialization
    // sort the first k elements in result in O(klogk) time
    for (int i = result.length - 2; i > 0; i--) { // range is (0, k-1; we don't worry about the last value)
      temp = result[i];
      result[i] = result[0]; // the root (largest) element is at 0
      result[0] = temp; // now the root does not have the largest element
      // note the range has reduced to (0, i-1)

      // first need to deal with some special cases and
      // decide the first larger child of the element at result[0]
      if (i - 1 == 0) {
        break; // only one node
      }
      if (i - 1 == 1) {// only two nodes: root and its child
        larger = 1; // the larger child is at position 1
      } else {
        if (result[1] > result[2]) { //two nodes, choose greates
          larger = 1;
        } else {
          larger = 2;
        }
      }
      // bubble-down result[0] – note: range was reduced to (0, i-1)
      parent = 0; // begin with the root of the entire heap
      while (result[parent] < result[larger]) {
        temp = result[parent]; // swap result[parent]
        result[parent] = result[larger]; // and its larger
        result[larger] = temp; // child
        parent = larger; // update parent
        child = (2 * parent) + 1; // update child
        if (child > i - 1) { // no child
          break;
        } else if (child + 1 > i - 1) { // only left child (almost complete tree)
          larger = child;
        } else { // two children: pick largest
          if (result[child + 1] > result[child]) {
            larger = child + 1;
          } else {
            larger = child;
          }
        }
      }
    }
    return result; //return the sorted heap
  }
  
  /* the above method is performed in O(nlogk) time
   * the original heap is created in klogk. It linear adds elements k times,
   * and then within each iteration checks the heap up to logk times to ensure it meets heap requirements.
   * The resulting heap is made in (n - k)logk, with similar reasoning to the original heap
   * (just now you progress with the remaining N elements in the original array).
   * Thus, the total time between the original and resulting heap
   * can be simplified to klogk + (n-k)logk, or nlogk - klogk + klogk, or just O(nlogk). 
   * The second part linear iterates through the heap backwards (so k time),
   * and whilst linearly iterating, within each iteration it again iterates through
   * the heap in logk time via traveling binarly (i.e. picking one child, traveling to that child).
   * Thus this second part takes O(klogk) time.
   * The overall method then takes nlogk + klogk time. Since N > k, this can be simplified
   * to O(nlogk), thus giving the desired efficiency.
   */

  //main method, for main method stuff; starts the main functionality here
  public static void main(String[] args) {
    // Test cases
    int[] data = { 31, 44, 64, 5, 61, 43, 6, 88, 59, 90, 39, 97, 77, 62, 99, 34, 57, 53, 60, 29 };

    int i, k = 5;
    System.out.println(" k = " + k);

    int[] largestK = getSmallestK(data, k);

    System.out.print("Sorted result (smallest k elements): ");
    for (i = 0; i < k; i++) {
      System.out.print(largestK[i] + " ");
    }

    k = 8;
    System.out.println("\n k = " + k);

    largestK = getSmallestK(data, k);

    System.out.print("Sorted result (smallest k elements): ");
    for (i = 0; i < k; i++) {
      System.out.print(largestK[i] + " ");
    }
  }
}