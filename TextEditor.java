import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextEditor extends JFrame implements ActionListener
{

	File chosenFile;
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenuButton = new JMenu("File");

	JMenuItem menuItemSaveas = new JMenuItem("Save as...", KeyEvent.VK_N);
	JMenuItem menuItemSave = new JMenuItem("Save...", KeyEvent.VK_S);
	JMenuItem menuItemOpen = new JMenuItem("Open...", KeyEvent.VK_O);

	JTextArea textArea = new JTextArea();
	JFileChooser fileChooser = new JFileChooser();

	public TextEditor(String title)
	{
		//All the settings for my JFrame(could have this. at the beginning but looked messy)
		setSize(300,400);	
		setTitle(title);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		
		this.add(textArea);
		textArea.setLineWrap(true);

		
		
		
		//These adds my menu bar to the JFrame
		this.setJMenuBar(menuBar);

		//These adds the major categories to the menu bar e.g. "File"
		menuBar.add(fileMenuButton);

		//These adds all of the drop down buttons to the menu bars
		fileMenuButton.add(menuItemSaveas);
		fileMenuButton.add(menuItemSave);
		fileMenuButton.add(menuItemOpen);

		//These add my drop down buttons to the list of actionListeners
		menuItemSaveas.addActionListener(this);
		menuItemSave.addActionListener(this);
		menuItemOpen.addActionListener(this);
		
		//This opens a pop-up when the close button is pressed
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                saveCheck();
                e.getWindow().dispose();
            }
        });
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == menuItemSaveas)
		{			
			saveAsFile();
		}

		if(e.getSource() == menuItemSave)
		{
			//This runs if the chosenFile variable already has 
			if(chosenFile != null)
			{			
				save();
			}

			else
			{
				saveAsFile();
			}

		}

		if(e.getSource() == menuItemOpen)
		{
			loadFile();
		}
	}

	public void saveAsFile()
	{
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);


		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
			try
			{
				chosenFile = fileChooser.getSelectedFile();                                                  
				FileWriter writer = new FileWriter(chosenFile.getPath() + ".txt");
				PrintWriter printWriter = new PrintWriter(writer);
				printWriter.print(textArea.getText());
				printWriter.close();
			} 
			catch (IOException exception) 
			{
				exception.printStackTrace();
			}

		}
	}

	public void save()
	{
		if(chosenFile != null)
		{	
			try 
			{
				PrintWriter writer = new PrintWriter(chosenFile.getAbsolutePath() + ".txt", "UTF-8");
				writer.println(textArea.getText());
				writer.close();
			} 
			catch (IOException exception) 
			{
				exception.printStackTrace();
		
			}
		}	
		
		else
		{
			saveAsFile();
		}
	}
		



	public void loadFile()
	{
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);		
		if(saveCheck() == true)
		{
			System.out.println("True");
			if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					//These are all the variables needed to open a file
					chosenFile = fileChooser.getSelectedFile();
					FileReader reader = new FileReader(chosenFile.getPath());
					int character;
					String fileContents = "";
					textArea.setText("");
	
					//This reads each character in the file to the end
					while((character = reader.read()) != -1)
					{
						//As each character is taken as it's ASCII value I convert them into chars when adding them to an array
						fileContents += (char)character;
					}
					reader.close();
	
					//This sets the current text in my note area to the new area
					textArea.setText(fileContents);
	
				}
				catch(IOException exception)
				{
					exception.printStackTrace();
				}
	
			}
		}
	}
	
	public void loadFilePath(File chosenFile)
	{
	
		if(saveCheck() == true)
		{
			System.out.println("True");
			{
				try
				{
					//These are all the variables needed to open a file
					FileReader reader = new FileReader(chosenFile.getPath());
					int character;
					String fileContents = "";
					textArea.setText("");
	
					//This reads each character in the file to the end
					while((character = reader.read()) != -1)
					{
						//As each character is taken as it's ASCII value I convert them into chars when adding them to an array
						fileContents += (char)character;
					}
					reader.close();
	
					//This sets the current text in my note area to the new area
					textArea.setText(fileContents);
	
				}
				catch(IOException exception)
				{
					exception.printStackTrace();
				}
	
			}
		}
	}

	public boolean saveCheck()
	{
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure? This will overwrite any previous information in the file", 
				"Save", JOptionPane.OK_CANCEL_OPTION);

		if(answer == JOptionPane.OK_OPTION)
		{	
			return true;
		}
		
		else
		{
			return false;
		}
	}
}
