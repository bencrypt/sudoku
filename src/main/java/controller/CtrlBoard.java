package controller;

import computing.SudokuGenerator;
import eventHandling.BoardHandler;
import eventHandling.FileHandler;
import model.BoardConstants;
import model.Cell;
import model.Sudoku;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.IndexConverter;
import utils.Pair;
import view.Board;
import view.HoverButton;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CtrlBoard implements PropertyChangeListener {

    /* --> Logger <-- */

    private static final Logger LOGGER = LogManager.getLogger(CtrlBoard.class);

    /* --> Fields <-- */

    // related Model and View
    private Sudoku model;
    private Board gui;

    // needed for changing a Cell's value through GUI input
    private CtrlValueSelector ctrlValueSelector;
    private int indexClickedCell;

    // flag for individual view
    private boolean showComparisonToSolution = true;

    /* --> Constructor <-- */

    /**
     * Creates a Controller for the given Board-GUI to synchronize the Model with the view.
     * Therefore the event handling and the observing pattern is initialized and a separate Controller for the
     * ValueSelector to be shown on clicks on the Board's Cells.
     *
     * @param gui
     *      the Board-GUI
     * @param model
     *      the Sudoku model, maybe null to create a random Sudoku
     *
     * @see #createEventHandling()
     * @see #initObserver()
     *
     */
    public CtrlBoard(Board gui, Sudoku model) {

        // set the needed fields
        this.gui = gui;
        this.ctrlValueSelector = new CtrlValueSelector(this);

        this.model = model;
        if (this.model == null) {
            this.model = SudokuGenerator.generateSudoku(25);
        }

        FileHandler.setLastSavedSudoku(this.model.getUnchanged());

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
     * Update explicitly every Cell
     */
    public void updateAll() {
        if (model != null) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    Cell cell = model.getBoard()[row][col];
                    propertyChange(new PropertyChangeEvent(cell, "all", null, null));
                }
            }
        }
    }

    /*
     * Update GUI when Model changed
     */

    /**
     * Initializes the observer pattern for the Board-Controller. Therefore the Controller observes each Cell of the
     * Model and updates the GUI with every change of a Cell.
     *
     * @see #propertyChange(PropertyChangeEvent)
     */
    private void initObserver() {
        if (model != null) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    Cell cell = model.getBoard()[row][col];
                    cell.addObserver(this);
                    propertyChange(new PropertyChangeEvent(cell, "all", null, null));
                }
            }
        }
    }

    /**
     * Updates the GUI so that it is synchronized with the Model. Concretely this method is called when a Model's Cell
     * is changed by one of its fields.
     * <p>
     * Does not use the possibilities of event fields like the new or old value or the changed property's name,
     * only uses the event cell and checks for all fields that might be updated. This is because the method was
     * designed originally with Observer and Observable interfaces which are now deprecated.
     *
     * @param event
     *      the property change event causing the update
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {

        // determine the updated Model-Cell and the corresponding GUI-Cell
        Cell updated = (Cell) event.getSource();
        HoverButton toUpdate =
                IndexConverter.determineGuiCellFromModelCell(updated.getRow(), updated.getColumn(), gui.getCells());

        // put the new value into the GUI
        if (updated.getValue() != 0) {
            // filled cell, actual value
            String newValue = String.valueOf(updated.getValue());
            toUpdate.setText(newValue);
        } else {
            // empty cell with value 0, should be shown as empty string
            toUpdate.setText("");
        }

        /* --> update the status of the GUI cell <-- */
        toUpdate.setEnabled(updated.isEditable());

        // editable --> font
        if (updated.isEditable()) {
            toUpdate.setFont(BoardConstants.FONT_EDITABLE);
            toUpdate.enableHover();
        } else {
            toUpdate.setFont(BoardConstants.FONT_UNEDITABLE);
            toUpdate.disableHover();
        }

        // check for valid input (no duplicate) --> background
        if (updated.isValid()) {
            toUpdate.setBackground(BoardConstants.CELL_COLOR_NORMAL);
        } else {
            toUpdate.setBackground(BoardConstants.CELL_COLOR_ERROR);
        }

        // compare with solution --> background color for wrong cells
        // CAREFUL: only change when comparison view enabled and background not already changed because of an
        //          invalid cell
        if (updated.isValid()) {

            // standard color for not changed (init value)
            toUpdate.setBackground(BoardConstants.CELL_COLOR_NORMAL);

            // only mark wrong filled Cells when comparison view enabled
            if (showComparisonToSolution && !updated.isSameAsSolution() && updated.getValue() != 0) {
                toUpdate.setBackground(BoardConstants.CELL_COLOR_NOT_LIKE_SOLUTION);
            }
        }

        // automatically solved --> foreground
        if (updated.isAutomaticallySolved()) {
            toUpdate.setForeground(BoardConstants.FONT_COLOR_AUTOMATICALLY_SOLVED);
        } else {
            toUpdate.setForeground(BoardConstants.FONT_COLOR_NORMAL);
        }

        // repaint the Board
        gui.repaint();

        // control output
        //LOGGER.debug("Sudoku updated: {}", model.getAsPrettyString());
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

        // compare the Sudoku with the solution
        model.checkAndMarkCellsNotSameAsSolution();
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

    /**
     * Sets the flag for comparison to solution so that the comparison can be activated with true or deactivated
     * with false.
     *
     * @param showComparisonToSolution
     *      true for showing, else false
     */
    public void setShowComparisonToSolution(boolean showComparisonToSolution) {
        this.showComparisonToSolution = showComparisonToSolution;
    }
}
