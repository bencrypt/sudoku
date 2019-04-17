package controller;

import eventHandling.BoardHandler;
import model.Sudoku;
import view.Board;

import java.util.Observable;
import java.util.Observer;

public class CtrlBoard implements Observer {

    /* --> Fields <-- */

    private Sudoku model;
    private Board gui;

    /* --> Constructor <-- */

    public CtrlBoard(Board gui) {

        this.gui = gui;

        createEventHandling();
    }

    /* --> Methods <-- */

    private void createEventHandling() {
        new BoardHandler(this);
    }

    private void initObserver() {
        if (model != null) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    model.getBoard()[row][col].addObserver(this);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }



    /* --> Getters and Setters <-- */

    public Sudoku getModel() {
        return model;
    }

    public Board getGui() {
        return gui;
    }
}
