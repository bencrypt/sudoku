package utils;

import view.HoverButton;

import java.util.List;

public class IndexConverter {


    /**
     * Converts a given one-dimensional Sudoku index (0-80) into a two-dimensional Index (0-8, 0-8) and returns
     * the both values in form of a Pair of Integers.
     *
     * @param index1D
     *      the one-dimensional index (0-80)
     * @return
     *      a Pair of Integers for the two-dimensional index (0-8, 0-8)
     */
    public static Pair<Integer, Integer> determineIndex2DFromIndex1D(int index1D) {

        // calculate the 2D-index of the Cell given by the 1D-index
        int cellRow = index1D / 9;
        int cellCol = index1D % 9;

        // return the 2D-Index
        return new Pair<>(cellRow, cellCol);
    }

    /**
     * Converts a given two-dimensional Sudoku index (0-8, 0-8) into a one-dimensional Index (0-80) and returns
     * this value.
     *
     * @param index2D
     *      a Pair of Integers for the two-dimensional index (0-8, 0-8)
     * @return
     *      the one-dimensional index (0-80)
     */
    public static int determineIndex1DFromIndex2D(Pair<Integer, Integer> index2D) {

        // calculate the 1D-index of the Cell given by the 2D-index
        int index1D = (index2D.getFirst()*9) + index2D.getSecond();

        // return the 1D-Index
        return index1D;
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
        int indexGuiCell = determineIndex1DFromIndex2D(new Pair<>(modelCellX, modelCellY));
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
