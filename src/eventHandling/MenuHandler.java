package eventHandling;

import computing.GeneratorThread;
import controller.CtrlBoard;
import controller.CtrlEnterBoardDialog;
import controller.CtrlWindow;
import eventHandling.printing.PrintHandler;
import model.BoardConstants;
import model.Sudoku;
import view.Board;
import view.EnterBoardDialog;
import view.HoverButton;
import view.WaitingDialog;
import view.menu.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

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
        addMenuHandlerGenerate();
        addMenuHandlerEnter();
        addMenuHandlerRestart();
        addMenuHandlerLoad();
        addMenuHandlerSave();
        addMenuHandlerSaveAs();
        addMenuHandlerPrintCurrent();
        addMenuHandlerPrintMultiple();
        addMenuHandlerExit();

        // create the event handling for choosing a font size
        addHandlerFontSize();
    }

    /* --> Methods <-- */

    /*
     * adding event handling to each component in the menu
     */

    /**
     * Creates the event handling for the button "New Sudoku". Therefore opens a dialog to ask for the count of
     * Cells to be predefined and afterwards creates the Sudoku with showing a waiting dialog while doing this.
     *
     * @see #askForCountOfPredefinedCells()
     * @see #createNewSudoku(int, boolean)
     */
    private void addMenuHandlerGenerate() {

        gui.getMnuFile().getMniGenerate().addActionListener(e -> {

            // ask for the count of the predefined Cells
            int countOfPredefinedCells = askForCountOfPredefinedCells();

            // create the Sudoku if the previous input is valid
            if (countOfPredefinedCells != -1) {

                // create the Sudoku and set it as new model
                Sudoku newSudoku = createNewSudoku(countOfPredefinedCells, true);
                if (newSudoku != null) {
                    ctrlBoard.changeModel(newSudoku);
                }
            }
        });
    }

    /**
     * Creates the event handling for the button "Enter a given Sudoku"
     */
    private void addMenuHandlerEnter() {
        gui.getMnuFile().getMniEnter().addActionListener(e -> {
            new CtrlEnterBoardDialog(ctrlWindow);
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
     * Creates the event handling for the button "Print current Sudoku".
     *
     * @see PrintHandler
     */
    private void addMenuHandlerPrintCurrent() {
        gui.getMnuPrint().getMniPrintCurrent().addActionListener(e -> {
            PrintHandler.printBoard(ctrlBoard.getGui());
        });
    }

    /**
     * Creates the event handling for the button "Print multiple Sudokus".
     *
     * @see PrintHandler
     */
    private void addMenuHandlerPrintMultiple() {
        gui.getMnuPrint().getMniPrintMultiple().addActionListener(e -> {

            int predefinedCells = askForCountOfPredefinedCells();
            if (predefinedCells != -1) {

                int countToPrint = askForCountOfSudokusToBePrinted();
                if (countToPrint != -1) {

                    List<Sudoku> sudokus = new ArrayList<>(countToPrint);
                    for (int i = 0; i < countToPrint; i++) {
                        sudokus.add(createNewSudoku(predefinedCells, false));
                    }
                    List<Board> boards = createBoards(sudokus);
                    PrintHandler.printMultiple(boards);
                }
            }

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
     * Creates the event handling for the combo box dealing choosing the font size.
     */
    private void addHandlerFontSize() {
        gui.getCbxFontSize().addActionListener(e -> {

            // update constants
            BoardConstants.FONT_SIZE = (int) gui.getCbxFontSize().getSelectedItem();
            BoardConstants.FONT_EDITABLE = new Font(BoardConstants.FONT_EDITABLE.getName(), Font.PLAIN, BoardConstants.FONT_SIZE);
            BoardConstants.FONT_UNEDITABLE = new Font(BoardConstants.FONT_UNEDITABLE.getName(), Font.PLAIN, BoardConstants.FONT_SIZE);

            // update the GUI
            ctrlBoard.updateAll();
        });
    }

    /*
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
                "Wie viele Sudoku-Zellen sollen vorbelegt werden?", 33);
        int countOfPredefinedCells = -1;

        // define the border values for input
        int min = 20;
        int max = 50;

        // if there is a input, check if it is valid
        TryParseInput(input, min, max);

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
     * Opens a input dialog to ask for the count of Sudokus to be printed in one flow.
     * If a valid count is entered, this count will be returned. Else an error dialog is shown and the value
     * -1 is returned.
     *
     * @return
     *      the count of Sudokus to print or -1
     *
     * @see #showErrorDialog(String, int, int)
     */
    private int askForCountOfSudokusToBePrinted() {

        // show a input dialog
        String input = JOptionPane.showInputDialog(
                "Wie viele Sudokus sollen gedruckt werden?", 10);
        int countToPrint = -1;

        // define the border values for input
        int min = 1;
        int max = 50;

        // if there is a input, check if it is valid
        TryParseInput(input, min, max);

        // return the input, maybe -1
        return countToPrint;
    }

    /**
     * Tries to parse the given input to an integer value and checks if this value lies between the given minimum
     * and maximum. If so, the parsed integer value is returned, else an error dialog is opened and -1 will be
     * returned.
     *
     * @param input
     *      the input to parse
     * @param min
     *      the minimum border
     * @param max
     *      the maximum border
     * @return
     *      the parsed value or -1 if an error occured
     */
    private int TryParseInput(String input, int min, int max) {

        // init return variable
        int parsed = -1;

        // if there is a input, check if it is valid
        if (input != null) {
            try {
                parsed = Integer.valueOf(input);

                if (parsed < min || parsed > max) {
                    parsed = -1;
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                showErrorDialog(input, min, max);
            }
        }

        // return parsed value, maybe -1 if error
        return parsed;
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
    private Sudoku createNewSudoku(int countOfPredefinedCells, boolean showWaitingDialog) {

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

        // generate a Sudoku and show the waiting dialog if needed while this
        generatorThread.execute();

        if (showWaitingDialog) {
            waitingDialog.makeVisible();
        }

        // return the created Sudoku or null when cancelled
        return generatorThread.getNewSudoku();
    }

    /**
     * Creates the GUI-Boards for the given Sudokus and adds them to a hidden frame so that they can be printed
     * correctly.
     *
     * @param sudokus
     *      the Sudokus to print
     * @return
     *      the GUI-Boards
     */
    private List<Board> createBoards(List<Sudoku> sudokus) {

        // list for all boards
        List<Board> boards = new ArrayList<>(sudokus.size());

        // helping frame (invisible) to let the board be correctly printed
        JFrame frame = new JFrame();

        // create a board for every sudoku and add it to the helping frame so that the
        // board can be printed correctly afterwards
        for (Sudoku sud: sudokus) {

            // create the board and update it via the controller
            Board board = new Board();
            new CtrlBoard(board);

            // add the board to the frame and the result list
            frame.add(board);
            boards.add(board);

            // repaint and pack the frame for correct printing afterwards
            frame.repaint();
            frame.pack();
        }

        // return the board list
        return boards;
    }
}
