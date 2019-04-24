package computing;

import model.Sudoku;

import javax.swing.*;

public class GeneratorThread extends SwingWorker<Void, Void> {

    /* --> Fields <-- */

    private int countOfPredefinedCells;
    private Sudoku newSudoku;

    private JDialog waiting;

    /* --> Constructor <-- */

    public GeneratorThread(int countOfPredefinedCells, JDialog waiting) {
        this.countOfPredefinedCells = countOfPredefinedCells;
        this.waiting = waiting;
    }

    /* --> Methods <-- */

    @Override
    protected Void doInBackground() {

        // save the start time
        long start = System.currentTimeMillis();

        while (newSudoku == null && !isCancelled()) {
            newSudoku = SudokuGenerator.tryToGenerateSudoku(countOfPredefinedCells);
        }

        // save the end time
        long end = System.currentTimeMillis();

        // control output with needed time
        if (isCancelled()) {
            System.out.println("Generator-Thread unterbrochen nach " + (end - start) + " ms.");
        } else {
            System.out.println("Neues Sudoku generiert in " + (end - start) + " ms.");
        }

        // return null (not needed)
        return null;
    }

    @Override
    protected void done() {
        waiting.dispose();
    }

    /* --> Getters and Setters <-- */

    public Sudoku getNewSudoku() {
        return newSudoku;
    }
}
