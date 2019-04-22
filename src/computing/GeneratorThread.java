package computing;

import model.Sudoku;

import javax.swing.*;
import java.util.List;

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
    protected Void doInBackground() throws Exception {
        newSudoku = SudokuGenerator.generateSudoku(countOfPredefinedCells);
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
