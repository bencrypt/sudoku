package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButton extends JButton {

    /* --> Fields <-- */

    private Border previousBorder;
    private Color highlight;
    private Color shadow;

    private MouseAdapter mouseAdapter;

    /* --> Constructors <-- */

    /**
     * Creates a Button which changes its border when hovering. To define own colors for the hover border, the
     * other constructor has to be used.
     *
     * @param text
     *      the text of the Button
     */
    public HoverButton(String text) {
        super(text);
        init(BoardConstants.BORDER_COLOR, BoardConstants.BORDER_COLOR);
    }

    /**
     * Creates a Button which changes its border when hovering. The given colors define the color of the border
     * when hovering.
     *
     * @param text
     *      the text of the Button
     */
    public HoverButton(String text, Color highlight, Color shadow) {
        super(text);

        this.highlight = highlight;
        this.shadow = shadow;

        init(highlight, shadow);
    }

    /* --> Methods <-- */

    /**
     * Initializes the HoverButton with the given colors for the border when hovering.
     *
     * @param highlight
     *      the highlight color for the border when hovered
     * @param shadow
     *      the shadow color for the border when hovered
     */
    private void init(Color highlight, Color shadow) {

        // preferences
        setBackground(BoardConstants.BACKGROUND);

        // add the hover-effect with the given colors
        initMouseAdapter(highlight, shadow);

        // set the font color as normal when disabled
        setUI(new MetalButtonUI() {
            protected Color getDisabledTextColor() {
                return BoardConstants.FONT_COLOR_NORMAL;
            }
        });
    }

    /**
     * Initializes the MouseAdapter which realizes the hover effect.
     *
     * @param highlight
     *      the highlight color for the border when hovered
     * @param shadow
     *      the shadow color for the border when hovered
     */
    private void initMouseAdapter(Color highlight, Color shadow) {

        // create hover border
        EtchedBorder border = new EtchedBorder(highlight, shadow);

        // create hover effect
        // --> if the hover is over, return to the previous border
        mouseAdapter = new MouseAdapter() {
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
        };

        // add the MouseAdapter
        addMouseListener(mouseAdapter);
    }

    /**
     * Resets the button so that it has the same hover effect as when it was initialized.
     */
    public void reset() {
        disableHover();
        enableHover();
    }

    /**
     * Enables the hover effect with the colors given at initializing.
     */
    public void enableHover() {

        // check if the mouse adapter is already set
        boolean containsMouseAdapter = false;
        for (int i = 0; i < getMouseListeners().length; i++) {
            if (getMouseListeners()[i] == mouseAdapter) {
                containsMouseAdapter = true;
                break;
            }
        }

        // only add the mouse adapter if not done already
        if (!containsMouseAdapter) {
            initMouseAdapter(highlight, shadow);
        }
    }

    /**
     * Disables the hover effect so that the HoverButton reacts like a normal JButton.
     */
    public void disableHover() {
        removeMouseListener(mouseAdapter);
    }

    /**
     * Sets the border and saves the previous border to change it later. Needed for the hover effect.
     *
     * @param border
     *      the new border
     */
    @Override
    public void setBorder(Border border) {

        // save previous border
        previousBorder = getBorder();

        // set new border
        super.setBorder(border);
    }
}
