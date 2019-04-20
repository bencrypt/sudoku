package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButton extends JButton {

    /* --> Fields <-- */

    private Border previousBorder;

    /* --> Constructors <-- */

    public HoverButton(String text) {
        super(text);
        init(Color.BLACK, BoardConstants.BORDER_COLOR);
    }

    public HoverButton(String text, Color highlight, Color shadow) {
        super(text);
        init(highlight, shadow);
    }

    /* --> Methods <-- */

    private void init(Color highlight, Color shadow) {

        // preferences
        setBackground(BoardConstants.BACKGROUND);

        // add the hover-effect with the given colors
        changeOnHover(highlight, shadow);

        // set the font color as normal when disabled
        setUI(new MetalButtonUI() {
            protected Color getDisabledTextColor() {
                return BoardConstants.FONT_COLOR_NORMAL;
            }
        });
    }

    @Override
    public void setBorder(Border border) {

        // save previous border
        previousBorder = getBorder();

        // set new border
        super.setBorder(border);
    }

    private void changeOnHover(Color highlight, Color shadow) {

        // create hover border
        EtchedBorder border = new EtchedBorder(highlight, shadow);

        // create hover effect
        // --> if the hover is over, return to the previous border
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBorder(border);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBorder(previousBorder);
            }
        });
    }
}
