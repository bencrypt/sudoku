package model;

import computing.SudokuGenerator;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class SudokuTest {

    /*==================================================*
     *==                   equals()                   ==*
     *==================================================*/

    @Test
    public void equals_TwoDifferentTypes_ShouldReturnFalse() {

        // create a cell and some other object
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();
        Object o = new Object();

        // check for equality
        assertFalse(sudoku.equals(o));
    }

    @Test
    public void equals_TwoEqualSudokus_ShouldReturnTrue() {

        // create two sudokus with same parameters
        Cell[][] board = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = new Cell(row, col, 0, true);
            }
        }

        Sudoku sudokuA = new Sudoku(board);
        Sudoku sudokuB = new Sudoku(board);

        // check both directions for equality
        assertTrue(sudokuA.equals(sudokuB));
        assertTrue(sudokuA.equals(sudokuB));

    }

    @Test
    public void equals_TwoDifferentSudokus_ShouldReturnFalse() {

        // create two different sudokus
        Cell[][] boardA = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                boardA[row][col] = new Cell(row, col, 0, true);
            }
        }

        Cell[][] boardB = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                boardB[row][col] = new Cell(row, col, 0, false);
            }
        }

        Sudoku sudokuA = new Sudoku(boardA);
        Sudoku sudokuB = new Sudoku(boardB);

        // check for equality
        assertFalse(sudokuA.equals(sudokuB));
    }

    /*==================================================*
     *==         makeFilledCellsImmutable()           ==*
     *==================================================*/

    @Test
    public void makeFilledCellsImmutable_EmptySudoku_ShouldNotModifySudoku() {

        Sudoku original = SudokuGenerator.generateEmptyAndEditableSudoku();

        Sudoku copy = original.copy();
        copy.makeFilledCellsImmutable();

        assertTrue(original.equals(copy));
    }

    @Test
    public void makeFilledCellsImmutable_PartlyFilledSudoku_ShouldModifySudoku() {

        Sudoku original = SudokuGenerator.generateEmptyAndEditableSudoku();

        Sudoku copy = original.copy();
        copy.getBoard()[0][0].setValue(4);
        copy.makeFilledCellsImmutable();

        assertFalse(original.equals(copy));
    }

    /*==================================================*
     *==                    copy()                    ==*
     *==================================================*/

    @Test
    public void copy_ShouldReturnEqualSudoku() {

        Sudoku original = SudokuGenerator.generateEmptyAndEditableSudoku();
        Sudoku copy = original.copy();

        // check that copy and original are not the same but equal
        assertFalse(original == copy);
        assertTrue(original.equals(copy));

        // check that each cell is not the same but equal
        for (int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                assertFalse(original.getBoard()[row][col] == copy.getBoard()[row][col]);
                assertTrue(original.getBoard()[row][col].equals(copy.getBoard()[row][col]));
            }
        }
    }

    /*==================================================*
     *==         checkAndMarkDuplicates()           ==*
     *==================================================*/

    /*
     * check for rows
     */

    @Test
    public void checkAndMarkDuplicates_RowWithNoDuplicates_MarkNothing() {

        // fill first row with 1..9, set no duplicates
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        for (int col = 0; col < 9; col++) {
            sudoku.getBoard()[0][col].setValue(col+1);
        }

        // verify there is no duplicate
        sudoku.checkAndMarkDuplicates();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(sudoku.getBoard()[row][col].isValid());
            }
        }
    }

    @Test
    public void checkAndMarkDuplicates_RowWithOneDuplicate_MarkDuplicateCells() {

        // fill first row with (1,1,2..9), set first two entries as duplicates
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        for (int col = 0; col < 9; col++) {
            sudoku.getBoard()[0][col].setValue(col+1);
        }
        sudoku.getBoard()[0][1].setValue(1);

        // verify there is only a duplicate in (0,0) and (0,1)
        sudoku.checkAndMarkDuplicates();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (row == 0 && (col == 0 || col == 1)) {
                    // duplicates
                    assertFalse(sudoku.getBoard()[row][col].isValid());
                } else {
                    // no duplicates
                    assertTrue(sudoku.getBoard()[row][col].isValid());
                }
            }
        }
    }

    @Test
    public void checkAndMarkDuplicates_RowWithOnlyDuplicates_MarkAllRowCells() {

        // fill first row with (1..1), set all two entries as duplicates
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        for (int col = 0; col < 9; col++) {
            sudoku.getBoard()[0][col].setValue(1);
        }

        // verify there is only a duplicate in (0,x)
        sudoku.checkAndMarkDuplicates();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (row == 0) {
                    // duplicates
                    assertFalse(sudoku.getBoard()[row][col].isValid());
                } else {
                    // no duplicates
                    assertTrue(sudoku.getBoard()[row][col].isValid());
                }
            }
        }
    }

    /*
     * check for columns
     */

    @Test
    public void checkAndMarkDuplicates_ColWithNoDuplicates_MarkNothing() {

        // fill first col with 1..9, set no duplicates
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        for (int row = 0; row < 9; row++) {
            sudoku.getBoard()[row][0].setValue(row+1);
        }

        // verify there is no duplicate
        sudoku.checkAndMarkDuplicates();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(sudoku.getBoard()[row][col].isValid());
            }
        }
    }

    @Test
    public void checkAndMarkDuplicates_ColWithOneDuplicate_MarkDuplicateCells() {

        // fill first col with (1,1,2..9), set first two entries as duplicates
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        for (int row = 0; row < 9; row++) {
            sudoku.getBoard()[row][0].setValue(row+1);
        }
        sudoku.getBoard()[1][0].setValue(1);

        // verify there is only a duplicate in (0,0) and (1,0)
        sudoku.checkAndMarkDuplicates();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (col == 0 && (row == 0 || row == 1)) {
                    // duplicates
                    assertFalse(sudoku.getBoard()[row][col].isValid());
                } else {
                    // no duplicates
                    assertTrue(sudoku.getBoard()[row][col].isValid());
                }
            }
        }
    }

    @Test
    public void checkAndMarkDuplicates_ColWithOnlyDuplicates_MarkAllRowCells() {

        // fill first row with (1..1), set all two entries as duplicates
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        for (int row = 0; row < 9; row++) {
            sudoku.getBoard()[row][0].setValue(1);
        }

        // verify there is only a duplicate in (x,0)
        sudoku.checkAndMarkDuplicates();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (col == 0) {
                    // duplicates
                    assertFalse(sudoku.getBoard()[row][col].isValid());
                } else {
                    // no duplicates
                    assertTrue(sudoku.getBoard()[row][col].isValid());
                }
            }
        }
    }

    /*==================================================*
     *==              getPossibleValues()             ==*
     *==================================================*/

    @Test
    public void getPossibleValues_EmptySudoku_Return1To9() {

        // create empty Sudoku
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        // create arrays for should-be and actual results
        Integer[] allValues = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Integer[] result = new Integer[9];

        // check each cell in Sudoku, each run should return possible values (1..9)
        Cell current;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                current = sudoku.getBoard()[row][col];
                assertArrayEquals(allValues, sudoku.getPossibleValues(current).toArray(result));
            }
        }
    }

    @Test
    public void getPossibleValues_CellInRowWith1_Return2To9() {

        // create Sudoku with only value 1 at (0,0)
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();
        sudoku.getBoard()[0][0].setValue(1);

        // create arrays for should-be and actual results
        Integer[] allValues = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Integer[] resultForNonAffectedUnits = new Integer[9];

        Integer[] shouldBeResult = new Integer[] { 2, 3, 4, 5, 6, 7, 8, 9 };
        Integer[] resultForAffectedUnits = new Integer[8];

        // check each cell in Sudoku
        Cell current;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                current = sudoku.getBoard()[row][col];

                if (row <= 2 && col <= 2) {
                    // cells in first box should return possible values (2..9)
                    assertArrayEquals(shouldBeResult, sudoku.getPossibleValues(current).toArray(resultForAffectedUnits));
                } else if (row == 0 || col == 0) {
                    // cells in first row and first column should return possible values (2..9)
                    assertArrayEquals(shouldBeResult, sudoku.getPossibleValues(current).toArray(resultForAffectedUnits));
                } else {
                    // all other cells should return possible values (1..9)
                    assertArrayEquals(allValues, sudoku.getPossibleValues(current).toArray(resultForNonAffectedUnits));
                }
            }
        }
    }

}
