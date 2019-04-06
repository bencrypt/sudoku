package loesen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.konsole.AusgabeKonsole;

public class SudokuLoeser {

	public static int[][] loeseSudoku(int[][] original) {
		
		int[][] kopie = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			kopie[i] = original[i].clone();
		}
		
		for (int zeile = 0; zeile < 9; zeile++) {
			for (int spalte = 0; spalte < 9; spalte++) {
				
				if (original[zeile][spalte] == 0) {
					
//					System.out.println("\n---------------\nNächstes Feld: " + zeile + "|" + spalte + "\n---------------\n");
					
					// ermittle alle möglichen Zahlen für das freie Feld
					List<Integer> moeglicheZahlen = ermittleAlleMoeglichenZahlenImFeld(original, zeile, spalte);
					
					for (int moeglicheZahl : moeglicheZahlen) {
						
						original[zeile][spalte] = moeglicheZahl;
						
//						System.out.println("\n------\nVersuche " + moeglicheZahl + ":");						
//						AusgabeKonsole.zeigeAufKonsole(original);
						
						if (loeseSudoku(original) != null) {
							return original;
						} else {
							original[zeile][spalte] = 0;
//							System.out.println("Versuch fehlgeschlagen!");
						}
					}
				
					return null;
				}
				
			}
		}
		
		return original;
		
	}
		
		
	public static int[][] loese(int[][] original) {
		
		// ermittle das nächste freie Feld im Sudoku
		// --> falls schon gefüllt, gebe das mitgegebene Sudoku zurück
		int[] freiesFeld = ermittleNaechstesFreiesFeld(original);
		
		if (freiesFeld == null) {
//			System.out.println("Das Sudoku ist bereits gelöst.");
			return original;
		}
		
		// Kontrollausgabe
//		System.out.println("\n---------------\nNächstes Feld: " + freiesFeld[0] + "|" + freiesFeld[1] + "\n---------------\n");
		
		// ermittle alle möglichen Zahlen für das freie Feld
		// --> falls keine Zahl mehr möglich ist, gebe null zurück
		List<Integer> moeglicheZahlen = ermittleAlleMoeglichenZahlenImFeld(original, freiesFeld[0], freiesFeld[1]);
		
		if (moeglicheZahlen.isEmpty()) {
			return null;
		}
		
		// kopiere das Original-Sudoku, damit Veränderungen nur an einer Kopie wirksam werden
		int[][] test = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			test[i] = original[i].clone();
		}
		
		// probiere die erste Zahl aus und nutze Rekursion	
		for(int moeglicheZahl: moeglicheZahlen) {		
			
			// setze die Testzahl in die Kopie des Sudokus
			test[freiesFeld[0]][freiesFeld[1]] = moeglicheZahl;			
			
			// Kontrollausgabe
//			System.out.println("\n------\nVersuche " + moeglicheZahl + ":");						
//			AusgabeKonsole.zeigeAufKonsole(original);
			
			// fahre mit der nächsten freie Zelle mittels Rekursion fort
			// --> falls das Sudoku gelöst werden kann, wird es am Ende zurückgegeben, ansonsten
			//     muss die aktuell eingesetzte Zahl zurückgesetzt und die nächste ausprobiert werden
			int[][] naechsterSchritt = loese(test);
			if (naechsterSchritt != null) {
				return naechsterSchritt;
			} else {
				test[freiesFeld[0]][freiesFeld[1]] = 0;
//				System.out.println("Versuch fehlgeschlagen!");
			}
		}
		
		// gebe null zurück, da keine der eingesetzten Zahlen zu einer Lösung geführt hat
		// --> in diesem Fall ist das Sudoku unlösbar
		return null;
		
	}
		
	public static List<int[][]> findeAlleLoesungen(int[][] original) {
		
		List<int[][]> loesungen = new ArrayList<int[][]>();
		
		findeLoesungen(original, loesungen);
		
		return loesungen;
		
	}
	
	public static void findeLoesungen(int[][] original, List<int[][]> loesungen) {
		
		// ermittle das nächste freie Feld im Sudoku
		// --> falls schon gefüllt, gebe das mitgegebene Sudoku zurück
		int[] freiesFeld = ermittleNaechstesFreiesFeld(original);
		
		if (freiesFeld == null) {
//			System.out.println("Das Sudoku ist bereits gelöst.");
			loesungen.add(original);
			return;
		}
		
		// Kontrollausgabe
//		System.out.println("\n---------------\nNächstes Feld: " + freiesFeld[0] + "|" + freiesFeld[1] + "\n---------------\n");
		
		// ermittle alle möglichen Zahlen für das freie Feld
		// --> falls keine Zahl mehr möglich ist, gebe null zurück
		List<Integer> moeglicheZahlen = ermittleAlleMoeglichenZahlenImFeld(original, freiesFeld[0], freiesFeld[1]);
		
		if (moeglicheZahlen.isEmpty()) {
			return;
		}
		
		// kopiere das Original-Sudoku, damit Veränderungen nur an einer Kopie wirksam werden
		int[][] test = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			test[i] = original[i].clone();
		}
		
		// probiere die erste Zahl aus und nutze Rekursion	
		for(int moeglicheZahl: moeglicheZahlen) {		
			
			// setze die Testzahl in die Kopie des Sudokus
			test[freiesFeld[0]][freiesFeld[1]] = moeglicheZahl;			
			
			// Kontrollausgabe
//			System.out.println("\n------\nVersuche " + moeglicheZahl + ":");						
//			AusgabeKonsole.zeigeAufKonsole(original);
			
			// fahre mit der nächsten freie Zelle mittels Rekursion fort
			// --> falls das Sudoku gelöst werden kann, wird es am Ende zurückgegeben, ansonsten
			//     muss die aktuell eingesetzte Zahl zurückgesetzt und die nächste ausprobiert werden
			findeLoesungen(test, loesungen);
		}		
	}
		

	
	public static int[][] erstelleSudoku(int anzahlFelder) {
	
		// initialisiere ein neues Sudoku mit 0en
		int[][] neuesSudoku = new int[9][9];
		
		for (int zeile = 0; zeile < 9; zeile++) {
			for (int spalte = 0; spalte < 9; spalte++) {
				neuesSudoku[zeile][spalte] = 0;
			}
		}
		
		// erstelle einen Zufallsgenerator
		Random zufallsgenerator = new Random();
		
		// belege so viele Felder per Zufall, wie vorgegeben sind
		for (int belegteFelder = 0; belegteFelder < anzahlFelder; belegteFelder++) {
			belegeNaechstesFeld(neuesSudoku, zufallsgenerator);
		}
		
		List<int[][]> loesungen = SudokuLoeser.findeAlleLoesungen(neuesSudoku);
		
		if (loesungen.size() == 1) {
			return neuesSudoku;
		} else {
			return erstelleSudoku(anzahlFelder);
		}		
	}
	
	private static int[][] belegeNaechstesFeld(int[][] sudoku, Random zufallsgenerator) {
		
		// wähle per Zufall ein freies Feld aus, das belegt werden soll		
		int feldX = -1;
		int feldY = -1;
		
		while (feldX == -1 && feldY == -1) {
			
			// bestimmte ein Feld per Zufall
			feldX = zufallsgenerator.nextInt(9);
			feldY = zufallsgenerator.nextInt(9);
			
			// sofern das Feld belegt ist, setze die Koordinaten zurück
			// --> neuer Schleifendurchgang, bis ein freies Feld gefunden wird
			if (sudoku[feldX][feldY] != 0) {
				feldX = -1;
				feldY = -1;
			}
		}
		
		// wähle per Zufall eine mögliche Zahl aus, die in das Feld geschrieben wird
		List<Integer> moeglicheZahlen = ermittleAlleMoeglichenZahlenImFeld(sudoku, feldX, feldY);
		if (moeglicheZahlen.size() > 0) {
			sudoku[feldX][feldY] = moeglicheZahlen.get(zufallsgenerator.nextInt(moeglicheZahlen.size()));	
		} else {
			return null;
		}
		
		// gebe das neue Sudoku zurück
		return sudoku;
		
	}
	
	private static int[] ermittleNaechstesFreiesFeld(int[][] sudoku) {
		
		int freiZeile = 0;
		int freiSpalte = 0;
		
		for (int zeile = 0; zeile < 10; zeile++) {		
			
			if (zeile == 9) {
				return null;
			}
			
			for (int spalte = 0; spalte < 9; spalte++) {
				
				if (sudoku[zeile][spalte] == 0) {
					freiZeile = zeile;
					freiSpalte = spalte;
					break;
				}
				
			}
			
			if (freiZeile != 0 || freiSpalte != 0) {
				break;
			}
		}
		
//		if (freiZeile != 0 || freiSpalte != 0) {
//			System.out.print("\nNächstes freies Feld: ");
//			System.out.println(freiZeile + "|" + freiSpalte);
//		} else {
//			System.out.println("Kein freies Feld mehr.");
//		}
		
		int[] freiesFeld = { freiZeile, freiSpalte };
		
		return freiesFeld;
	}
	
	private static List<Integer> ermittleAlleMoeglichenZahlenImFeld(int[][] sudoku, int feldZeile, int feldSpalte) {
		
		// Hilfsliste, die am Ende zurückgegeben wird
		ArrayList<Integer> moeglicheZahlen = new ArrayList<Integer>();
		
		// fülle die Ausgangsliste mit allen Zahlen von 1 bis 9
		for (int i = 1; i <= 9; i++) {
			moeglicheZahlen.add(i);
		}
		
		/* --> ermittle alle Zahlen, die nicht mehr vorkommen dürfen <-- */
		
		// Hilfsliste
		ArrayList<Integer> schonVorhandeneZahlen = new ArrayList<Integer>();
		
		// ermittle alle Zahlen, die schon in der Zeile vorkommen
		for (int spalte = 0; spalte < 9; spalte++) {
			if (sudoku[feldZeile][spalte] != 0 && !schonVorhandeneZahlen.contains(sudoku[feldZeile][spalte])) {
				schonVorhandeneZahlen.add(sudoku[feldZeile][spalte]);
			}
		}
		
		// ermittle alle Zahlen, die schon in der Spalte vorkommen
		for (int zeile = 0; zeile < 9; zeile++) {
			if (sudoku[zeile][feldSpalte] != 0 && !schonVorhandeneZahlen.contains(sudoku[zeile][feldSpalte])) {
				schonVorhandeneZahlen.add(sudoku[zeile][feldSpalte]);
			}
		}
		
		// ermittle alle Zahlen in dem 3x3-Kasten
		
		int zeileImKasten = feldZeile % 3;
		int spalteImKasten = feldSpalte % 3;
		
		int ersteZeileImKasten = feldZeile - zeileImKasten;
		int ersteSpalteImKasten = feldSpalte - spalteImKasten;
				
				
		
		for (int zeile = ersteZeileImKasten; zeile < ersteZeileImKasten + 3; zeile++) {
			for (int spalte = ersteSpalteImKasten; spalte < ersteSpalteImKasten + 3; spalte++) {
				
				if (sudoku[zeile][spalte] != 0 && !schonVorhandeneZahlen.contains(sudoku[zeile][spalte])) {
					schonVorhandeneZahlen.add(sudoku[zeile][spalte]);
				}
				
			}
		}
		
		// lösche alle vorhandenen Zahlen aus der Liste der möglichen Zahlen
				moeglicheZahlen.removeAll(schonVorhandeneZahlen);
		
		// Kontrollausgabe
//		System.out.println("Schon vorhandene Zahlen: " + schonVorhandeneZahlen);
//		System.out.println("Mögliche Zahlen: " + moeglicheZahlen);
		
		/* --> gebe die Liste zurück <-- */
		
		return moeglicheZahlen;
		
	}

}
