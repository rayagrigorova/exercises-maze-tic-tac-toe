<h1 align="center">Задачи</h1>

## Maze

Преработете проекта Maze, за да визуализирате лабиринта и пътеката решение

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Maze {
	public int[][] maze;
	public Stack<Position> path = new Stack<Position>();
	public Position start;

	public Maze(String filePath) throws FileNotFoundException {

		Scanner in = new Scanner(new File(filePath));
		int rows = Integer.parseInt(in.nextLine());
		this.maze = new int[rows][];

		for (int i = 0; i < rows; i++) {
			String line = in.nextLine();
			String[] numbers = line.split(", ");
			this.maze[i] = new int[numbers.length];
			for (int j = 0; j < numbers.length; j++) {
				this.maze[i][j] = Integer.parseInt(numbers[j]);
			}
		}

		this.start = new Position(Integer.parseInt(in.nextLine()), Integer.parseInt(in.nextLine()));
	}
}
```

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class MazeSolver {

	// 0 = wall
	// 1 = path
	// 2 = destination

	public static void main(String[] args) {

		try {
			Maze m = new Maze("MazeSolver/mazes.txt");
			if (solveMaze(m)) {
				System.out.println("You won!");
			} else {
				System.out.println("No path");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static boolean solveMaze(Maze m) {
		Position p = m.start;
		m.path.push(p);

		while (true) {
			int y = m.path.peek().y;
			int x = m.path.peek().x;

			m.maze[y][x] = 0; // marking the positions I have already been on with zero, so we avoid going in
								// circles

			// down
			if (isValid(y + 1, x, m)) {
				if (m.maze[y + 1][x] == 2) {
					System.out.println("Moved down");
					return true;
				} else if (m.maze[y + 1][x] == 1) {
					System.out.println("Moved down");
					m.path.push(new Position(y + 1, x));
					continue;
				}
			}

			// left
			if (isValid(y, x - 1, m)) {
				if (m.maze[y][x - 1] == 2) {
					System.out.println("Moved left");
					return true;
				} else if (m.maze[y][x - 1] == 1) {
					System.out.println("Moved left");
					m.path.push(new Position(y, x - 1));
					continue;
				}
			}

			// up
			if (isValid(y - 1, x, m)) {
				if (m.maze[y - 1][x] == 2) {
					System.out.println("Moved up");
					return true;
				} else if (m.maze[y - 1][x] == 1) {
					System.out.println("Moved up");
					m.path.push(new Position(y - 1, x));
					continue;
				}
			}

			// right
			if (isValid(y, x + 1, m)) {
				if (m.maze[y][x + 1] == 2) {
					System.out.println("Moved right");
					return true;
				} else if (m.maze[y][x + 1] == 1) {
					System.out.println("Moved right");
					m.path.push(new Position(y, x + 1));
					continue;
				}
			}

			m.path.pop(); // going back in case we have reached a dead end
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

}
```

```java
public class Position {
	public int x; // column
	public int y; // row
	
	public Position(int y, int x) {
		this.x = x;
		this.y = y;
	}
}
```

## Морски шах

Направете игра морски шах с GUI. Сами преценете какъв точно да е дизайна ви и какви визуални компоненти ще използвате за отделните стъпки. 
