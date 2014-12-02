import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;


class FinalGameSubFrame extends JFrame
{
	private FinalGamePanel gPanel;
	private JFileChooser gFileChooser;
	private FinalTogizBoard board;

	public FinalGameSubFrame()
	{
		setTitle("Toguz Kumalak");
		setSize(1000, 500);

		board = new FinalTogizBoard();
		gPanel = new FinalGamePanel(board);

		Container frameContentPane = getContentPane();
		frameContentPane.add(gPanel);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);

		SaveListener mySaveListener = new SaveListener();
		saveItem.addActionListener(mySaveListener);

		JMenuItem loadItem = new JMenuItem("Load");
		fileMenu.add(loadItem);

		LoadListener myLoadListener = new LoadListener();
		loadItem.addActionListener(myLoadListener);

		JMenuItem restartItem = new JMenuItem("Restart");
		fileMenu.add(restartItem);

		RestartListener myRestartListener = new RestartListener();
		restartItem.addActionListener(myRestartListener);

		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);

		ExitListener myExitListener = new ExitListener();
		exitItem.addActionListener(myExitListener);

		gFileChooser = new JFileChooser();

		// Centers new frame on screen
		setLocationRelativeTo(null);
		setVisible(true);


	}

	private class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String fileName;
			gFileChooser.setCurrentDirectory(new File("/home/sean"));
			int userChoice = gFileChooser.showSaveDialog(FinalGameSubFrame.this);

			try
			{
				if(userChoice == gFileChooser.APPROVE_OPTION)
				{
					fileName = gFileChooser.getSelectedFile().getPath();
					ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(fileName));

					objOut.writeObject(gPanel.getBoard());
					objOut.writeObject(gPanel.getPlayer());
					objOut.writeObject(gPanel.getMove());
					objOut.writeObject(gPanel.getMoveRecord());
					objOut.close();
				}
			}
			catch(IOException e)
			{
				System.out.println("Problem making or writing to an output stream.");
				System.out.println(e);
			}
		}
	}

	private class LoadListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String fileName;
			gFileChooser.setCurrentDirectory(new File("/home/sean"));
			int userChoice = gFileChooser.showOpenDialog(FinalGameSubFrame.this);

			try
			{
				if(userChoice == gFileChooser.APPROVE_OPTION)
				{
					fileName = gFileChooser.getSelectedFile().getPath();
					ObjectInputStream objIn = new ObjectInputStream(
							new FileInputStream(fileName));

					gPanel.setBoard((FinalTogizBoard) objIn.readObject());
					gPanel.setPlayer((int) objIn.readObject());
					gPanel.setMove((int) objIn.readObject());
					gPanel.getMoveRecord().setText(((JTextArea)objIn.readObject()).getText());

					gPanel.updateBoard();
					setVisible(true);
					objIn.close();
				}
			}
			catch(IOException e)
			{
				System.out.println("Problem making an input stream.");
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("Class not found problem when reading objects.");
			}


		}
	}

	private class RestartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			gPanel.resetGame(board);
		}
	}
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			dispose();
		}
	}

}