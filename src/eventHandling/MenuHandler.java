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

    // related View
    private MenuBar gui;

    // needed for event handling
    private CtrlWindow ctrlWindow;
    private CtrlBoard ctrlBoard;

    /* --> Constructor <-- */

    /**
     * Creates the event handling for the menu of the Window given by its Controller.
     *
     * @param ctrlWindow
     *      the Controller of the Window in which the Menu is placed
     * @param ctrlBoard
     *      the Board-Controller needed for event handling
     * @param gui
     *      the GUI to be supplemented by the event handling
     */
    public MenuHandler(CtrlWindow ctrlWindow, CtrlBoard ctrlBoard, MenuBar gui) {

        // set the fields
        this.ctrlWindow = ctrlWindow;
        this.ctrlBoard = ctrlBoard;
        this.gui = gui;

        // create the event handling for each single element in the menu
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
     * adding event handling to each component in the menu
     */

    /**
     * Creates the event handling for the button "New Sudoku". Therefore opens a dialog to ask for the count of
     * Cells to be predefined and afterwards creates the Sudoku with showing a waiting dialog while doing this.
     *
     * @see #askForCountOfPredefinedCells()
     * @see #createNewSudoku(int)
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

    /**
     * Creates the event handling for the button "Restart Sudoku".
     */
    private void addMenuHandlerRestart() {
        gui.getMnuFile().getMniRestart().addActionListener(e -> {
            ctrlBoard.restartSudoku();
        });
    }

    /**
     * Creates the event handling for the button "Load Sudoku".
     */
    private void addMenuHandlerLoad() {
        gui.getMnuFile().getMniLoad().addActionListener(e -> {
            Sudoku loaded = FileHandler.importSudokuFromXml();
            if (loaded != null) {
                ctrlBoard.changeModel(loaded);
            }
        });
    }

    /**
     * Creates the event handling for the button "Save Sudoku".
     *
     * @see FileHandler#exportSudokuIntoXml(Sudoku, boolean)
     */
    private void addMenuHandlerSave() {
        gui.getMnuFile().getMniSave().addActionListener(e -> {
            FileHandler.exportSudokuIntoXml(ctrlBoard.getModel(), true);
        });
    }

    /**
     * Creates the event handling for the button "Save Sudoku As ...".
     *
     * @see FileHandler#exportSudokuIntoXml(Sudoku, boolean)
     */
    private void addMenuHandlerSaveAs() {
        gui.getMnuFile().getMniSaveAs().addActionListener(e -> {
            FileHandler.exportSudokuIntoXml(ctrlBoard.getModel(), false);
        });
    }

    /**
     * Creates the event handling for the button "Print Sudoku".
     *
     * @see PrintHandler
     */
    private void addMenuHandlerPrint() {
        gui.getMnuFile().getMniPrint().addActionListener(e -> {
            PrintHandler.printBoard(ctrlBoard.getGui());
        });
    }

    /**
     * Creates the event handling for the button "Exit".
     *
     * @see CtrlWindow#closeWindow()
     */
    private void addMenuHandlerExit() {
        gui.getMnuFile().getMniExit().addActionListener(e -> {
            ctrlWindow.closeWindow();
        });
    }

    /**
     * Helping methods for creating a new Sudoku
     */

    /**
     * Opens a input dialog to ask for the count of predefined Cells in the Sudoku which should be created.
     * If a valid count is entered, this count will be returned. Else an error dialog is shown and the value
     * -1 is returned.
     *
     * @return
     *      the count of predefined Cells or -1
     *
     * @see #showErrorDialog(String, int, int)
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

    /**
     * Shows an error dialog with the given minimal and maximal value that can be entered by the user.
     *
     * @param input
     *      the invalid input of the user
     * @param min
     *      the minimal valid value
     * @param max
     *      the maximal valid value
     */
    private void showErrorDialog(String input, int min, int max) {
        String errorMessage = "'" + input + "' ist kein gültiger Wert.\nGültige Werte: " + min + " bis " + max;
        JOptionPane.showMessageDialog(null, errorMessage, "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Creates a new Sudoku with the given count of predefined Cells. For the generating process the
     * {@link GeneratorThread} is used while in front of the application is shown a modal waiting dialog. Afterwards
     * the new Sudoku is returned.
     *
     * @param countOfPredefinedCells
     *      the count of Cells to be predefined in the new Sudoku
     * @return
     *      the new Sudoku
     */
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
