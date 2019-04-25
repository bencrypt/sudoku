package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    /* --> Fields <-- */

    /* --> Constructor <-- */

    public Window() {
        init();
    }

    /* --> Methods <-- */

    private void init() {

        // preferences
        setTitle("Sudoku");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
    }

    public void makeVisible() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* --> Getters and Setters <-- */

    public void addToBorderLayout(JComponent component, Object constraints) {

        // first check for right arguments
        if (component == null || !(constraints instanceof String)) {
            throw new IllegalArgumentException();
        }

        // use different cases for BorderLayout
        switch((String) constraints) {
            case BorderLayout.CENTER:
                add(component, BorderLayout.CENTER);
                break;
            case BorderLayout.NORTH:
                add(component, BorderLayout.NORTH);
                break;
            case BorderLayout.EAST:
                add(component, BorderLayout.EAST);
                break;
            case BorderLayout.SOUTH:
                add(component, BorderLayout.SOUTH);
                break;
            case BorderLayout.WEST:
                add(component, BorderLayout.WEST);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
