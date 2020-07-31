package view;

import model.BoardConstants;
import utils.LanguageBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class ControlPanel extends JPanel {

    /* --> Internationalization <-- */

    private ResourceBundle bundle = LanguageBundle.getBundle();

    /* --> Fields <-- */

    private HoverButton btnSolveCompletely;
    private HoverButton btnSolveNextStep;

    private JCheckBox chkCompareWithSolution;

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
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BoardConstants.BACKGROUND);

        // initalize checkbox
        chkCompareWithSolution = new JCheckBox(bundle.getString("CompareWithSolution"));
        chkCompareWithSolution.setBackground(BoardConstants.BACKGROUND);
        chkCompareWithSolution.setHorizontalAlignment(SwingConstants.RIGHT);
        chkCompareWithSolution.setFocusable(false);
        chkCompareWithSolution.setSelected(true);

        // initialize button panel
        JPanel panButtons = new JPanel();
        panButtons.setLayout(new GridLayout(1, 2, 20, 0));
        panButtons.setBackground(BoardConstants.BACKGROUND);
        panButtons.setBorder(new EmptyBorder(10,  0, 0, 0));

        btnSolveCompletely = new HoverButton(bundle.getString("SolveCompletely"));
        btnSolveNextStep = new HoverButton(bundle.getString("NextStep"));

        btnSolveCompletely.setBackground(BoardConstants.CELL_COLOR_NORMAL);
        btnSolveNextStep.setBackground(BoardConstants.CELL_COLOR_NORMAL);

        btnSolveCompletely.setFocusable(false);
        btnSolveNextStep.setFocusable(false);

        panButtons.add(btnSolveCompletely);
        panButtons.add(btnSolveNextStep);

        // add the elements
        add(chkCompareWithSolution, BorderLayout.NORTH);
        add(panButtons, BorderLayout.CENTER);
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

    /**
     * @return the checkbox "Compare with solution"
     */
    public JCheckBox getChkCompareWithSolution() {
        return chkCompareWithSolution;
    }
}
