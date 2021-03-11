

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;

/*
 * The main GUI for the battleship game.
 * This class is the starter program for the application.
 */
public class GameWindow {

	
	private static final String title = "Battleship";
	
	private JFrame frame;
	private JLabel messageText;
	private JTextField inputText;
	private JButton fireButton;

	private JLabel totalScoreLabel;
	private  JTextField scoreOutputText;

	private JTable table;
	private GameTableModel tmodel;
	
	private Controller controller;


	/*
	 * Main method, starts the application.
	 */
	
	public static void main(String [] args) {
	
		new GameWindow().displayGui();
	}

	/*
	 * The GUI for the main window of the game.
	 */
	private void displayGui() {
	
		frame = new JFrame();
		frame.getContentPane().setBackground(getColor());
		frame.setTitle(title);

		// Menu setup

		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("Game");
		menuFile.setMnemonic(KeyEvent.VK_G);
		JMenuItem menuItemReset = new JMenuItem("Reset", KeyEvent.VK_R);
		menuItemReset.addActionListener(e -> resetGameListener());
		JMenuItem menuItemExit = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItemExit.addActionListener(e -> System.exit(0));

		menuFile.add(menuItemReset); 
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		menuBar.add(menuFile);
		
		frame.setJMenuBar(menuBar);

		// Message panel

		JPanel messagePanel = new JPanel();
		messagePanel.setBackground(getColor());
		messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		messageText = new JLabel("Hello game!");
		messagePanel.add(messageText);
		messageText.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		// Table panel
		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(getColor());
		
		tmodel = new GameTableModel();
		table = new JTable(tmodel);
		table.setRowSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setRowHeight(table.getRowHeight() + 50);
	
		tablePanel.add(rowNumberTable());
		tablePanel.add(new JSeparator());
		tablePanel.add(table);
		
		// Column number panel
		
		JPanel columnNumberPanel = new JPanel();
		columnNumberPanel.setBackground(getColor());
		columnNumberPanel.add(new JSeparator());
		columnNumberPanel.add(columnNumberTable());

		// Added a new feature display total score
		totalScoreLabel = new JLabel("Total Score:");
		scoreOutputText = new JTextField("", 5);
		scoreOutputText.setMargin(new Insets(6, 5, 2, 10));

		JPanel displayScore = new JPanel();
		displayScore.setBackground(getColor());
		displayScore.setLayout(new FlowLayout(FlowLayout.RIGHT));
		displayScore.add(totalScoreLabel);
		displayScore.add(scoreOutputText); // End of display total score code
		
		// Input panel
		inputText = new JTextField("", 5);
		inputText.setMargin(new Insets(2, 2, 2, 2));
		inputText.setFont(new Font("Dialog", Font.PLAIN, 16));
		fireButton = new JButton("Fire");
		fireButton.setMnemonic(KeyEvent.VK_F);
		fireButton.addActionListener(e -> fireButtonListener());
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(getColor());
		inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		inputPanel.add(inputText);
		inputPanel.add(fireButton);
		
		// Main panel

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getColor());
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(messagePanel);
		mainPanel.add(displayScore);
		mainPanel.add(tablePanel);
		mainPanel.add(columnNumberPanel);
		mainPanel.add(inputPanel);
		
		// Finish frame
		
		frame.add(mainPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // this centers the frame on screen
		frame.setVisible(true);
		
		// Start game
		
		initGame();
	}
	
	/*
	 * Initializes the game and its model data.
	 */
	private void initGame() {
	
		controller = new Controller(this);
		displayMessage("Game start!");	
	}
	
	/*
	 * Method returns a color that is used within the GUI for
	 * all the components background (except the JTable).
	 * The color is Lavender Blue with RGB values 204, 212, 255.
	 */
	private Color getColor() {
	
		return new Color(204, 212, 255);
	}
	
	/*
	 * The reset menu item's action listener.
	 * Resets the game and its model data.
	 * Initializes the table and the table model for another game.
	 */
	private void resetGameListener() {
	
		fireButton.setEnabled(true);
		initGame();
		tmodel = new GameTableModel();
		table.setModel(tmodel);
		table.updateUI();
	}

	/*
	 *  Display total score
	 */
	public void displayTotalScore(int score) {
		scoreOutputText.setText(String.valueOf(score));
	}
	
	/*
	 * Listener for the fire button press action.
	 * Initiates the actions to update the table with hit or miss
	 * for an input guess.
	 */
	private void fireButtonListener() {
		
		String guessText  = inputText.getText();
		controller.processGuess(guessText);		
	}
	
	/*
	 * The JTable component that is used for making the column
	 * titles 0, 1, 2, ...
	 */
	private JTable columnNumberTable() {
	
		JTable table = new JTable(getColumnNumberModel());
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setGridColor(getColor());
		table.setFont(new Font("Dialog", Font.BOLD, 16));
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		cellRenderer.setBackground(getColor());

		for (int col = 0; col < table.getModel().getColumnCount(); col++) {
			table.getColumnModel().getColumn(col).setCellRenderer(cellRenderer);
		}
		
		return table;
	}

	/*
	 * The rowNumberTable's table model.
	 */	
	private TableModel getColumnNumberModel() {	
	
		TableModel model = new AbstractTableModel() {

			@Override
			public int getColumnCount() { 
				return Model.boardSize; 
			}
			
			@Override
			public int getRowCount() { 
				return 1;
			}
			
			@Override
			public Object getValueAt(int row, int col) {
				return String.valueOf(col);
			}
		};

		return model;
	}

	/*
	 * The JTable component that is used for making the row
	 * titles A, B, C ...
	 */	
	private JTable rowNumberTable() {

		JTable table = new JTable(getRowNumberModel());
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setGridColor(getColor());
		table.setFont(new Font("Dialog", Font.BOLD, 16));
		table.setRowHeight(table.getRowHeight() + 50);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		cellRenderer.setBackground(getColor());

		for (int col = 0; col < table.getModel().getColumnCount(); col++) {
			table.getColumnModel().getColumn(col).setCellRenderer(cellRenderer);
		}
		
		return table;
	}

	/*
	 * The rowNumberTable's table model.
	 */
	private TableModel getRowNumberModel() {
	
		TableModel model = new AbstractTableModel() {
		
			String [] data = Controller.alphabetsArray;
			
			@Override 
			public int getColumnCount() { 
				return 1; 
			}
			
			@Override
			public int getRowCount() { 
				return data.length;
			}
			
			@Override
			public Object getValueAt(int row, int col) { 
				return data[row]; 
			}
      };

		return model;
	}
	
	/*
	 * Update the table model and the table with the fired
	 * guess of hit or miss.
	 */
	public void displayHitOrMiss(String guess, String hitOrMiss) {
	
		int row = Integer.valueOf(guess.substring(0, 1));
		int col = Integer.valueOf(guess.substring(1));
		
		tmodel.updateCell(row, col, hitOrMiss);
		table.updateUI();
	}

	/*
	 * Displays messages about the game actions.
	 * The messages are displayed in the top panel of
	 * this GameWindow.
	 * Example messages: "Hit!" and "You sank my battleship!".
	 */
	public void displayMessage(String msg) {
	
		messageText.setText(msg);
		inputText.requestFocusInWindow();
		inputText.selectAll();
	}

	/*
	 * Display alert dialog for game action on invalid input guess.
	 * Message example: "Oops, that isn't on the board."
	 */
	public void alertDialog(Component c, String msg) {
	
		Toolkit.getDefaultToolkit().beep();
		JLabel alertLabel = new JLabel(msg);		
		JOptionPane.showMessageDialog(c, alertLabel, title, JOptionPane.PLAIN_MESSAGE);

		inputText.requestFocusInWindow();
		inputText.selectAll();
	}
	
	/*
	 * The GUI actions after all the ships are sunk.
	 */
	public void gameOver() {
	
		Toolkit.getDefaultToolkit().beep();
		fireButton.setEnabled(false);
		inputText.setText("");
		JLabel alertLabel = new JLabel("Game over! Reset to start again (File -> Reset)");		
		JOptionPane.showMessageDialog(frame, alertLabel, title, JOptionPane.PLAIN_MESSAGE);
	}
}