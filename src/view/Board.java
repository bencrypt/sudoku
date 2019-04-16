package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    /* --> Fields <-- */

    private int cellSize = BoardConstants.CELL_SIZE;

    private ArrayList<JButton> cells;

    private ArrayList<JPanel> boxes;

    /* --> Constructor <-- */

    public Board() {
        init();
    }

    /* --> Methods <-- */

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
        cells = new ArrayList<JButton>(81);
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

    private JButton initCell() {

        // create the cell
        JButton cell = new JButton("0");

        cell.setFocusable(false);

        // set the standard size
        cell.setPreferredSize(new Dimension(cellSize, cellSize));

        // make the cell non transparent and set the standard colors
        cell.setOpaque(true);
        cell.setForeground(BoardConstants.FONT_COLOR_NORMAL);
        cell.setBackground(BoardConstants.CELL_COLOR_NORMAL);

        // put text in the center
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setVerticalAlignment(SwingConstants.CENTER);

        // return the cell
        return cell;
    }

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

    private void addCellsToBoxes() {

        // add each cell to the corresponding box
        for (int i = 0; i < cells.size(); i++) {
            int box = determineBoxForCell(i);
            boxes.get(box).add(cells.get(i));
        }

    }

    private int determineBoxForCell(int cellIndex) {

        // calculate the position of the cell given by its index
        int cellRow = cellIndex / 9;
        int cellCol = cellIndex % 9;

        // calculate the position of the box with help of the cell position
        int boxRow = cellRow / 3;
        int boxCol = cellCol / 3;

        // calculate the box index and return it
        return (boxRow*3) + boxCol;
    }

    /* --> Getters And Setters <-- */

    public ArrayList<JButton> getCells() {
        return cells;
    }
}
