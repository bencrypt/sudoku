package model;

import java.util.Observable;

public class Cell extends Observable {
	
	/* --> Fields <-- */

    // position of the cell in a Sudoku board
	private int row;
	private int column;

	// value of the cell (1 to 9, 0 if empty)
	private int value;

	// status of the cell
	private boolean editable;
	private boolean valid;
	private boolean automaticallySolved;
	
	/* --> Constructors <-- */
	
	public Cell(int row, int column, int value, boolean editable) {

	    // set fields with given values
		this.row = row;
		this.column = column;
		this.value = value;
		this.editable = editable;

		// set fields with pre-defined values
		this.valid = true;
		this.automaticallySolved = false;
	}

    public Cell(int row, int column, int value, boolean editable, boolean valid, boolean automaticallySolved) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.editable = editable;
        this.valid = valid;
        this.automaticallySolved = automaticallySolved;
    }

    /* --> Methods <-- */

    public Cell copy() {
        return new Cell(row, column, value, editable, valid, automaticallySolved);
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
        clearChanged();
    }

    @Override
    public String toString() {
        return "Cell[row: " + row + ", column: " + column + ", value: " + value + ", editable: " + editable
                + ", valid: " + valid + ", automaticallySolved: " + automaticallySolved + "]";
    }

    /* --> Getters and Setters <-- */

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
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

    public void setAutomaticallySolved(boolean automaticallySolved) {
        this.automaticallySolved = automaticallySolved;
        notifyObservers();
    }
}
