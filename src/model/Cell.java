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

    /**
     * Creates a Cell with the given fields. This Cell is initialized as valid and not automatically solved.
     *
     * @param row
     *      the row of the Cell on the Sudoku board
     * @param column
     *      the column of the Cell on the Sudoku board
     * @param value
     *      the value of the Cell
     * @param editable
     *      true when the Cell is editable, else false (when predefined)
     */
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

    /**
     * Creates a Cell with the given fields.
     *
     * @param row
     *      the row of the Cell on the Sudoku board
     * @param column
     *      the column of the Cell on the Sudoku board
     * @param value
     *      the value of the Cell
     * @param editable
     *      true when the Cell is editable, else false (when predefined)
     * @param valid
     *      true when the Cell is valid, else false (e.g. when duplicate)
     * @param automaticallySolved
     *      true when the Cell was automatically solved, else false
     */
    public Cell(int row, int column, int value, boolean editable, boolean valid, boolean automaticallySolved) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.editable = editable;
        this.valid = valid;
        this.automaticallySolved = automaticallySolved;
    }

    /* --> Methods <-- */

    /**
     * Returns a copy of the Cell with all existing fields.
     *
     * @return
     *      a copy of the Cell
     */
    public Cell copy() {
        return new Cell(row, column, value, editable, valid, automaticallySolved);
    }

    /**
     * Notifies the Observers when a field of the Cell has been changed.
     */
    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
        clearChanged();
    }

    /**
     * Returns true when the calling Cell is equal to the given Object. Therefore the given Object is checked for being
     * a Cell itself and afterwards each field of the calling Cell is compared to the given Cell.
     * When each field is equal, true is returned. If at least one field is not equal, return false.
     *
     * @param obj
     *      the Object to be compared to the calling Cell
     * @return
     *      true when the Cell is equal to the given Object (too a Cell), else false
     */
    @Override
    public boolean equals(Object obj) {

        // if the given Object is not a Cell, return false
        if (!(obj instanceof Cell)) {
            return false;
        }

        // get the Cell which is to be compared
        Cell toCompare = (Cell) obj;

        // helping variables
        boolean sameRow = row == toCompare.getRow();
        boolean sameCol = column == toCompare.getColumn();
        boolean sameValue = value == toCompare.getValue();
        boolean sameEditable = editable == toCompare.isEditable();
        boolean sameValid = valid == toCompare.isValid();
        boolean sameAutomaticallySolved = automaticallySolved == toCompare.isAutomaticallySolved();

        // check if the Cells are equal
        boolean equal = sameRow && sameCol && sameValue && sameEditable && sameValid && sameAutomaticallySolved;

        // return the result
        return equal;
    }

    /**
     * Returns a String representation of the Cell with the current values of each field.
     *
     * @return
     *      a String representation of the Cell
     */
    @Override
    public String toString() {
        return "Cell[row: " + row + ", column: " + column + ", value: " + value + ", editable: " + editable
                + ", valid: " + valid + ", automaticallySolved: " + automaticallySolved + "]";
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the row of the Cell
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column of the Cell
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the value of the Cell
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the new value of the Cell
     */
    public void setValue(int value) {
        this.value = value;
        notifyObservers();
    }

    /**
     * @return true when the Cell is editable, else false
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable true for an editable Cell, else false
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyObservers();
    }

    /**
     * @return true when the Cell is valid, else false
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param valid true for a valid Cell, else false
     */
    public void setValid(boolean valid) {
        this.valid = valid;
        notifyObservers();
    }

    /**
     * @return true when the Cell is automatically solved, else false
     */
    public boolean isAutomaticallySolved() {
        return automaticallySolved;
    }

    /**
     * @param automaticallySolved true for an automatically solved Cell, else false
     */
    public void setAutomaticallySolved(boolean automaticallySolved) {
        this.automaticallySolved = automaticallySolved;
        notifyObservers();
    }
}
