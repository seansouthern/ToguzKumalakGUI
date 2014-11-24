import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


public class FinalTogizBoard implements Serializable
{
	private FinalCup[][] board;
	private FinalCup[] winnings;
	private int BOARDWIDTH = 10;
	
	public FinalTogizBoard()
	{
		board = new FinalCup[2][BOARDWIDTH];
		winnings = new FinalCup[2];
		initializeBoard();
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

	public void playCup(int player, int cupPlayedInd)
	{
		FinalCup cupPlayed = getBoard()[player][cupPlayedInd];
		int seedsToPlay;
		int playerWhoseTurnItIs = player;
		
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
			if(player == 0)
			{

				if(cupPlayedInd > 0)
				{
					getBoard()[player][cupPlayedInd-1].addSeed();
					cupPlayedInd--;
				}
				else if(cupPlayedInd == 0)
				{
					player = 1;
					cupPlayedInd = 0;
					getBoard()[player][cupPlayedInd].addSeed();
				}

			}
			else if(player == 1)
			{
				if(cupPlayedInd < BOARDWIDTH-1)
				{
					getBoard()[player][cupPlayedInd+1].addSeed();
					cupPlayedInd++;
				}

				else if(cupPlayedInd == BOARDWIDTH-1)
				{
					player = 0;
					cupPlayedInd = BOARDWIDTH-1;
					getBoard()[player][cupPlayedInd].addSeed();
				}
			}
		}
		if((getBoard()[player][cupPlayedInd].getSeeds() % 2 == 0) && player != playerWhoseTurnItIs)
		{
			int winnings = getBoard()[player][cupPlayedInd].getSeeds();
			depositWinnings(playerWhoseTurnItIs, winnings);
			getBoard()[player][cupPlayedInd].clearSeeds();
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

}