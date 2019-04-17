package eventHandling;

import controller.CtrlBoard;
import view.Board;
import view.ValueSelector;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardHandler {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;
    private Board gui;

    private ValueSelector valueSelector;

    /* --> Constructor <-- */

    public BoardHandler(CtrlBoard ctrlBoard) {

        this.ctrlBoard = ctrlBoard;
        this.gui = ctrlBoard.getGui();

        this.valueSelector = new ValueSelector();

        addBoardClickHandler();
    }

    /* --> Methods <-- */

    private void addBoardClickHandler() {
        gui.getCells().forEach(cell -> {
            addCellClickHandler(cell);
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

                // show a popup for selecting a value on the clicked point
                if (e.getButton() == MouseEvent.BUTTON1) {
                    valueSelector.showPopup(e.getXOnScreen(), e.getYOnScreen());
                }
            }
        });
    }
}
