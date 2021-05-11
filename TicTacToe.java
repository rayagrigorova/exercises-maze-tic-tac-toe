package uprajnenie_6;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class TicTacToe extends JPanel {
	//Board is an array, which is only filled with zeroes in the beginning. 
	//Let O be 1 and X - 2
	int[][] board; 
	Square[][] squares; 
	//The points of player 1 will be on index 0 and the points of player 2 - on index 1
	int[] points;
	//Side of the square  
	int a;
	// player 1 ще е с О, а player 2 - с X
	int currentPlayer; 
	//Position of the last clicked square in the array 
	int iLast, jLast; 
	boolean gameEnded = false;
	
	JButton restartButton;
	JLabel currentPlayerLabel; 
	JLabel pointsLabel; 
	
	TicTacToe(){
		board = new int [3][3];
		points = new int[2];
		currentPlayerLabel = new JLabel("");
		Random r = new Random(); 
		//We determine which player will be the first to play 
		currentPlayer = r.nextInt(100) % 2 + 1;
		if(currentPlayer == 1) {
			currentPlayerLabel.setText("Player 1's turn O");
		}
		else {
			currentPlayerLabel.setText("Player 2's turn X");
		}
		pointsLabel = new JLabel("Points:\nPlayer 1:" + points[0] + "\nPlayer 2: " + points[1]);
		restartButton = new JButton("Restart");
		restartButton.setBackground(Color.RED);
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Set all values on the board to 0
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						board[i][j] = 0;
						squares[i][j].isFull = false;
					}
				}
				currentPlayerLabel.setText("");
				Random r = new Random(); 
				// We determine which player will be the first to play 
				currentPlayer = r.nextInt(100) % 2 + 1;
				gameEnded = false;
				repaint();
			}
			
		});
		this.add(currentPlayerLabel);
		this.add(pointsLabel);
		this.add(restartButton);
		
		this.setBounds(new Rectangle(200, 200, 400, 400));
		
		a = (int)(this.getBounds().width * 0.8) / 3; 
		iLast = -1;
		jLast = -1;
		squares = new Square[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				int xLeft = (int) (a * j + this.getBounds().width * 0.1);
				int yLeft = (int) (a * i + this.getBounds().height* 0.1);
				
				squares[i][j] = new Square(xLeft, yLeft);
			}
			System.out.println();
		}
		this.addMouseListener(new MouseListener() {
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	if(!gameEnded) {
			        int x = e.getX();
			        int y = e.getY();
			        for(int i = 0; i < 3; i++) {
			        	for(int j = 0; j < 3; j++) {
			        		if(inSquare(i, j, x, y)) {
			        			iLast = i;
			        			jLast = j;
			        			board[i][j] = currentPlayer; 
			        			squares[i][j].isFull = true;
			        			if(gameEnded()) {gameEnded = true; break;} 
			        			currentPlayer = (currentPlayer == 1)? 2 : 1;
			        			if(currentPlayer == 1) {
			        				currentPlayerLabel.setText("Player 1's turn O");
			        			}
			        			else {
			        				currentPlayerLabel.setText("Player 2's turn X");
			        			}
			        			break;
			        		}
			        	}
			        }
			        pointsLabel.setText("Points:\nPlayer 1:" + points[0] + "\nPlayer 2: " + points[1]);
			        repaint();
		    	}
		    }

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		print();
		g.setColor(Color.black);
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++){
				int xLeft = squares[i][j].x;
				int yLeft = squares[i][j].y;
				g.drawRect(xLeft, yLeft, a, a);
				switch(board[i][j]) {
				case 0: // Just draw a square 
					break;
				case 1: //Draw an O in the square 
					g.drawOval(xLeft, yLeft, a , a );
					break;
				case 2: //Draw an X in the square 
					g.drawLine(xLeft, yLeft, xLeft + a, yLeft + a);
					g.drawLine(xLeft + a, yLeft, xLeft, yLeft + a);
					break;	
				
				}
			}
		}
	}
	
	public boolean inSquare(int i, int j, int x, int y) {
		Square square = squares[i][j];
		if(!square.isFull && x >= square.x && x <= square.x + a && y >= square.y && y <= square.y + a) {
			return true;
		}
		return false;
	}
	public boolean gameEnded() {
		//We check if the last player to play has won 
		if(checkHorizontally() || checkVertically() || checkDiagonally()) {
			currentPlayerLabel.setText("Player " + currentPlayer + " won!");
			points[currentPlayer - 1 ]++;
			return true;
		}
		
		//If nobody has won, we check for a tie 
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == 0) return false;
			}
		}
		//There is a tie if all fields are full and there is no winner 
		currentPlayerLabel.setText("Tie!");
		return true;
		
	}
	public boolean checkHorizontally() {
		//We must check if all numbers on the row are equal to currentPlayer 
		for(int j = 0; j < 3; j++) {
			if(board[iLast][j] != currentPlayer) return false;
		}
		return true;
	}
	public boolean checkVertically() {
		//We must check if all numbers on the column are equal to currentPlayer 
		for(int i = 0; i < 3; i++) {
			if(board[i][jLast] != currentPlayer) return false;
		}
		return true;
	}
	
	public boolean checkDiagonally() {
		//The diagonals of the array are: [0][0] [1][1] [2][2] и [0][2] [1][1] [2][0]
		//If the last step wasn't on a diagonal, there can't be a diagonal win 
		if(iLast != jLast && iLast + jLast != 2) {
			return false;
		}
		
		boolean first = true, second = true; 
		//First diagonal [0][0] [1][1] [2][2]
		for(int i = 0; i < 3; i++) {
			if(board[i][i] != currentPlayer) {first = false; break;}
		}
		//Second diagonal  
		for(int i = 0; i < 3; i++) {
			if(board[i][2 - i] != currentPlayer) {second = false; break;}
		}
		
		if(first || second) return true;
		return false;
	}
	
//	public void print() {
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		for(int i = 0; i < 3; i++) {
//			for(int j = 0; j < 3; j++) {
//				System.out.print(board[i][j] + " ");
//			}
//			System.out.println();
//		}
//	}
}
