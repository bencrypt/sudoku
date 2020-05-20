package view;

import model.BoardConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EnterBoardDialog extends JDialog {

    /* --> Fields <-- */

    private JButton btnOk;
    private JButton btnCancel;

    /* --> Constructor <-- */

    /**
     * Creates the dialog with all subelements for entering a given Sudoku.
     * Does not open this dialog. This has to be done separately by calling {@link #makeVisible()}.
     */
    public EnterBoardDialog(Board board) {

        // preferences
        setTitle("Vorgegebenes Sudoku eingeben");
        setLayout(new BorderLayout());
        setBackground(BoardConstants.BACKGROUND);

        // init components
        JPanel panButtons = new JPanel();

        btnOk = new HoverButton("OK");
        btnOk.setBackground(BoardConstants.CELL_COLOR_NORMAL);
        btnOk.setFocusable(false);

        btnCancel = new HoverButton("Abbrechen");
        btnCancel.setBackground(BoardConstants.CELL_COLOR_NORMAL);
        btnCancel.setFocusable(false);

        panButtons.setLayout(new GridLayout(1, 2, 5, 5));
        panButtons.setBorder(new EmptyBorder(10, 10, 10, 10));
        panButtons.setBackground(BoardConstants.BACKGROUND);

        panButtons.add(btnCancel);
        panButtons.add(btnOk);

        // add components
        add(board, BorderLayout.CENTER);
        add(panButtons, BorderLayout.SOUTH);
    }

    /* --> Methods <-- */

    /**
     * Optimizes the dialog and displays it on the center of the screen.
     */
    public void makeVisible() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the button "OK"
     */
    public JButton getBtnOk() {
        return btnOk;
    }

    /**
     * @return the button "Cancel"
     */
    public JButton getBtnCancel() {
        return btnCancel;
    }
}
