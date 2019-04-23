package eventHandling;

import computing.SudokuSolver;
import controller.CtrlBoard;
import model.Cell;
import model.Sudoku;
import view.ControlPanel;

import javax.swing.*;

public class ControlPanelHandler {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;
    private ControlPanel gui;

    /* --> Constructor <-- */

    public ControlPanelHandler(CtrlBoard ctrlBoard, ControlPanel gui) {

        this.ctrlBoard = ctrlBoard;
        this.gui = gui;

        addSolveCompletelyHandler();
        addSolveNextStepHandler();
    }

    /* --> Methods <-- */

    private void addSolveCompletelyHandler() {
        gui.getBtnSolveCompletely().addActionListener(e -> {

            // solve the Sudoku
            Sudoku toSolve = ctrlBoard.getModel();
            Sudoku solution = SudokuSolver.findFirstSolution(toSolve);

            // if there is no solution, show a info message
            if (solution == null) {
                JOptionPane.showMessageDialog(null, "Es gibt keine Lösung für den aktuellen Stand des Sudokus.");
            }

            // work with the solution if existing
            if (solution != null) {

                // mark all automatically solved Cells
                for (int row = 0; row < toSolve.getBoard().length; row++) {
                    for (int col = 0; col < toSolve.getBoard().length; col++) {
                        if (toSolve.getBoard()[row][col].getValue() == 0) {
                            solution.getBoard()[row][col].setAutomaticallySolved(true);
                        }

                    }
                }

                // set the solution as new model
                ctrlBoard.changeModel(solution);
            }
        });
    }

    private void addSolveNextStepHandler() {
        gui.getBtnSolveNextStep().addActionListener(e -> {

            // determine the next Cell (copy) to be changed
            Cell nextStep = SudokuSolver.determineNextStep(ctrlBoard.getModel());

            // if no further step is possible, show a info message
            if (nextStep == null) {
                JOptionPane.showMessageDialog(null, "Es konnte kein weiterer Schritt berechnet werden.");
            }

            // work with the determined Cell if existing
            if (nextStep != null) {

                // get the corresponding (unmodified) Cell from the model
                Cell toChange = ctrlBoard.getModel().getBoard()[nextStep.getRow()][nextStep.getColumn()];

                // change the value and status of the model Cell
                toChange.setValue(nextStep.getValue());
                toChange.setAutomaticallySolved(true);
            }
        });
    }
}
