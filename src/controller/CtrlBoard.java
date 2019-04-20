package controller;

import computing.SudokuGenerator;
import console.SudokuPrinter;
import eventHandling.BoardHandler;
import model.BoardConstants;
import model.Cell;
import model.Sudoku;
import utils.IndexConverter;
import view.Board;
import view.HoverButton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.util.Observable;
import java.util.Observer;

public class CtrlBoard implements Observer {

    /* --> Fields <-- */

    private Sudoku model;
    private Board gui;

    private CtrlValueSelector ctrlValueSelector;

    /* --> Constructor <-- */

    public CtrlBoard(Board gui) {

        this.gui = gui;
        this.ctrlValueSelector = new CtrlValueSelector(this);

        Sudoku test = SudokuGenerator.generateSudoku(35);
        changeModel(test);

        createEventHandling();
        initObserver();
    }

    /* --> Methods <-- */

    private void createEventHandling() {
        new BoardHandler(this, ctrlValueSelector);
    }

    public void changeModel(Sudoku newModel) {
        this.model = newModel;
        initObserver();
    }

    /*
     * Update GUI when model changed
     */

    private void initObserver() {
        if (model != null) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    Cell cell = model.getBoard()[row][col];
                    cell.addObserver(this);
                    update(cell, null);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        // determine the updated model-Cell and the corresponding GUI-Cell
        Cell updated = (Cell) o;
        HoverButton toUpdate = IndexConverter.determineGuiCellFromModelCell(updated.getGridX(), updated.getGridY(), gui.getCells());

        // put the new value into the GUI
        if (updated.getValue() != 0) {
            String newValue = String.valueOf(updated.getValue());
            toUpdate.setText(newValue);
        } else {
            toUpdate.setText("");
        }

        // update the status of the GUI cell
        toUpdate.setEnabled(updated.isEditable());

        if (updated.isEditable()) {
            toUpdate.setFont(BoardConstants.FONT_EDITABLE);
            toUpdate.setBorderPainted(true);
        } else {
            toUpdate.setFont(BoardConstants.FONT_UNEDITABLE);
            //toUpdate.setBorderPainted(false);
            toUpdate.disableHover();
        }

        if (updated.isValid()) {
            toUpdate.setBackground(BoardConstants.CELL_COLOR_NORMAL);
        } else {
            toUpdate.setBackground(BoardConstants.CELL_COLOR_ERROR);
        }

        if (updated.isAutomaticallySolved()) {
            toUpdate.setForeground(BoardConstants.FONT_COLOR_AUTOMATICALLY_SOLVED);
        } else {
            toUpdate.setForeground(BoardConstants.FONT_COLOR_NORMAL);
        }

        // repaint the Board
        gui.repaint();

        // control output
//        System.out.println("Sudoku aktualisiert:");
//        SudokuPrinter.showOnConsole(model);
    }

    /*
     * Update the model through GUI input
     */

    public void updateFromValueInput(int indexClickedCell, int newValue) {

        Cell toUpdate = IndexConverter.determineModelCellFromIndexOfGuiCell(model, indexClickedCell);
        toUpdate.setValue(newValue);
    }

    /* --> Getters and Setters <-- */

    public Sudoku getModel() {
        return model;
    }

    public Board getGui() {
        return gui;
    }
}
