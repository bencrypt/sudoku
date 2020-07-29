package view;

import model.BoardConstants;
import utils.IndexConverter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    /* --> Fields <-- */

    // the cells and boxes of the grid, each with an one-dimensional array
    private ArrayList<HoverButton> cells;
    private ArrayList<JPanel> boxes;

    /* --> Constructor <-- */

    /**
     * Creates the View for the Sudoku-Board.
     * Does not create the event handling, this has to be done by {@link eventHandling.BoardHandler}
     */
    public Board() {
        init();
    }

    /* --> Methods <-- */

    /*
     * methods for initializing the GUI
     */

    /**
     * Initializes the Sudoku-Board by creating the 81 Cells and the 9 Boxes.
     */
    private void init() {

        // preferences
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setBackground(BoardConstants.BACKGROUND);

        // create a separate content pane for the actual board to use another border
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(3, 3, 0, 0));
        contentPane.setBorder(new LineBorder(BoardConstants.BORDER_COLOR, 3));
        add(contentPane);

        // initialize the 81 cells
        cells = new ArrayList<>(81);
        for (int i = 0; i < 81; i++) {
            cells.add(initCell());
        }

        // initialize the 9 boxes
        boxes = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            boxes.add(initBox());
        }

        // add the cells to the boxes
        addCellsToBoxes();

        // add the filled boxes to the content pane
        boxes.forEach(box -> {
            contentPane.add(box);
        });


    }

    /**
     * Initializes and returns a Cell for the Sudoku-Board.
     *
     * @return
     *      a Cell for the Sudoku board
     */
    private HoverButton initCell() {

        // create the cell
        HoverButton cell = new HoverButton("0", BoardConstants.BACKGROUND, BoardConstants.BACKGROUND);

        // preferences
        cell.setFocusable(false);

        // set the standard size
        cell.setPreferredSize(new Dimension(BoardConstants.CELL_SIZE, BoardConstants.CELL_SIZE));

        // make the cell non transparent and set the standard colors
        cell.setForeground(BoardConstants.FONT_COLOR_NORMAL);
        cell.setBackground(BoardConstants.CELL_COLOR_NORMAL);

        // put text in the center
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setVerticalAlignment(SwingConstants.CENTER);

        // return the cell
        return cell;
    }

    /**
     * Initializes and returns a box for the Sudoku-Board which contains 3x3 Cells. The Cells are not assigned to the
     * boxes here, this has to be done by {@link #addCellsToBoxes()}.
     *
     * @return
     *      a box for the Sudoku board
     */
    private JPanel initBox() {

        // create the box
        JPanel box = new JPanel();

        // set a grid layout with thin spaces between each cell
        box.setLayout(new GridLayout(3, 3, 3, 3));

        // set the background color the same as the border color for the grid gaps
        box.setBackground(BoardConstants.BORDER_COLOR);

        // set the border for the box
        box.setBorder(new LineBorder(BoardConstants.BORDER_COLOR, 3));

        // return the box
        return box;
    }

    /**
     * Assignes all 81 Cells to their corresponding 9 boxes of the Sudoku-Board.
     */
    private void addCellsToBoxes() {

        // add each cell to the corresponding box
        for (int i = 0; i < cells.size(); i++) {
            int box = IndexConverter.determineGuiBoxForGuiCell(i);
            boxes.get(box).add(cells.get(i));
        }
    }

    /* --> Getters And Setters <-- */

    /**
     * @return all 81 GUI-Cells of the Sudoku-Board
     */
    public ArrayList<HoverButton> getCells() {
        return cells;
    }
}
