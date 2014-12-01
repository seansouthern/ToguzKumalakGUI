import java.io.Serializable;



public class FinalTogizBoard implements Serializable
{
	private FinalCup[][] board;
	private FinalCup[] winnings;
	private int BOARDWIDTH = 10;
	private int player;
	private int lastWinnings;
	
	public FinalTogizBoard()
	{
		board = new FinalCup[2][BOARDWIDTH];
		winnings = new FinalCup[2];
		initializeBoard();
		player = 0;
	}

	public FinalCup[][] getBoard()
	{
		return board;
	}

	public void initializeBoard()
	{
		for(int i = 0; i < 2; i++)
		{	
			for(int j = 0; j < BOARDWIDTH; j++)
			{
				board[i][j] = new FinalCup(9, false);
			}
		}	
		for(int j = 0; j < 2; j++)
		{
			winnings[j] = new FinalCup(0, true);
		}

	}

	public void playCup(int playerWhoseTurnItIs, int cupPlayedInd)
	{
		FinalCup cupPlayed = getBoard()[player][cupPlayedInd];
		int seedsToPlay;

		System.out.println(playerWhoseTurnItIs + " is the player parameter of playCup");
		
		if(playerWhoseTurnItIs == player)
		{

			if(cupPlayed.getSeeds() == 1)
			{
				cupPlayed.clearSeeds();
				seedsToPlay = 1;
			}
			else if (cupPlayed.getSeeds() == 0)
			{
				seedsToPlay = 0;
			}
			else
			{
				seedsToPlay = cupPlayed.getSeeds() - 1;
				cupPlayed.setSeeds(1);
			}

			for(int i = seedsToPlay; i > 0; i--)
			{
				if(playerWhoseTurnItIs == 0)
				{

					if(cupPlayedInd > 0)
					{
						getBoard()[playerWhoseTurnItIs][cupPlayedInd-1].addSeed();
						cupPlayedInd--;
					}
					else if(cupPlayedInd == 0)
					{
						playerWhoseTurnItIs = 1;
						cupPlayedInd = 0;
						getBoard()[playerWhoseTurnItIs][cupPlayedInd].addSeed();
					}

				}
				else if(playerWhoseTurnItIs == 1)
				{
					if(cupPlayedInd < BOARDWIDTH-1)
					{
						getBoard()[playerWhoseTurnItIs][cupPlayedInd+1].addSeed();
						cupPlayedInd++;
					}

					else if(cupPlayedInd == BOARDWIDTH-1)
					{
						playerWhoseTurnItIs = 0;
						cupPlayedInd = BOARDWIDTH-1;
						getBoard()[playerWhoseTurnItIs][cupPlayedInd].addSeed();
					}
				}
			}
			if((getBoard()[playerWhoseTurnItIs][cupPlayedInd].getSeeds() % 2 == 0) && player != playerWhoseTurnItIs)
			{
				int winnings = getBoard()[playerWhoseTurnItIs][cupPlayedInd].getSeeds();
				depositWinnings(player, winnings);
				lastWinnings = winnings;
				getBoard()[playerWhoseTurnItIs][cupPlayedInd].clearSeeds();
			}
			else
			{
				lastWinnings = 0;
			}

			togglePlayer();
			
			System.out.println(player + " is the player at the end of playCup");
			
			printBoard();
		}
	}

	public void togglePlayer()
	{
		
		System.out.println(player + " is the player in TogglePlayer method");
		if (player == 1)
		{
			player = 0;
		}
		else if (player == 0)
		{
			player = 1;
		}
		
	}
	
	public void depositWinnings(int player, int amount)
	{
		winnings[player].addSeeds(amount);
	}

	public int getWinnings(int player)
	{
		return winnings[player].getSeeds();
	}

	public int getLastWinnings()
	{
		return lastWinnings;
	}
	
	public FinalCup getWinOneCup()
	{
		return winnings[0];
	}

	public FinalCup getWinTwoCup()
	{
		return winnings[1];
	}

	public int getCurrentPlayer()
	{
		return player;
	}
	
	public void setPlayer(int inPlayer)
	{
		player = inPlayer;
	}
	
	public void printBoard()
	{
		int j =0;
		for(int i =0; i < board[j].length;i++ )
		{
			System.out.print(board[j][i].getSeeds() + " | ");
		}
		System.out.println("");
		System.out.println("---------------" + getWinnings(0) + "--------------");
		System.out.println("---------------" + getWinnings(1) + "--------------");
		j = 1;
		for(int i =0; i < board[j].length;i++ )
		{
			System.out.print(board[j][i].getSeeds() + " | ");
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

}