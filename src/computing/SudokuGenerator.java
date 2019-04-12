package computing;

import model.Cell;
import model.Sudoku;

import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    public static Sudoku generateEmptyAndNonEditableSudoku() {
        Cell[][] board = new Cell[9][9];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Cell(row, col,0,false);
            }
        }

        return new Sudoku(board);
    }

    public static Sudoku generateSudoku(int countOfPredifinedCells) {

        // initialize an empty Sudoku which is to be filled
        Sudoku newSudoku = generateEmptyAndNonEditableSudoku();

        // create an instance of Random which will be given the following methods
        Random rand = new Random();

        // TODO: korrigieren
        
        // fill as much Cells as predefined
        for (int filledCells = 0; filledCells < countOfPredifinedCells; filledCells++) {
            Cell filled = fillNextCell(newSudoku, rand);
        }

        List<Sudoku> solutions = SudokuSolver.findAllSolutions(newSudoku);

        if (solutions.size() == 1) {
            return newSudoku;
        } else {
            return generateSudoku(countOfPredifinedCells);
        }
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
