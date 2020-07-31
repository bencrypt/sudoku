package eventHandling;

import computing.SudokuSolver;
import controller.CtrlBoard;
import model.Cell;
import model.Sudoku;
import utils.LanguageBundle;
import view.ControlPanel;

import javax.swing.*;
import java.util.ResourceBundle;

public class ControlPanelHandler {

    /* --> Internationalization <-- */

    private ResourceBundle bundle = LanguageBundle.getBundle();

    /* --> Fields <-- */

    // related View
    private ControlPanel gui;

    // needed for event handling
    private CtrlBoard ctrlBoard;

    /* --> Constructor <-- */

    /**
     * Creates the event handling for the given ControlPanel-GUI. Therefore uses the given Board-Controller e.g. to make
     * solving the showed Sudoku possible.
     *
     * @param ctrlBoard
     *      the Board-Controller needed for the event handling
     * @param gui
     *      the GUI to be supplemented by the event handling
     */
    public ControlPanelHandler(CtrlBoard ctrlBoard, ControlPanel gui) {

        // set the fields
        this.ctrlBoard = ctrlBoard;
        this.gui = gui;

        // add the event handlers for the single elements on the GUI
        addSolveCompletelyHandler();
        addSolveNextStepHandler();
        addCompareWithSolutionHandler();
    }

    /* --> Methods <-- */

    /**
     * Creates the event handling for the button "Solve completely". Therefore the current shown Sudoku is completely
     * solved and the solution made to the new Model of the Board-Controller given in the constructor.
     *
     * @see SudokuSolver
     */
    private void addSolveCompletelyHandler() {
        gui.getBtnSolveCompletely().addActionListener(e -> {

            // solve the Sudoku
            Sudoku toSolve = ctrlBoard.getModel();
            Sudoku solution = SudokuSolver.findFirstSolution(toSolve);

            // if there is no solution, show a info message
            if (solution == null) {
                JOptionPane.showMessageDialog(null, bundle.getString("NoSolutionForCurrentState"));
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

    /**
     * Creates the event handling for the button "Solve next step". Therefore the current shown Sudoku is supplemented
     * for the next to solve Cell and the solution made to the new Model of the Board-Controller given in the constructor.
     *
     * @see SudokuSolver
     */
    private void addSolveNextStepHandler() {
        gui.getBtnSolveNextStep().addActionListener(e -> {

            // determine the next Cell (copy) to be changed
            Cell nextStep = SudokuSolver.determineNextStep(ctrlBoard.getModel());

            // if no further step is possible, show a info message
            if (nextStep == null) {
                JOptionPane.showMessageDialog(null, bundle.getString("NoFurtherStepComputable"));
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

    /**
     * Adds event handling for the Checkbox "Compare with solution" to enable special marked Cells when this
     * option is enabled.
     */
    private void addCompareWithSolutionHandler() {
        gui.getChkCompareWithSolution().addChangeListener(e -> {
            ctrlBoard.setShowComparisonToSolution(gui.getChkCompareWithSolution().isSelected());
            ctrlBoard.updateAll();
        });
    }
}
