package maze4;

import java.io.*;

/*
 * Contains the all of the methods involved in the solving process
 * Most useful method is findPath() which uses a combination of the other methods to return the solution of the maze passed
 */
public class MazeTool
{
	// the minimum path and the length of the path
	private static char[][] minGrid;
	private static int minPathLength;
	
	/*
	 * Returns whether or not the grid passed would work as a maze
	 * Widths must be consistent and there must be a start and finish labeled 'S' and 'E' accordingly
	 */
	public static boolean checkMaze(char[][] grid)
	{
		// if the grid passed is null
		if (grid == null)
		{
			return false;
		}
		
		// if the start is not present in the grid
		if (findChar(grid, 'S')[0] == -1)
		{
			return false;
		}
		
		// if the end is not present in the grid
		if (findChar(grid, 'E')[0] == -1)
		{
			return false;
		}
		
		// length of first line will be compared against all other lines
		int firstLineLength = grid[0].length;
		
		// check for grid width inconsistencies
		for (int i = 0; i < grid.length; i++)
		{
			// if current line length is not the same as first line length
			if (grid[i].length != firstLineLength)
			{
				return false;
			}
		}
		
		// grid must be maze worthy
		return true;
	}
	
	/*
	 * Returns the number of times the letter occurs in the char[][] passed
	 * Returns -1 if the grid passed is null
	 */
	public static int countLetter(char[][] grid, char letter)
	{
		// handles null grid being passed
		if (grid == null)
		{
			System.out.println("Null grid");
			return -1;
		}
		
		int letterCount = 0;
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				// increment letter count if the letter is at the location
				if (grid[i][j] == letter)
				{
					letterCount++;
				}
			}
		}
		
		return letterCount;
	}
	
	/*
	 * Reads in the file given and outputs a char[][] grid matching the file
	 * Returns null if there is a problem reading the file
	 */
	public static char[][] readGrid(String fileName)
	{
		try
		{
			// file interaction
			FileReader myFR = new FileReader(fileName);
			BufferedReader myBR = new BufferedReader(myFR);
			
			String line = myBR.readLine();
			
			// currentNode will be used to string lines together, startNode will be the head of the String list
			Node currentNode = new Node(line, null);
			Node startNode = currentNode;
			
			// string used to hold lines
			StringList lineList;
			
			// scan through file's lines
			while (line != null)
			{
				line = myBR.readLine();
				
				// end loop once the 
				if (line == null)
				{
					break;
				}
				
				// insert new line into list and continue traversal
				currentNode.nextNode = new Node(line, null);
				currentNode = currentNode.nextNode;
			}
			
			// establish lineList as list with head being starNode
			lineList = new StringList(startNode);
			lineList.updateLength();
			
			// close bufferedReader
			myBR.close();
			
			// return completed grid, or null if file could not be read
			return lineList.toCharArray();
		}
		catch(Exception e)
		{
			// System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * Returns an length 2 int[] containing the y and x coordinates of the letter passed
	 * Returns (-1, -1) if char not in grid
	 */
	public static int[] findChar(char[][] grid, char letter)
	{
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				if (grid[i][j] == letter)
				{
					return new int[] {i, j};
				}
			}
		}
		
		return new int[] {-1, -1};
	}

	/*
	 * Returns the character at the given location
	 * Returns '*' if location is not in the grid
	 */
	public static char getCharAt(char[][] grid, int y, int x)
	{
		// if the location is not in the grid
		if ((y < 0 || y >= grid.length) || (x < 0 || x >= grid.length))
		{
			return '*';
		}
		
		return grid[y][x];
	}

	/*
	 * Returns whether or not the current location passed (y and x) matches the location of the S character
	 */
	public static boolean isGameOver(char[][] grid, int y, int x)
	{
		int[] coords = findChar(grid, 'E');
		
		return coords[0] == y && coords[1] == x;
	}

	/*
	 * Prints the contents of grid
	 */
	public static void printGrid(char[][] grid)
	{
		if (grid == null)
		{
			System.out.println("Null grid passed");
			return;
		}
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}

	/*
	 * Returns whether or not the coordinates represent a legal move
	 * A move is illegal if it is outside the grid, covered by a '#' (wall), or covered by an 'X' (path taken)
	 */
	public static boolean isLegalMove(char[][] grid, int y, int x)
	{
		// if the location outside of the grid
		if ((y < 0 || y >= grid.length) || (x < 0 || x >= grid[0].length))
		{
			return false;
		}
		
		// if the location is a wall or a crossing of the path taken
		if (grid[y][x] == '#' || grid[y][x] == 'X')
		{
			return false;
		}
		
		// move must be legal
		return true;
	}

	/*
	 * Copies contents of oldGrid to newGrid
	 * Exits of grids do not share dimensions
	 */
	public static void copyGrid(char[][] oldGrid, char[][] newGrid)
	{
		// if either grid is null
		if (oldGrid == null || newGrid == null)
		{
			return;
		}
		
		// if dimensions are inconsistent
		if (oldGrid.length != newGrid.length && oldGrid[0].length != newGrid[0].length)
		{
			return;
		}
		
		// copy
		for (int i = 0; i < oldGrid.length; i++)
		{
			for (int j = 0; j < oldGrid[0].length; j++)
			{
				newGrid[i][j] = oldGrid[i][j];
			}
		}
	}

	/*
	 * Prints path if the end of the maze has been reached
	 * Recursively calls findPath of adjacent spaces until end is reached
	 */
	private static void helpFindPath(char[][] grid, int y, int x)
	{		
		// if the path traveled is already farther than an already traveled path, abandon current path
		if (MazeTool.countLetter(grid, 'X') >= minPathLength)
		{
			return;
		}
		
		// if location passed match the location of 'E' (end of maze has been reached)
		if (MazeTool.isGameOver(grid, y, x))
		{
			// added after game is checked to so 'E' (end) can be detected
			grid[y][x] = 'X';
			
			// if this path is the shortest yet, update minPath
			if (MazeTool.countLetter(grid, 'X') < minPathLength)
			{
				minGrid = grid;	
				minPathLength = MazeTool.countLetter(grid, 'X');
			}
			
			// because end was reached, exit this branch of the recursion
			return;
		}
		
		// add current location to the path traveled
		grid[y][x] = 'X';
		
		// grid used to store new move
		char[][] newGrid;
		
		// try up move
		if (MazeTool.isLegalMove(grid, y - 1, x))
		{
			// create new grid identical to grid
			newGrid = new char[grid.length][grid[0].length];
			MazeTool.copyGrid(grid, newGrid);
			
			// recursively call findPath on checked location
			helpFindPath(newGrid, y - 1, x);
		}
		
		// try down move
		if (MazeTool.isLegalMove(grid, y + 1, x))
		{
			// create new grid identical to grid
			newGrid = new char[grid.length][grid[0].length];
			MazeTool.copyGrid(grid, newGrid);
			
			// recursively call findPath on checked location
			helpFindPath(newGrid, y + 1, x);
		}
		
		// try left move
		if (MazeTool.isLegalMove(grid, y, x - 1))
		{
			// create new grid identical to grid
			newGrid = new char[grid.length][grid[0].length];
			MazeTool.copyGrid(grid, newGrid);
			
			// recursively call findPath on checked location
			helpFindPath(newGrid, y, x - 1);
		}
		
		// try right move
		if (MazeTool.isLegalMove(grid, y, x + 1))
		{
			// create new grid identical to grid
			newGrid = new char[grid.length][grid[0].length];
			MazeTool.copyGrid(grid, newGrid);
			
			// recursively call findPath on checked location
			helpFindPath(newGrid, y, x + 1);
		}
	}

	/*
	 * Returns the shortest path from 'S' to 'E'
	 * Path taken is represented by 'X's
	 */
	public static char[][] findPath(char[][] grid)
	{
		// probably safe to assume that any maze passed will have a shorted path than 2147483647
		minPathLength = Integer.MAX_VALUE;
		
		// find location of the starting location
		int[] start = MazeTool.findChar(grid, 'S');
		
		// start searching for the path
		helpFindPath(grid, start[0], start[1]);
		
		return minGrid;
	}
}
