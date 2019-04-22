package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ControlPanel extends JPanel {

    /* --> Fields <-- */

    private HoverButton btnSolveCompletely;
    private HoverButton btnSolveNextStep;

    /* --> Constructor <-- */

    public ControlPanel() {
        init();
    }

    /* --> Methods <-- */

    private void init() {

        // preferences
        setLayout(new GridLayout(1, 2, 20, 0));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BoardConstants.BACKGROUND);

        // initialize the buttons
        btnSolveCompletely = new HoverButton("Komplett lösen");
        btnSolveNextStep = new HoverButton("Nächster Schritt");

        btnSolveCompletely.setBackground(BoardConstants.CELL_COLOR_NORMAL);
        btnSolveNextStep.setBackground(BoardConstants.CELL_COLOR_NORMAL);

        btnSolveCompletely.setFocusable(false);
        btnSolveNextStep.setFocusable(false);

        // add the elements
        add(btnSolveCompletely);
        add(btnSolveNextStep);
    }

    /* --> Getters and Setters <-- */

    public HoverButton getBtnSolveCompletely() {
        return btnSolveCompletely;
    }

    public HoverButton getBtnSolveNextStep() {
        return btnSolveNextStep;
    }
}
