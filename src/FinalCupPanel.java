import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FinalCupPanel extends JPanel
{

	private FinalCup gameCup;
	private JButton button;
	private JTextField textField;
	private int player;
	private int position;
	private FinalTogizBoard board;
	private String name;
	
	FinalCupPanel(FinalTogizBoard inBoard, FinalCup inCup, int inPlayer, int inPosition)
	{
		gameCup = inCup;
		player = inPlayer;
		position = inPosition;
		board = inBoard;
		textField = new JTextField();
		
		if(!inCup.isWinner())
		{
			setLayout(new GridLayout(4, 1));
			button = new JButton("Play Cup");
			button.setName("button");
			textField.setName("textfield");
			
			if(player == 0 && gameCup.isWinner() == false)
			{
				add(new JLabel("Player 1,"));
				add(new JLabel(" Cup " + (10-position)));
				add(button);
				textField.setText(Integer.toString(gameCup.getSeeds()));
				add(textField);
			}
			else if(player == 1 && gameCup.isWinner() == false)
			{
				textField.setText(Integer.toString(gameCup.getSeeds()));
				add(textField);
				add(button);
				add(new JLabel("Player 2,"));
				add(new JLabel(" Cup " + (position + 1)));
			}
			
		}

		else if(gameCup.isWinner())
		{
			setLayout(new GridLayout(3, 1));
			if(player == 0)
			{
				add(new JLabel("Player 1,"));
				add(new JLabel("Captured Cup"));
				textField.setText("0");
				textField.setColumns(3);
				add(textField);
			}
			else if(player == 1)
			{
				textField.setText("0");
				textField.setColumns(3);
				add(textField);
				add(new JLabel("Player 2,"));
				add(new JLabel("Captured Cup"));
			}
		}
		
	}
	
	public JTextField getTextField()
	{
		return textField;
	}
	
	public FinalCup getCup()
	{
		return gameCup;
	}
	
	public int getPlayer()
	{
		return player;
	}
	
	public int getPosition()
	{
		return position;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;		
		super.paintComponent(g2);
	}
	
}