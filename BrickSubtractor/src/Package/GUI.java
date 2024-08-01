// By Blake Edinger
// Last Edit: 08/01/2024
// The purpose of this class is to give the user the ability
// to select what parts lists are wanted and choose the directory
// where the subtracted parts list is stored.

package Package;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// Creating basic global variables that we will need to
	// access from various parts of the program
	ArrayList<Brick> brickList = new ArrayList<Brick>();
	String file1Path;
	String file2Path;
	String file3Path;
	JLabel file1Label = new JLabel("");
	JLabel file2Label = new JLabel("");
	JLabel file3Label = new JLabel("");

	// Main method, when a program is run whatever is in
	// this method is run. In this case, it starts the GUI
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.stGui();
	}

	public void stGui() {
		// Setting up the GUI by formatting it and adding
		// buttons for the user to use.
		JFrame f = new JFrame("Parts List Subtractor");
		f.setSize(1000, 250);
		f.setLocation(300, 200);
		f.setLayout(new GridLayout(3, 2));
		JButton file1Finder = new JButton("Click me to source your first parts list.");
		JButton file2Finder = new JButton("Click me to source your second parts list.");
		JButton file3Maker = new JButton("Click me to create your subtracted parts list.");

		// Adding action listeners to each button. This
		// allows it so that when a button is clicked
		// something happens
		file1Finder.addActionListener(this);
		file2Finder.addActionListener(this);
		file3Maker.addActionListener(this);

		// Finishing formatting of GUI by adding all the
		// labels and buttons
		f.add(file1Finder);
		f.add(file1Label);
		f.add(file2Finder);
		f.add(file2Label);
		f.add(file3Maker);
		f.add(file3Label);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		// e.getActionCommand().equals corresponds to each
		// button clicked. In the case of buttons 1 and 2
		// it is used to select the file and then updates
		// the file path in the Parser class
		if (e.getActionCommand().equals("Click me to source your first parts list.")) {
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int i = j.showOpenDialog(null);
			if (i == JFileChooser.APPROVE_OPTION) {
				File file = j.getSelectedFile();
				file1Path = file.getPath();
				Parser.file1Path = file1Path;
				file1Label.setText("File chosen: " + file1Path);
			}
		}
		if (e.getActionCommand().equals("Click me to source your second parts list.")) {
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int i = j.showOpenDialog(null);
			if (i == JFileChooser.APPROVE_OPTION) {
				File file = j.getSelectedFile();
				file2Path = file.getPath();
				Parser.file2Path = file2Path;
				file2Label.setText("File chosen: " + file2Path);
			}
		}
		// This buttons takes the subtracted brick list
		// from the parser class and writes it to a new
		// file.
		if (e.getActionCommand().equals("Click me to create your subtracted parts list.")) {
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int i = j.showOpenDialog(null);
			if (i == JFileChooser.APPROVE_OPTION) {
				// Setting the path the user chose and then
				// setting the file that needs to be made.
				File file = j.getSelectedFile();
				file3Path = file.getPath();
				File subList = new File(file3Path + "\\SubtractedList.xml");
				// Creating the subtractedlist parts file
				try {
					subList.createNewFile();
				} catch (IOException e1) {
				}
				ArrayList<Brick> secondFileList = new ArrayList<Brick>();
				// Making parser run and therefore subtract
				// the chosen parts lists
				try {
					secondFileList = Parser.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// Making an iterator so we can loop through
				// the final brick list
				Iterator<Brick> secondListIterator = secondFileList.iterator();

				try {
					// This simply writes everything to the file.
					// It starts off with <INVENTORY> then writes
					// each item to the file and then follows that
					// up with </INVENTORY> when done. It then displays
					// where the final file was made.
					PrintWriter thirdFileWriter = new PrintWriter(new FileWriter(subList, true));
					thirdFileWriter.println("<INVENTORY>");
					while (secondListIterator.hasNext()) {
						Brick brick = secondListIterator.next();
						thirdFileWriter.println("	<ITEM>");
						thirdFileWriter.println("		<ITEMTYPE>" + brick.getType() + "</ITEMTYPE>");
						thirdFileWriter.println("		<ITEMID>" + brick.getId() + "</ITEMID>");
						thirdFileWriter.println("		<COLOR>" + brick.getColor() + "</COLOR>");
						thirdFileWriter.println("		<MINQTY>" + brick.getQuantity() + "</MINQTY>");
						thirdFileWriter.println("	</ITEM>");
					}
					thirdFileWriter.println("</INVENTORY>");
					thirdFileWriter.close();
					file3Label.setText("File made at: " + subList);
				} catch (FileNotFoundException e1) {

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}
	}
}
