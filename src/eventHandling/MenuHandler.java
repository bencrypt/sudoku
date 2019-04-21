package eventHandling;

import computing.GeneratorThread;
import computing.SudokuGenerator;
import controller.CtrlBoard;
import model.Sudoku;
import view.WaitingDialog;
import view.menu.MenuBar;
import view.menu.MnuFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuHandler {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;
    private MenuBar gui;

    /* --> Constructor <-- */

    public MenuHandler(CtrlBoard ctrlBoard, MenuBar gui) {

        this.ctrlBoard = ctrlBoard;
        this.gui = gui;

        addMenuHandlerNew();
        addMenuHandlerRestart();
        addMenuHandlerLoad();
        addMenuHandlerSave();
        addMenuHandlerSaveAs();
        addMenuHandlerExit();
    }

    /* --> Methods <-- */

    /**
     * Methods for EventHandling
     */

    private void addMenuHandlerNew() {

        gui.getMnuFile().getMniNew().addActionListener(e -> {

            // ask for the count of the predefined Cells
            int countOfPredefinedCells = askForCountOfPredefinedCells();

            // create the Sudoku if the previous input is valid
            if (countOfPredefinedCells != -1) {

                // create the Sudoku and set it as new model
                Sudoku newSudoku = createNewSudoku(countOfPredefinedCells);
                if (newSudoku != null) {
                    ctrlBoard.changeModel(newSudoku);
                }
            }
        });
    }

    private void addMenuHandlerRestart() {
        gui.getMnuFile().getMniRestart().addActionListener(e -> {
            ctrlBoard.restartSudoku();
        });
    }

    private void addMenuHandlerLoad() {
        gui.getMnuFile().getMniLoad().addActionListener(e -> {
            // TODO: Sudoku laden
        });
    }

    private void addMenuHandlerSave() {
        gui.getMnuFile().getMniSave().addActionListener(e -> {
            // TODO: Sudoku speichern
        });
    }

    private void addMenuHandlerSaveAs() {
        gui.getMnuFile().getMniSaveAs().addActionListener(e -> {
            // TODO: Sudoku speichern unter
        });
    }

    private void addMenuHandlerExit() {
        gui.getMnuFile().getMniExit().addActionListener(e -> {
            // TODO: Beenden bestätigen lassen, sofern ungespeichertes Sudoku
            System.exit(0);
        });
    }

    /**
     * Helping methods for creating a new Sudoku
     */

    private int askForCountOfPredefinedCells() {

        // show a input dialog
        String input = JOptionPane.showInputDialog(
                "Wie viele Sudoku-Zellen sollen vorbelegt werden?", 30);
        int countOfPredefinedCells = -1;

        // check if the input is valid
        try {
            countOfPredefinedCells = Integer.valueOf(input);

            if (countOfPredefinedCells < 20 || countOfPredefinedCells > 40) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            showErrorDialog(input);
        }

        // return the input, maybe -1
        return countOfPredefinedCells;
    }

    private void showErrorDialog(String input) {
        String errorMessage = "'" + input + "' ist kein gültiger Wert.\nGültige Werte: 20 bis 40";
        JOptionPane.showMessageDialog(null, errorMessage, "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    private Sudoku createNewSudoku(int countOfPredefinedCells) {

        // create a waiting dialog and a Thread for generating a new Sudoku
        WaitingDialog waitingDialog = new WaitingDialog();
        final GeneratorThread generatorThread = new GeneratorThread(countOfPredefinedCells, waitingDialog);

        // when the waiting dialog is closed, stop generating a new Sudoku
        waitingDialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                generatorThread.cancel(true);
            }
        });

        // generate a Sudoku and show the waiting dialog while this
        generatorThread.execute();
        waitingDialog.makeVisible();

        // return the created Sudoku or null when cancelled
        return generatorThread.getNewSudoku();
    }
}
