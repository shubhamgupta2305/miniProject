
import java.util.List;
import java.util.Arrays;

/*
 * Class represents the game controller.
 */
public class Controller {

	public static final String [] alphabetsArray = {  "A", "B", "C", "D", "E", "F", "G" };
	int guesses;
	private Model model;
	private GameWindow mainWindow;

	/*
	 * Constructs the controller.
	 * Gets a refernce to the GameWindow.
	 * Creates the model.
	 */		
	public Controller(GameWindow mainWindow) {	
	
		this.mainWindow = mainWindow;
		this.model = new Model(mainWindow);
	}

	/*
	 * Method takes the input guess string and parses it.
	 * Initiates the "fire" action on the model.
	 * Tracks the total guesses for the game.
	 * Tracks if all the ships are sunk and initiates
	 * the game complete actions.
	 */
	public void processGuess(String guess) {
	
		String location = parseGuess(guess);
		
		if (location == null) {
			return;
		}
		
		this.guesses++;
		boolean hit = model.fire(location);
		
		if (hit && (model.shipsSunk == Model.numShips)) {
		
			String message = "You sank all my battleships, in " +
								this.guesses + " guesses";
			mainWindow.displayMessage(message);
			
			// All ships are sunk - game is over
			mainWindow.gameOver();
		}
	}
	
	/*
	 * The method takes a guess as input, parses it.
	 * Validates it for its existence, length, and format (needs to be
	 * two characters with an uppercase letter followed by a number).
	 * The letters and numbers are pre-defined based upon the game table.
	 * attributes Method returns a null if the input is not valid, or a newly
	 * formed location string with valid row and column numbers.
	 */
	private String parseGuess(String guess) {

		List<String> alphabets = Arrays.asList(alphabetsArray);
		
		if (guess == null || guess.trim().length() != 2) {
		
			mainWindow.alertDialog(null, "Oops, please enter a letter and a number on the board.");
			return null;
		}
		
		guess = guess.trim();
		String firstChar = guess.substring(0, 1);
		int row = alphabets.indexOf(firstChar);
		int column;

		try {
			column = Integer.valueOf(guess.substring(1));
		}
		catch(NumberFormatException e) {
			mainWindow.alertDialog(null, "Oops, that isn't on the board.");
			return null;			
		}
		
		if (row < 0 || 
				row >= Model.boardSize ||
				column < 0 || 
				column >= Model.boardSize) {

			mainWindow.alertDialog(null, "Oops, that's off the board.");			
			return null;				
		}
		
		return (Integer.toString(row) + Integer.toString(column));
	}
}