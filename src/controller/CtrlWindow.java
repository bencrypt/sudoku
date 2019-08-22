package controller;

import eventHandling.ControlPanelHandler;
import eventHandling.FileHandler;
import eventHandling.MenuHandler;
import eventHandling.WindowHandler;
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

    // event handling for window
    private WindowHandler windowHandler;

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
        ctrlBoard = new CtrlBoard(board, null);
    }

    /**
     * Creates the event handling for the Window and all nested GUI elements but the Board.
     *
     * @see MenuHandler
     * @see ControlPanelHandler
     * @see eventHandling.WindowHandler
     */
    private void addEventHandlers() {

        // event handling for Menu and ControlPanel
        new MenuHandler(this, ctrlBoard, menuBar);
        new ControlPanelHandler(ctrlBoard, controlPanel);

        // event handling for the window itself
        windowHandler = new WindowHandler(window, ctrlBoard);
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
     * Closes the Window after might asking for confirm when a unsaved Sudoku is opened.
     */
    public void closeWindow() {
        windowHandler.closeWindow();
    }

    /**
     * Disables the Window as if a modal dialog is opened.
     */
    public void DisableWindow() {
        window.setEnabled(false);
    }

    /**
     * Enables the Window as if a modal dialog is closed.
     */
    public void enableWindow() {
        window.setEnabled(true);
        window.requestFocus();
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the board controller of the main window's board
     */
    public CtrlBoard getCtrlBoard() {
        return ctrlBoard;
    }
}
