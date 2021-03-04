
/*
 * Class represents the game model.
 */
public class Model {

	// Constant values for game attributes.
	public static final int boardSize = 7;
	public static final int numShips = 3;
	public static final int shipLength = 3;
	
	int shipsSunk;
	Ship [] ships;
	private GameWindow mainWindow;

	/*
	 * Constructs the model.
	 * Creates the ships and their locations on the table.
	 * Gets a refernce to the GameWindow.
	 */	
	public Model(GameWindow mainWindow) {
	
		ships = new Ship[numShips];
		
		for (int i = 0; i < numShips; i++) {
			ships[i] = new Ship();
		}
		
		generateShipLocations();
		
		this.mainWindow = mainWindow;
	}
	
	/*
	 * The "fire" action updates the ship attributes with "hit" or "miss" data.
	 * Tracks the ships sunk.
	 * Updates the GameWindow table with hit or miss.
	 */
	public boolean fire(String guess) {
	
		for (int i = 0; i < numShips; i++) {
		
			Ship ship = ships[i];
			int index = getShipLocationIndex(guess, ship.locations);
		
			if (index >= 0) {
			
				ship.hits[index] = "hit";
				mainWindow.displayMessage("HIT!");
				mainWindow.displayHitOrMiss(guess, "HIT");
				
				if (isSunk(ship)) {

					mainWindow.displayMessage("You sank my battleship!");
					this.shipsSunk++;
				}
				
				return true;
			}
		}

		mainWindow.displayMessage("You missed.");
		mainWindow.displayHitOrMiss(guess, "MISS");	
		return false;
	}
	
	/*
	 * Gets the index of the ship's location for a input guess.
	 * Returns the index or a -1 (-1 indicates there is no ship location
	 * for the guess).
	 */
	private int getShipLocationIndex(String guess, String [] shipLocations) {
	
		for (int i = 0; i < shipLocations.length; i++) {
		
			if (shipLocations[i].equals(guess)) {			
				return i;
			}
		}
		
		return -1;
	}
	
	/*
	 * Method determines if a input ship is sunk or not -
	 * returns a boolean true or false.
	 */
	public boolean isSunk(Ship ship) {
	
		for (int i = 0; i < shipLength; i++) {
		
			if (! ship.hits[i].equals("hit")) {
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * Method creates the locations for all the ships.
	 */
	private void generateShipLocations() {
	
		String [] locations;
		
		for (int i = 0; i < numShips; i++) {
		
			do {
				locations = generateShip();
			} while (collision(locations));
		
			ships[i].locations = locations;
		}
	}
	
	/*
	 * Method creates locations for a ship.
	 */
	private String [] generateShip() {
	
		int row, col;
		double direction = Math.floor(Math.random() * 2);
		
		if (direction == 1) {
			row = (int) Math.floor(Math.random() * boardSize);
			col = (int) Math.floor(Math.random() * (boardSize - shipLength));
		}
		else {
			row = (int) Math.floor(Math.random() * (boardSize - shipLength));
			col = (int) Math.floor(Math.random() * boardSize);
		}
		
		String [] newShipLocations = new String [shipLength];
		
		for (int i = 0; i < shipLength; i++) {
		
			if (direction == 1) {
				newShipLocations[i] = row + "" + (col + i);
			}
			else {
				newShipLocations[i] = (row + i) + "" + col;
			}
		}
		
		return newShipLocations;
	}
	
	/*
	 * Method determines if the locations a ship are
	 * not overlapping with other ship's locations.
	 * Returns a boolean true if collison occurs, else false.
	 */
	private boolean collision(String [] locations) {

		for (int i = 0; i < numShips; i++) {
		
			Ship ship = ships[i];
			
			for (int j = 0; j < locations.length; j++) {

				for (int k = 0; k < ship.locations.length; k++) {
				
					if (locations[j].equals(ship.locations[k])) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}