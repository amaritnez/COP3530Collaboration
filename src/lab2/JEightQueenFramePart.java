package lab2;
/*
 * Class: JEightQueenFramePart.java
 * Date last modified: 1/30/2018 12:02PM
 * Version 1.0
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

/*
 * This class is used to draw a chess board with queens.
 * Where the queens are placed on the board is controlled by
 * the instance variable: boolean board[][]. The size of board is 8x8.
 * Each element in board indicates if a queen is there. For example,
 * if board[4][5]==true, there is a queen at row 4 and column 5.
 * 
 * The value of this 2D array is passed in at the constructor. 
 * This class does not decide which cells have queens.
 */
class JChessBoardPanel extends JPanel{
	
	// this variable is used to indicate which cell has a queen
	private boolean board[][];
	
	// the user should pass a 2D boolean array to tell
	// which cells have queens
	public JChessBoardPanel(boolean board[][]){
		super();
		this.board = board;
	}
	
	
	public void drawChessBoard(Graphics g){
		g.setColor(Color.BLACK);

		// get the cell width/height
		int height = getHeight(), width=getWidth();
		int cellHeight = height/8;
		int cellWidth = width/8;

		/* based on code below, write a loop to draw 
		 * all chess board squares	 */
		boolean isWhite = true;
		for (int i = 0; i < 8; i++) {
		  isWhite = !isWhite; //use to alternate color in board
		  for (int j = 0; j < 8; j++) {
		    if (isWhite == true) {
		      g.setColor(Color.WHITE);
		      isWhite = false;
		    } else {
		      g.setColor(Color.BLACK);
		      isWhite = true;
		    }
		    g.fillRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);	
		    repaint();
		  }
		  
		}
			
	}

	public void drawQueens(Graphics g){
		g.setColor(Color.RED);

		/* insert your code here */
		int oHeight = this.getHeight();
		int oWidth = this.getWidth();
		oHeight = oHeight/8;
		oWidth = oWidth/8;
		//takes a quarter of the dimensions so that the oval starts 1/4 past the square
		double startHeight = 0.25*oHeight;
		double startWidth = 0.25*oWidth;
				
		/* write a loop to draw all queens = ovals 
		 * Ovals should be smaller than squares
		 * and centered on the square */
		for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j] == true) {
          g.fillOval((int) (j * oWidth + startWidth), (int) (i * oHeight + startHeight), oWidth / 2, oHeight / 2);
          repaint();
        }
      }
    }
	}
	
	public void paint(Graphics g){
		super.paint(g);
		drawChessBoard(g);
		drawQueens(g);
	}
}

/*
 * This JFrame-based class is the container of JChessBoardPanel.
 * When the user creates a JEightQueenFrame object, he/she is supposed
 * to pass a 2D (8x8) boolean array, board, to tell where the queens should be
 * placed.  The size of board is 8x8. Each element in board indicates if a 
 * queen is there. For example, if board[4][5]==true, there is a queen at 
 * row 4 and column 5.
 */
public class JEightQueenFramePart extends JFrame {
	private boolean board[][];
	private JChessBoardPanel chessBoard;
		
	public JEightQueenFramePart(boolean board[][]){

		super();

		this.board=board;
		
		//setting the layout
		getContentPane().setLayout(new BorderLayout());
		
		//adding the ChessBoard
		getContentPane().add(new JChessBoardPanel(board),
			BorderLayout.CENTER);
		
		//other settings
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(590,610);
		setResizable(false);
		setVisible(true);
	}
	
	public static boolean trial(boolean board[][],int n){
	
		/* add code here */
		for (int i = 0; i < 8; i++) { //iterate through the columns here
		  board[n][i] = true; //assume the current position is true
		  if (n == 7 && good(board, n, i)) {
		    return true;
		  }
		  if (n < 7 && good(board, n, i) && trial(board, n+1) ) { //recursion iterates through the rows
		    return true;
		  }
		  board[n][i] = false;
		}
		return false;
	  
	  
		 /* see trial() code on book pg. 154 */		
		
	}
	/*returns true if the current position is valid for a queen
	 * good is defined as the current position having no queens in the same row, col
	 * or diagonals. returns false otherwise
	 */
	public static boolean good(boolean board[][], int row, int col) {
	  //checks if previous rows have a queen at the current column
	  for (int a = 0; a < row; a++) {
	    if (board[a][col] == true) {
	      return false;
	    }
	  }
	  //checks if previous columns have a queen at the current row
	  for (int b = 0; b < col; b++) {
	    if (board[row][b] == true) {
	      return false;
	    }
	  }
	  //checks the upper left-most diagonals of the current position for a queen
	  int c = row;
	  int d = col;
	  while (c != 0 && d != 0) {
	    if (board[--c][--d] == true) {
	      return false;
	    }
	  }
	  //checks the upper right-most diagonals of the current position for a queen
	  c = row;
	  d = col;
	  while (c != 0 && d < 7) {
	    if (board[--c][++d]) {
	      return false;
	    }
	  }
    return true;
	  
	}
	
	public static void main(String args[]){
		
		/* add code to declare the board and frame */
		boolean [][]board = new boolean[8][8];
		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				board[i][j] = false;
			}
		
		trial(board,0);//see if we can place a queen at row 0
		JEightQueenFramePart queenFrame = new JEightQueenFramePart(board);
		
	}
}

