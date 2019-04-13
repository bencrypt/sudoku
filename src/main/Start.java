package main;

import java.util.ArrayList;
import java.util.List;

import console.SudokuPrinter;
import computing.SudokuSolver;
import model.Sudoku;

public class Start {
	
	public static void main(String[] args) {

		Sudoku sudoku = Test.createExampleSudoku();

		Test.findAndPrintSolution(sudoku);

		Test.generateAndPrintNewSudokuWithSolutions();
	}
}
