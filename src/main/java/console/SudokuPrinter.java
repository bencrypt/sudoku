package console;

import model.Sudoku;

public class SudokuPrinter {

	/**
	 * Prints the values of the given Sudoku after the given title on the console.
	 *
	 * @param sudoku
	 * 		the Sudoku to be printed on console
	 * @param title
	 * 		the title to print before the Sudoku
	 */
	public static void showOnConsole(Sudoku sudoku, String title) {

		// header with title
		System.out.println("-------------");
		System.out.println(title + ":");

		// Sudoku
		showOnConsole(sudoku);

		// footer
		System.out.println("-------------");
	}

	/**
	 * Prints the values of the given Sudoku on the console.
	 *
	 * @param sudoku
	 * 		the Sudoku to be printed on console
	 */
	public static void showOnConsole(Sudoku sudoku) {

		// go through each row
		for (byte row = 0; row < 9; row++) {
			
			// all 3 rows a free line
			if (row % 3 == 0) {
				System.out.println("");
			}
			
			// print all columns of the current row
			for (int column = 0; column < 9; column++) {
	
				// all 3 columns additionally spaces
				if (column % 3 == 0) {
					System.out.print("  ");
				}
				
				// print cell value
				System.out.print(" " + sudoku.getBoard()[row][column].getValue());
			}
			
			// line break after the row
			System.out.println("");
		}
	}
}
