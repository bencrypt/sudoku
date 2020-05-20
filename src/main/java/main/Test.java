package main;

import computing.SudokuGenerator;
import computing.SudokuSolver;
import console.SudokuPrinter;
import model.Cell;
import model.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class Test {

    /* --> Methods <-- */

    /**
     * Creates an example Sudoku and returns it.
     *
     * @return
     *      an example Sudoku
     */
    public static Sudoku createExampleSudoku() {

        // create example
        byte[][] values =
                {
                        {8, 0, 0,   0, 0, 0,   0, 0, 0},
                        {0, 0, 3,   6, 0, 0,   0, 0, 0},
                        {0, 7, 0,   0, 9, 0,   2, 0, 0},

                        {0, 5, 0,   0, 0, 7,   0, 0, 0},
                        {0, 0, 0,   0, 4, 5,   7, 0, 0},
                        {0, 0, 0,   1, 0, 0,   0, 3, 0},

                        {0, 0, 1,   0, 0, 0,   0, 6, 8},
                        {0, 0, 8,   5, 0, 0,   0, 1, 0},
                        {0, 9, 0,   0, 0, 0,   4, 0, 0}
                };

        // create a Sudoku board from the values
        Cell[][] board = new Cell[9][9];
        for (byte row = 0; row < 9; row++) {
            for (byte column = 0; column < 9; column++) {
                boolean editable = values[row][column] == 0;
                board[row][column] = new Cell(row, column, values[row][column], editable);
            }
        }

        // create the new Sudoku and return it
        return new Sudoku(board);
    }

    /**
     * Solves the given Sudoku and prints the solution with the needed time to solve on console.
     *
     * @param toSolve
     *      the Sudoku to solve
     */
    public static void findAndPrintSolution(Sudoku toSolve) {

        // solve the Sudoku and determine the computing time
        long start = System.currentTimeMillis();

        Sudoku solvedSudoku = SudokuSolver.findFirstSolution(toSolve);

        long ende = System.currentTimeMillis();

        // print the solution and the time needed to solve
        SudokuPrinter.showOnConsole(solvedSudoku, "Ergebnis (" + (ende-start) + " ms)");
    }

    /**
     * Searches for all solutions of the given Sudoku and prints them with the needed time to solve on console.
     *
     * @param toSolve
     *      the Sudoku to solve
     */
    public static void findAndPrintAllSolutions(Sudoku toSolve) {

        // get all solutions in a list and determine the computing time
        long start = System.currentTimeMillis();
        List<Sudoku> solutions = SudokuSolver.findAllSolutions(toSolve);
        long end =System.currentTimeMillis();

        // print the solutions and the time needed for the whole computing
        System.out.println("\n-------\n"+solutions.size()+" Lösung(en) ("+(end -start)+"ms):\n-------");
        for (int i = 0; i < solutions.size(); i++) {
            SudokuPrinter.showOnConsole(solutions.get(i), "Lösung " + (i+1));
        }
    }

    /**
     * Generates a new Sudoku, prints it on the console, searches for all solutions and also prints them on the
     * console.
     *
     * @param countOfPredefinedCells
     *      the count of predefined Cells in the Sudoku to be created
     */
    public static void generateAndPrintNewSudokuWithSolutions(int countOfPredefinedCells) {

        // new Sudoku
        long start = System.currentTimeMillis();

        Sudoku newSudoku = SudokuGenerator.generateSudoku(countOfPredefinedCells);

        long end = System.currentTimeMillis();

        SudokuPrinter.showOnConsole(newSudoku, "Neues Sudoku (" + (end - start) + " ms)");

        // solve the new Sudoku
        start = System.currentTimeMillis();
        List<Sudoku> solutions = SudokuSolver.findAllSolutions(newSudoku);
        end = System.currentTimeMillis();

        System.out.println("\n-------\n" + solutions.size() + " Lösung(en) (" + (end - start) + "ms):\n-------");

        for (int i = 0; i < solutions.size(); i++) {
            SudokuPrinter.showOnConsole(solutions.get(i), "Lösung " + (i+1));
        }
    }
}
