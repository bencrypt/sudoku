package computing;

import model.Sudoku;

import javax.swing.*;

public class GeneratorThread extends SwingWorker<Void, Void> {

    /* --> Fields <-- */

    // the parameter and result for generating
    private int countOfPredefinedCells;
    private Sudoku newSudoku;

    // the waiting dialog showed while computing
    private JDialog waiting;

    /* --> Constructor <-- */

    /**
     * Creates an instance of GeneratorThread with passing the needed parameter for generating a Sudoku and a
     * waiting dialog. Does NOT activate the thread. This is to be done with {@link #execute()}.
     *
     * @param countOfPredefinedCells
     *      the count of Cells that should be predefined in the generated Sudoku
     * @param waiting
     *      a waiting dialog
     */
    public GeneratorThread(int countOfPredefinedCells, JDialog waiting) {
        this.countOfPredefinedCells = countOfPredefinedCells;
        this.waiting = waiting;
    }

    /* --> Methods <-- */

    /**
     * Generates a Sudoku in background. This action may be cancelled.
     *
     * @return
     *      always null since not needed
     */
    @Override
    protected Void doInBackground() {

        // save the start time
        long start = System.currentTimeMillis();

        // generate the Sudoku
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

    /**
     * Disposes the waiting dialog if existing.
     */
    @Override
    protected void done() {
        if (waiting != null) {
            waiting.dispose();
        }
    }

    /* --> Getters and Setters <-- */

    /**
     * Returns the newly created Sudoku or null when cancelled.
     *
     * @return
     *      the new Sudoku or null
     */
    public Sudoku getNewSudoku() {
        return newSudoku;
    }
}
