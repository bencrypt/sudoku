package eventHandling;

import controller.CtrlEnterBoardDialog;
import view.EnterBoardDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnterBoardDialogHandler {

    /* --> Fields <-- */

    // related controller and view
    private CtrlEnterBoardDialog ctrlEnterBoardDialog;
    private EnterBoardDialog gui;

    /* --> Constructor <-- */

    /**
     * Creates the event handling for the dialog to enter a predefined Sudoku manually.
     *
     * @param ctrlEnterBoardDialog
     *      the controller of the dialog to be supplemented with event handling
     */
    public EnterBoardDialogHandler(CtrlEnterBoardDialog ctrlEnterBoardDialog) {

        // get references
        this.ctrlEnterBoardDialog = ctrlEnterBoardDialog;
        this.gui = ctrlEnterBoardDialog.getGui();

        // add event handling
        addOkHandler();
        addCancelHandler();
        addDialogHandler();
    }

    /* --> Methods <-- */

    /**
     * Creates the event handling for applying the button "OK" which means the entered Sudoku should be
     * setted as the main window's Sudoku. This is done by {@link CtrlEnterBoardDialog#ApplySudoku()}.
     */
    private void addOkHandler() {
        gui.getBtnOk().addActionListener(e -> {
            ctrlEnterBoardDialog.ApplySudoku();
        });
    }

    /**
     * Creates the event handling for applying the button "Cancel" which means the entered Sudoku should NOT be
     * setted as the main window's Sudoku.
     *
     * @see CtrlEnterBoardDialog#ApplySudoku()
     */
    private void addCancelHandler() {
        gui.getBtnCancel().addActionListener(e -> {
            ctrlEnterBoardDialog.Cancel();
        });
    }

    /**
     * Creates the event handling for the dialog window itself so that closing it can be seen as cancelling the
     * entering.
     *
     * @see CtrlEnterBoardDialog#Cancel()
     */
    private void addDialogHandler() {
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ctrlEnterBoardDialog.Cancel();
            }
        });
    }
}
