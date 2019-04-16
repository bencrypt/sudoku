package controller;

import view.Board;
import view.Window;
import view.menu.MenuBar;

import javax.swing.*;
import java.awt.*;

public class CtrlWindow {

    /* --> Fields <-- */

    private Window window;
    private MenuBar menuBar;
    private Board board;

    /* --> Constructor <-- */

    public CtrlWindow () {

        // initialize the components in the Window and create the event handling for them
        initView();
        createEventHandling();
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

    private void createEventHandling() {
        // TODO: EventHandling einbauen
    }

    public void showWindow() {
        window.makeVisible();
    }
}
