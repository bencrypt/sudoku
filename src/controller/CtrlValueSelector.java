package controller;

import eventHandling.ValueSelectorHandler;
import view.ValueSelector;

public class CtrlValueSelector {

    /* --> Fields <-- */

    private CtrlBoard ctrlBoard;
    private ValueSelector gui;

    private int IndexClickedCell;

    /* --> Constructor <-- */

    public CtrlValueSelector(CtrlBoard ctrlBoard) {

        this.ctrlBoard = ctrlBoard;
        this.gui = new ValueSelector();

        addClickHandler();
    }

    /* --> Methods <-- */

    private void addClickHandler() {
        new ValueSelectorHandler(ctrlBoard, this);
    }

    /* --> Getters and Setters <-- */

    public ValueSelector getGui() {
        return gui;
    }

    public int getIndexClickedCell() {
        return IndexClickedCell;
    }

    public void setIndexClickedCell(int indexClickedCell) {
        this.IndexClickedCell = indexClickedCell;
    }
}
