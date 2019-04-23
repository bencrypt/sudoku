package controller;

import eventHandling.ControlPanelHandler;
import eventHandling.MenuHandler;
import view.Board;
import view.ControlPanel;
import view.Window;
import view.menu.MenuBar;

import java.awt.*;

public class CtrlWindow {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;

    private Window window;
    private MenuBar menuBar;
    private Board board;
    private ControlPanel controlPanel;

    /* --> Constructor <-- */

    public CtrlWindow () {

        // initialize the components in the Window and create the event handling for them
        initView();
        createBoardController(board);
        addEventHandlers();
    }

    /* --> Methods <-- */

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

    private void createBoardController(Board board) {
        ctrlBoard = new CtrlBoard(board);
    }

    private void addEventHandlers() {
        new MenuHandler(ctrlBoard, menuBar);
        new ControlPanelHandler(ctrlBoard, controlPanel);
    }

    public void showWindow() {
        window.makeVisible();
    }
}
