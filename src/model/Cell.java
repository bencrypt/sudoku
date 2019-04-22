package model;

import java.util.Observable;

public class Cell extends Observable {
	
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
	
	/* --> Constructors <-- */
	
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

    public Cell(int gridX, int gridY, int value, boolean editable, boolean valid, boolean automaticallySolved) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.value = value;
        this.editable = editable;
        this.valid = valid;
        this.automaticallySolved = automaticallySolved;
    }

    /* --> Methods <-- */

    public Cell copy() {
        return new Cell(gridX, gridY, value, editable);
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
        clearChanged();
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
        notifyObservers();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyObservers();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        notifyObservers();
    }

    public boolean isAutomaticallySolved() {
        return automaticallySolved;
    }
}
