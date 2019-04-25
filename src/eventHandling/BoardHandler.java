package eventHandling;

import controller.CtrlBoard;
import controller.CtrlValueSelector;
import view.Board;
import view.HoverButton;
import view.ValueSelector;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardHandler {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;
    private Board gui;

    private CtrlValueSelector ctrlValueSelector;
    private ValueSelector valueSelector;

    /* --> Constructor <-- */

    public BoardHandler(CtrlBoard ctrlBoard, CtrlValueSelector ctrlValueSelector) {

        this.ctrlBoard = ctrlBoard;
        this.gui = ctrlBoard.getGui();

        this.ctrlValueSelector = ctrlValueSelector;
        this.valueSelector = ctrlValueSelector.getGui();

        addBoardClickHandler();
    }

    /* --> Methods <-- */

    private void addBoardClickHandler() {
        gui.getCells().forEach(cell -> {
            addCellClickHandler(cell);
        });

        // hide ValueSelector when clicked on Board or when exited Board
        gui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                ctrlValueSelector.getGui().hidePopup();
            }
        });
    }

    private void addCellClickHandler(JButton cell) {

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
                    ctrlValueSelector.setIndexClickedCell(indexClickedCell);

                    // show a popup for selecting a value on the clicked point
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        valueSelector.showPopup(e.getXOnScreen(), e.getYOnScreen());
                    }
                }
            }
        });
    }
}
