package eventHandling;

import controller.CtrlBoard;
import view.HoverButton;
import view.ValueSelector;

public class ValueSelectorHandler {

    /* --> Fields <-- */

    // related view
    private ValueSelector gui;

    // needed for event handling
    private CtrlBoard ctrlBoard;


    /* --> Constructor <-- */

    /**
     * Creates the event handling for the given ValueSelector-GUI. Therefore uses the given Board-Controller to use
     * the selected value as input.
     *
     * @param ctrlBoard
     *      the Board-Controller needed for event handling
     * @param gui
     *      the GUI to be supplemented by the event handling
     *
     * @see #addValueButtonHandler(HoverButton)
     * @see #addDeleteButtonHandler()
     */
    public ValueSelectorHandler(CtrlBoard ctrlBoard, ValueSelector gui) {

        // set the fields
        this.ctrlBoard = ctrlBoard;
        this.gui = gui;

        // create a ValueButtonHandler for each value button
        gui.getValueButtons().forEach(btn -> {
            addValueButtonHandler(btn);
        });

        // create the event handling for the delete button
        addDeleteButtonHandler();
    }

    /* --> Methods <-- */

    /**
     * Creates the event handling for the given value button to select a value for the clicked Cell.
     *
     * @param btn
     *      the button to be supplemented by the event handling
     */
    private void addValueButtonHandler(HoverButton btn) {
        btn.addActionListener(e -> {
            ctrlBoard.updateFromValueInput(ctrlBoard.getIndexClickedCell(), Integer.valueOf(btn.getText()));
            gui.hidePopup();
        });
    }

    /**
     * Creates the event handling for the delete button to clear the clicked Cell.
     */
    private void addDeleteButtonHandler() {
        gui.getBtnDelete().addActionListener(e -> {
            ctrlBoard.updateFromValueInput(ctrlBoard.getIndexClickedCell(), 0);
            gui.hidePopup();
        });
    }
}
