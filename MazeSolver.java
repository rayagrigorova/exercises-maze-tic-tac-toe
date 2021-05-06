package uprajnenie_6;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;

public class MazeSolver {

	// 0 = wall
	// 1 = path
	// 2 = destination

	public static void main(String[] args) {
		JFrame frame = new JFrame ("Maze");
		MazePanel panel = new MazePanel();
		frame.add(panel);
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
