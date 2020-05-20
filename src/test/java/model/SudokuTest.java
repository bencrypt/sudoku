package model;

import computing.SudokuGenerator;
import org.junit.Test;

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
}
