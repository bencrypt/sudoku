package controller;

import computing.SudokuGenerator;
import eventHandling.BoardHandler;
import eventHandling.FileHandler;
import model.BoardConstants;
import model.Cell;
import model.Sudoku;
import utils.IndexConverter;
import utils.Pair;
import view.Board;
import view.HoverButton;

import java.util.Observable;
import java.util.Observer;

public class CtrlBoard implements Observer {

    /* --> Fields <-- */

    // related Model and View
    private Sudoku model;
    private Board gui;

    // needed for changing a Cell's value through GUI input
    private CtrlValueSelector ctrlValueSelector;
    private int indexClickedCell;

    /* --> Constructor <-- */

    /**
     * Creates a Controller for the given Board-GUI to synchronize the Model with the view.
     * Therefore the event handling and the observing pattern is initialized and a separate Controller for the
     * ValueSelector to be shown on clicks on the Board's Cells.
     *
     * @param gui
     *      the Board-GUI
     *
     * @see #createEventHandling()
     * @see #initObserver()
     *
     */
    public CtrlBoard(Board gui) {

        // set the needed fields
        this.gui = gui;
        this.ctrlValueSelector = new CtrlValueSelector(this);

        // TODO: Test-Methoden rausnehmen
        Sudoku test = SudokuGenerator.generateSudoku(30);
//        Sudoku test = FileHandler.importSudokuFromXml(".\\res\\naechsterSchritt.suk");
        changeModel(test);

        // initialize event handling and observing pattern
        createEventHandling();
        initObserver();
    }

    /* --> Methods <-- */

    /**
     * Creates a {@link eventHandling.BoardHandler} to do the event handling for the GUI.
     */
    private void createEventHandling() {
        new BoardHandler(this, ctrlValueSelector);
    }

    /**
     * Changes the Model to be shown on GUI and initializes the observer pattern for this new Model.
     *
     * @param newModel
     *      the new model
     */
    public void changeModel(Sudoku newModel) {
        this.model = newModel;
        initObserver();
    }

    /**
     * Restarts the Sudoku.
     */
    public void restartSudoku() {
        model.restart();
    }

    /*
     * Update GUI when Model changed
     */

    /**
     * Initializes the observer pattern for the Board-Controller. Therefore the Controller observes each Cell of the
     * Model and updates the GUI with every change of a Cell.
     *
     * @see #update(Observable, Object)
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

    /**
     * Updates the GUI so that it is synchronized with the Model. Concretely this method is called when a Model's Cell
     * is changed by one of its fields.
     *
     * @param o
     *      the observed Cell which has been changed
     * @param arg
     *      additional arguments, not used here
     */
    @Override
    public void update(Observable o, Object arg) {

        // determine the updated Model-Cell and the corresponding GUI-Cell
        Cell updated = (Cell) o;
        HoverButton toUpdate =
                IndexConverter.determineGuiCellFromModelCell(updated.getRow(), updated.getColumn(), gui.getCells());

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
            toUpdate.enableHover();
        } else {
            toUpdate.setFont(BoardConstants.FONT_UNEDITABLE);
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
     * Update the Model through GUI input
     */

    /**
     * Updates the Model through a input from the GUI. Therefore the index of the changed GUI's Cell and the Cell's
     * new value is needed.
     * <p>
     * This method is only used when directly clicked on a GUI-Cell so that the status 'automaticallySolved' from the
     * Model's Cell is changed to <code>false</code> when activated before.
     *
     * @param indexClickedCell
     *      the GUI-index (one-dimensional) of the changed Cell
     * @param newValue
     *      the new value for the Cell
     */
    public void updateFromValueInput(int indexClickedCell, int newValue) {

        // determine the Model's Cell to update
        Pair<Integer, Integer> index2D = IndexConverter.determineIndex2DFromIndex1D(indexClickedCell);
        Cell toUpdate = model.getBoard()[index2D.getFirst()][index2D.getSecond()];
        toUpdate.setValue(newValue);

        // change the Cell to manually solved when it was solved automatically before
        if (toUpdate.isAutomaticallySolved()) {
            toUpdate.setAutomaticallySolved(false);
        }

        // check the whole Sudoku for duplicate Cells and mark them as invalid
        model.checkAndMarkDuplicates();
    }

    /* --> Getters and Setters <-- */

    /**
     * Returns the Model of the Controller.
     * @return
     *      the Model of the Controller
     */
    public Sudoku getModel() {
        return model;
    }

    /**
     * Returns the View of the Controller.
     * @return
     *      the View of the Controller
     */
    public Board getGui() {
        return gui;
    }

    /**
     * Returns the (one-dimensional) index of the Cell clicked on the GUI of the Board.
     * @return
     *      the (one-dimensional) index of the clicked GUI-Cell
     */
    public int getIndexClickedCell() {
        return indexClickedCell;
    }

    /**
     * Sets the (one-dimensional) index of the Cell clicked on the GUI of the Board.
     * @param indexClickedCell
     *      the new (one-dimensional) index of the clicked GUI-Cell
     */
    public void setIndexClickedCell(int indexClickedCell) {
        this.indexClickedCell = indexClickedCell;
    }
}
