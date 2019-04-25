package eventHandling;

import computing.GeneratorThread;
import controller.CtrlBoard;
import controller.CtrlWindow;
import eventHandling.printing.PrintHandler;
import model.Sudoku;
import view.WaitingDialog;
import view.menu.MenuBar;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuHandler {

    /* --> Fields <-- */

    private CtrlWindow ctrlWindow;
    private CtrlBoard ctrlBoard;
    private MenuBar gui;

    /* --> Constructor <-- */

    public MenuHandler(CtrlWindow ctrlWindow, CtrlBoard ctrlBoard, MenuBar gui) {

        this.ctrlWindow = ctrlWindow;
        this.ctrlBoard = ctrlBoard;
        this.gui = gui;

        addMenuHandlerNew();
        addMenuHandlerRestart();
        addMenuHandlerLoad();
        addMenuHandlerSave();
        addMenuHandlerSaveAs();
        addMenuHandlerPrint();
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
            Sudoku loaded = FileHandler.importSudokuFromXml();
            if (loaded != null) {
                ctrlBoard.changeModel(loaded);
            }
        });
    }

    private void addMenuHandlerSave() {
        gui.getMnuFile().getMniSave().addActionListener(e -> {
            FileHandler.exportSudokuIntoXml(ctrlBoard.getModel(), true);
        });
    }

    private void addMenuHandlerSaveAs() {
        gui.getMnuFile().getMniSaveAs().addActionListener(e -> {
            FileHandler.exportSudokuIntoXml(ctrlBoard.getModel(), false);
        });
    }

    private void addMenuHandlerPrint() {
        gui.getMnuFile().getMniPrint().addActionListener(e -> {
            PrintHandler.printBoard(ctrlBoard.getGui());
        });
    }

    private void addMenuHandlerExit() {
        gui.getMnuFile().getMniExit().addActionListener(e -> {
            // TODO: Beenden bestätigen lassen, sofern ungespeichertes Sudoku
            ctrlWindow.closeWindow();
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

        // define the border values for input
        int MIN = 20;
        int MAX = 50;

        // if there is a input, check if it is valid
        if (input != null) {
            try {
                countOfPredefinedCells = Integer.valueOf(input);

                if (countOfPredefinedCells < MIN || countOfPredefinedCells > MAX) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                showErrorDialog(input, MIN, MAX);
            }
        }

        // return the input, maybe -1
        return countOfPredefinedCells;
    }

    private void showErrorDialog(String input, int min, int max) {
        String errorMessage = "'" + input + "' ist kein gültiger Wert.\nGültige Werte: " + min + " bis " + max;
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
