import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	private int BOARDWIDTH = 10;

	public FinalGamePanel(FinalTogizBoard inBoard)
	{
		//10 cups 8 seeds in a horizontal layout
		setPlayer(0);
		moveCount = 0;
		setBoard(inBoard);
		buildBoard(inBoard);
		setFocusable(true);
		addKeyListener(new KeyboardMoveHandler());
	}

	private class KeyboardMoveHandler extends KeyAdapter
	{
		public void keyTyped(KeyEvent event)
		{
			System.out.println("Hello, key pressed.");
			int numInput = 0;
			char keyChar = event.getKeyChar();
			if(Character.isDigit(keyChar))
			{
				String stringInput = Character.toString(keyChar);
				numInput = Integer.parseInt(stringInput);
				if(getPlayer() == 0)
				{
					if(numInput == 0)
					{
						numInput = 1;
					}
					else
					{						
						numInput = BOARDWIDTH - numInput + 1 ;
					}
				}
				else if(getPlayer() == 1 && numInput == 0)
				{
					numInput = 10;
				}
				position = numInput -1;
				getBoard().playCup(getPlayer(), position);
				moveCount++;
				updateMoveRecord();
				setPlayer(getBoard().getCurrentPlayer());
				updateBoard();

			}
		}
	}
	
	private class TextboxKeyFocusHandler extends KeyAdapter
	{
		public void keyTyped(KeyEvent event)
		{
			System.out.println("Hello, key pressed on TextField");
			if (event.getKeyCode() == event.VK_ENTER)
			{
				
				
			}
		}
	}
	
	public void buildBoard(FinalTogizBoard inBoard)
	{
		graphicalPanel = new FinalCupPanel[2][BOARDWIDTH];
		playerOneWinnings = new FinalCupPanel(getBoard(), new FinalCup(0, true), 0, 0);
		playerTwoWinnings = new FinalCupPanel(getBoard(), new FinalCup(0, true), 1, 0);
		grid = new GridLayout(2,BOARDWIDTH);

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
			for(int j = 0; j < BOARDWIDTH; j++)
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
							
							//Necessary to allow for coexisting keyboard listeners
							components[k].setFocusable(false);
							
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
						else if(components[k].getName().equals("textfield"))
						{
							components[k].addKeyListener(new TextboxKeyFocusHandler());
						}
					}
				}
			}
		}

		winTwoContainer.add(playerTwoWinnings);

		moveRecord = new JTextArea(6,54);
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
				getBoard().playCup(getPlayer(), position);
				moveCount++;
				updateMoveRecord();
				setPlayer(getBoard().getCurrentPlayer());
				updateBoard();
			}
		}
	}

	public void updateMoveRecord()
	{

		int userCupNumber = position;
		if(getPlayer() == 0)
		{
			userCupNumber= BOARDWIDTH - userCupNumber;
			moveRecord.append("Move:" + getMove() + ", Player " + (getPlayer() + 1) + 
					" has played Cup " + userCupNumber + " and captured " + getBoard().getLastWinnings() + " seeds\n");
		}
		else if (getPlayer() == 1)
		{
			userCupNumber+=1;
			moveRecord.append("Move:" + getMove() + ", Player " + (getPlayer() + 1) + 
					" has played Cup " + userCupNumber + " and captured " + getBoard().getLastWinnings() + " seeds\n");
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

	public JTextArea getMoveRecord()
	{
		return moveRecord;
	}

	public void updateBoard()
	{
		Component[] components;
		for( int i=0; i< 2; i++)
		{
			for(int j = 0; j < BOARDWIDTH; j++)
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
		playerOneWinnings.getTextField().setText(String.valueOf(getBoard().getWinOneCup().getSeeds()));
		playerTwoWinnings.getTextField().setText(String.valueOf(getBoard().getWinTwoCup().getSeeds()));

		setPlayer(getBoard().getCurrentPlayer());

	}



	public void resetGame(FinalTogizBoard inBoard)
	{
		setPlayer(0);
		moveCount = 0;
		setBoard(inBoard);
		inBoard.initializeBoard();
		inBoard.setPlayer(0);
		updateBoard();
		moveRecord.setText("");
	}


}