package model;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {

	/* --> Fields <-- */

	private Cell[][] board;

	/* --> Constructor <-- */

	public Sudoku(Cell[][] board) {
		this.board = board;
	}

	/* --> Methods <-- */

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
		ArrayList<Integer> possibleValues = new ArrayList<>();

		// initialize the help list with all values from 1 to 9
		for (int i = 1; i <= 9; i++) {
			possibleValues.add(i);
		}

		/* --> determine all values which can't occur and delete them from the list to be returned <-- */

		// list with all existing values in the related units (row, column, box)
		ArrayList<Integer> existingValues = new ArrayList<>();

		// helper variables, defined before the following loop to save memory
		Cell currentCell = null;
		int currentValue = 0;

		// determine all values which are existing in the column
		for (int col = 0; col < 9; col++) {

			// get the current Cell and its value
			currentCell = board[cell.getGridX()][col];
			currentValue = currentCell.getValue();

			// add the current value to the existing values if needed
			if (currentValue != 0 && !existingValues.contains(currentValue)) {
				existingValues.add(currentValue);
			}
		}

		// determine all values which are existing in the row
		for (int row = 0; row < 9; row++) {

			// get the current Cell and its value
			currentCell = board[row][cell.getGridY()];
			currentValue = currentCell.getValue();

			// add the current value to the existing values if needed
			if (currentValue != 0 && !existingValues.contains(currentValue)) {
				existingValues.add(currentValue);
			}
		}

		// determine all values which are existing in the box

		// get the first coordinate in the affected box
		int rowInBox = cell.getGridX() % 3;
		int columnInBox = cell.getGridY() % 3;

		int firstRowInBox = cell.getGridX() - rowInBox;
		int firstColumnInBox = cell.getGridY() - columnInBox;

		// go through each Cell in the box
		for (int row = firstRowInBox; row < firstRowInBox + 3; row++) {
			for (int col = firstColumnInBox; col < firstColumnInBox + 3; col++) {

				// get the current Cell and its value
				currentCell = board[row][col];
				currentValue = currentCell.getValue();

				// add the current value to the existing values if needed
				if (currentValue != 0 && !existingValues.contains(currentValue)) {
					existingValues.add(currentValue);
				}
			}
		}

		// delete every existing value from the list of possible values
		possibleValues.removeAll(existingValues);

		// control output
//		System.out.println("Schon vorhandene Zahlen: " + existingValues);
//		System.out.println("Mögliche Zahlen: " + possibleValues);

		/* --> return the list of possible values <-- */
		return possibleValues;
	}

	/* --> Getters and Setters <-- */

	public Cell[][] getBoard() {
		return board;
	}

}
