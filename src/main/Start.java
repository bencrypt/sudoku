package main;

import controller.CtrlWindow;
import model.Sudoku;

import java.awt.*;

public class Start {
	
	public static void main(String[] args) {

//		Sudoku sudoku = Test.createExampleSudoku();
//		Test.findAndPrintSolution(sudoku);
//		Test.generateAndPrintNewSudokuWithSolutions(30);

		EventQueue.invokeLater(() -> {
			CtrlWindow ctrlWindow = new CtrlWindow();
			ctrlWindow.showWindow();
		});

	}
}
