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

    private Popup popup;

    private List<HoverButton> valueButtons;
    private HoverButton btnDelete;

    /* --> Constructor <-- */

    public ValueSelector() {
        init();
    }

    /* --> Methods <-- */

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

    private JPanel createValuePanel() {
        JPanel panValues = new JPanel();

        panValues.setLayout(new GridLayout(3, 3, 0, 0));
        panValues.setPreferredSize(new Dimension(100, 100));

        valueButtons.forEach(btn -> {
            panValues.add(btn);
        });

        return panValues;
    }

    private JPanel createDeletePanel() {
        JPanel panDelete = new JPanel();

        panDelete.setLayout(new BorderLayout());
        panDelete.add(btnDelete, BorderLayout.CENTER);

        return panDelete;
    }

    public void showPopup(int posX, int posY) {

        PopupFactory pf = PopupFactory.getSharedInstance();

        popup = pf.getPopup(null, this, posX, posY);
        popup.show();
    }

    public void hidePopup() {
        if (popup != null) {
            popup.hide();
        }
    }

    /* --> Getters and Setters <-- */

    public List<HoverButton> getValueButtons() {
        return Collections.unmodifiableList(valueButtons);
    }

    public HoverButton getBtnDelete() {
        return btnDelete;
    }
}
