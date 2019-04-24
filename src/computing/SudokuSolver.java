package computing;

import console.SudokuPrinter;
import model.Cell;
import model.Sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SudokuSolver {

    /*
     * find first solutions via backtracking
     */

	public static Sudoku findFirstSolution(Sudoku toSolve) {

		// copy the Sudoku to be solved so that the solution has another reference
		Sudoku solution = toSolve.copy();

		// solve the Sudoku and give the solution back
        boolean solved = solveViaBacktracking(solution);

        // if the Sudoku cannot be solved, return null, else the solution
        if (!solved) {
            solution = null;
        }
		return solution;
	}

	private static boolean solveViaBacktracking(Sudoku toSolve) {

		// determine the next free Cell on the Sudoku board
		// --> if the board is already filled, return true
		Cell freeCell = toSolve.determineNextFreeCell();
		if (freeCell == null) {
			return true;
		}

		// determine all possible values for the free Cell
		// --> if no value is possible, return false
		List<Integer> possibleValues = toSolve.determinePossibleValues(freeCell);
		if (possibleValues.isEmpty()) {
			return false;
		}

		// try the first possible value and use backtracking
		for(int possibleValue: possibleValues) {

			// put the value in the free cell
			// --> if the Sudoku cannot be solved in this way, the value will be changed in another
			//     level of the recursion
			freeCell.setValue(possibleValue);

			// control output
//			System.out.println("\n------\nVersuche " + possibleValue + ":");
//			SudokuPrinter.showOnConsole(toSolve);

			// try to solve the next free Cell
			// --> if the Sudoku can be solved, true will be returned in the end, otherwhise the current
			//     setted value has to be switched with the next possible value
			boolean nextStepSuccessfull = solveViaBacktracking(toSolve);
			if (nextStepSuccessfull) {
				return true;
			} else {
				freeCell.setValue(0);
//				System.out.println("Versuch fehlgeschlagen!");
			}
		}

		// return null because no possible value made it able to solve the Sudoku
		// --> the Sudoku cannot be solved
		return false;
	}

	/*
	 * all solutions via backtracking
	 */
		
	public static List<Sudoku> findAllSolutions(Sudoku toSolve) {
		
		List<Sudoku> solutions = new ArrayList<>();
		
		findAllSolutionsViaBacktracking(toSolve, solutions);
		
		return solutions;
		
	}
	
	private static void findAllSolutionsViaBacktracking(Sudoku toSolve, List<Sudoku> solutions) {
		
		// determine the next free Cell in the Sudoku
		// --> if the Sudoku is completely filled, a copy of the given Sudoku is added to the solution list and
        //     go back to the last backtracking level
		Cell freeCell = toSolve.determineNextFreeCell();
		if (freeCell == null) {
		    Sudoku solution = toSolve.copy();
			solutions.add(solution);
			return;
		}

		// determine all possible values for the free Cell
		// --> if no value is possible, return null
		List<Integer> possibleValues = toSolve.determinePossibleValues(freeCell);
		if (possibleValues.isEmpty()) {
			return;
		}

		// try the first possible value and use backtracking
		for(int possibleValue: possibleValues) {

			// put the value in the free cell
			// --> if the Sudoku cannot be solved in this way, the value will be changed in another
			//     level of the recursion
			freeCell.setValue(possibleValue);

			// control output
//			System.out.println("\n------\nVersuche " + possibleValue + ":");
//			SudokuPrinter.showOnConsole(original);

			// try to solve the next free Cell
			// --> if the Sudoku can be solved, it will be added to the solution list, afterwards the current
			//     setted value has to be switched with the next possible value
			// --> if not solved, the current value is just switched
			findAllSolutionsViaBacktracking(toSolve, solutions);

			// reset the value of the free Cell to try the next value
            freeCell.setValue(0);
		}
	}

	/*
	 * unique and count of solutions
	 */

    public static boolean HasExactlyOneSolution(Sudoku toSolve)
    {
        // help list
        List<Sudoku> solutions = new ArrayList<>();

        // search for exactly one solution
        // --> if there is one more solution, the method stops and returns false
        determineMaxCountOfSolutions(toSolve, solutions, 1);

        // if there is exactly one solution, return true, else false
        if (solutions.size() == 1)
            return true;
        else
            return false;
    }

    private static void determineMaxCountOfSolutions(Sudoku toSolve, List<Sudoku> solutions, int maxCount)
    {
        // as soon there is one more solution as searched stop the method
        if (solutions.size() > maxCount)
        {
            return;
        }

        // determine the next free Cell in the Sudoku
        // --> if the Sudoku is completely filled, a copy of the given Sudoku is added to the solution list and
        //     go back to the last backtracking level
        Cell freeCell = toSolve.determineNextFreeCell();
        if (freeCell == null) {
            Sudoku solution = toSolve.copy();
            solutions.add(solution);
            return;
        }

        // determine all possible values for the free Cell
        // --> if no value is possible, return null
        List<Integer> possibleValues = toSolve.determinePossibleValues(freeCell);
        if (possibleValues.isEmpty()) {
            return;
        }

        // try the first possible value and use backtracking
        for(int possibleValue: possibleValues) {

            // put the value in the free cell
            freeCell.setValue(possibleValue);

            // continue with the next free Cell via recursion
            determineMaxCountOfSolutions(toSolve, solutions, maxCount);

            // if the max count of searched solution is exceeded, stop the method
            if (solutions.size() > maxCount) {
                return;
            }

            // reset the value to try the next one
            freeCell.setValue(0);
        }
    }

    /*
     * solution via human strategy
     */

    public static boolean isSolveableByHumanStrategy(Sudoku toSolve) {

        Sudoku copy = toSolve.copy();

        Cell nextStep;
        do {
            nextStep = determineNextStep(copy);
            if (nextStep != null) {
                copy.getBoard()[nextStep.getRow()][nextStep.getColumn()].setValue(nextStep.getValue());
            }

        } while (nextStep != null);

        if (copy.determineNextFreeCell() == null) {
            return true;
        } else {
            return false;
        }
    }

    public static Cell determineNextStep(Sudoku sudoku)
    {
        // TODO: weitere Berechnungen

        // determine the value for the next Cell to be solved
        // --> a copy of the changed Cell will be returned
        Cell nextStep = onlyOnePossibleValue(sudoku);

        if (nextStep == null) {
            nextStep = onlyOnePossibleCellInRelatingUnits(sudoku);
//            System.out.println("zweiter Test: " + nextStep);
        }

        // Kontrollausgabe
//        System.out.println("Nächster Schritt: " + nextStep);

        // return the copy of the changed Cell
        return nextStep;
    }

    private static Cell onlyOnePossibleValue(Sudoku sudoku)
    {
        // help variable
        Cell changed = null;

        // go through each Cell until one Cell with only one possible value is found
        for (Cell[] row : sudoku.getBoard()) {
            for (Cell cell : row) {

                // if the current Cell cannot be edited or is already filled, continue with the next Cell
                if (!cell.isEditable() || cell.getValue() != 0) {
                    continue;
                }

                // determine all possible values for the Cell
                List<Integer> possibleValues = sudoku.determinePossibleValues(cell);

                // if there is exactly one possible value, set it into the Cell
                if (possibleValues.size() == 1) {
                    changed = cell.copy();
                    changed.setValue(possibleValues.get(0));
                    break;
                }
            }

            // break if there is already a changed cell
            if (changed != null) {
                break;
            }
        }

        // return the changed Cell (maybe null)
        return changed;
    }

    private static Cell onlyOnePossibleCellInRelatingUnits(Sudoku sudoku) {

        // help variable
        Cell changed = null;

        // go through each Cell until one Cell with only one possible value is found
        for (Cell[] row : sudoku.getBoard()) {
            for (Cell cell : row) {

                // if the current Cell cannot be edited or is already filled, continue with the next Cell
                if (!cell.isEditable() || cell.getValue() != 0) {
                    continue;
                }

                // determine the possible values of the cell
                List<Integer> possibleValuesInCell = sudoku.determinePossibleValues(cell);

                // determine all empty Cells and their possible values in the related row
                List<Cell> otherCellsInRow = sudoku.getAllCellsInRow(cell)
                                                .stream().filter(c -> c.getValue() == 0).collect(Collectors.toList());
                otherCellsInRow.remove(cell);

                Set<Integer> possibleValuesInRow = new TreeSet<>();
                otherCellsInRow.forEach(related -> {
                    possibleValuesInRow.addAll(sudoku.determinePossibleValues(related));
                });

                // determine all empty Cells and their possible values in the related column
                List<Cell> otherCellsInColumn = sudoku.getAllCellsInColumn(cell)
                                                    .stream().filter(c -> c.getValue() == 0).collect(Collectors.toList());
                otherCellsInColumn.remove(cell);

                Set<Integer> possibleValuesInColumn = new TreeSet<>();
                otherCellsInColumn.forEach(related -> {
                    possibleValuesInColumn.addAll(sudoku.determinePossibleValues(related));
                });

                // determine all empty Cells and their possible values in the related box
                List<Cell> otherCellsInBox = sudoku.getAllCellsInBox(cell)
                                                .stream().filter(c -> c.getValue() == 0).collect(Collectors.toList());
                otherCellsInBox.remove(cell);

                Set<Integer> possibleValuesInBox = new TreeSet<>();
                otherCellsInBox.forEach(related -> {
                    possibleValuesInBox.addAll(sudoku.determinePossibleValues(related));
                });

                // control output
//                System.out.println("\nonlyOnePossibleCellInRelatingUnits: " + cell.getRow() + "|" + cell.getColumn());
//                System.out.println("Möglich in Feld: " + possibleValuesInCell);
//                System.out.println("Möglich in anderen Feldern der Reihe:  " + possibleValuesInRow);
//                System.out.println("Möglich in anderen Feldern der Spalte: " + possibleValuesInColumn);
//                System.out.println("Möglich in anderen Feldern der Box:    " + possibleValuesInBox);

                // if there is exactly one possible value left for the cell, set it into the Cell
                for (Integer value : possibleValuesInCell) {

                    // if the value is possible in another Cell of an unit (row, column, box), try the next value
                    if (possibleValuesInRow.contains(value)
                            && possibleValuesInColumn.contains(value)
                            && possibleValuesInBox.contains(value))
                    {
                        continue;
                    }

                    // here the current value is only possible in the current Cell for one unit (row, column, box)
                    // --> set it into a copy of the Cell and stop the loop
                    changed = cell.copy();
                    changed.setValue(value);
                    break;
                }

                // break if there is already a changed cell
                if (changed != null) {
                    break;
                }
            }

            // break if there is already a changed cell
            if (changed != null) {
                break;
            }
        }

        // return the changed Cell (maybe null)
        return changed;

    }
}
