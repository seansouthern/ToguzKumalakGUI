import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private FinalCupPanel[][] graphicalBoard;
	private FinalCupPanel[] graphicalWinnings;
	private int x;
	private int y;
	private JTextArea gTextArea;
	private JScrollPane gScrollPane;
	private int moveCount = 1;



	public FinalGamePanel()
	{
		board = new FinalTogizBoard();
		player = 0;
		x = 200;
		y = 145;
		initializeCups();
		updateCups();
		repaint();
		

		gTextArea = new JTextArea(6,24);
		gTextArea.setEditable(false);
		gScrollPane = new JScrollPane(gTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		
		add(gScrollPane);
		
		MouseClickHandler mListener = new MouseClickHandler();
		MouseMotionHandler motionHandler= new MouseMotionHandler();
		KeyHandler kHandler = new KeyHandler();

		addMouseListener(mListener);
		addMouseMotionListener(motionHandler);
		addKeyListener(kHandler);
		
		setFocusable(true);
		
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;		
		super.paintComponent(g2);

		for(int i = 0; i < 2; i++)
		{	
			for(int j = 0; j < 9; j++)
			{
				graphicalBoard[i][j].drawCup(g2);
			}
		}
		for(int k = 0; k < 2; k++)
		{
			graphicalWinnings[k].drawCup(g2);
		}
	}

	private class KeyHandler extends KeyAdapter
	{
		public void keyTyped(KeyEvent event)
		{
			int numInput = 0;
			char keyChar = event.getKeyChar();
			if(Character.isDigit(keyChar) && Integer.parseInt(Character.toString(keyChar))!=0)
			{
				String stringInput = Character.toString(keyChar);
				numInput = Integer.parseInt(stringInput);
				getBoard().playCup(player, (numInput-1));
				gTextArea.append("Move: " + moveCount + "  Player: " + (player+1) +
						".  Cup: " + stringInput + ".\n");

				if (player == 0)
				{
					player = 1;
				}
				else if(player == 1)
				{
					player = 0;
				}
				updateCups();

				repaint();
				moveCount++;
			}
		}
	}
	private class MouseClickHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			for(int j = 0; j < 9; j++)
			{
				if(graphicalBoard[player][j].getRect().contains(event.getPoint()))
				{
					getBoard().playCup(player, j);

					gTextArea.append("Move: " + moveCount + "  Player: " + (player+1) +
							".  Cup: " + (j+1) + ".\n");
					
					if (player == 0)
					{
						player = 1;
					}
					else if(player == 1)
					{
						player = 0;
					}
					updateCups();

					repaint();
					moveCount++;
				}
			}
		}
	}

	private class MouseMotionHandler extends MouseAdapter
	{
		public void mouseMoved(MouseEvent event)
		{
			boolean handFlag = false;
			for(int j = 0; j < 9; j++)
			{
				if(graphicalBoard[player][j].getRect().contains(event.getPoint()))
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					handFlag = true;
					repaint();
				}
				else if(handFlag == false)
				{
					setCursor(Cursor.getDefaultCursor());
				}
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	public void initializeCups()
	{
		setBoard(new FinalTogizBoard());
		graphicalBoard = new FinalCupPanel[2][9];
		graphicalWinnings = new FinalCupPanel[2];
		FinalCup dummyCup = new FinalCup();
		x=200;
		y=145;

		for(int i = 0; i < 2; i++)
		{	
			x=200;
			for(int j = 0; j < 9; j++)
			{
				FinalCupPanel inCupPanel = new FinalCupPanel(dummyCup, x, y, 40, 80, false);
				graphicalBoard[i][j] = inCupPanel;
				x+=45;
			}
			y+=125;
		}		
		y= 228;
		for(int j = 0; j < 2; j++)
		{
			FinalCupPanel winningsCupPanel = new FinalCupPanel(dummyCup, 200, y, 400, 20, true);
			graphicalWinnings[j] = winningsCupPanel;
			graphicalWinnings[j].clearSeeds();
			y+=20;
		}
		repaint();
	}

	public void updateCups()
	{
		x=200;
		y=145;
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				graphicalBoard[i][j].setSeeds(getBoard().getBoard()[i][j].getSeeds());
				System.out.println("Index: " + i + "   Index: " + j + " Seeds:" + graphicalBoard[i][j].getSeeds());
			}
		}	
		for(int j = 0; j < 2; j++)
		{
			graphicalWinnings[j].setSeeds(getBoard().getWinnings(j));
			y+=40;
		}
	}

	
	
	public String getText()
	{
		return gTextArea.getText();
	}
	public void setText(String inText)
	{
		gTextArea.setText(inText);
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
		repaint();
	}
	

}