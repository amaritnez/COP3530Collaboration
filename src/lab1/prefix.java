package lab1;
/*
 * Class: prefix.java
 * Date last modified: 1/18/2018 8:06PM EST
 * Version: 1.0
 */

import java.util.Scanner;

class charStack {

	private final int STACKSIZE=100;
	private int top;
	private char [] items;
	
	//stack constructor
	public charStack() {
	  this.top = -1;
	  items = new char[STACKSIZE];
	}
	
	//checks if stack is empty
	public boolean empty() {
	  if (top == -1) {
	    return true;
	  } else {
	    return false;
	  }
	}
	
	//removes top-most element in stack, if present
	public char pop() {
	  if(empty()){
	    System.out.println("Stack underflow");
	    System.exit(1);
	  }
	  return items[top--];
	}
	
	//adds an element to the stack, if room exists
  public void push(char x) {
	  if(top==STACKSIZE-1){
	    System.out.println("Stack Overflow");
	    System.exit(1);
	  } else {
	    items[++top] = x;    
	  }
  }
	
  //returns the top-most element, if present, but doesn't remove it
	public char peek() {
	  if (empty()) {
	    System.out.println("Stack underflow");
	    System.exit(1);
	    return 'l';
	  } else {
	    return items[top];
	  }
	}
	
}


public class prefix{

  //checks if a char is an operand
	public static boolean isOperand(char symb){
		if(symb=='+' || symb=='-' || symb=='*'
			|| symb=='/' || symb=='$'
			|| symb=='(' || symb==')'){
			return false;
		}else{
			return true;
		}
	}	

	//converts the infix passed in to prefix
	public static String infix_to_prefix(String infix){
	  
	  charStack operatorStack = new charStack(); //creates a stack to store operators
	  StringBuilder buildOutfix = new StringBuilder(); //creates a stringbuilder for the prefix
	  char currentSym; //char to store the current symbol as we iterate through the infix
	  
	  for (int position = infix.length() - 1; position > -1; position--) {
	    currentSym = infix.charAt(position); //gets the char in the infix and stores it
	    if(isOperand(currentSym) == true) {
	      buildOutfix.append(currentSym); //if it's an operand, append it to the prefix
	    } else {
	      /* loop through the stack while it isn't empty and the top of the stack
	       * has a higher precedence than the current symbol
	      */
	      while (operatorStack.empty() == false && precedence(operatorStack.peek(),currentSym) == true) {
	        if (currentSym != '(' && currentSym != ')') { //if the currentSym is a parenthesis, don't append it
	          buildOutfix.append(operatorStack.pop()); //append the top of the stack to the prefix
	        } else {
	          break; //if currentSym is a parenthesis, end the loop (and do nothing else)
	        }
	      }
	      
	      if (currentSym != '(') {
	        operatorStack.push(currentSym); //as long as currentSym isn't the start parenthesis, push it to the stack
	      } else {
	        /*
	         * otherwise, loop through the stack, pop, and append the popped char onto the
	         * prefix until you reach an end parenthesis
	         */
	        while (operatorStack.peek() != ')') {
	          buildOutfix.append(operatorStack.pop());
	        }
	        operatorStack.pop(); //then pop the stack one more time to get rid of the end parenthesis
	      }
	    }
	  }
	  
	  /*
	   * once we iterate through the infix, check if any operators remain in the stack
	   * if so, loop through the stack and get rid of everything in there and append it to the prefix
	   */
    while (operatorStack.empty() == false) {
      currentSym = operatorStack.pop();
      buildOutfix.append(currentSym);
    }
	  
	  buildOutfix.reverse(); //reverse the stringbuilder since we want the prefix
	  return buildOutfix.toString(); //convert the stringbuilder to string and return it as our prefix
	  
	}
	
  // compares for precedence: op1 is the operator on top of stack, op2 is the incoming operator
	public static boolean precedence(char op1, char op2){
				
    int opcode1, opcode2;
    /* opcode for + or - is 1 */
    /* opcode for * or / is 2 */
    /* opcode for $ is 3 */
	     
    switch (op1) {

      case '+':
      case '-':
        opcode1 = 1;
        break;

      case '*':
      case '/':
        opcode1 = 2;
        break;

      case '$':
        opcode1 = 3;
        break;
      
      default:
        opcode1 = 0;
        break;

    }

    switch (op2) {

      case '+':
      case '-':
        opcode2 = 1;
        break;

      case '*':
      case '/':
        opcode2 = 2;
        break;

      case '$':
        opcode2 = 3;
        break;
      
      default:
        opcode2 = 0;
        break;
    }

    if (opcode1 > opcode2) {
      return true; //top has higher precedence than incoming
    } else {
      return false; //incoming has higher precedence than top OR both have equal precedence
    }
    
	}
	
	public static void main(String args[]){
		String infix, preFix;
		System.out.println("Enter an infix string: ");

		//scanner for input of infix
		Scanner input = new Scanner(System.in);
		infix = input.nextLine();

		preFix = infix_to_prefix(infix); // method to convert
		
		System.out.println("The prefix is " + preFix);
		
		input.close();
	}
}