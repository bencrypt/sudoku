package eventHandling;

import controller.CtrlBoard;
import view.Window;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowHandler {

    /* --> Fields <-- */

    // related View
    Window gui;

    // needed for event handling
    CtrlBoard ctrlBoard;

    /* --> Constructor <-- */

    public WindowHandler(Window gui, CtrlBoard ctrlBoard) {

        // set fields
        this.gui = gui;
        this.ctrlBoard = ctrlBoard;

        // create event handling
        addCloseHandler();
    }

    /* --> Methods <-- */

    /*
     * eventHandling methods
     */

    /**
     * Adds the event handling for closing the Window.
     *
     * @see #closeWindow()
     */
    private void addCloseHandler() {
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    /**
     * Prepares closing the Window and closes it under given circumstandes.
     * <p>
     * When the current Sudoku showed on the Board equals the last saved Sudoku of the FileHandler, the Window
     * is automatically closed. If both Sudokus are not equal, the user has to confirm that the Window should be closed.
     * Alternatively the user can cancel the closing operation and e.g. save the current Sudoku first.
     */
    public void closeWindow() {

        // needed variables
        boolean equalsLastSavedSudoku = FileHandler.equalsLastSavedSudoku(ctrlBoard.getModel());
        int option = JOptionPane.YES_OPTION;

        // confirm dialog when not equal Sudokus
        if (!equalsLastSavedSudoku) {
            option = JOptionPane.showConfirmDialog(null,
                    "Der aktuelle Stand des Sudokus wurde nicht gespeichert. " +
                            "Soll das Programm wirklich beendet werden?",
                    "Schließen", JOptionPane.YES_NO_OPTION);
        }

        // when confirmed (or directly equal Sudokus) close the window
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
