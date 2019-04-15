package model;

public class Cell {
	
	/* --> Fields <-- */

    // position of the cell in a Sudoku board
	private int gridX;
	private int gridY;

	// value of the cell (1 to 9, 0 if empty)
	private int value;

	// status of the cell
	private boolean editable;
	private boolean valid;
	private boolean automaticallySolved;
	
	/* --> Constructor <-- */
	
	public Cell(int gridX, int gridY, int value, boolean editable) {

	    // set fields with given values
		this.gridX = gridX;
		this.gridY = gridY;
		this.value = value;
		this.editable = editable;

		// set fields with pre-defined values
		this.valid = true;
		this.automaticallySolved = false;
	}
	
	/* --> Methods <-- */

    public Cell copy() {
        return new Cell(gridX, gridY, value, editable);
    }

    /* --> Getters and Setters <-- */

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isAutomaticallySolved() {
        return automaticallySolved;
    }
}
