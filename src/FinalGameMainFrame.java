import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class FinalGameMainFrame extends JFrame
{
	public FinalGameMainFrame()
	{

		setTitle("Togiz Kumalak");
		setSize(400, 400);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem subFrameItem = new JMenuItem("Create New Subframe");
		fileMenu.add(subFrameItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);

		SubFrameListener mySubListener = new SubFrameListener();
		subFrameItem.addActionListener(mySubListener);
		
		ExitListener myExitListener = new ExitListener();
		exitItem.addActionListener(myExitListener);

	}
	
	private class SubFrameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			FinalGameSubFrame newGameFrame = new FinalGameSubFrame();
			newGameFrame.setVisible(true);
			
		}
		
	}

	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
	
}
