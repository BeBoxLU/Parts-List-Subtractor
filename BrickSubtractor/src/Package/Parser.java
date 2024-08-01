// By Blake Edinger
// Last Edit: 08/01/2024
// The purpose of this class is to take in two XML lists
// and subtracts the contents of them 

package Package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	// These will be the file paths of the two chosen XML files
	static String file1Path = "";
	static String file2Path = "";

	public static ArrayList<Brick> start() throws IOException {

		// This is creating the necessary structures to read from
		// files and to contain the content of them.
		ArrayList<Brick> firstFileList = new ArrayList<Brick>();
		ArrayList<Brick> secondFileList = new ArrayList<Brick>();
		BufferedReader firstFileReader = new BufferedReader(new FileReader(file1Path));
		BufferedReader secondFileReader = new BufferedReader(new FileReader(file2Path));
		// This calls "listMaker" on the two brick containers to
		// format them into items that we can parse easily
		firstFileList = listMaker(firstFileReader);
		secondFileList = listMaker(secondFileReader);

		// The purpose of this is to iterate through a given list
		Iterator<Brick> firstListIterator = firstFileList.iterator();

		// This will loop through the second list for every brick
		// in the first file. If the bricks have the same id and color
		// then it subtracts them. If the subtraction is negative
		// then it just removes the part, and if its non zero and positive
		// then it adjusts the quantity accordingly
		while (firstListIterator.hasNext()) {
			Brick firstBrick = firstListIterator.next();
			Iterator<Brick> secondListIterator = secondFileList.iterator();
			while (secondListIterator.hasNext()) {
				Brick secondBrick = secondListIterator.next();
				if (secondBrick.getId().equals(firstBrick.getId()) && secondBrick.getColor() == firstBrick.getColor()) {
					if (secondBrick.getQuantity() > firstBrick.getQuantity()) {
						secondBrick.setQuantity(secondBrick.getQuantity() - firstBrick.getQuantity());
					} else {
						secondListIterator.remove();
					}
				}
			}
		}
		// Returns the adjusted brick list for file writing.
		return secondFileList;
	}

	public static ArrayList<Brick> listMaker(BufferedReader reader) throws NumberFormatException, IOException {
		// Creating basic structures that will be required in this method
		ArrayList<Brick> brickList = new ArrayList<Brick>();
		String val;
		String regex = ">([^<]+)<";
		Pattern pattern = Pattern.compile(regex);
		// Regex allows us to find certain values within a string
		// in this case, the values of items in a XML list are stored
		// between a > and <, so we use regex to file whats between
		// those points
		while ((val = reader.readLine()) != null) {
			if (val.equals("	<ITEM>")) {
				// If we've gotten to a new item then we iterate
				// 4 times to find each individual corresponding
				// value. Pattern.match uses the regex parameters
				// to find the items between > <
				String itemType = "";
				String itemId = "";
				int color = 0;
				int quantity = 0;
				val = reader.readLine();
				Matcher match1 = pattern.matcher(val);
				while (match1.find()) {
					itemType = match1.group(1);
				}
				val = reader.readLine();
				Matcher match2 = pattern.matcher(val);
				while (match2.find()) {
					itemId = match2.group(1);
				}
				val = reader.readLine();
				Matcher match3 = pattern.matcher(val);
				while (match3.find()) {
					color = Integer.parseInt(match3.group(1));
				}
				val = reader.readLine();
				Matcher match4 = pattern.matcher(val);
				while (match4.find()) {
					quantity = Integer.parseInt(match4.group(1));
				}
				// Creating a new brick and updating all of its
				// values before adding it to our list
				Brick brick = new Brick();
				brick.setType(itemType);
				brick.setId(itemId);
				brick.setColor(color);
				brick.setQuantity(quantity);
				brickList.add(brick);

			}
		}
		// Once we have iterated through the entire file we return
		// the brick list
		return brickList;
	}
}
