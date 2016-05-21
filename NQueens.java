/*
 * Used to generate the possible ways of placing N queens on an N-by-N chess board so that none threaten the other
 * A queens is threatened if another queen is in its row, column, or diagonal
 * Visits every board configuration, unless shown to not contain a solution
 */
public class NQueens
{
	// used to toggle between printing progress of each move (move is promising, move is NOT promising etc.)
	private boolean showProgress;
	
	/*
	 * Assume user does not want progress to be shown
	 */
	public NQueens()
	{
		showProgress = false;
	}
	
	/*
	 * Prints the grid passed, Queens (trues) printed as 'Q', blanks (falses) printed as 'X'
	 */
	private void printGrid(boolean[][] grid)
	{
		for (int i = 0; i < grid.length; i++)
		{
			// end method if grid dimensions are not square
			if (grid.length != grid[i].length)
			{
				return;
			}
			
			for (int j = 0; j < grid[i].length; j++)
			{
				// if there is a queen at this location
				if (grid[i][j])
				{
					System.out.print(" Q ");
				}
				else
				{
					System.out.print(" X ");
				}
			}
			
			System.out.println();
		}
	}

	/*
	 * Returns whether or not the location passed is being threatened by queens
	 */
	private boolean isPromising(boolean[][] grid, int y, int x)
	{
		// check rows
		for (int i = 0; i < grid.length; i++)
		{
			if (i != y && grid[i][x])
			{
				return false;
			}

		}
		
		// check columns
		for (int i = 0; i < grid.length; i++)
		{
			if (i != x && grid[y][i])
			{
				return false;
			}
		}
		
		// check diagonals
		for (int i = 1; i < grid.length; i++)
		{
			// check top left
			if (y - i > -1 && x - i > -1)
			{
				if (grid[y - i][x - i])
				{
					return false;
				}
			}
			
			// check top right
			if (y - i > -1 && x + i < grid.length)
			{
				if (grid[y - i][x + i])
				{
					return false;
				}
			}
			
			// check bottom left
			if (y + i < grid.length && x - i > -1)
			{
				if (grid[y + i][x - i])
				{
					return false;
				}
			}
			
			// check bottom right
			if (y + i < grid.length && x + i < grid.length)
			{
				if (grid[y + i][x + i])
				{
					return false;
				}
			}
		}
		
		// location must be promising
		return true;
	}
	
	/*
	 * Copies all contents of oldGrid over to newGrid
	 * Ends method if grid sizes do not match
	 */
	private void copyTo(boolean[][] oldGrid, boolean[][] newGrid)
	{
		// catches grid size difference
		if (oldGrid.length != newGrid.length || oldGrid[0].length != newGrid[0].length)
		{			
			System.out.println("Grid sizes do not match...");
			return;
		
		}
		
		// copy
		for (int i = 0; i < oldGrid.length; i++)
		{
			for (int j = 0; j < oldGrid[i].length; j++)
			{
				newGrid[i][j] = oldGrid[i][j];
			}
		}
	}
	
	/*
	 * Traverses through every configuration of queens, unless no solution is reachable
	 * Prints each solution found
	 */
	private void helpFindBoard(boolean[][] grid, int y, int x)
	{
		// grid dimensions
		int dims = grid.length;
		
		// ensure x is within grid
		if (x >= dims)
		{
			return;
		}
		
		// indicates whether or not the location is threatened
		boolean isLocSafe = isPromising(grid, y, x);
		
		// show user success of moves if showProgress is true
		if (showProgress)
		{
			if (isLocSafe)
			{
				System.out.println("Row " + y + " column " + x + " is promising");
			}
			else
			{
				System.out.println("Row " + y + " column " + x + " is NOT promising");
			}
		}
		
		// if the location is not threatened
		if (isLocSafe)
		{
			// create copy of grid with current location added
			boolean[][] gridCopy = new boolean[dims][dims];
			copyTo(grid, gridCopy);
			gridCopy[y][x] = true;
		
			// if this is the last row, it must be a solution
			if (y == (dims - 1))
			{
				printGrid(gridCopy);
				System.out.println();
			}
			
			// else go to next row, starting at the left most column (x = 0)
			else
			{
				helpFindBoard(gridCopy, y + 1, 0);
			}
		}
		
		// continue regardless of whether or not the location was shown to be threatened
		helpFindBoard(grid, y, x + 1);
	}
	
	/*
	 * Runs helpFindBoard with the initial position being 0, 0 (upper left corner)
	 */
	public void findBoard(boolean[][] grid)
	{
		helpFindBoard(grid, 0, 0);
	}
	
	/*
	 * Allow user to change whether or not move success messages are shown
	 */
	public void setShowProgress(boolean b)
	{
		showProgress = b;
	}
}
