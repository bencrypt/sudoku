package utils;

import model.Cell;
import model.Sudoku;
import view.HoverButton;

import java.util.List;

public class IndexConverter {

    /**
     * Converts the given one-dimensional index of a GUI-Cell in the GUI-list to the two-dimensional index for
     * the model-Cells and returns the Cell at this position.
     *
     * @param sudoku
     *      the Sudoku with the Cell
     * @param guiCell
     *      the index of the GUI-Cell (with one-dimensional index)
     * @return
     *      the model-Cell which corresponds to the GUI-Cell at the given position
     */
    public static Cell determineModelCellFromIndexOfGuiCell(Sudoku sudoku, int guiCell) {

        // calculate the position of the Cell given by its index
        int cellRow = guiCell / 9;
        int cellCol = guiCell % 9;

        // calculate the box index and return it
        return sudoku.getBoard()[cellRow][cellCol];
    }

    /**
     * Converts the given two-dimensional index of a model-Cell to the one-dimensional index in the GUI-list
     * and returns the GUI-Cell at this position.
     *
     * @param modelCellX
     *      the x-coordinate of the model's Cell
     * @param modelCellY
     *      the y-coordinate of the model's Cell
     * @param guiCells
     *      the list of the GUI-Cells (with one-dimensional index)
     * @return
     *      the GUI-Cell which corresponds to the model-Cell at the given coordinates
     */
    public static HoverButton determineGuiCellFromModelCell(int modelCellX, int modelCellY, List<HoverButton> guiCells) {
        int indexGuiCell = (modelCellX*9) + modelCellY;
        return guiCells.get(indexGuiCell);
    }

    /**
     * Converts the given one-dimensional list-index of a GUI-Cell to the corresponding 3x3-box in which this Cell
     * should be placed within the Sudoku.
     *
     * @param cellIndex
     *      the one-dimensional index of a GUI-Cell in the GUI-list
     * @return
     *      the index of the 3x3-box in which the GUI-Cell should be placed
     */
    public static int determineGuiBoxForGuiCell(int cellIndex) {

        // calculate the position of the cell given by its index
        int cellRow = cellIndex / 9;
        int cellCol = cellIndex % 9;

        // calculate the position of the box with help of the cell position
        int boxRow = cellRow / 3;
        int boxCol = cellCol / 3;

        // calculate the box index and return it
        return (boxRow*3) + boxCol;
    }
}
