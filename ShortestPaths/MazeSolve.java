import java.util.Scanner;

/*
 * Calculates the shortest path in a maze from start to finish
 * Beginning from start, recursively checks every legal move in the 4 cardinal directions
 * When end is reached save configuration and path length, update when a shorter path is discovered
 * Performs better than brute force, but still far from ideal
 * This class allows the user to specify the maze, and shows them the before and after the path is founds
 */
public class MazeSolve
{
	// used for user console interaction
	private static Scanner myScan = new Scanner(System.in);
	
	// stores the maze to be solved
	private static char[][] maze;
	
	/*
	 * After using getGrid, displays the maze in both solved and unsolved states
	 */
	public static void runThrough()
	{
		System.out.println("Weclome to the maze solver!");
		
		// used to keep track of good/bad user maze input
		boolean isGridGot = false;
		
		// while the maze input is bad
		while (!isGridGot)
		{
			// get maze from user
			isGridGot = getGrid();
			
			if (!isGridGot)
			{
				System.out.println("Error, please try again");
				System.out.println(); 
			}
		}
		
		// display unsolved maze
		MazeTool.printGrid(maze);
		System.out.println("Unsolved Maze");
		
		System.out.println();
		
		// display solved maze
		MazeTool.printGrid(MazeTool.findPath(maze));
		System.out.println("Solved Maze");
	}
	
	/*
	 * Gets maze from either fromFile, fromInput, or fromPre
	 * Returns whether or not the maze is usable
	 */
	private static boolean getGrid()
	{
		// used to get user input
		String input = "";
		char choice = '0';
		
		// inform user of options
		System.out.println("Choose from the three options");
		System.out.println();
		System.out.println("Enter 'I' if you would like to input a maze through the console");
		System.out.println("Enter 'F' if you would like to read in the maze from a file");
		System.out.println("Enter 'P' if you would like to use a pre-made maze");
		System.out.println();
		
		// get user input
		System.out.print("Enter your choice: ");
		input = myScan.nextLine().toUpperCase();
		choice = input.charAt(0);
		
		System.out.println();
		
		switch(choice)
		{
		case 'I':
			maze = fromInput();
			break;
		case 'F':
			maze = fromFile();
			break;
		case 'P':
			maze = fromPre();
			break;
			
		default:
			return false;
		
		}
		
		System.out.println();
		return MazeTool.checkMaze(maze);	
	}
	
	/*
	 * Gets maze passed via file IO
	 */
	private static char[][] fromFile()
	{
		String fileName = "";
		
		// get file name from user
		System.out.print("Enter a file name: ");
		fileName = myScan.nextLine();
		
		// return true of if the file was read correctly, and the file read contained a useful maze
		return MazeTool.readGrid(fileName);
	}
	
	/*
	 * Gets maze passed via console input
	 */
	private static char[][] fromInput()
	{
		// holds the expected number of user input lines
		int numLines = -1;
		
		// get expected maze height, or number of lines to read in
		System.out.print("What will be the height of your maze?: ");
		numLines = myScan.nextInt();
		myScan.nextLine();
		
		// String List will store lines of maze
		StringList inputLines;
		
		// String to store each line as it is given by the user
		String line = "";
		
		// nodes will be manually linked together and stored as StringList
		Node startNode = null;
		Node currentNode = null;
		
		System.out.println("Enter your entire maze line by line");
		
		// read in the expect number of lines (height of maze)
		for (int i = 0; i < numLines; i++)
		{
			// collect line from user
			line = myScan.nextLine();
			
			// if this is the first Node in the list, create new node and point startNode to it
			if (currentNode == null)
			{
				currentNode = new Node(line, null);
				startNode = currentNode;
			}
			// else add this string to the link of other strings
			else
			{
				currentNode.nextNode = new Node(line, null);
				currentNode = currentNode.nextNode;
			}
		}
		
		// establish String List from connection of nodes
		inputLines = new StringList(startNode);
		
		return inputLines.toCharArray();
	}

	/*
	 * Gets pre made maze
	 */
	private static char[][] fromPre()
	{
		return new char[][]
		{
			{'#','#','#','#','#','#','#','#','#','#','#'},
			{'#',' ','#','#','#',' ',' ',' ','S',' ','#'},
			{'#',' ','#','#','#',' ','#','#','#',' ','#'},
			{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			{'#',' ','#','#','#',' ','#','#','#',' ','#'},
			{'#',' ',' ',' ',' ',' ',' ','E',' ',' ','#'},
			{'#','#','#','#','#','#','#','#','#','#','#'},
			
		};
	}
}
