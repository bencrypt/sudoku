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
        new MenuHandler(this, ctrlBoard, menuBar);
        new ControlPanelHandler(ctrlBoard, controlPanel);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    public void showWindow() {
        window.makeVisible();
    }

    public void closeWindow() {
        boolean equalsLastSavedSudoku = FileHandler.equalsLastSavedSudoku(ctrlBoard.getModel());
        int option = JOptionPane.YES_OPTION;

        if (!equalsLastSavedSudoku) {
            option = JOptionPane.showConfirmDialog(null,
                    "Der aktuelle Stand des Sudokus wurde nicht gespeichert. " +
                            "Soll das Programm wirklich beendet werden??",
                    "Schließen", JOptionPane.YES_NO_OPTION);
        }

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
