package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    /* --> Constructor <-- */

    /**
     * Creates the main window for the application. The elements on the Window should be added by
     * {@link #addToBorderLayout(JComponent, Object)} for correct useness.
     * Does not add the subelements and does not create event handling. Latter has to be done by
     * {@link eventHandling.WindowHandler}.
     */
    public Window() {
        init();
    }

    /* --> Methods <-- */

    /**
     * Initializes the Window. Does NOT initializes the components on the Window.
     */
    private void init() {

        // preferences
        setTitle("Sudoku");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
    }

    /**
     * Optimizes the Window and displays it on the center of the screen.
     */
    public void makeVisible() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* --> Getters and Setters <-- */

    /**
     * Adds the given component on the given place to the BorderLayout of the Window if both parameters have a correct
     * value. Therefore the BorderLayout constraints like {@link BorderLayout#CENTER} have to be used.
     *
     * @param component
     *      the component to add
     * @param constraints
     *      a BorderLayout constraint like {@link BorderLayout#CENTER}
     */
    public void addToBorderLayout(JComponent component, Object constraints) {

        // first check for right arguments
        if (component == null || !(constraints instanceof String)) {
            throw new IllegalArgumentException();
        }

        // use different cases for BorderLayout
        switch ((String) constraints) {
            case BorderLayout.CENTER -> add(component, BorderLayout.CENTER);
            case BorderLayout.NORTH -> add(component, BorderLayout.NORTH);
            case BorderLayout.EAST -> add(component, BorderLayout.EAST);
            case BorderLayout.SOUTH -> add(component, BorderLayout.SOUTH);
            case BorderLayout.WEST -> add(component, BorderLayout.WEST);
            default -> throw new IllegalArgumentException();
        }
    }
}
