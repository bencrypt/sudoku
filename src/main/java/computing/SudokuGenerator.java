package computing;

import model.Cell;
import model.Sudoku;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    /* --> Logger <-- */

    private static final Logger LOGGER = LogManager.getLogger(SudokuGenerator.class);

    /* --> Methods <-- */

    /*
     * methods that can be accessed from outside
     */

    /**
     * Creates and returns an empty Sudoku (all values 0) where all Cells are NOT editable by default.
     *
     * @return
     *      an empty Sudoku with non-editable Cells
     */
    public static Sudoku generateEmptyAndNonEditableSudoku() {

        // create the board
        Cell[][] board = new Cell[9][9];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Cell(row, col,0,false);
            }
        }

        // return a Sudoku with the created board
        return new Sudoku(board);
    }

    /**
     * Creates and returns an empty Sudoku (all values 0) where all Cells are editable by default.
     *
     * @return
     *      an empty Sudoku with editable Cells
     */
    public static Sudoku generateEmptyAndEditableSudoku() {

        // create the board
        Cell[][] board = new Cell[9][9];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Cell(row, col,0,true);
            }
        }

        // return a Sudoku with the created board
        return new Sudoku(board);
    }

    /**
     * Tries to generate a Sudoku and returns it. The result may be null if the try does not get a valid and solveable
     * Sudoku.
     * This method is needed for the {@link GeneratorThread} to check after each try of generating a Sudoku
     * if the generating-process should be cancelled. With the alternate method {@link #generateSudoku(int)} this
     * checking would not be enabled because this method works until a solution is found. Because this can need a much
     * time, the trying-method can be used.
     *
     * @param countOfPredefinedCells
     *      the count of Cells that should be predefined
     * @return
     *      a valid and solveable Sudoku or null
     */
    public static Sudoku tryToGenerateSudoku(int countOfPredefinedCells) {

        // define a new Sudoku
        Sudoku newSudoku = null;

        // generate a new Sudoku until there is created one with exactly one solution
        if (countOfPredefinedCells < 25) {
        	newSudoku = createSudokuViaHumanStrategy(countOfPredefinedCells);
        } else {
        	newSudoku = createSudokuWithClearing(countOfPredefinedCells);
        }

        if (newSudoku != null) {

            // make the predefined Cells non editable
            for (Cell[] row : newSudoku.getBoard()) {
                for (Cell cell : row) {

                    // if the current Cell is empty (value 0) it can be edited
                    if (cell.getValue() != 0) {
                        cell.setEditable(false);
                    }
                }
            }
        }

        // return the created Sudoku
        return newSudoku;
    }

    /**
     * Generates a Sudoku with the given count of predefined Cells in one step. So there is definitely returned a
     * Sudoku and it is valid and solveable.
     * This method may not be used by {@link GeneratorThread} because it needs as long as a Sudoku is found. Instead
     * the {@link GeneratorThread} should use {@link #tryToGenerateSudoku(int)} to only make one try each time, so that
     * after this try the Thread can check whether it is cancelled or not.
     *
     * @param countOfPredefinedCells
     *      the count of Cells that should be predefined
     * @return
     *      a valid and solveable Sudoku
     */
    public static Sudoku generateSudoku(int countOfPredefinedCells) {

        // define a new Sudoku
        Sudoku newSudoku = null;

        //  save the start time
        long start = System.currentTimeMillis();

        // generate a new Sudoku until there is created one with exactly one solution
        while (newSudoku == null)
        {
            // TODO: create sudokus faster? ?
            //    --> until 23 okay, 22 very slow
            //    --> until 25 is Clearing the fastest approach, below HumanStrategy

            // used method (fastest until now and correct)
        	if (countOfPredefinedCells < 25) {
        		newSudoku = createSudokuViaHumanStrategy(countOfPredefinedCells);
	        } else {
	        	newSudoku = createSudokuWithClearing(countOfPredefinedCells);
	        }
        }

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
        LOGGER.debug("New Sudoku generated in {} ms", (end-start));

        // return the created Sudoku
        return newSudoku;
    }

    /*
     * Generating methods which randomly fill Cells and checks if there is a uniquely solution for the created
     * Sudoku. If not, null is returned.
     */

    /**
     * @deprecated
     *
     * Creates a Sudoku with filling randomly Cells. If this Sudoku has exactly one solution, returns it, else null.
     *
     * @param countOfPredefinedCells
     *      the count of Cells that should be filled
     * @return
     *      a Sudoku with exactly one solution or null
     */
    private static Sudoku createSudokuViaTrialAndError(int countOfPredefinedCells)
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
        boolean hasExactlyOneSolution = SudokuSolver.hasExactlyOneSolution(newSudoku);
        if (!hasExactlyOneSolution) {
            newSudoku = null;
        }

        return newSudoku;
    }

    /**
     * @deprecated
     *
     * Selects a random empty Cell of the given Sudoku and puts a random possible value in it. If there is no possible
     * value left for this Cell, returns null, else the Cell.
     *
     * @param sudoku
     *      the Sudoku in which a Cell should be filled
     * @param rand
     *      a random generator
     * @return
     *      the filled Cell or null if there is no possible value for this Cell
     */
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
        List<Integer> possibleValues = sudoku.getPossibleValues(toBeFilled);
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

    /**
     * Creates a Sudoku by filling as much Cells as given and checking if the created Sudoku can be solved by human
     * strategy afterwards. Returns a valid Sudoku which can be solved by those strategy.
     *
     * @param countOfPredefinedCells
     *      the count of Cells that should be filled
     * @return
     *      a human solveable Sudoku
     */
    private static Sudoku createSudokuViaHumanStrategy(int countOfPredefinedCells)
    {
        // initialize an empty Sudoku and a random generator
        Sudoku sudoku = generateEmptyAndEditableSudoku();
        Random rand = new Random();

        // fill as much Cells as needed
        // --> if there occurs an Cell with no possible values, directly return null
        List<Cell> empty = sudoku.getEmptyCells();
        Collections.shuffle(empty);

        Cell nextCell = null;
        for (int cellsFilled = 0; cellsFilled < countOfPredefinedCells; cellsFilled++) {

            // get the next empty Cell (random number because shuffled) and the possible values for it
            nextCell = empty.remove(0);
            List<Integer> possibleValues = sudoku.getPossibleValues(nextCell);

            // when there is no possible value, the Sudoku generated until now is invalid
            // --> return null directly, else fill the Cell
            if (possibleValues.size() > 0) {
                nextCell.setValue(possibleValues.get(rand.nextInt(possibleValues.size())));
            } else {
                return null;
            }
        }

        // determine all solutions
        // --> only return the new Sudoku, if there is exactly one solution, else return null
        boolean isSolveableByHumanStrategy = SudokuSolver.isSolveableByHumanStrategy(sudoku);
        if (!isSolveableByHumanStrategy) {
            sudoku = null;
        }

        return sudoku;
    }

    /*
     * Generating methods which delete a certain number of Cells from a full created Sudoku and check for a human
     * solution afterwards. If there is no such solution, try to clear another Cell.
     */

    /**
     * Creates first a full valid Sudoku and afterwards clears as much Cells as needed to have only the given count
     * of Cells left filled.
     *
     * @param countOfPredefinedCells
     *      the count of Cells that should NOT be cleared
     * @return
     *      the created Sudoku
     */
    private static Sudoku createSudokuWithClearing(int countOfPredefinedCells) {

        // create a full valid Sudoku
        Sudoku sudoku = null;
        while (sudoku == null) {
            sudoku = createFullSudoku();
        }

        // control output
        //LOGGER.debug("Complete Sudoku: {}", sudoku.getAsPrettyString());

        // create a random generator
        Random rand = new Random();

        // clear as much Cells as needed
        boolean cleared = false;
        while (!cleared) {
            cleared = clearCells(sudoku, countOfPredefinedCells, rand);
        }

        //LOGGER.debug("Cleared all needed Cells: {}" + cleared);

        // return the generated Sudoku
        return sudoku;
    }

    /*
     * methods for clearing Cells
     */

    /**
     * Clears all Cells but the given count of the given Sudoku.
     *
     * @param sudoku
     *      the Sudoku in which the Cells should be cleared
     * @param countOfPredefinedCells
     *      the count of Cells which should NOT be cleared
     * @param rand
     *      a random generator
     * @return
     *      true when as much Cells as whished are cleared, else false
     */
    private static boolean clearCells(Sudoku sudoku, int countOfPredefinedCells, Random rand) {

        // if already enough Cells cleared, return true
        if (sudoku.getCountOfFilledCells() == countOfPredefinedCells) {
            return true;
        }

        // try each possible Cell step by step via backtracking
        List<Cell> triedCells = new ArrayList<>();
        while (triedCells.size() < sudoku.getCountOfFilledCells()) {

            // control output
            //LOGGER.debug("tried: {}\nfilled: {}", triedCells.size(), sudoku.getCountOfFilledCells());

            // get an untried Cell
            Cell toClear = getCellToBeCleared(sudoku, triedCells);

            // save the old value
            int oldValue = toClear.getValue();

            // clear
            toClear.setValue(0);

            // do backtracking to clear the other Cells
            // --> if all Cells to be cleared are cleared, true is returned
            boolean solveAbleByHuman = SudokuSolver.isSolveableByHumanStrategy(sudoku);
            if (solveAbleByHuman) {
                //LOGGER.debug("cleared");
                clearCells(sudoku, countOfPredefinedCells, rand);
            }

            // if now all needed Cells are cleared and there is a qualified solution, return true
            if (solveAbleByHuman && sudoku.getCountOfFilledCells() == countOfPredefinedCells) {
                return true;
            }

            // here the cleared Cell does not give a valid solution, so it is resetted
            // --> another Cell can be tried to clear
            toClear.setValue(oldValue);
            triedCells.add(toClear);
        }

        // here no try was successful
        // --> return false to return to the upper level of backtracking
        return false;
    }

    /**
     * Returns an random choosed non-empty Cell of the given Sudoku.
     *
     * @param sudoku
     *      the Sudoku in which the Cell should be chosen
     * @param triedCells
     *      the Cells already tried to clear for the current backtracking level
     * @return
     *      the chosen, non-empty Cell
     */
    private static Cell getCellToBeCleared(Sudoku sudoku, List<Cell> triedCells) {

        // get a list with all filled but untried Cells
        List<Cell> filled = sudoku.getFilledCells();
        filled.removeAll(triedCells);

        // shuffle this list for random places of Cells
        Collections.shuffle(filled);

        // return the first Cell (random!)
        return filled.remove(0);
    }

    /*
     * methods for generating a full valid Sudoku.
     */

    /**
     * Generates a full valid Sudoku and returns it. Therefore uses the strategy to first fill the diagonal and then
     * solve the rest via backtracking.
     *
     * @return
     *      a full valid Sudoku
     */
    private static Sudoku createFullSudoku() {

        // initialize an empty Sudoku and a random generator
        Sudoku sudoku = generateEmptyAndEditableSudoku();

        // fill the diagonal and check for solution
        // --> if no solution, repeat until a solution is available
        do {
            // fill diagonal
            fillBoxOfDiagonal(0, sudoku);
            fillBoxOfDiagonal(1, sudoku);
            fillBoxOfDiagonal(2, sudoku);

            // control output
            //LOGGER.debug("Filled diagonal: {}", sudoku.getAsPrettyString());

        } while (SudokuSolver.findFirstSolution(sudoku) == null);

        // fill the rest of the Sudoku with solving it via backtracking
        sudoku = SudokuSolver.findFirstSolution(sudoku);

        // return the created Sudoku
        return sudoku;
    }

    /**
     * Fills the given box (0, 1 or 2) of the diagonal of the given Sudoku randomly with the values 1 to 9.
     * That means the box 0 is at the coordinates (0|0), the box 1 is at (1|1) and the box 2 is at (2|2) with three
     * boxes each row and column.
     *
     * @param diagonalBox
     *      the box to be filled, must be 0, 1 or 2
     * @param sudoku
     *      the Sudoku in which the box should be filled
     */
    private static void fillBoxOfDiagonal(int diagonalBox, Sudoku sudoku) {

        // if no valid box is given, throw an Exception
        if (diagonalBox < 0 || diagonalBox > 2) {
            throw new IllegalArgumentException();
        }

        // create and shuffle a list with the values 1 to 9
        List<Integer> oneToNine = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            oneToNine.add(i);
        }
        Collections.shuffle(oneToNine);

        // determine the start and ending coordinates of the box to be filled
        int indexStart = -1;
        int indexEnd = -1;
        switch (diagonalBox) {
            case 0:
                indexStart = 0;
                indexEnd = 3;
                break;
            case 1:
                indexStart = 3;
                indexEnd = 6;
                break;
            case 2:
                indexStart = 6;
                indexEnd = 9;
                break;
        }

        // to each Cell in the given box add the first value of the shuffled list and remove it
        Cell toFill;
        for (int row = indexStart; row < indexEnd; row++) {
            for (int col = indexStart; col < indexEnd; col++) {
                toFill = sudoku.getBoard()[row][col];
                toFill.setValue(oneToNine.remove(0));
            }
        }
    }
}
