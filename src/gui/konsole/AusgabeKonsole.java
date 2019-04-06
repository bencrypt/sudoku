package gui.konsole;

public class AusgabeKonsole {
	
	public static void zeigeAufKonsole(int[][] sudoku) {
		
//		System.out.println("Ausgabe:");
		
		for (int zeile = 0; zeile < 9; zeile++) {
			
			// alle 3 Zeilen eine freie Zeile
			if (zeile % 3 == 0) {
				System.out.println("");
			}
			
			// jeweils alle Spalten der Zeile ausgeben
			for (int spalte = 0; spalte < 9; spalte++) {
	
				// alle 3 Spalten zwei zusätzliche Leerzeichen einsetzen
				if (spalte % 3 == 0) {
					System.out.print("  ");
				}
				
				// Zelle ausgeben
				System.out.print(" " + sudoku[zeile][spalte]);
			}
			
			// Zeilenumbruch
			System.out.println("");
			
		}
		
	}

}
