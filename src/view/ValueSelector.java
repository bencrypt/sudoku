package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ValueSelector extends JPanel {

    /* --> Fields <-- */

    private Popup popup;

    /* --> Constructor <-- */

    public ValueSelector() {

        setLayout(new BorderLayout());

        add(createValuePanel(), BorderLayout.CENTER);
        add(createDeletePanel(), BorderLayout.SOUTH);
    }

    /* --> Methods <-- */

    private JPanel createValuePanel() {
        JPanel panValues = new JPanel();

        panValues.setLayout(new GridLayout(3, 3, 0, 0));
        panValues.setPreferredSize(new Dimension(100, 100));

        panValues.add(new HoverButton("1"));
        panValues.add(new HoverButton("2"));
        panValues.add(new HoverButton("3"));
        panValues.add(new HoverButton("4"));
        panValues.add(new HoverButton("5"));
        panValues.add(new HoverButton("6"));
        panValues.add(new HoverButton("7"));
        panValues.add(new HoverButton("8"));
        panValues.add(new HoverButton("9"));

        return panValues;
    }

    private JPanel createDeletePanel() {
        JPanel panDelete = new JPanel();
        panDelete.setLayout(new BorderLayout());
        panDelete.add(new HoverButton("Entfernen"), BorderLayout.CENTER);
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
}
