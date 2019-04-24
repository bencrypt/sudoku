package main;

import controller.CtrlWindow;

import javax.swing.*;
import java.awt.*;

public class Start {
	
	public static void main(String[] args) {

//		Sudoku sudoku = Test.createExampleSudoku();
//		Test.findAndPrintSolution(sudoku);
//		Test.generateAndPrintNewSudokuWithSolutions(30);

		SwingUtilities.invokeLater(() -> {
			CtrlWindow ctrlWindow = new CtrlWindow();
			ctrlWindow.showWindow();
		});

	}
}
