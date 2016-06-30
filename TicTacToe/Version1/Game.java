/*
 * Tic Tac Toe
 * Three play modes are available: User vs User, User vs CPU, or CPU vs CPU
 * CPU uses minimax algorithm to choose best move to make
 * Good luck beating the CPU!
 */

import java.util.Scanner;

/*
 * Game class handles all user interaction, including game modes
 */
public class Game
{
	private static Scanner myScan = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		Game.topLevel();
	}
	
	/*
	 * Main game method
	 * User selects which mode they would like to play
	 * Displays winner
	 */
	public static void topLevel()
	{
		// inform user of options
		System.out.println("Choose which Tic Tac Toe game mode you would like to play");
		System.out.println("Enter C for CPU vs CPU game");
		System.out.println("Enter U for User vs User game");
		System.out.println("Enter M for User vs CPU game");
		
		// used to ensure user supplies valid game mode input
		boolean isAnsGood = true;
		
		// used in direct user console retrieval
		String Sresp;
		char Cresp;
		
		// outcome of game
		char outCome;
		
		// retrieve choice from user
		do
		{
			// accept user input
			System.out.println();
			System.out.print("Enter your choice: ");
			Sresp = myScan.nextLine().toUpperCase();
			Cresp = Sresp.charAt(0);
			
			
			// isAnsGood reflects whether or not the choice was useful
			if (Cresp != 'C' && Cresp != 'U' && Cresp != 'M')
			{
				System.out.println("Unrecognized choice. Try again");
				isAnsGood = false;
			}
			else
			{
				isAnsGood = true;
			}
			
		} while (!isAnsGood);
		
		System.out.println();
		System.out.println("For convienience, X goes first");
		System.out.println();
		
		// select game mode based on user choice
		switch(Cresp)
		{
		case 'C':
			outCome = CPUvsCPU();
			break;
			
		case 'U':
			outCome = USERvsUSER();
			break;
			
		case 'M':
			outCome = USERvsCPU();
			break;
			
		default:
			outCome = 'L';
			System.out.println("Error unrecognized choice: " + Cresp);
		}
		
		// inform user of game post game information
		System.out.println("GAME OVER");
		if (outCome == 'D')
		{
			System.out.println("It's a draw!");
		}
		else
		{
			System.out.println(outCome + " wins!");
		}
	}
	
	/*
	 * Pits the two computer players against each other
	 * X goes first
	 * Returns winner
	 */
	public static char CPUvsCPU()
	{
		// currentMove initially holds empty grid, grid will be used to store state of board
		char[][] grid = TicTool.getEmptyGrid();
		Move currentMove = new Move(grid, 'O');
		
		// display (empty) initial board
		TicTool.printGrid(grid);
		System.out.println();
		
		// the game board is shown to be unfinished
		while (TicTool.getBoardState(grid) == 'U')
		{
			// declare whose turn it is
			System.out.println(TicTool.getOpponent(currentMove.player) + "'s turn");
			System.out.println();
			
			// make move and adjust grid
			currentMove = currentMove.chooseMove();
			grid = currentMove.grid;
			
			// display board and who played it
			System.out.println(currentMove.player + " played: ");
			TicTool.printGrid(grid);
			System.out.println();
		}
		
		// return the winner
		return TicTool.getBoardState(grid);
	}
	
	/*
	 * Pits user against CPU
	 * User can choose to be 'X' or 'O', but X goes first
	 * Returns winner
	 */
	public static char USERvsCPU()
	{
		// grid storing game board
		char[][] gameGrid = TicTool.getEmptyGrid();
		
		// used to determine stopping point of while loop (evaluated each turn)
		char gameState;
		
		// used to alternate between user and CPU turns
		boolean userGoes;
		
		// store player user will play as
		char user = Game.getUserPlayer();
		System.out.println();
		
		// display (empty) initial board
		TicTool.printGrid(gameGrid);
		System.out.println();
		
		// if user has chosen 'X' they will start
		userGoes = (user == 'X');
		
		do
		{
			// display's who's turn it is
			System.out.println((userGoes ? user : TicTool.getOpponent(user)) + "'s turn");
			System.out.println();
			
			// user's turn
			if (userGoes)
			{
				// update gameGrid to match user's move
				gameGrid = Game.getUserGrid(gameGrid, user);
				System.out.println();
			}
			
			// CPU's turn
			else
			{
				// update gameGrid to match CPU's move
				gameGrid = new Move(gameGrid, user).chooseMove().grid;
			}
			
			// display board and who played
			System.out.println((userGoes ? user : TicTool.getOpponent(user)) + " played");
			TicTool.printGrid(gameGrid);
			System.out.println();
			
			// switch who goes next turn
			userGoes = !userGoes;
			
			// game state is updated after move has been made
			gameState = TicTool.getBoardState(gameGrid);
			
		} while (gameState == 'U');
		
		// return the winner (or draw)
		return gameState;
	}
	
	/*
	 * Pits user against user
	 * X goes first
	 */
	public static char USERvsUSER()
	{
		// grid storing game board
		char[][] gameGrid = TicTool.getEmptyGrid();
		
		// used to determine stopping point of while loop (evaluated each turn)
		char gameState;
		
		// X is the first to go
		char whoTurn = 'X';
		
		// display (empty) initial board
		TicTool.printGrid(gameGrid);
		System.out.println();
				
		do
		{
			// display's who's turn it is
			System.out.println(whoTurn + "'s turn");
			System.out.println();
			
			// update gameGrid to match user's move
			gameGrid = Game.getUserGrid(gameGrid, whoTurn);
			System.out.println();
			
			// display results of move
			System.out.println(whoTurn + " played:");
			TicTool.printGrid(gameGrid);
			System.out.println();
			
			// update state of game
			gameState = TicTool.getBoardState(gameGrid);
			
			// switch whose turn it is
			whoTurn = TicTool.getOpponent(whoTurn);
			
		} while (gameState == 'U');
		
		// return the winner (or draw)
		return gameState;
	}
	
	/*
	 * Retrieves move from user
	 */
	public static char[][] getUserGrid(char[][] oldGrid, char player)
	{
		// copy of current grid passed
		char[][] currentGrid = TicTool.copyGrid(oldGrid);
		
		// able to test move's legality before assigning it to currentMove
		char[][] testGrid;
		
		// coordinates of move
		int xCord;
		int yCord;
		
		// run until valid input received
		do
		{
			// get x coordinate
			System.out.print("Enter the column: ");
			xCord = myScan.nextInt();
			myScan.nextLine();
			
			// get y coordinate
			System.out.print("Enter the row: ");
			yCord = myScan.nextInt();
			myScan.nextLine();
			
			// place desired move onto grid
			testGrid = TicTool.checkMove(currentGrid, player, xCord, yCord);
			
			// move place was unsuccessful
			if (testGrid == null)
			{
				System.out.println();
				System.out.println("Illegal Move. Try another");
				System.out.println();
			}
			else
			{
				currentGrid = testGrid;
			}
			
		} while(testGrid == null);
		
		// return grid
		return currentGrid;
	}

	/*
	 * Retries player preference from user
	 */
	public static char getUserPlayer()
	{
		// signals the cease of do while
		boolean isInputGood;
		
		// gets user input
		String stringGrab;
		
		do
		{
			// get raw user player input
			System.out.print("Enter the player you would like to be (X or O): ");
			stringGrab = myScan.nextLine().toUpperCase();
			
			// if the input is not 'X' or 'O', loop will continue
			if (stringGrab.length() != 1 || (stringGrab.charAt(0) != 'X' && stringGrab.charAt(0) != 'O'))
			{
				System.out.println("Unrecognized player entered. Try again");
				System.out.println();
				isInputGood = false;
			}
			// good input ends the loop
			else
			{
				isInputGood = true;
			}
			
		} while (!isInputGood);
		
		// return the player
		return stringGrab.charAt(0);
	}
}
