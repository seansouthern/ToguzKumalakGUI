import java.awt.BorderLayout;
import java.awt.Cursor;
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
	
	public FinalGamePanel(FinalTogizBoard inBoard)
	{
		//10 cups 8 seeds in a horizontal layout
		setPlayer(0);
		moveCount = 1;
		graphicalPanel = new FinalCupPanel[2][10];
		playerOneWinnings = new FinalCupPanel(getBoard(), new FinalCup(0, true), 0, 0);
		playerTwoWinnings = new FinalCupPanel(getBoard(), new FinalCup(0, true), 1, 0);
		grid = new GridLayout(2,10);
		
		setLayout(new BorderLayout());
		setBoard(inBoard);
		
		JPanel boardContainer = new JPanel(grid);
		JPanel boardAndWinningsContainer = new JPanel(new BorderLayout());
		JPanel winOneContainer = new JPanel(new FlowLayout());
		JPanel winTwoContainer = new JPanel(new FlowLayout());
		
		boardAndWinningsContainer.add(winOneContainer, "North");
		boardAndWinningsContainer.add(boardContainer, "Center");
		boardAndWinningsContainer.add(winTwoContainer, "South");
		
		winOneContainer.add(playerOneWinnings);
		
		for( int i=0; i< 2; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				graphicalPanel[i][j] = new FinalCupPanel(getBoard(), getBoard().getBoard()[i][j], i, j);
				boardContainer.add(graphicalPanel[i][j]);
			}
		}
		
		winTwoContainer.add(playerTwoWinnings);
		
		moveRecord = new JTextArea(6,24);
		moveRecord.setEditable(false);
		gScrollPane = new JScrollPane(moveRecord,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JPanel scrollContainer = new JPanel(new FlowLayout());
		scrollContainer.add(gScrollPane);
		
		add(scrollContainer, BorderLayout.NORTH);
		
		add(boardAndWinningsContainer, BorderLayout.SOUTH);
		
		setFocusable(true);
	    setVisible(true);
	}


	public void updateCups()
	{
		for( int i=0; i< 2; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				graphicalPanel[i][j] = new FinalCupPanel(getBoard(), getBoard().getBoard()[i][j], i, j);
			}
		}
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;		
		//updateCups();
		super.paintComponent(g2);
		g2.drawString("Test string", 100, 100);
		
	}
	
	private class MouseClickHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			int position = ((FinalCupPanel) event.getComponent().getParent()).getPosition();
			getBoard().playCup(player, position);
			updateCups();
			repaint();
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


}