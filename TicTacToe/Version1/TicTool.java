import java.util.Random;

/*
 * TicTool class contains methods that help in the processing of game grids
 */
public class TicTool
{
	// score constants
	private static int MAX_SCORE = 100;
	private static int MIN_SCORE = -100;
	private static int DRAW_SCORE = 0;
	
	/*
	 * Returns the player passed's corresponding score constant
	 * X is the designated high score
	 */
	public static int getScoreConst(char player)
	{
		switch(player)
		{
		case 'X':
			return MAX_SCORE;
			
		case 'O':
			return MIN_SCORE;
			
		case 'D':
			return DRAW_SCORE;
			
		default:
			System.out.println("Unkown player passed to getScoreConst: " + player);
			return 'N';
		}
	}
	
	/*
	 * Returns the number of empty locations in a grid
	 * Assumes valid tictactoe grid passed
	 */
	public static int getSpaceCount(char[][] grid)
	{
		int spaceCount = 0;
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (grid[i][j] == ' ')
				{
					spaceCount++;
				}
			}
		}
		
		return spaceCount;
	}

	/*
	 * Returns the opponent to the player passed
	 */
	public static char getOpponent(char player)
	{
		switch(player)
		{
		case 'X':
			return 'O';
			
		case 'O':
			return 'X';
			
		default:
			System.out.println("Unfamiliar player passed to getOpponent: " + player);
			return 'N';
		}
	}

	/*
	 * Returns an empty grid, used for the beginning of a game
	 */
	public static char[][] getEmptyGrid()
	{
		char[][] emptyGrid = new char[3][3];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				emptyGrid[i][j] = ' ';
			}
		}
		
		return emptyGrid;
	}
	
	/*
	 * Returns an exact copy of the grid passed, used to create a duplicate object
	 * Assumes valid tictactoe grid passed
	 */
	public static char[][] copyGrid(char[][] grid)
	{
		char[][] copyGrid = new char[3][3];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				copyGrid[i][j] = grid[i][j];
			}
		}
		
		return copyGrid;
	}

	/*
	 * Returns the state of the board with a char representing the outcome
	 * X, O, D, U represent X winning, O winning, draw, and game unfinished accordingly
	 */
	public static char getBoardState(char[][] grid)
	{
		// check for X victory
		if (TicTool.hasPlayerWon(grid, 'X'))
		{
			return 'X';
		}
		
		// check for O victory
		if (TicTool.hasPlayerWon(grid, 'O'))
		{
			return 'O';
		}
		
		// check for a full board, indicating a draw
		if (TicTool.getSpaceCount(grid) == 0)
		{
			return 'D';
		}
		
		// else board must be unfinished
		return 'U';
	}
	
	/*
	 * Returns whether or not the player passed has won the game
	 */
	private static boolean hasPlayerWon(char[][] grid, char player)
	{
		// check for null grid
		if (grid == null)
		{
			System.out.println("null grid accessed by hasPlayerWon");
			return false;
		}
		
		// check rows
		for (int i = 0; i < 3; i++)
		{
			if (grid[i][0] == player && grid[i][1] == player && grid[i][2] == player)
			{
				return true;
			}
		}
		
		// check columns
		for (int i = 0; i < 3; i++)
		{
			// if entire column matches player passed
			if (grid[0][i] == player && grid[1][i] == player && grid[2][i] == player)
			{
				return true;
			}
		}
		
		// check diagonal: \
		if (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player)
		{
			return true;
		}
				
		// check diagonal: /
		if (grid[2][0] == player && grid[1][1] == player && grid[0][2] == player)
		{
			return true;
		}
				
		// player passed must not have won
		return false;
	}

	/*
	 * Returns all of the legal counter moves that can be played by the opponent
	 * Assumes contains legal grid and player
	 */
	public static Move[] getPossibles(Move theMove)
	{
		// stores identical copies of grid
		char[][] gridCopy;
		
		// holds all combinations of moves. Size determined by the number of open spaces in grid
		Move[] moveList = new Move[TicTool.getSpaceCount(theMove.grid)];
		
		// store the move to be inserted into the moveList
		Move newMove;
		
		// stores index location for the grid to be inserted, initially set 0 (indicating no moves have been added
		int nextMoveIndex = 0;
		
		// for every location in the grid
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				// if the grid is empty at this location
				if (theMove.grid[i][j] == ' ')
				{
					// create a copy of the grid and have opponent play at this location
					gridCopy = copyGrid(theMove.grid);
					gridCopy[i][j] = TicTool.getOpponent(theMove.player);
					
					// create a new move with the played grid and the opposing player
					newMove = new Move(gridCopy, TicTool.getOpponent(theMove.player));
					
					// insert the new move into the moveList
					moveList[nextMoveIndex] = newMove;
					nextMoveIndex++;
				}
			}
		}
		
		return moveList;
	}

	/*
	 * Prints the passed tictactoe grid with style
	 * Assumes valid grid
	 */
	public static void printGrid(char[][] grid)
	{
		// handle null grid passed
		if (grid == null)
		{
			System.out.println("Cannot print null grid");
			return;
		}
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				System.out.print(" " + grid[i][j] + " ");
				
				if (j != 2)
				{
					System.out.print("|");
				}
			}
			System.out.println();
			if (i != 2)
			{
				System.out.println("-----------");
			}
		}
	}

	/*
	 * Returns the index of either the smallest or largest number in the array passed
	 * If multiple max/min's exist, return the index of a random max/min
	 */
	public static int getMinMaxIndex(int[] array, boolean goForMax)
	{
		// initially assume best candidate in first element
		int bestNum = array[0];
		
		// counts the number of times the best number occurs
		int bestFreq = 0;
		
		// stores the indexes of best numbers
		int[] bestIndexes;
		int nextOpenIndex = 0;
		
		// search array for best element
		for (int i = 0; i < array.length; i++)
		{
			// if this element in the array is a better candidate, replace it
			if (goForMax ? (array[i] > bestNum) : (array[i] < bestNum))
			{
				bestNum = array[i];
			}
		}
		
		// count frequency of best element
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == bestNum)
			{
				bestFreq++;
			}
		}
		
		bestIndexes = new int[bestFreq];
		
		// save indexes of containing the bestNum
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == bestNum)
			{
				bestIndexes[nextOpenIndex] = i;
				nextOpenIndex++;
			}
		}
		
		// randomly return the index of one of the best numbers
		return bestIndexes[new Random().nextInt(bestFreq)];
	}
	
	/*
	 * Takes input and determines whether or not a legal move has been provided
	 * Returns the grid with the move if it is legal, returns null otherwise
	 */
	public static char[][] checkMove(char[][] grid, char player, int x, int y)
	{
		// if coordinates passed are out of range
		if (x < 0 || x > 2 || y < 0 || y > 2)
		{
			// System.out.println("Coordinates out of bounds");
			return null;
		}
		
		// check if location is empty
		if (grid[y][x] != ' ')
		{
			// System.out.println("checkMove cannot play there");
			return null;
		}
		
		// create a new grid with the desired move played
		char[][] newGrid = TicTool.copyGrid(grid);
		newGrid[y][x] = player;
		
		return newGrid;
	}
}
