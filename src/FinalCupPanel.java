import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FinalCupPanel extends FinalCup
{

	private FinalCup gameCup;
	private boolean winnings;
	private int x;
	private int y;
	private int w;
	private int h;
	private int seedX;
	private int seedY;
	private ArrayList<FinalSeedPanel> seedList;
	private Rectangle cupRect;
	
	FinalCupPanel(FinalCup inCup, int xIn, int yIn, int wIn, int hIn, boolean winner)
	{
		x = xIn;
		y = yIn;
		w = wIn;
		h = hIn;
		seedX = 0;
		seedY = 0;
		gameCup = inCup;
		winnings = winner;

		seedList = new ArrayList<FinalSeedPanel>();

		cupRect = new Rectangle(x,y,w,h);
		for(int i = 0;i < 162/**getSeeds()**/; i++ )
		{
			seedList.add(new FinalSeedPanel());
		}
	}

	
	public void drawCup(Graphics2D g2)
	{
		g2.draw(cupRect);
		
		int seedsRemaining = getSeeds();
		int lastLine = getSeeds() % 4;
		seedX = x + 13;
		seedY = y + 3;

		if(winnings == false)
		{
			for(int i = 0; i < getSeeds() / 2; i++)
			{
				for(int j = 0; j < 2; j++)
				{
					if (seedsRemaining > 0)
					{
						seedList.get(j).drawSeed(g2, cupRect, seedX, seedY);
						seedX+=10;
						seedsRemaining--;
					}
				}
				seedY +=10;
				seedX -=20;
			}
			for (int k = 0; k < lastLine; k++)
			{
				if (seedsRemaining > 0)
				{
					seedList.get(k).drawSeed(g2, cupRect, seedX, seedY);
					seedX+=10;
					seedsRemaining--;
				}
			}
		}
		else if(winnings == true)
		{
			int seedIndent = 0;
			for(int i = 0; i < getSeeds() / 4; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					if (seedsRemaining > 0)
					{
						seedList.get(j).drawSeed(g2, cupRect, seedX, seedY);
						seedX+=10;
						seedsRemaining--;
					}
				}
				if(seedIndent < 1)
				{
					seedY +=10;
					seedX -=40;
					seedIndent++;
				}
				else
				{
					seedY-=10;
					seedX+=10;
					seedIndent = 0;
				}
			}
			for (int k = 0; k < lastLine; k++)
			{
				if (seedsRemaining > 0)
				{
					seedList.get(k).drawSeed(g2, cupRect, seedX, seedY);
					seedX+=10;
					seedsRemaining--;
				}	
			}
		}
	}

	public Rectangle getRect()
	{
		return cupRect;
	}
	
	public void setX(int inX)
	{
		x = inX;
	}
	public int getX()
	{
		return x;
	}

	public void setY(int inY)
	{
		y = inY;
	}
	public int getY()
	{
		return y;
	}



	public String getParams()
	{
		String output = "X:" + Integer.toString(x) + "  Y:" + Integer.toString(y) + "  W:" + Integer.toString(w) + 
				"  H:" + Integer.toString(h);
		return output;
	}

}