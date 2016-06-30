package tictactoe2;

/*
 * Move class contains a grid and the player who played it
 */
public class Move
{
	// move characteristics
	public char[][] grid;
	public char player;
	
	/*
	 * Allows user to set the grid and player
	 */
	public Move(char[][] grid, char player)
	{
		this.grid = grid;
		this.player = player;
	}
	
	/*
	 * Returns the score of the Move
	 * Recursively calls scoreMove until a basic board is presented
	 */
	public int scoreMove()
	{
		// save state of board
		char boardState = TicTool.getBoardState(this.grid);
		
		// if the game is over, return corresponding score
		if (boardState != 'U')
		{
			return TicTool.getScoreConst(boardState);
		}
		
		// list of moves and empty list of their corresponding scores
		Move[] moveList = TicTool.getPossibles(this);
		int[] scoreList = new int[moveList.length];
		
		// determine whether or not to look for max score
		boolean maximize = moveList[0].player == 'X';
		
		// populate scores list through recursive call to scoreMove
		for (int i = 0; i < moveList.length; i++)
		{
			scoreList[i] = moveList[i].scoreMove();
		}
		
		// get index of most outstanding score (max if opponent player is X, min if opponent player is O)
		int MOIndex = TicTool.getMinMaxIndex(scoreList, maximize);
		
		// subtract 1 from the score to account for depth
		return scoreList[MOIndex] - 1;
	}

	/*
	 * Chooses the best possible counter move to the current one
	 * Uses scoreMove to rank outcomes
	 */
	public Move chooseMove()
	{
		Move[] moveList = TicTool.getPossibles(this);
		int[] scoreList = new int[moveList.length];
		
		boolean goForMax = moveList[0].player == 'X';
		
		int OIndex;
		
		for (int i = 0; i < moveList.length; i++)
		{
			scoreList[i] = moveList[i].scoreMove();
		}
		
		OIndex = TicTool.getMinMaxIndex(scoreList, goForMax);
		
		return moveList[OIndex];
	}
}
