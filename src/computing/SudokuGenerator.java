package computing;

import model.Cell;
import model.Sudoku;

import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    // TODO: schnelleres Erstellen von Sudokus

    public static Sudoku generateEmptyAndNonEditableSudoku() {
        Cell[][] board = new Cell[9][9];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Cell(row, col,0,false);
            }
        }

        return new Sudoku(board);
    }

    public static Sudoku generateSudoku(int countOfPredefinedCells) {

        // define a new Sudoku
        Sudoku newSudoku = null;

        // generate a new Sudoku until there is created one with exactly one solution
        while (newSudoku == null)
        {
            newSudoku = createSudokuViaTrialAndError(countOfPredefinedCells);
        }

        // make the not predefined Cells editable
        for (Cell[] row : newSudoku.getBoard()) {
            for (Cell cell: row) {

                // if the current Cell is empty (value 0) it can be edited
                if (cell.getValue() == 0) {
                    cell.setEditable(true);
                }
            }
        }

        return newSudoku;
    }

    private static Sudoku createSudokuViaTrialAndError(int countOfPredefinedCells)
    {
        // initialize an empty Sudoku and a random generator
        Sudoku newSudoku = generateEmptyAndNonEditableSudoku();
        Random rand = new Random();

        // fill as much Cells as needed
        Cell nextCellFilled;
        for (byte cellsFilled = 0; cellsFilled < countOfPredefinedCells; cellsFilled++) {
            nextCellFilled = null;

            while (nextCellFilled == null)
            {
                nextCellFilled = fillNextCell(newSudoku, rand);
            }
        }

        // determine all solutions
        // --> only return the new Sudoku, if there is exactly one solution, else return null
        boolean hasExactlyOneSolution = SudokuSolver.HasExactlyOneSolution(newSudoku);
        if (!hasExactlyOneSolution) {
            newSudoku = null;
        }

        return newSudoku;
    }

    private static Cell fillNextCell(Sudoku sudoku, Random rand) {

        // select a random Cell to be filled
        Cell toBeFilled = null;
        int x = 0;
        int y = 0;

        while (toBeFilled == null) {

            // create random coordinates
            x = rand.nextInt(9);
            y = rand.nextInt(9);

            // if the Cell is empty, select it
            if (sudoku.getBoard()[x][y].getValue() == 0) {
                toBeFilled = sudoku.getBoard()[x][y];
            }
        }

        // select a random value and set it into the Cell
        // --> if no value is possible, return null
        List<Integer> possibleValues = sudoku.determinePossibleValues(toBeFilled);
        if (possibleValues.size() > 0) {
            int randValue = possibleValues.get(rand.nextInt(possibleValues.size()));
            toBeFilled.setValue(randValue);
        } else {
            return null;
        }

        // return the changed Cell
        return toBeFilled;
    }
}
