package main;

import computing.SudokuSolver;
import console.SudokuPrinter;
import model.Cell;
import model.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class Test {

    /* --> Methods <-- */

    public static Sudoku createExampleSudoku() {

        // create example
        byte[][] values =
                {
                        {8, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 6, 0, 0, 0, 0, 0},
                        {0, 7, 0, 0, 9, 0, 2, 0, 0},

                        {0, 5, 0, 0, 0, 7, 0, 0, 0},
                        {0, 0, 0, 0, 4, 5, 7, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 3, 0},

                        {0, 0, 1, 0, 0, 0, 0, 6, 8},
                        {0, 0, 8, 5, 0, 0, 0, 1, 0},
                        {0, 9, 0, 0, 0, 0, 4, 0, 0}
                };

        // create a Sudoku board from the values
        Cell[][] board = new Cell[9][9];
        for (byte row = 0; row < 9; row++) {
            for (byte column = 0; column < 9; column++) {
                boolean changeable = values[row][column] == 0 ? true : false;
                board[row][column] = new Cell(row, column, values[row][column], true);
            }
        }

        // create the new Sudoku and return it
        return new Sudoku(board);
    }

    public static void findAndPrintSolution(Sudoku toSolve) {

        // solve the Sudoku and determine the computing time
        long start = System.currentTimeMillis();

        Sudoku solvedSudoku = SudokuSolver.findFirstSolution(toSolve);

        long ende = System.currentTimeMillis();

        // print the solution and the time needed to solve
        System.out.println("\n-------\nErgebnis (" + (ende - start) + "ms):\n-------");
        SudokuPrinter.showOnConsole(solvedSudoku);
    }

    public static void findAndPrintAllSolutions(Sudoku toSolve) {

        // get all solutions in a list and determine the computing time
        List<Sudoku> solutions = new ArrayList<>();

        long start =System.currentTimeMillis();

        solutions = SudokuSolver.findAllSolutions(toSolve);

        long ende =System.currentTimeMillis();

        // print the solutions and the time needed for the whole computing
        System.out.println("\n-------\n"+solutions.size()+" Lösung(en) ("+(ende -start)+"ms):\n-------");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("\nLösung " + (i + 1) + ":");
            SudokuPrinter.showOnConsole(solutions.get(i));
        }
    }

    public static void generateAndPrintNewSudokuWithSolutions() {

        // new Sudoku
        long start = System.currentTimeMillis();

        // TODO: Sudoku-Generator
        Sudoku newSudoku = null;

        long end = System.currentTimeMillis();

        System.out.println("\n-------\nNeues Sudoku (" + (end - start) + "ms):\n-------");
        SudokuPrinter.showOnConsole(newSudoku);


        // solve the new Sudoku
        List<Sudoku> solutions = new ArrayList<>();

        start = System.currentTimeMillis();

        solutions = SudokuSolver.findAllSolutions(newSudoku);

        end = System.currentTimeMillis();

        System.out.println("\n-------\n" + solutions.size() + " Lösung(en) (" + (end - start) + "ms):\n-------");

        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("\nLösung " + (i+1) + ":");
            SudokuPrinter.showOnConsole(solutions.get(i));
        }
    }
}
