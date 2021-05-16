package uprajnenie_6;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.*;

public class MazePanel extends JPanel {
	Maze m; 
	//Let the free spaces be white, the walls - grey, and the path - green 
	//I will mark the path with the value 3
	
	JButton findButton;
	JButton clearButton; 
	JLabel solvableLabel; 
	MazePanel(){			
		solvableLabel = new JLabel("");
		
		findButton = new JButton("Find Path");
		findButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean solvable = solveMaze();
				if(solvable) {
					solvableLabel.setText("Path found");
				}
				else {
					solvableLabel.setText("Path not found");
				}
				repaint();
				
			}
			
		});
		
		clearButton = new JButton("Clear Path");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					m = new Maze("mazes.txt");
				}
				catch (FileNotFoundException e1) {
					System.out.println("dead");
					e1.printStackTrace();
				}
				solvableLabel.setText("");
				repaint();
				
			}
			
		});
		this.add(findButton);
		this.add(clearButton);
		this.add(solvableLabel);
	}
	private boolean solveMaze() {
		try {
			m = new Maze("mazes.txt");
		}
		catch (FileNotFoundException e) {
			System.out.println("dead");
			e.printStackTrace();
		}
		Position p = m.start;
		m.path.push(p);
	
		while (true) {
			int y = m.path.peek().y;
			int x = m.path.peek().x;
	
			m.maze[y][x] = 3; // marking the positions I have already been on with zero, so we avoid going in
								// circles
	
			// down
			if (isValid(y + 1, x, m)) {
				if (m.maze[y + 1][x] == 2) {
					//Стигнали сме целта 
					m.maze[y + 1][x] = 3; 
					System.out.println("Moved down");
					return true;
				} else if (m.maze[y + 1][x] == 1) {
					m.maze[y + 1][x] = 3;
					System.out.println("Moved down");
					m.path.push(new Position(y + 1, x));
					continue;
				}
			}
	
			// left
			if (isValid(y, x - 1, m)) {
				if (m.maze[y][x - 1] == 2) {
					m.maze[y][x - 1] = 3;
					System.out.println("Moved left");
					return true;
				} else if (m.maze[y][x - 1] == 1) {
					m.maze[y][x - 1] = 3;
					System.out.println("Moved left");
					m.path.push(new Position(y, x - 1));
					continue;
				}
			}
	
			// up
			if (isValid(y - 1, x, m)) {
				if (m.maze[y - 1][x] == 2) {
					m.maze[y - 1][x] = 3;
					System.out.println("Moved up");
					return true;
				} else if (m.maze[y - 1][x] == 1) {
					m.maze[y - 1][x] = 3;
					System.out.println("Moved up");
					m.path.push(new Position(y - 1, x));
					continue;
				}
			}
	
			// right
			if (isValid(y, x + 1, m)) {
				if (m.maze[y][x + 1] == 2) {
					m.maze[y][x + 1] = 3;
					System.out.println("Moved right");
					return true;
				} else if (m.maze[y][x + 1] == 1 ) {
					m.maze[y][x + 1] = 3;
					System.out.println("Moved right");
					m.path.push(new Position(y, x + 1));
					continue;
				}
			}
	
			Position deadEndPos =  m.path.pop(); // going back in case we have reached a dead end
			//We must color the dead end in white 
			m.maze[deadEndPos.y][deadEndPos.x] = 4; 
			System.out.println("Moved back");
			if (m.path.size() <= 0) { // nowhere new to go
				return false;
			}
		}
	}

	public static boolean isValid(int y, int x, Maze m) {
		if (y < 0 || y >= m.maze.length || x < 0 || x >= m.maze[y].length) {
			return false;
		}
		return true;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(m != null) {
			for(int i = 0; i < m.maze.length; i++) {
				for(int j = 0; j < m.maze[i].length; j++) {
					drawSquare(i, j, g);
				}
			}
		}
	}
	public void drawSquare(int i, int j, Graphics g) {
		//The number of squares on a given row will be squaresToColor[i].length
		//The side of the square will be = the width of the element / the number of columns on the row 
		int a = this.getBounds().width / m.maze[i].length;
		
		int x = a * j;
		int y = a * i + 100;
		
		// 0 = wall
		// 1 = path
		// 2 = destination
		
		switch(m.maze[i][j]) {
		case 0: //Wall, color in grey 
			g.setColor(Color.GRAY);
			g.fillRect(x, y, a, a);
			break;
		case 1: //Blank, white 
		case 4:
			g.setColor(Color.WHITE);
			g.fillRect(x, y, a, a);
			break;
		case 2://Destination, green 
		case 3://Path, green 
			g.setColor(Color.GREEN);
			g.fillRect(x, y, a, a);
			break;
		}
	}
}
