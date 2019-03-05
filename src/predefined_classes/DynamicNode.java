package predefined_classes;

public class DynamicNode {

  Object info; //stores the info of the node
  DynamicNode next; //stores the point to the next node
  
  public DynamicNode(Object a, DynamicNode b) {
    this.info = a;
    this.next = b;
  }
  
  public Object getInfo() {
    return this.info;
  }
  
  public void setInfo(Object info) {
    this.info = info;
  }
  
  public DynamicNode getNext() {
    return this.next;
  }
  
  public void setNext(DynamicNode node) {
    this.next = node;
  }
  
}
