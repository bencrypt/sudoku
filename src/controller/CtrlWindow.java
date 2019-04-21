package controller;

import eventHandling.MenuHandler;
import view.Board;
import view.Window;
import view.menu.MenuBar;

import java.awt.*;

public class CtrlWindow {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;

    private Window window;
    private MenuBar menuBar;
    private Board board;

    /* --> Constructor <-- */

    public CtrlWindow () {

        // initialize the components in the Window and create the event handling for them
        initView();
        createBoardController(board);
        addMenuHandlers();
    }

    /* --> Methods <-- */

    private void initView() {

        // create view elements
        window = new Window();
        menuBar = new MenuBar();
        board = new Board();

        // build the Window with the contained elements
        window.setJMenuBar(menuBar);
        window.addToBorderLayout(board, BorderLayout.CENTER);
    }

    private void createBoardController(Board board) {
        ctrlBoard = new CtrlBoard(board);
    }

    private void addMenuHandlers() {
        new MenuHandler(ctrlBoard, menuBar);
    }

    public void showWindow() {
        window.makeVisible();
    }
}
