package computing;

import model.Cell;
import model.Sudoku;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuGeneratorTest {

    /*==================================================*
     *==       generateEmptyAndNonEditableSudoku()    ==*
     *==================================================*/

    @Test
    public void generateEmptyAndNonEditableSudoku_ReturnEmptyAndNonEditableSudoku() {

        // create sudoku
        Sudoku sudoku = SudokuGenerator.generateEmptyAndNonEditableSudoku();

        // check if every board cell is empty (value == 0) and not editable
        Cell currentCell;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                currentCell = sudoku.getBoard()[row][col];
                assertFalse(currentCell.isEditable());
                assertFalse(currentCell.getValue() == 0);
            }
        }

    }

    /*==================================================*
     *==        generateEmptyAndEditableSudoku()      ==*
     *==================================================*/

    @Test
    public void generateEmptyAndEditableSudoku_ReturnEmptyAndEditableSudoku() {

        // create sudoku
        Sudoku sudoku = SudokuGenerator.generateEmptyAndEditableSudoku();

        // check if every board cell is empty (value == 0) and editable
        Cell currentCell;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                currentCell = sudoku.getBoard()[row][col];
                assertTrue(currentCell.isEditable());
                assertTrue(currentCell.getValue() == 0);
            }
        }

    }
}
