package controller;

import eventHandling.ValueSelectorHandler;
import view.ValueSelector;

public class CtrlValueSelector {

    /* --> Fields <-- */

    // related View
    private ValueSelector gui;

    // needed for event handling
    private CtrlBoard ctrlBoard;

    /* --> Constructor <-- */

    /**
     * Creates a Controller for a ValueSelector.
     * Initializes the GUI and creates the related event handling.
     *
     * @param ctrlBoard
     *      the Board-Controller which works together with the ValueSelector-Controller
     *
     * @see ValueSelectorHandler
     */
    public CtrlValueSelector(CtrlBoard ctrlBoard) {

        // set the needed fields
        this.ctrlBoard = ctrlBoard;
        this.gui = new ValueSelector();

        // create event handling
        addClickHandler();
    }

    /* --> Methods <-- */

    /**
     * Creates the event handling for mouse clicks on the GUI of the ValueSelector.
     *
     * @see ValueSelectorHandler
     */
    private void addClickHandler() {
        new ValueSelectorHandler(ctrlBoard, gui);
    }

    /* --> Getters and Setters <-- */

    /**
     * Returns the View of the ValueSelector.
     * @return
     *      the View of the ValueSelector
     */
    public ValueSelector getGui() {
        return gui;
    }
}
