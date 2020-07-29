package eventHandling;

import controller.CtrlBoard;
import controller.CtrlValueSelector;
import view.Board;
import view.HoverButton;
import view.ValueSelector;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardHandler {

    /* --> Fields <-- */

    // related Controller and View
    private CtrlBoard ctrlBoard;
    private Board gui;

    // needed for changing a Cell's value through GUI input
    private CtrlValueSelector ctrlValueSelector;
    private ValueSelector valueSelector;

    /* --> Constructor <-- */

    /**
     * Creates the event handling for the Board-GUI given by its related Board-Controller. Therefore also uses the
     * given ValueSelector-Controller to enable the user to select a certain value for a Board's Cell.
     *
     * @param ctrlBoard
     *      the Board-Controller for the Board to be supplemented by the event handling
     * @param ctrlValueSelector
     *      the ValueSelector-Controller needed for GUI input
     *
     * @see #addBoardClickHandler()
     */
    public BoardHandler(CtrlBoard ctrlBoard, CtrlValueSelector ctrlValueSelector) {

        // set the fields
        this.ctrlBoard = ctrlBoard;
        this.gui = ctrlBoard.getGui();

        this.ctrlValueSelector = ctrlValueSelector;
        this.valueSelector = ctrlValueSelector.getGui();

        // add event handling for a Click on a Board's Cell
        addBoardClickHandler();
    }

    /* --> Methods <-- */

    /**
     * Creates the event handling for a mouse click on a Board's Cell. When a editable Cell is clicked, the GUI of a
     * ValueSelector is shown to enable the user to select a value for this Cell.
     * <p>
     * If there was already a ValueSelector showing, this one will be closed firstly. A ValueSelector is also closed
     * when clicking on the border of the Board or leaving the Board with the mouse cursor.
     *
     * @see #addCellClickHandler(HoverButton)
     */
    private void addBoardClickHandler() {

        // add a click handler for each Cell
        gui.getCells().forEach(cell -> {
            addCellClickHandler(cell);
        });

        // hide ValueSelector when clicked on Board or when exited Board
        gui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ctrlValueSelector.getGui().hidePopup();
            }

            @Override
            public void mouseExited(MouseEvent e) {

                // get the absolute coordinates of the Board and the mouse cursor
                Point boardStart = gui.getLocationOnScreen();
                Point cursor = e.getLocationOnScreen();

                // if the mouse is NOT on the board, hide the ValueSelector
                if (cursor.x < boardStart.x || cursor.x > (boardStart.x + gui.getWidth())
                        || cursor.y < boardStart.y || cursor.y > (boardStart.y + gui.getHeight()))
                {
                    ctrlValueSelector.getGui().hidePopup();
                }
            }
        });
    }

    /**
     * Creates the event handling for a mouse click on a Board's Cell. When a editable Cell is clicked, the GUI of a
     * ValueSelector is shown to enable the user to select a value for this Cell.
     * <p>
     * If there was already a ValueSelector showing, this one will be closed firstly.
     *
     * @param cell
     *      the GUI of the Cell to be supplemented by the event handling
     */
    private void addCellClickHandler(HoverButton cell) {

        // use MouseListener instead of ActionListener to react to every mouse action and not only to
        // left clicks on the button
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // hide the previous popup if needed
                valueSelector.hidePopup();

                // only go on when clicked on a editable Cell
                if (cell.isEnabled()) {

                    // determine the index of the clicked cell
                    int indexClickedCell = gui.getCells().indexOf(cell);
                    ctrlBoard.setIndexClickedCell(indexClickedCell);

                    // show a popup for selecting a value on the clicked point
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        valueSelector.showPopup(e.getXOnScreen(), e.getYOnScreen());
                    }
                }
            }
        });
    }
}
