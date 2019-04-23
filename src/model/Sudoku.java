package model;

import utils.DuplicatesChecker;

import java.util.*;

public class Sudoku {

	/* --> Fields <-- */

	private Cell[][] board;

	/* --> Constructors <-- */

	public Sudoku(Cell[][] board) {
		this.board = board;
	}

	/* --> Methods <-- */

	public void restart() {

		Cell currentCell;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				currentCell = board[row][col];
				if (currentCell.isEditable()) {
					currentCell.setValue(0);
				}
			}
		}

		checkAndMarkDuplicates();
	}

	/*
	 * methods for marking duplicate Cells as invalid
	 */

	public void checkAndMarkDuplicates() {

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

	/*
	 * methods for solving
	 * --> copy Sudoku, next free Cell and possible Values
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

	public Cell determineNextFreeCell() {

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

	public List<Integer> determinePossibleValues(Cell cell) {

		// list for all possible values, to be returned
		List<Integer> possibleValues = new ArrayList<>();

		// initialize the help list with all values from 1 to 9
		for (int i = 1; i <= 9; i++) {
			possibleValues.add(i);
		}

		/* --> determine all values which can't occur and delete them from the list to be returned <-- */

		// list with all existing values in the related units (row, column, box)
		Set<Integer> existingValues = new TreeSet<>();
		existingValues.addAll(determineAllValuesInRow(cell));
		existingValues.addAll(determineAllValuesInColumn(cell));
		existingValues.addAll(determineAllValuesInBox(cell));

		// delete every existing value from the list of possible values
		possibleValues.removeAll(existingValues);

		// control output
//		System.out.println("\ndeterminePossibleValues: " + cell.getRow() + "|" + cell.getColumn());
//		System.out.println("Schon vorhandene Zahlen: " + existingValues);
//		System.out.println("Mögliche Zahlen: " + possibleValues);

		/* --> return the list of possible values <-- */
		return possibleValues;
	}

	/*
	 * helping methods for solving
	 * --> determine existing values in a unit
	 */

	public List<Integer> determineAllValuesInRow(Cell cell) {

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

	public List<Integer> determineAllValuesInColumn(Cell cell) {

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

	public List<Integer> determineAllValuesInBox(Cell cell) {

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

	/* --> Getters and Setters <-- */

	public Cell[][] getBoard() {
		return board;
	}

	public List<Cell> getAllRelatedCells(Cell cell) {

		// create the list to be returned
		List<Cell> relatedCells = new ArrayList<>();

		// add all Cells in the related units (row, column, box)
		relatedCells.addAll(getAllCellsInRow(cell));
		relatedCells.addAll(getAllCellsInColumn(cell));
		relatedCells.addAll(getAllCellsInBox(cell));

		// return the filled list
		return relatedCells;
	}

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
}
