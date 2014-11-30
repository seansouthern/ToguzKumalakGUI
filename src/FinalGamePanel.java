import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class FinalGamePanel extends JPanel
{
	private FinalTogizBoard board;
	private int player;
	private int moveCount;
	private GridLayout grid;
	private FinalCupPanel[][] graphicalPanel;
	private FinalCupPanel playerOneWinnings;
	private FinalCupPanel playerTwoWinnings;
	private JTextArea moveRecord;
	private JScrollPane gScrollPane;
	private int position;
	private JPanel boardContainer;
	private JPanel boardAndWinningsContainer;
	private JPanel winOneContainer;
	private JPanel winTwoContainer;
	private JPanel scrollContainer;

	public FinalGamePanel(FinalTogizBoard inBoard)
	{
		//10 cups 8 seeds in a horizontal layout
		setPlayer(0);
		moveCount = 1;
		setBoard(inBoard);
		buildBoard(inBoard);

	}

	public void buildBoard(FinalTogizBoard inBoard)
	{
		graphicalPanel = new FinalCupPanel[2][10];
		playerOneWinnings = new FinalCupPanel(getBoard(), new FinalCup(0, true), 0, 0);
		playerTwoWinnings = new FinalCupPanel(getBoard(), new FinalCup(0, true), 1, 0);
		grid = new GridLayout(2,10);

		setLayout(new BorderLayout());
		setBoard(inBoard);

		boardContainer = new JPanel(grid);
		boardAndWinningsContainer = new JPanel(new BorderLayout());
		winOneContainer = new JPanel(new FlowLayout());
		winTwoContainer = new JPanel(new FlowLayout());

		boardAndWinningsContainer.add(winOneContainer, "North");
		boardAndWinningsContainer.add(boardContainer, "Center");
		boardAndWinningsContainer.add(winTwoContainer, "South");

		winOneContainer.add(playerOneWinnings);


		Component[] components;

		for( int i=0; i< 2; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				graphicalPanel[i][j] = new FinalCupPanel(getBoard(), getBoard().getBoard()[i][j], i, j);
				boardContainer.add(graphicalPanel[i][j]);
				components = graphicalPanel[i][j].getComponents();

				for (int k = 0; k < components.length; k++)
				{
					if(components[k].getName() != null)
					{
						if(components[k].getName().equals("button"))
						{
							components[k].addMouseListener(new MouseClickHandler());

							for(int l = 0; l < components[k].getMouseListeners().length; l++)
							{
								if(components[k].getMouseListeners()[l].getClass().getName().equals("FinalGamePanel$MouseClickHandler"))
								{
									((MouseClickHandler)components[k].getMouseListeners()[l]).stopMouseListener();
								}
							}

							if(getPlayer() == i)
							{
								for(int m = 0; m < components[k].getMouseListeners().length; m++)
								{
									if(components[k].getMouseListeners()[m].getClass().getName().equals("FinalGamePanel$MouseClickHandler"))
									{
										((MouseClickHandler)components[k].getMouseListeners()[m]).startMouseListener();
									}
								}
							}
						}
					}
				}
			}
		}

		winTwoContainer.add(playerTwoWinnings);

		moveRecord = new JTextArea(6,24);
		moveRecord.setEditable(false);
		gScrollPane = new JScrollPane(moveRecord,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollContainer = new JPanel(new FlowLayout());
		scrollContainer.add(gScrollPane);
		add(scrollContainer, BorderLayout.NORTH);
		add(boardAndWinningsContainer, BorderLayout.SOUTH);

	}



	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;		
		super.paintComponent(g2);

	}

	private class MouseClickHandler extends MouseAdapter
	{
		private boolean mouseListenerIsActive = true;

		public void startMouseListener()
		{
			mouseListenerIsActive = true;
		}

		public void stopMouseListener()
		{
			mouseListenerIsActive = false;
		}

		public void mouseClicked(MouseEvent event)
		{
			System.out.println("Button pressed!");
			if(mouseListenerIsActive == true)
			{
				position = ((FinalCupPanel) event.getComponent().getParent()).getPosition();
				System.out.println(player + " is the current player");
				getBoard().playCup(getPlayer(), position);
				System.out.println(getBoard().getCurrentPlayer() + " is the board class player after playCup method");
				setPlayer(getBoard().getCurrentPlayer());
				updateCups();
			}
		}
	}
	
	
	private class KeyHandler extends KeyAdapter
	{
		public void keyTyped(KeyEvent event)
		{

		}
	}


	public int getMove()
	{
		return moveCount;
	}
	public void setMove(int inMove)
	{
		moveCount = inMove;
	}
	public int getPlayer()
	{
		return player;
	}
	public void setPlayer(int inPlayer)
	{
		player = inPlayer;
	}
	public FinalTogizBoard getBoard()
	{
		return board;
	}
	public void setBoard(FinalTogizBoard gBoard)
	{
		board = gBoard;
	}

	public void updateCups()
	{
		Component[] components;
		System.out.println("UpdateCups has been called!");
		for( int i=0; i< 2; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				graphicalPanel[i][j].getCup().setSeeds(getBoard().getBoard()[i][j].getSeeds());
				graphicalPanel[i][j].getTextField().setText(String.valueOf(getBoard().getBoard()[i][j].getSeeds()));

				components = graphicalPanel[i][j].getComponents();

				for (int k = 0; k < components.length; k++)
				{
					if(components[k].getName() != null)
					{
						if(components[k].getName().equals("button"))
						{
							for(int l = 0; l < components[k].getMouseListeners().length; l++)
							{
								if(components[k].getMouseListeners()[l].getClass().getName().equals("FinalGamePanel$MouseClickHandler"))
								{
									//System.out.println("A clicker has been stopped at Player " + i + " Position " + j);
									((MouseClickHandler)components[k].getMouseListeners()[l]).stopMouseListener();
								}
							}

							if(getPlayer() == i)
							{
								for(int m = 0; m < components[k].getMouseListeners().length; m++)
								{
									if(components[k].getMouseListeners()[m].getClass().getName().equals("FinalGamePanel$MouseClickHandler"))
									{
										//System.out.println("A clicker has been started at Player " + i + " Position " + j);
										((MouseClickHandler)components[k].getMouseListeners()[m]).startMouseListener();
									}
								}
							}
						}
					}
				}
			}
		}
		playerOneWinnings.getTextField().setText(String.valueOf(getBoard().getWinOneCup().getSeeds()));
		playerTwoWinnings.getTextField().setText(String.valueOf(getBoard().getWinTwoCup().getSeeds()));
		
		setPlayer(getBoard().getCurrentPlayer());
		
	}


}