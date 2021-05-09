package uprajnenie_6;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class TicTacToe extends JPanel {
	//board е масив, който от начало е пълен само с нули. Нека 1 да ни е O, а 2 - X
	int[][] board; 
	Square[][] squares; 
	//Точките на играч 1 ще са на 0 индекс, а тези на играч 2 - на първи индекс 
	int[] points;
	//Страната на квадратчето 
	int a;
	// player 1 ще е с О, а player 2 - с X
	int currentPlayer; 
	//Позицията на последното кликнато квадратче в масива 
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
		//Определяме кой играч е първи
		currentPlayer = r.nextInt() % 2 + 1;
		if(currentPlayer == 1) {
			currentPlayerLabel.setText("Player 1's turn O");
		}
		//Този с X - овете 
		else {
			currentPlayerLabel.setText("Player 2's turn X");
		}
		pointsLabel = new JLabel("Points:\nPlayer 1:" + points[0] + "\nPlayer 2: " + points[1]);
		restartButton = new JButton("Restart");
		restartButton.setBackground(Color.red);
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Нулираме дъската 
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						board[i][j] = 0;
						squares[i][j].isFull = false;
					}
				}
				currentPlayerLabel.setText("");
				Random r = new Random(); 
				//Определяме кой играч е първи
				currentPlayer = r.nextInt() % 2 + 1;
				gameEnded = false;
				repaint();
			}
			
		});
		this.add(currentPlayerLabel);
		this.add(pointsLabel);
		this.add(restartButton);
		
		this.setBounds(new Rectangle(200, 200, 400, 400));
		
		//Размер на страната, * 0.8, за да не се опира таблицата в панела 
		a = (int)(this.getBounds().width * 0.8) / 3; 
		iLast = -1;
		jLast = -1;
		squares = new Square[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				int xLeft = (int) (a * i + this.getBounds().height * 0.1);
				int yLeft = (int) (a * j + this.getBounds().width* 0.1);
				
				System.out.print("(" + xLeft + "," + yLeft + ")  ");
				squares[i][j] = new Square(xLeft, yLeft);
			}
			System.out.println();
		}
		System.out.println("a = " + a);
		
		this.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	if(!gameEnded) {
			        int x = e.getX();
			        int y = e.getY();
			        System.out.println("x = " + x + " y = " + y);
			        for(int i = 0; i < 3; i++) {
			        	for(int j = 0; j < 3; j++) {
			        		if(inSquare(i, j, x, y)) {
			        			System.out.println("i: " + i  + " j: " + j);
			        			iLast = i;
			        			jLast = j;
			        			board[i][j] = currentPlayer; 
			        			squares[i][j].isFull = true;
			        			currentPlayer = currentPlayer % 2 + 1;
			        			if(gameEnded()) {gameEnded = true; break;} 
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
			public void mousePressed(MouseEvent e) {
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
		print();
		g.setColor(Color.black);
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++){
				int xLeft = squares[i][j].x;
				int yLeft = squares[i][j].y;
				g.drawRect(xLeft, yLeft, a, a);
				switch(board[i][j]) {
				case 0: // рисува се само правоъгълник 
					break;
				case 1: //Рисува се O
					g.drawOval((int)(xLeft + 0.25 * a),(int)(yLeft + 0.25 * a), (int)((a / 2) * 0.7),(int) ((a / 2) * 0.7));
					break;
				case 2: //Рисува се X
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
		//Проверяваме за победа на последния играл 
		// player 1 ще е с О, а player 2 - с X

		if(checkHorizontally() || checkVertically() || checkDiagonally()) {
			currentPlayerLabel.setText("Player " + currentPlayer + " won!");
			points[currentPlayer - 1 ]++;
			return true;
		}
		
		//Ако никой не е победил, то търсим дали има равенство 
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == 0) return false;
			}
		}
		//Има равенство, ако всички полета са запълнени и няма победител 
		currentPlayerLabel.setText("Tie!");
		return true;
		
	}
	public boolean checkHorizontally() {
		//Трябва да промерим дали всички числа на реда са равни на currentPlayer 
		for(int j = 0; j < 3; j++) {
			if(board[iLast][j] != currentPlayer) return false;
		}
		return true;
	}
	public boolean checkVertically() {
		//Трябва да проверим дали всички числа на колоната са равни на currentPlayer 
		for(int i = 0; i < 3; i++) {
			if(board[i][jLast] != currentPlayer) return false;
		}
		return true;
	}
	
	public boolean checkDiagonally() {
		//Диагоналите на масива са: [0][0] [1][1] [2][2] и [0][2] [1][1] [2][0]
		//Последната стъпка не е била по някой от диагоналите, значи няма как да има победа по диагоналите 
		if(iLast != jLast && iLast + jLast != 2) {
			return false;
		}
		
		boolean first = true, second = true; 
		//Първи диагонал [0][0] [1][1] [2][2]
		for(int i = 0; i < 3; i++) {
			if(board[i][i] != currentPlayer) {first = false; break;}
		}
		//Втори диагонал 
		for(int i = 0; i < 3; i++) {
			if(board[i][2 - i] != currentPlayer) {second = false; break;}
		}
		
		if(first || second) return true;
		return false;
	}
	
	public void print() {
		System.out.println();
		System.out.println();
		System.out.println();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
}
