package lab3;
/*
 * Main class: DynamicList
 * Date last modified: 2/13/2018 @ 11:19PM
 * Version: 1.0
 */

import predefined_classes.DynamicNode;

public class DynamicList {

  DynamicNode list; //used to denote the starting node in a list

  //prints out all the contents of a list
  public void print() {
    if (this.list == null) {
      return;
    }
    String finalList = new String();
    Object temp;
    DynamicNode next = list;
    while (next != null) {
      temp = next.getInfo();
      finalList = finalList.concat(temp + ", ");
      next = next.getNext();
    }
    finalList = finalList.substring(0, finalList.length() - 2);
    System.out.println(finalList);
  }

  /*searches through a list for the given object
   * returns the first node containing the passed object
   * if not found, creates a new node at the end of the list and returns
   * the new node
   */
  private DynamicNode searchInsert(Object info) {
    DynamicNode node = null;
    DynamicNode nextNode = null;
    DynamicNode newNode;
    try { //initial assignments for the starting nodes
      node = this.list;
      nextNode = node.getNext();
    } catch (NullPointerException e) { //if nextNode is null, an exception is thrown
      newNode = new DynamicNode(info, null);
      if (node == null) { //if the node is null, then the list is empty
        this.list = newNode;
        return list;
      } else { //otherwise, it's a list of one element
        this.list.setNext(newNode);
        return newNode;
      }
    }
    Object nodeInfo = node.getInfo(); //get the info of the node
    boolean isFound = nodeInfo.equals(info); //use the boolean to compare the passed in object to current object
    while (nextNode != null && isFound == false) {
      //loop through the list until either we reach the final node or we find the passed-in object
      isFound = nodeInfo.equals(info);
      node = node.getNext();
      nextNode = nextNode.getNext();
    }
    if (isFound == false) { //if we haven't found it after the loop, check one last time for the last node
      isFound = nodeInfo.equals(info);
      if (isFound == false) { //if it's still false, then it's not in this list, so add it
        newNode = new DynamicNode(info,null);
        node.setNext(newNode);
        return newNode;
      }
    }
    return node; //otherwise, just return the node with the object
  }

  //inserts the passed-in object to the start of the list
  private void insertFirst(Object info) {
    DynamicNode node = new DynamicNode(info, null);
    if (list != null) {//if the list isn't empty, set the new node's next to current list
      node.setNext(list);
    }
    list = node; //update the lsit for the new node
  }

  /*Concatenates two list; attaches the passed list to the end of the first list
   * returns true if the lsit was changed; false otherwise
   */
  private boolean addAllElements(DynamicList othrList) {
    if (othrList.list == null) {//if null, then the passed in list in empty
      System.out.println("Error: Passed in empty list");
      return false; //so don't change anything
    }
    DynamicNode node = null;
    DynamicNode nextNode = null;
    try {
      node = this.list;
      nextNode = node.getNext();
    } catch (NullPointerException e) {//an exception is thrown if nextNode is null
      if (this.list == null) {//if the list node is null, then the starting list is empty
        this.list = othrList.list;
        return true;
      }
    }
    while (node.getNext() != null) {//simply loop through the list until we reach the end
      node = node.getNext();
    }
    node.setNext(othrList.list); //set the next of the end of this list to the start of the next list
    return true;
  }

  //reverses the list i.e. the list node is now the last node, and vice versa
  private void reverse() {
    DynamicNode prevNode = null;
    DynamicNode node = null;
    DynamicNode nextNode = null;
    try {
      prevNode = this.list;
      node = prevNode.getNext();
      nextNode = node.getNext();
      prevNode.setNext(null);
      node.setNext(prevNode);
    } catch (Exception e) { //an exception is thrown when one of the above nodes are null
      if (this.list == null) {//if list is null, then the list is empty
        System.out.println("Error: List is empty.");
      } else if (node == null) {//if node is null, it's a list of one element
        return;//so don't do anything
      } else { //otherwise, it's a list of two elements so just flip them
        this.list = node;
        node.setNext(prevNode);
        prevNode.setNext(null);
      }
    }
    
    while (nextNode != null) { //loop through the list until we reach the end
      prevNode = node;
      node = nextNode;
      nextNode = nextNode.getNext();
      node.setNext(prevNode);//this is where reversal happens; the next node is now the previous node
    }
    this.list = node; //set the list node to node, which should be the last node in the list
  }
  
  /*Deletes the middle node in the list; if the list is an odd length
   * if it's an even length, a one-element length, or empty, don't do anything
   * returns the deleted node's info if it was deleted; returns null otherwise
   */
  public Object deleteMid() {
    DynamicNode node = null;
    DynamicNode prevNode = null;
    DynamicNode scoutNode = null; //scout node advances at twice the rate of node
    try {
      node = this.list;
      scoutNode = node;
      if (node.getNext() == null) {
        throw new NullPointerException(); //throw an exception if there is only one node in the list
      }
    } catch (NullPointerException e) {
      if (node == null) {//if node is null, then list is empty
        System.out.println("Error: The list is empty");
        return null;
      } else {//otherwise, it's just a one-element list
        System.out.println("Error: Only one element in list");
        return null;
      }
    }
    try {
      do {
        prevNode = node;
        node = node.getNext();
        scoutNode = scoutNode.getNext().getNext();
      } while (scoutNode.getNext() != null);
    } catch (NullPointerException e) {//an exception is thrown when scout node goes to far off the list
      System.out.println("Error: The list is even, no middle node to remove");
      return null;
    } //if it makes it this far, then it must be odd, so "delete" the middle node
    prevNode.setNext(node.getNext()); //by "delete" just point prev node to the node after node 
    return node.getInfo();
  }
  
  // main method
  public static void main(String[] args) {

    DynamicList list1 = new DynamicList();
    DynamicList list2 = new DynamicList();

    // tests out insert first
    list1.insertFirst(2);
    list1.insertFirst(1);
    System.out.print("List: ");
    list1.print();

    // tests out search insert for an element in list
    System.out.println("  Search insert with 1...");
    list1.searchInsert(1);
    System.out.print("List: ");
    list1.print();

    // tests out search insert for an element not in a list
    System.out.println("  Search insert with 7...");
    list1.searchInsert(7);
    System.out.print("List: ");
    list1.print();

    // tests out search insert for an empty list
    System.out.println("\nSearch insert Empty list with 1...");
    list2.searchInsert(1);
    System.out.print("List: ");
    list2.print();

    // test out add all elements for two lists
    DynamicList list3 = new DynamicList();
    list3.insertFirst(4);
    list3.insertFirst(5);
    System.out.println("\nLists: ");
    list1.print();
    list3.print();
    list1.addAllElements(list3);
    System.out.println("Add all elements");
    list1.print();

    // test out add all elements for one list and an empty list
    System.out.println("\nList: ");
    list1.print();
    list1.addAllElements(new DynamicList());
    System.out.println("Add all elements and an empty list");
    list1.print();

    // test out reverse list
    System.out.println("\nList: ");
    list1.print();
    System.out.println("Reverse list...");
    list1.reverse();
    list1.print();

    // test out reverse list on an empty list
    System.out.println("\n Reverse empty list...");
    DynamicList list4 = new DynamicList();
    list4.reverse();
    System.out.println("List: ");
    list4.print();

    // test out reverse list on a list of one element
    System.out.println("\nList: ");
    list2.print();
    System.out.println("Reverse small list");
    list2.reverse();
    System.out.println("List: ");
    list2.print();

    // test out reverse list on a list of two elements
    DynamicList list5 = new DynamicList();
    list5.insertFirst(1);
    list5.searchInsert(2);
    System.out.print("\nList: ");
    list5.print();
    System.out.println("Reverse two element list");
    list5.reverse();
    System.out.print("List: ");
    list5.print();

    // test out delete mid on an odd # list
    System.out.println("\nList: ");
    list1.print();
    System.out.println("Delete mid...");
    list1.deleteMid();
    System.out.println("List: ");
    list1.print();

    // test out delete mid in a list of one element
    System.out.println("\nList: ");
    list2.print();
    System.out.println("Delete mid...");
    list2.deleteMid();
    System.out.println("List: ");
    list2.print();

    // test out delete mid in a smaller odd list
    System.out.println("\nList: ");
    list3.print();
    System.out.println("Delete mid...");
    list3.deleteMid();
    System.out.println("List: ");
    list3.print();

    // test out delete mid in an empty list
    System.out.println("\nList: ");
    list4.print();
    System.out.println("Delete mid...");
    list4.deleteMid();
    System.out.println("List: ");
    list4.print();

    // test out delete mid in an even list
    list5.insertFirst(3);
    list5.searchInsert(10);
    System.out.print("\nList: ");
    list5.print();
    list5.deleteMid();
    System.out.print("List: ");
    list5.print();
  }
  
}
