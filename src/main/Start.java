package main;

import java.util.ArrayList;
import java.util.List;

import gui.konsole.AusgabeKonsole;
import loesen.SudokuLoeser;

public class Start {
	
	public static void main(String[] args) {
		
		// Test-Sudoku
/*		int [][] sudoku = 
			{
				{ 5, 3, 0,   6, 7, 8,   9, 0, 2 },
                { 0, 7, 2,   1, 9, 5,   3, 4, 8 }, 
                { 0, 9, 8,   3, 4, 2,   5, 0, 7 },
                
                { 8, 5, 9,   7, 6, 1,   4, 2, 3 }, 
                { 4, 2, 6,   8, 0, 3,   7, 0, 1 },
                { 7, 1, 0,   9, 2, 4,   8, 5, 6 },
                
                { 9, 6, 0,   5, 3, 7,   2, 8, 4 },
                { 2, 8, 7,   4, 1, 9,   6, 3, 5 }, 
                { 3, 4, 5,   2, 8, 6,   1, 7, 9 }
			};
*/	
		
/*
		int [][] sudoku = 
			{
				{ 8, 0, 0,   0, 0, 0,   0, 0, 0 },
				{ 0, 0, 3,   6, 0, 0,   0, 0, 0 },
				{ 0, 7, 0,   0, 9, 0,   2, 0, 0 },
				
				{ 0, 5, 0,   0, 0, 7,   0, 0, 0 },
				{ 0, 0, 0,   0, 4, 5,   7, 0, 0 },
				{ 0, 0, 0,   1, 0, 0,   0, 3, 0 },
				
				{ 0, 0, 1,   0, 0, 0,   0, 6, 8 },
				{ 0, 0, 8,   5, 0, 0,   0, 1, 0 },
				{ 0, 9, 0,   0, 0, 0,   4, 0, 0 } 
			};
		
*/
		
		int [][] sudoku = 
			{
				{ 8, 0, 0,   0, 0, 0,   0, 0, 0 },
				{ 0, 0, 3,   6, 0, 0,   0, 0, 0 },
				{ 0, 7, 0,   0, 9, 0,   2, 0, 0 },
				
				{ 0, 5, 0,   0, 0, 7,   0, 0, 0 },
				{ 0, 0, 0,   0, 4, 5,   7, 0, 0 },
				{ 0, 0, 0,   1, 0, 0,   0, 3, 0 },
				
				{ 0, 0, 1,   0, 0, 0,   0, 6, 8 },
				{ 0, 0, 8,   5, 0, 0,   0, 1, 0 },
				{ 0, 9, 0,   0, 0, 0,   4, 0, 0 } 
			};
		
		
		
		int[][] test = new int[sudoku.length][];
		for (int i = 0; i < sudoku.length; i++) {
			test[i] = sudoku[i].clone();
		}
		
		
		// Ausgabe
		System.out.println("\n-------\nOriginal:\n-------");
		AusgabeKonsole.zeigeAufKonsole(sudoku);
		
		long start = System.currentTimeMillis();
		
		// Lösung 1
		int[][] geloestesSudoku = SudokuLoeser.loeseSudoku(sudoku);
		
		long ende = System.currentTimeMillis();
		
		System.out.println("\n-------\nErgebnis (" + (ende - start) + "ms):\n-------");
		AusgabeKonsole.zeigeAufKonsole(geloestesSudoku);
		
		// Lösung 2, ca. 2 bis 3 mal so schnell
		start = System.currentTimeMillis();
				
		geloestesSudoku = SudokuLoeser.loese(test);
			
		ende = System.currentTimeMillis();
		
		System.out.println("\n-------\nErgebnis (" + (ende - start) + "ms):\n-------");
		AusgabeKonsole.zeigeAufKonsole(geloestesSudoku);
		
		
		// Liste mit allen möglichen Lösungen
		List<int[][]> loesungen = new ArrayList<int[][]>();
		
		start = System.currentTimeMillis();
		
		loesungen = SudokuLoeser.findeAlleLoesungen(test);
		
		ende = System.currentTimeMillis();
		
//		if (loesungenGefunden) {
			System.out.println("\n-------\n" + loesungen.size() + " Lösung(en) (" + (ende - start) + "ms):\n-------");
			
			for (int i = 0; i < loesungen.size(); i++) {
				System.out.println("\nLösung " + (i+1) + ":");
				AusgabeKonsole.zeigeAufKonsole(loesungen.get(i));
			}
			
//		}
		
		
		
		
		
		// neues Sudoku
		start = System.currentTimeMillis();
		
		int[][] neuesSudoku = SudokuLoeser.erstelleSudoku(30);
		
		ende = System.currentTimeMillis();
		
		System.out.println("\n-------\nNeues Sudoku (" + (ende - start) + "ms):\n-------");
		AusgabeKonsole.zeigeAufKonsole(neuesSudoku);
		
		
		// löse das neue Sudoku
		loesungen = new ArrayList<int[][]>();
		
		start = System.currentTimeMillis();
		
		loesungen = SudokuLoeser.findeAlleLoesungen(neuesSudoku);
		
		ende = System.currentTimeMillis();
		
//		if (neuesSudokuGeloest) {
			System.out.println("\n-------\n" + loesungen.size() + " Lösung(en) (" + (ende - start) + "ms):\n-------");
			
			for (int i = 0; i < loesungen.size(); i++) {
				System.out.println("\nLösung " + (i+1) + ":");
				AusgabeKonsole.zeigeAufKonsole(loesungen.get(i));
			}
//		}
		
		
	}
	
	
	

}
