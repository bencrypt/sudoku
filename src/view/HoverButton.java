package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButton extends JButton {

    public HoverButton(String text) {
        super(text);
        init();
    }

    private void init() {

        setBorder(new LineBorder(Color.BLACK));
        setBackground(BoardConstants.BACKGROUND);

        changeOnHover();
    }

    private void changeOnHover() {

        EtchedBorder border = new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, BoardConstants.BORDER_COLOR);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBorder(border);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBorder(new LineBorder(Color.BLACK));
            }
        });
    }
}
