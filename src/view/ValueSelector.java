package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValueSelector extends JPanel {

    /* --> Fields <-- */

    // the popup which displays the ValueSelector
    private Popup popup;

    // the items on the ValueSelector-Panel
    private List<HoverButton> valueButtons;
    private HoverButton btnDelete;

    /* --> Constructor <-- */

    /**
     * Creates the ValueSelector with all its items
     * Does not create the event handling,. this has to be done by {@link eventHandling.ValueSelectorHandler}.
     */
    public ValueSelector() {
        init();
    }

    /* --> Methods <-- */

    /**
     * Initializes the ValueSelector with all its items.
     */
    private void init() {

        // preferences
        setLayout(new BorderLayout());

        // init buttons
        valueButtons = new ArrayList<>(9);

        valueButtons.add(new HoverButton("1"));
        valueButtons.add(new HoverButton("2"));
        valueButtons.add(new HoverButton("3"));
        valueButtons.add(new HoverButton("4"));
        valueButtons.add(new HoverButton("5"));
        valueButtons.add(new HoverButton("6"));
        valueButtons.add(new HoverButton("7"));
        valueButtons.add(new HoverButton("8"));
        valueButtons.add(new HoverButton("9"));

        btnDelete = new HoverButton("Entfernen");

        // add borders to the buttons
        valueButtons.forEach(btn -> {
            btn.setBorder(new LineBorder(BoardConstants.BORDER_COLOR));
        });

        btnDelete.setBorder(new LineBorder(BoardConstants.BORDER_COLOR));

        // create and add panels
        // --> DeletePanel in Center to avoid resizing when the hover border from the delete button is showed
        add(createValuePanel(), BorderLayout.NORTH);
        add(createDeletePanel(), BorderLayout.CENTER);
    }

    /**
     * Creates and returns the panel for selecting a value from 1 to 9.
     *
     * @return
     *      the value panel
     */
    private JPanel createValuePanel() {

        // create the panel
        JPanel panValues = new JPanel();

        // preferences
        panValues.setLayout(new GridLayout(3, 3, 0, 0));
        panValues.setPreferredSize(new Dimension(100, 100));

        // add the 9 value selection buttons
        valueButtons.forEach(btn -> {
            panValues.add(btn);
        });

        // return the panel
        return panValues;
    }

    /**
     * Creates and returns the panel for deleting a value.
     *
     * @return
     *      the delete panel
     */
    private JPanel createDeletePanel() {

        // create the panel
        JPanel panDelete = new JPanel();

        // preferences
        panDelete.setLayout(new BorderLayout());

        // add the deletion button
        panDelete.add(btnDelete, BorderLayout.CENTER);

        // return the panel
        return panDelete;
    }

    /**
     * Shows the ValueSelector in form of a popup on the given position on screen.
     *
     * @param posX
     *      the x-coordinate for the popup
     * @param posY
     *      the y-coordinate for the popup
     */
    public void showPopup(int posX, int posY) {

        // create the popup
        PopupFactory pf = PopupFactory.getSharedInstance();
        popup = pf.getPopup(null, this, posX, posY);

        // show the popup
        popup.show();
    }

    /**
     * Hides the ValueSelector in form of a popup and resets all buttons so that the next popup is showed correctly
     * when needed.
     */
    public void hidePopup() {
        if (popup != null) {

            // reset the value buttons so that the border is shown correctly next time the popup opens
            valueButtons.forEach(btn -> {
                btn.reset();
            });

            // hide the popup
            popup.hide();
        }
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the value buttons to select a value from 1 to 9
     */
    public List<HoverButton> getValueButtons() {
        return Collections.unmodifiableList(valueButtons);
    }

    /**
     * @return the delete button to delete a value
     */
    public HoverButton getBtnDelete() {
        return btnDelete;
    }
}
