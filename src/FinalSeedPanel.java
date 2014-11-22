import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;

public class FinalSeedPanel implements Serializable
{
	private final Color seedColor = Color.BLUE;
	private final int diameter = 6;

	public FinalSeedPanel()
	{
	}

	public void drawSeed(Graphics2D g2, Rectangle boundingRectangle, int seedX, int seedY)
	{

		if((diameter <= boundingRectangle.getWidth())
				&& (diameter <= boundingRectangle.getHeight()))
		{

			Ellipse2D.Double dot = new Ellipse2D.Double(seedX, seedY, diameter, diameter);
			g2.setColor(seedColor);
			g2.fill(dot);
			g2.setColor(Color.BLACK);
		}
	}



}