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

public class FinalGame
{	
	public static void main(String[] args)
	{
		FinalGameMainFrame gFrame = new FinalGameMainFrame();
		gFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gFrame.setVisible(true);
	}

}

