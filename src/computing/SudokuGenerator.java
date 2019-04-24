package computing;

import console.SudokuPrinter;
import model.Cell;
import model.Sudoku;
import utils.IndexConverter;
import utils.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
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

    public static Sudoku generateEmptyAndEditableSudoku() {
        Cell[][] board = new Cell[9][9];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Cell(row, col,0,true);
            }
        }

        return new Sudoku(board);
    }

    public static Sudoku generateSudoku(int countOfPredefinedCells) {

        // define a new Sudoku
        Sudoku newSudoku = null;

        //  save the start time
        long start = System.currentTimeMillis();

        // generate a new Sudoku until there is created one with exactly one solution
        while (newSudoku == null)
        {
//            newSudoku = createSudokuWithBacktracking(countOfPredefinedCells);
//            newSudoku = createSudokuViaTrialAndError(countOfPredefinedCells);
            newSudoku = createSudokuViaHumanStrategy(countOfPredefinedCells);
        }

        // TODO: falls zurück auf Backtracking oder Trial-and-Error,
        //       müssen die NICHT vordefinierten Zellen auf editable gesetzt werden

        // make the predefined Cells non editable
        for (Cell[] row : newSudoku.getBoard()) {
            for (Cell cell: row) {

                // if the current Cell is empty (value 0) it can be edited
                if (cell.getValue() != 0) {
                    cell.setEditable(false);
                }
            }
        }

        // save the end time
        long end = System.currentTimeMillis();

        // control output with needed time
        System.out.println("Neues Sudoku generiert in " + (end - start) + " ms.");

        // return the created Sudoku
        return newSudoku;
    }

    /*
     * Generating methods which randomly fill Cells and checks if there is a uniquely solution for the created
     * Sudoku. If not, null is returned.
     */

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
            toBeFilled = null;
        }

        // return the changed Cell
        return toBeFilled;
    }

    /*
     * Generating methods which randomly fill Cells and checks if the Sudoku is able to solve with human strategy.
     */

    private static Sudoku createSudokuViaHumanStrategy(int countOfPredefinedCells)
    {
        // initialize an empty Sudoku and a random generator
        Sudoku newSudoku = generateEmptyAndEditableSudoku();
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
        boolean isSolveableByHumanStrategy = SudokuSolver.isSolveableByHumanStrategy(newSudoku);
        if (!isSolveableByHumanStrategy) {
            newSudoku = null;
        }

        return newSudoku;
    }

    /*
     * Generating methods which delete a certain number of Cells from a full created Sudoku and check for a unique
     * solution afterwards. If there is no unique solution, use backtracking to try another Cell.
     */

    private static Sudoku createSudokuWithBacktracking(int countOfPredefinedCells) {

        // create a full valid Sudoku
        Sudoku sudoku = null;
        while (sudoku == null) {
            sudoku = createFullSudoku();
        }

        // control output
        System.out.println("Vollständiges Sudoku: ");
        SudokuPrinter.showOnConsole(sudoku);

        // create a list with all Cells and shuffle this list
        List<Cell> shuffledBoard = new ArrayList<>(81);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                shuffledBoard.add(sudoku.getBoard()[row][col]);
            }
        }
        Collections.shuffle(shuffledBoard);

        // clear as much Cells as wished
        int listIndex = 0;

        boolean cleared = false;
        while (!cleared) {
            cleared = clearCellViaBacktracking(sudoku, shuffledBoard, countOfPredefinedCells, listIndex);
            listIndex++;
        }

        return sudoku;
    }

    private static boolean clearCellViaBacktracking(Sudoku sudoku, List<Cell> shuffledBoard, int shouldBeCleared, int index) {

        // if already enough filled Cells, return true
        if (sudoku.getCountOfFilledCells() == shouldBeCleared) {
            return true;
        }

        // get the first Cell and its value
        Cell toClear = shuffledBoard.get(index);
        int originalValue = toClear.getValue();

        // make the Cell empty (value 0)
        toClear.setValue(0);

        // if the Sudoku has a unique solution, go on with the next Cell from the shuffled board,
        // else reset the value and
        if (SudokuSolver.HasExactlyOneSolution(sudoku)) {
            boolean cleared = clearCellViaBacktracking(sudoku, shuffledBoard, shouldBeCleared, index+1);
            if (cleared) {
                return true;
            }
        } else {
            toClear.setValue(originalValue);
        }

        return false;
    }

    private static Sudoku createFullSudoku() {

        // initialize an empty Sudoku and a random generator
        Sudoku sudoku = generateEmptyAndNonEditableSudoku();
        Random rand = new Random();

        // fill all Cells via backtracking so that a valid full Sudoku is created
        Cell nextStep = fillNextCell(sudoku, rand);
        while (sudoku.determineNextFreeCell() != null) {

            if (nextStep == null) {
                return null;
            }

            if (SudokuSolver.findFirstSolution(sudoku) != null) {
                nextStep = fillNextCell(sudoku, rand);
            } else {
                nextStep.setValue(0);
            }
        }

//        sudoku = createFullSudokuViaBacktracking(sudoku, rand);

        // return the created Sudoku
        return sudoku;
    }

    private static Sudoku createFullSudokuViaBacktracking(Sudoku sudoku, Random rand) {

        // if the Sudoku is filled, return it
        if (sudoku.determineNextFreeCell() == null) {
            return sudoku;
        }

        // helping list for all tried Cells
        List<Cell> triedCells = new ArrayList<>();

        // use backtracking to get a filled Sudoku
        // --> fill randomly one Cell after another as long as the Sudoku is solveable,
        //     when the Sudoku is not solveable undo the changes and try the next Cell
        while (triedCells.size() != 81) {

            // create the next random Cell
            Cell nextCell = null;
            do {
                nextCell = fillNextCell(sudoku, rand);
            } while (!triedCells.contains(nextCell));

            // if the Sudoku is solveable, create the next Cell,
            // else reset the upside created Cell and add the coordinate to the tried-list and try another Cell
            if (SudokuSolver.findFirstSolution(sudoku) != null) {
                Sudoku nextStep = createFullSudokuViaBacktracking(sudoku, rand);
                if (nextStep != null) {
                    return nextStep;
                }
            }

                nextCell.setValue(0);
                triedCells.add(nextCell);
        }

        return null;
    }
}
