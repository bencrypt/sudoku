package controller;

import eventHandling.ControlPanelHandler;
import eventHandling.FileHandler;
import eventHandling.MenuHandler;
import view.Board;
import view.ControlPanel;
import view.Window;
import view.menu.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CtrlWindow {

    /* --> Fields <-- */

    // separate Board-Controller for better overview
    private CtrlBoard ctrlBoard;

    // the GUI elements nested in the Window
    private Window window;
    private MenuBar menuBar;
    private Board board;
    private ControlPanel controlPanel;

    /* --> Constructor <-- */

    /**
     * Creates a Controller for the main window of the application.
     * Initializes the GUI and creates the event handling. For better overview the Sudoku-Board has its own Controller
     * which is also created by the Window-Controller.
     *
     * @see #initView()
     * @see #addEventHandlers()
     * @see CtrlBoard
     */
    public CtrlWindow () {

        // initialize the components in the Window and create the event handling for them
        initView();
        createBoardController(board);
        addEventHandlers();
    }

    /* --> Methods <-- */

    /**
     * Initializes the Window and the GUI elements nested in it.
     */
    private void initView() {

        // create view elements
        window = new Window();
        menuBar = new MenuBar();
        board = new Board();
        controlPanel = new ControlPanel();

        // build the Window with the contained elements
        window.setJMenuBar(menuBar);
        window.addToBorderLayout(board, BorderLayout.CENTER);
        window.addToBorderLayout(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the Board-Controller for the given Board-GUI.
     * @param board
     *      the related View for the Board-Controller
     */
    private void createBoardController(Board board) {
        ctrlBoard = new CtrlBoard(board);
    }

    /**
     * Creates the event handling for the Window and all nested GUI elements but the Board.
     *
     * @see MenuHandler
     * @see ControlPanelHandler
     * @see #closeWindow()
     */
    private void addEventHandlers() {

        // event handling for Menu and ControlPanel
        new MenuHandler(this, ctrlBoard, menuBar);
        new ControlPanelHandler(ctrlBoard, controlPanel);

        // event handling for the window itself
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    /**
     * Optimizes the window and makes it visible.
     *
     * @see Window#makeVisible()
     */
    public void showWindow() {
        window.makeVisible();
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
                            "Soll das Programm wirklich beendet werden??",
                    "Schließen", JOptionPane.YES_NO_OPTION);
        }

        // when confirmed (or directly equal Sudokus) close the window
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
