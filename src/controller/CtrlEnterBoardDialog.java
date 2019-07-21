package controller;

import eventHandling.EnterBoardDialogHandler;
import model.Sudoku;
import view.Board;
import view.EnterBoardDialog;

public class CtrlEnterBoardDialog {

    /* --> Fields <-- */

    private CtrlWindow ctrlWindow;
    private CtrlBoard ctrlNewBoard;
    private EnterBoardDialog dialog;

    /* --> Constructor <-- */

    /**
     * Creates a dialog for entering a given Sudoku and opens it. The dialog is opened in a modal way
     * so that it has to be closed or applied to go on working with the main window.
     *
     * @param ctrlWindow
     *      the controller of the window to make the modal effect work
     */
    public CtrlEnterBoardDialog(CtrlWindow ctrlWindow) {

        // get references
        this.ctrlWindow = ctrlWindow;

        // create board with empty Sudoku
        this.ctrlNewBoard = new CtrlBoard(new Board());
        this.ctrlNewBoard.changeModel(new Sudoku());
        this.ctrlNewBoard.setShowComparisonToSolution(false);

        // disable the main window to get a modal effect
        // --> not directly modal dialogue because of popups on it (would also be disabled else)
        ctrlWindow.DisableWindow();

        // create dialog and event handling
        dialog = new EnterBoardDialog(ctrlNewBoard.getGui());
        new EnterBoardDialogHandler(this);

        // open dialog
        dialog.makeVisible();
    }

    /* --> Methods <-- */

    /**
     * Sets the Sudoku entered in the dialog as the current Sudoku to solve in the main window.
     * Afterwards closes the entering dialog so that the main window can be used again.
     */
    public void ApplySudoku() {

        // make every entered field immutable and restart the Sudoku for the correct Cell state
        Sudoku model = ctrlNewBoard.getModel();
        model.makeFilledCellsImmutable();
        model.restart();

        // set the new Sudoku as main window's sudoku
        ctrlWindow.getCtrlBoard().changeModel(model);

        // close dialog
        dialog.dispose();

        // enable the main window
        ctrlWindow.enableWindow();
    }

    /**
     * Closes the dialog without changing anything in the main window.
     */
    public void Cancel() {
        dialog.dispose();
        ctrlWindow.enableWindow();
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the gui for entering a Sudoku
     */
    public EnterBoardDialog getGui() {
        return this.dialog;
    }
}
