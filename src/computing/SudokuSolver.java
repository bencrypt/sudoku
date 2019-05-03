package computing;

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

    /**
     * Returns the first solution of the given Sudoku that is found via backtracking.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @return
     *      the solved Sudoku
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

    /**
     * Solves the given Sudoku via backtracking so that the first solution is finally saved in the given instance.
     * Therefore a copy should be given to this method.
     * Returns true when a solution is found, else false.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @return
     *      true when a solution is found, else false
     */
	private static boolean solveViaBacktracking(Sudoku toSolve) {

		// determine the next free Cell on the Sudoku board
		// --> if the board is already filled, return true
		Cell freeCell = toSolve.getNextFreeCell();
		if (freeCell == null) {
			return true;
		}

		// determine all possible values for the free Cell
		// --> if no value is possible, return false
		List<Integer> possibleValues = toSolve.getPossibleValues(freeCell);
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

    /**
     * Returns a list with all solutions the given Sudoku has.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @return
     *      a list with all solutions for the given Sudoku, may be empty
     */
	public static List<Sudoku> findAllSolutions(Sudoku toSolve) {

	    // create and fill the result list
		List<Sudoku> solutions = new ArrayList<>();
		findAllSolutionsViaBacktracking(toSolve, solutions);

		// return the list
		return solutions;
		
	}

    /**
     * Fills the given list with all solutions the given Sudoku has. Those solutions are made in copies of the original
     * Sudoku so that the original Sudoku stays untouched from outside view (internally it is changed but resetted).
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @param solutions
     *      all (yet found) solutions of the given Sudoku
     */
	private static void findAllSolutionsViaBacktracking(Sudoku toSolve, List<Sudoku> solutions) {
		
		// determine the next free Cell in the Sudoku
		// --> if the Sudoku is completely filled, a copy of the given Sudoku is added to the solution list and
        //     go back to the last backtracking level
		Cell freeCell = toSolve.getNextFreeCell();
		if (freeCell == null) {
		    Sudoku solution = toSolve.copy();
			solutions.add(solution);
			return;
		}

		// determine all possible values for the free Cell
		// --> if no value is possible, return null
		List<Integer> possibleValues = toSolve.getPossibleValues(freeCell);
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

    /**
     * Returns true when the given Sudoku has exactly one solution, else false.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @return
     *      true when the Sudoku has exactly one solution, else false
     */
    public static boolean hasExactlyOneSolution(Sudoku toSolve)
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

    /**
     * Returns true when the given Sudoku has at least one solution, else false.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @return
     *      true when the Sudoku has at least one solution, else false
     */
    public static boolean hasAtLeastOneSolution(Sudoku toSolve)
    {
        // help list
        List<Sudoku> solutions = new ArrayList<>();

        // solve via backtracking because it is the fastest way to check for a solution
        Sudoku copy = toSolve.copy();
        if (SudokuSolver.solveViaBacktracking(copy)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches for <code>maxCount</code> solutions of the given Sudoku. If the given limit is exceeded for exactly
     * one count, the method will stop. All solutions found until then are saved in the given list.
     * <p>
     * Example: when the limit is 1, the method stops when 2 solutions are found. Both solutions are added to the
     * given list.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @param solutions
     *      the found solutions
     * @param maxCount
     *      the limit for searching solutions
     */
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
        Cell freeCell = toSolve.getNextFreeCell();
        if (freeCell == null) {
            Sudoku solution = toSolve.copy();
            solutions.add(solution);
            return;
        }

        // determine all possible values for the free Cell
        // --> if no value is possible, return null
        List<Integer> possibleValues = toSolve.getPossibleValues(freeCell);
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

    /**
     * Returns true when the given Sudoku is solveable via human strategies used in the method
     * {@link #determineNextStep(Sudoku)}. If the Sudoku is not solveable in this way, false will be returned.
     *
     * @param toSolve
     *      the Sudoku which should be solved
     * @return
     *      true when solveable by human strategy, else false
     */
    public static boolean isSolveableByHumanStrategy(Sudoku toSolve) {

        // copy the given Sudoku
        Sudoku copy = toSolve.copy();

        // search for the next (human strategy) step as long as possible
        Cell nextStep;
        do {
            nextStep = determineNextStep(copy);
            if (nextStep != null) {
                copy.getBoard()[nextStep.getRow()][nextStep.getColumn()].setValue(nextStep.getValue());
            }
        } while (nextStep != null);

        // if now there is no empty Cell, the Sudoku is solved
        // --> return true or false for each case
        if (copy.getNextFreeCell() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines the next Step for solving the given Sudoku. This step is calculated by human strategies, e.g. by
     * searching a Cell which has only one possible value left.
     * Returns a copy of the Cell to fill next, where this copy has the value to set for this step.
     *
     * @param sudoku
     *      the Sudoku which should be solved
     * @return
     *      a copy of the Cell to fill for the next step (has the value to set for solving)
     */
    public static Cell determineNextStep(Sudoku sudoku)
    {
        // TODO: weitere Berechnungen?

        // determine the value for the next Cell to be solved
        // --> a copy of the changed Cell will be returned
        Cell nextStep = onlyOnePossibleValue(sudoku);

        if (nextStep == null) {
            nextStep = onlyOnePossibleCellInRelatingUnits(sudoku);
//            System.out.println("zweiter Test: " + nextStep);
        }

        // control output
//        System.out.println("Nächster Schritt: " + nextStep);

        // return the copy of the changed Cell
        return nextStep;
    }

    /**
     * Goes through the given Sudoku to find a Cell where only one possible value is left. If such a Cell is found,
     * the left value is setted in a copy of this Cell. Afterwards this Cell is returned.
     * If no Cell has only one possible value, null is returned.
     *
     * @param sudoku
     *      the Sudoku which should be solved
     * @return
     *      a copy of the Cell found with the only possible value setted
     */
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
                List<Integer> possibleValues = sudoku.getPossibleValues(cell);

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

    /**
     * Goes through the given Sudoku to find a Cell which is the only Cell in a unit (row, column, box) to have a
     * certain value. If such a Cell is found, the value is setted in a copy of this Cell. Afterwards this Cell is
     * returned.
     * If no Cell has a definitively value, null is returned.
     * <p>
     * Example: In the first row the Cell (0|5) is the only Cell which can have the value 8. So 8 has to be setted
     * into this Cell.
     *
     * @param sudoku
     *      the Sudoku which should be solved
     * @return
     *      a copy of the Cell found with the only possible value setted
     */
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
                List<Integer> possibleValuesInCell = sudoku.getPossibleValues(cell);

                // determine all empty Cells and their possible values in the related row
                List<Cell> otherCellsInRow = sudoku.getAllCellsInRow(cell)
                                                .stream().filter(c -> c.getValue() == 0).collect(Collectors.toList());
                otherCellsInRow.remove(cell);

                Set<Integer> possibleValuesInRow = new TreeSet<>();
                otherCellsInRow.forEach(related -> {
                    possibleValuesInRow.addAll(sudoku.getPossibleValues(related));
                });

                // determine all empty Cells and their possible values in the related column
                List<Cell> otherCellsInColumn = sudoku.getAllCellsInColumn(cell)
                                                    .stream().filter(c -> c.getValue() == 0).collect(Collectors.toList());
                otherCellsInColumn.remove(cell);

                Set<Integer> possibleValuesInColumn = new TreeSet<>();
                otherCellsInColumn.forEach(related -> {
                    possibleValuesInColumn.addAll(sudoku.getPossibleValues(related));
                });

                // determine all empty Cells and their possible values in the related box
                List<Cell> otherCellsInBox = sudoku.getAllCellsInBox(cell)
                                                .stream().filter(c -> c.getValue() == 0).collect(Collectors.toList());
                otherCellsInBox.remove(cell);

                Set<Integer> possibleValuesInBox = new TreeSet<>();
                otherCellsInBox.forEach(related -> {
                    possibleValuesInBox.addAll(sudoku.getPossibleValues(related));
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
