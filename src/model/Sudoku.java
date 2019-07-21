package model;

import computing.SudokuSolver;
import console.SudokuPrinter;
import utils.DuplicatesChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Sudoku {

	/* --> Fields <-- */

	private Cell[][] board;
	private Sudoku solution;

	/* --> Constructors <-- */

	/**
	 * Creates a Sudoku with the given board.
	 *
	 * @param board
	 * 		the Sudoku board in form of a two-dimensional array of Cells
	 */
	public Sudoku(Cell[][] board) {
		this.board = board;
	}

	/**
	 * Creates a empty editable Sudoku
	 *
	 */
	public Sudoku() {

		Cell[][] board = new Cell[9][9];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				board[row][col] = new Cell(row, col, 0, true);
			}
		}

		this.board = board;
	}

	/* --> Methods <-- */

	/**
	 * Sets every Cell with a value (!= 0) as not editable.
	 */
	public void makeFilledCellsImmutable() {

		// set filled Cells as non editable
		Cell currentCell;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				currentCell = board[row][col];
				if (currentCell.getValue() != 0) {
					currentCell.setEditable(false);
				}
			}
		}
	}

	/**
	 * Restarts the Sudoku which means the value of every editable Cell is resetted to 0.
	 */
	public void restart() {

		// reset all editable Cells
		Cell currentCell;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				currentCell = board[row][col];
				if (currentCell.isEditable()) {
					currentCell.setValue(0);
				}
			}
		}

		// check for duplicates
		// --> only duplicates if user manipulated Sudoku
		checkAndMarkDuplicates();

		// compare to solution and mark incorrect cells
		checkAndMarkCellsNotSameAsSolution();
	}

	/**
	 * Returns a copy of the calling Sudoku. Therefore each Cell is copied into a new board array so that there is no
	 * dependency between the original and the copied Sudoku.
	 *
	 * @return
	 * 		a copy of the calling Sudoku
	 */
	public Sudoku copy() {

		// copy the board
		Cell[][] boardCopy = new Cell[9][9];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				boardCopy[row][col] = board[row][col].copy();
			}
		}

		// create and return a new Sudoku with the copied board
		return new Sudoku(boardCopy);
	}

	/**
	 * Returns true when the calling Sudoku is equal to the given Object. Therefore the given Object is checked for
	 * being a Sudoku itself and afterwards each Cell of the calling Sudoku is compared to the corresponding Cell in the
	 * given Sudoku.
	 * When each Cell is equal, true is returned. If at least one Cell is not equal, return false.
	 *
	 * @param obj
	 *      the Object to be compared to the calling Cell
	 * @return
	 *      true when the Cell is equal to the given Object (too a Cell), else false
	 */
	@Override
	public boolean equals(Object obj) {

		// if the given Object is not a Sudoku, return false
		if (!(obj instanceof Sudoku)) {
			return false;
		}

		// get the Sudoku which is to be compared
		Sudoku toCompare = (Sudoku) obj;

		// check equality of each Cell
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				// if one Cell is not equal, return false
				if (!board[row][col].equals(toCompare.getBoard()[row][col])) {
					return false;
				}
			}
		}

		// if arrived here, the Sudokus are equal
		return true;
	}

	/*
	 * methods for checking if the current filled Cells have the same values as the solution's cells
	 */

	public void checkAndMarkCellsNotSameAsSolution() {

		// find solution if not already done or if the Sudoku has changed since then
		if (solution == null || solution != null && !solution.equals(this.getUnchanged())) {
			solution = SudokuSolver.findFirstSolution(this.getUnchanged());
		}

		// check each Cell
		Cell currentCell = null;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				// get the current Cell
				currentCell = board[row][col];

				// mark the Cell as correct
				// --> all incorrect Cells are determined in following and marked as such
				currentCell.setSameAsSolution(true);

				// if the Cell is empty, skip the check
				if (currentCell.getValue() == 0) {
					continue;
				}

				// mark the Cell if it has not the same value as the solution
				if (currentCell.getValue() != solution.getBoard()[row][col].getValue()) {
					currentCell.setSameAsSolution(false);
				}
			}
		}
	}

	/*
	 * methods for marking duplicate Cells as invalid
	 */

	/**
	 * Checks for each Cell if it has a value that is already contained in the related units (row, column, box).
	 * Every affected Cell (duplicate) is marked as invalid.
	 *
	 * @see #checkAndMarkDuplicatesInRow(Cell)
	 * @see #checkAndMarkDuplicatesInColumn(Cell)
	 * @see #checkAndMarkDuplicatesInBox(Cell)
	 */
	public void checkAndMarkDuplicates() {

		// check each Cell
		Cell currentCell = null;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				// get the current Cell
				currentCell = board[row][col];

				// mark the Cell as valid
				// --> all invalid Cells are determined in following and marked as such
				currentCell.setValid(true);

				// if the Cell is empty, skip the check
				if (currentCell.getValue() == 0) {
					continue;
				}

				// check if the value of the cell is already existing in a related unit (row, column, box)
				// --> if so, mark the Cell as invalid
				checkAndMarkDuplicatesInRow(currentCell);
				checkAndMarkDuplicatesInColumn(currentCell);
				checkAndMarkDuplicatesInBox(currentCell);
			}
		}
	}

	/**
	 * Checks the row which contains the given Cell for Cells with duplicate values and marks them as invalid.
	 *
	 * @param cellInRow
	 * 		a Cell in the row to be checked
	 */
	private void checkAndMarkDuplicatesInRow(Cell cellInRow)
	{
		// determine all affected Cells and Values
		List<Cell> cells = getAllCellsInRow(cellInRow);
		List<Integer> values = new ArrayList<>(9);

		cells.forEach(cell -> {
			if (cell.getValue() != 0) {
				values.add(cell.getValue());
			}
		});

		// get the duplicate values
		DuplicatesChecker<Integer> duplicatesChecker = new DuplicatesChecker<>();
		Set<Integer> duplicates = duplicatesChecker.getAllDuplicatesInList(values);

		// mark every Cell with a duplicate value as invalid
		cells.forEach(cell -> {
			if (duplicates.contains(cell.getValue())) {
				cell.setValid(false);
			}
		});
	}

	/**
	 * Checks the column which contains the given Cell for Cells with duplicate values and marks them as invalid.
	 *
	 * @param cellInCol
	 * 		a Cell in the column to be checked
	 */
	private void checkAndMarkDuplicatesInColumn(Cell cellInCol)
	{
		// determine all affected Cells and Values
		List<Cell> cells = getAllCellsInColumn(cellInCol);
		List<Integer> values = new ArrayList<>(9);

		cells.forEach(cell -> {
			if (cell.getValue() != 0) {
				values.add(cell.getValue());
			}
		});

		// get the duplicate values
		DuplicatesChecker<Integer> duplicatesChecker = new DuplicatesChecker<>();
		Set<Integer> duplicates = duplicatesChecker.getAllDuplicatesInList(values);

		// mark every Cell with a duplicate value as invalid
		cells.forEach(cell -> {
			if (duplicates.contains(cell.getValue())) {
				cell.setValid(false);
			}
		});
	}

	/**
	 * Checks the box which contains the given Cell for Cells with duplicate values and marks them as invalid.
	 *
	 * @param cellInBox
	 * 		a Cell in the box to be checked
	 */
	private void checkAndMarkDuplicatesInBox(Cell cellInBox)
	{
		// determine all affected Cells and Values
		List<Cell> cells = getAllCellsInBox(cellInBox);
		List<Integer> values = new ArrayList<>(9);

		cells.forEach(cell -> {
			if (cell.getValue() != 0) {
				values.add(cell.getValue());
			}
		});

		// get the duplicate values
		DuplicatesChecker<Integer> duplicatesChecker = new DuplicatesChecker<>();
		Set<Integer> duplicates = duplicatesChecker.getAllDuplicatesInList(values);

		// mark every Cell with a duplicate value as invalid
		cells.forEach(cell -> {
			if (duplicates.contains(cell.getValue())) {
				cell.setValid(false);
			}
		});
	}

	/* --> Getters and Setters <-- */

	/*
	 * Getters and Setters for fields
	 */

	/**
	 * @return the board of the Sudoku as two-dimensional array of Cells
	 */
	public Cell[][] getBoard() {
		return board;
	}

    /**
     * @return all filled Cells of the board
     */
	public List<Cell> getFilledCells() {
	    List<Cell> filled = new ArrayList<>();

	    for (Cell[] row : board) {
	        for (Cell cell : row) {
	            if (cell.getValue() != 0) {
	                filled.add(cell);
                }
            }
        }

	    return filled;
    }

	/*
	 * Getters and Setters for implicite values
	 */

	public Sudoku getUnchanged() {

		// create a copy of the given Sudoku where all editable fields are cleared and
		// the flags are made equal
		Sudoku unchanged = this.copy();
		for (Cell[] row : unchanged.getBoard()) {
			for (Cell cell : row) {

				// make flags equal
				cell.setSameAsSolution(true);
				cell.setValid(true);
				cell.setAutomaticallySolved(false);

				// clear the cell
				if (cell.getValue() != 0 && cell.isEditable()) {
					cell.setValue(0);
				}
			}
		}

		// control output
		//SudokuPrinter.showOnConsole(unchanged, "Unchanged");

		// return the copy
		return unchanged;
	}

	/**
	 * @return all empty Cells of the board
	 */
	public List<Cell> getEmptyCells() {
		List<Cell> filled = new ArrayList<>();

		for (Cell[] row : board) {
			for (Cell cell : row) {
				if (cell.getValue() == 0) {
					filled.add(cell);
				}
			}
		}

		return filled;
	}

	/**
	 * @return the count of Cells with a value != 0
	 */
	public int getCountOfFilledCells() {
		int filled = 0;

		for (Cell[] row : board) {
			for (Cell cell : row) {
				if (cell.getValue() != 0) {
					filled++;
				}
			}
		}

		return filled;
	}

	/**
	 * @return the count of Cells with a value == 0
	 */
	public int getCountOfEmptyCells() {
		int empty = 0;

		for (Cell[] row : board) {
			for (Cell cell : row) {
				if (cell.getValue() == 0) {
					empty++;
				}
			}
		}

		return empty;
	}

	/*
	 * Getters for related Cells (in one ore multiple units)
	 */

	/**
	 * Returns a list which contains every Cell that is related to the given Cell. That means that the list contains
	 * each Cell in the realted row, column and box of the given Cell.
	 * <p>
	 * IMPORTANT: The given Cell itself is NOT included in the result list.
	 *
	 * @param cell
	 * 		the Cell for which all related Cells should be returned
	 * @return
	 * 		a list with all Cells related to the given Cell
	 *
	 * @see #getAllCellsInRow(Cell)
	 * @see #getAllCellsInColumn(Cell)
	 * @see #getAllCellsInBox(Cell)
	 */
	public List<Cell> getAllRelatedCells(Cell cell) {

		// create the list to be returned
		List<Cell> relatedCells = new ArrayList<>();

		// add all Cells in the related units (row, column, box)
		relatedCells.addAll(getAllCellsInRow(cell));
		relatedCells.addAll(getAllCellsInColumn(cell));
		relatedCells.addAll(getAllCellsInBox(cell));

		// remove all occurrences of the given Cell from the result list
		while (relatedCells.contains(cell)) {
			relatedCells.remove(cell);
		}

		// return the filled list
		return relatedCells;
	}

	/**
	 * Returns all Cells in the row where the given Cell is placed in. This also includes the given Cell itself.
	 *
	 * @param cell
	 * 		a Cell in the row to be gone through
	 * @return
	 * 		a list of all Cells in the affected row
	 */
	public List<Cell> getAllCellsInRow(Cell cell) {

		// needed variables
		List<Cell> allCells = new ArrayList<>(9);
		int row = cell.getRow();

		// get all Cells in the given row
		for (int col = 0; col < 9; col++) {
			allCells.add(board[row][col]);
		}

		// return the list
		return allCells;
	}

	/**
	 * Returns all Cells in the column where the given Cell is placed in. This also includes the given Cell itself.
	 *
	 * @param cell
	 * 		a Cell in the column to be gone through
	 * @return
	 * 		a list of all Cells in the affected column
	 */
	public List<Cell> getAllCellsInColumn(Cell cell) {

		// needed variables
		List<Cell> allCells = new ArrayList<>(9);
		int col = cell.getColumn();

		// get all Cells in the given column
		for (int row = 0; row < 9; row++) {
			allCells.add(board[row][col]);
		}

		// return the list
		return allCells;
	}

	/**
	 * Returns all Cells in the box where the given Cell is placed in. This also includes the given Cell itself.
	 *
	 * @param cell
	 * 		a Cell in the box to be gone through
	 * @return
	 * 		a list of all Cells in the affected box
	 */
	public List<Cell> getAllCellsInBox(Cell cell) {

		// needed variables
		List<Cell> allCells = new ArrayList<>(9);
		int row = cell.getRow();
		int col = cell.getColumn();

		// get the first coordinate in the affected box
		int rowInBox = row % 3;
		int columnInBox = col % 3;

		int firstRowInBox = row - rowInBox;
		int firstColumnInBox = col - columnInBox;

		// get all Cells in the given box
		for (int r = firstRowInBox; r < firstRowInBox + 3; r++) {
			for (int c = firstColumnInBox; c < firstColumnInBox + 3; c++) {
				allCells.add(board[r][c]);
			}
		}

		// return the list
		return allCells;
	}

	/*
	 * Getters for values in related Cells (in one or multiple units)
	 * --> determine existing values in a unit
	 */

	/**
	 * Returns all values contained in the row in which the given Cell is placed. This also includes the value of the
	 * given Cell.
	 *
	 * @param cell
	 * 		a Cell in the row to be gone through
	 * @return
	 * 		a list of all values in the affected row
	 */
	public List<Integer> getAllValuesInRow(Cell cell) {

		// needed variables
		List<Integer> allValues = new ArrayList<>();
		int row = cell.getRow();

		// helper variables, defined before the following loop to save memory
		Cell currentCell = null;
		int currentValue = 0;

		// determine all values which are existing in the column
		for (int col = 0; col < 9; col++) {

			// get the current Cell and its value
			currentCell = board[row][col];
			currentValue = currentCell.getValue();

			// add the current value to the existing values if needed
			if (currentValue != 0 && !allValues.contains(currentValue)) {
				allValues.add(currentValue);
			}
		}

		// return the set with all column values
		return allValues;
	}

	/**
	 * Returns all values contained in the column in which the given Cell is placed. This also includes the value of the
	 * given Cell.
	 *
	 * @param cell
	 * 		a Cell in the column to be gone through
	 * @return
	 * 		a list of all values in the affected column
	 */
	public List<Integer> getAllValuesInColumn(Cell cell) {

		// needed variables
		List<Integer> allValues = new ArrayList<>();
		int col = cell.getColumn();

		// helper variables, defined before the following loop to save memory
		Cell currentCell = null;
		int currentValue = 0;

		// determine all values which are existing in the column
		for (int row = 0; row < 9; row++) {

			// get the current Cell and its value
			currentCell = board[row][col];
			currentValue = currentCell.getValue();

			// add the current value to the existing values if needed
			if (currentValue != 0 && !allValues.contains(currentValue)) {
				allValues.add(currentValue);
			}
		}

		// return the set with all column values
		return allValues;
	}

	/**
	 * Returns all values contained in the box in which the given Cell is placed. This also includes the value of the
	 * given Cell.
	 *
	 * @param cell
	 * 		a Cell in the box to be gone through
	 * @return
	 * 		a list of all values in the affected box
	 */
	public List<Integer> getAllValuesInBox(Cell cell) {

		// needed variables
		List<Integer> allValues = new ArrayList<>();
		int row = cell.getRow();
		int col = cell.getColumn();

		// get the first coordinate in the affected box
		int rowInBox = row % 3;
		int columnInBox = col % 3;

		int firstRowInBox = row - rowInBox;
		int firstColumnInBox = col - columnInBox;

		// helper variables, defined before the following loop to save memory
		Cell currentCell = null;
		int currentValue = 0;

		// go through each Cell in the box
		for (int r = firstRowInBox; r < firstRowInBox + 3; r++) {
			for (int c = firstColumnInBox; c < firstColumnInBox + 3; c++) {

				// get the current Cell and its value
				currentCell = board[r][c];
				currentValue = currentCell.getValue();

				// add the current value to the existing values if needed
				if (currentValue != 0 && !allValues.contains(currentValue)) {
					allValues.add(currentValue);
				}
			}
		}

		// return the set with all column values
		return allValues;
	}

	/*
	 * Getters for solving and generating
	 * --> next free Cell, possible Values
	 */

	/**
	 * Returns the next free Cell the Sudoku contains. If no empty Cell is contained, null will be returned.
	 * "Free" or "empty" means here that the value of the Cell is 0.
	 *
	 * @return
	 * 		the next free Cell or null
	 */
	public Cell getNextFreeCell() {

		/* --> determine the next free Cell's coordinate on board <-- */

		// the coordinates for the free Cell
		int cellRow = -1;
		int cellColumn = -1;

		// go through each Cell until a free one is found
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				// a Cell is free when its value is 0
				if (board[row][col].getValue() == 0) {
					cellRow = row;
					cellColumn = col;
					break;
				}
			}

			// if there is already a cell found, stop the loop
			if (cellRow != -1 && cellColumn != -1) {
				break;
			}
		}

		/* --> get and return the free cell (maybe null) <-- */

		Cell freeCell = null;

		if (cellRow != -1 || cellColumn != -1) {
			freeCell = board[cellRow][cellColumn];
		}

		return freeCell;
	}

	/**
	 * Returns all possible values for the given Cell in form of a list.
	 *
	 * @param cell
	 * 		the Cell for which the possible values should be returned
	 * @return
	 * 		the possible values for the given Cell
	 */
	public List<Integer> getPossibleValues(Cell cell) {

		// list for all possible values, to be returned
		List<Integer> possibleValues = new ArrayList<>();

		// initialize the help list with all values from 1 to 9
		for (int i = 1; i <= 9; i++) {
			possibleValues.add(i);
		}

		/* --> determine all values which can't occur and delete them from the list to be returned <-- */

		// list with all existing values in the related units (row, column, box)
		Set<Integer> existingValues = new TreeSet<>();
		existingValues.addAll(getAllValuesInRow(cell));
		existingValues.addAll(getAllValuesInColumn(cell));
		existingValues.addAll(getAllValuesInBox(cell));

		// delete every existing value from the list of possible values
		possibleValues.removeAll(existingValues);

		// control output
//		System.out.println("\ndeterminePossibleValues: " + cell.getRow() + "|" + cell.getColumn());
//		System.out.println("Schon vorhandene Zahlen: " + existingValues);
//		System.out.println("Mögliche Zahlen: " + possibleValues);

		/* --> return the list of possible values <-- */
		return possibleValues;
	}
}
