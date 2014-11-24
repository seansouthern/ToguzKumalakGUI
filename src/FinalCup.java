import java.io.Serializable;

public class FinalCup implements Serializable
{
	private int seedCount;
	private boolean winningsCup;
	
	public FinalCup()
	{
		seedCount = 9;
		winningsCup = false;
	}
	
	public FinalCup(int seeds, boolean winner)
	{
		seedCount = seeds;
		winningsCup = winner;
	}

	public boolean isWinner()
	{
		return winningsCup;
	}
	
	public int getSeeds()
	{
		return seedCount;
	}

	public void setSeeds(int count)
	{
		seedCount = count;
	}
	
	public void addSeed()
	{
		seedCount++;
	}
	public void clearSeeds()
	{
		seedCount = 0;
	}

	public void addSeeds(int seeds)
	{
		seedCount += seeds;
	}
	

}