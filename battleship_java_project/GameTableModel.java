import javax.swing.table.AbstractTableModel;
import javax.swing.ImageIcon;
import java.awt.Image;

/*
 * Table model for the JTable.
 */
public class GameTableModel extends AbstractTableModel {
	

	// The model data displayed in the JTable.
	private ImageIcon[][] data;
	
	// The path of images displayed in the table cells
	private static final String BLANK_ICON = "blank.png";
	private static final String SHIP_ICON = "ship.png";
	private static final String MISS_ICON = "miss.png";
	

	/*
	 * Constructor.
	 * Initializes all cells of the table model with blank image.
	 */
	public GameTableModel() {
	
		data = new ImageIcon[Model.boardSize][Model.boardSize];
		
		for (int i = 0; i < Model.boardSize; i++) {
			for (int j = 0; j < Model.boardSize; j++) {
				data[i][j] = getIconFromPath(BLANK_ICON);
			}
		}
	}
	
	/*
	 * Returns the image icon for a given path.
	 */
	private ImageIcon getIconFromPath(String inputIcon) {

		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(inputIcon));
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	@Override
	public int getRowCount() {
		return Model.boardSize;
	}
	
	@Override
	public int getColumnCount() {
		return Model.boardSize;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public Class getColumnClass(int column) {
		return ImageIcon.class;
	}

	/*
	 * Updates the table model with the "hit" or "miss" icons,
	 * for a given cell row, column and a hit/miss vale.
	 */
	public void updateCell(int row, int col, String hitOrMiss) {
	
		switch(hitOrMiss) {
			case "HIT": data[row][col] = getIconFromPath(SHIP_ICON);
						break;
			case "MISS": data[row][col] = getIconFromPath(MISS_ICON);
						break;
			default: 
				throw new IllegalArgumentException("Cell can only have Hit or Miss: " +
													hitOrMiss);
		}

		fireTableCellUpdated(row, col);
	}
}