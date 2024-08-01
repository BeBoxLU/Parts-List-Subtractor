// By Blake Edinger
// Last Edit: 08/01/2024
// This class is a basic class to create new "bricks" in the program
// It holds all 4 attributes seen in a brick XML list

package Package;

public class Brick {
	String itemType;
	String itemId;
	int color;
	int quantity;

	public void setType(String itemType) {
		this.itemType = itemType;
	}

	public String getType() {
		return itemType;
	}

	public void setId(String itemId) {
		this.itemId = itemId;
	}

	public String getId() {
		return itemId;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
}
