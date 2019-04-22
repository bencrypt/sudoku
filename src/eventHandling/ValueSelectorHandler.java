package eventHandling;

import controller.CtrlBoard;
import controller.CtrlValueSelector;
import view.ValueSelector;

import javax.swing.*;

public class ValueSelectorHandler {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;
    private CtrlValueSelector ctrlValueSelector;

    private ValueSelector gui;

    /* --> Constructor <-- */

    public ValueSelectorHandler(CtrlBoard ctrlBoard, CtrlValueSelector ctrlValueSelector) {

        this.ctrlBoard = ctrlBoard;
        this.ctrlValueSelector = ctrlValueSelector;
        this.gui = ctrlValueSelector.getGui();

        gui.getValueButtons().forEach(btn -> {
            addValueButtonHandler(btn);
        });

        addDeleteButtonHandler();
    }

    /* --> Methods <-- */

    private void addValueButtonHandler(JButton btn) {
        btn.addActionListener(e -> {
            ctrlBoard.updateFromValueInput(ctrlValueSelector.getIndexClickedCell(), Integer.valueOf(btn.getText()));
            gui.hidePopup();
        });
    }

    private void addDeleteButtonHandler() {
        gui.getBtnDelete().addActionListener(e -> {
            ctrlBoard.updateFromValueInput(ctrlValueSelector.getIndexClickedCell(), 0);
            gui.hidePopup();
        });
    }
}
