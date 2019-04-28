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

    /**
     * Creates the ControlPanel for the main window which enables the user to solve the Sudoku completely or step
     * by step. Does not add the event handling, this has to be done by {@link eventHandling.ControlPanelHandler}.
     */
    public ControlPanel() {
        init();
    }

    /* --> Methods <-- */

    /**
     * Initializes the ControlPanel with all its elements.
     */
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

    /**
     * @return the button "Solve completely"
     */
    public HoverButton getBtnSolveCompletely() {
        return btnSolveCompletely;
    }

    /**
     * @return the button "Solve next step"
     */
    public HoverButton getBtnSolveNextStep() {
        return btnSolveNextStep;
    }
}
