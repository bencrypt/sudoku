package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WaitingDialog extends JDialog {

    /* --> Constructor <-- */

    /**
     * Creates a modal waiting dialog which has to be showed by {@link #makeVisible()} and hidden by {@link #dispose()}
     * from extern.
     */
    public WaitingDialog() {
        init();
    }

    /* --> Methods <-- */

    /**
     * Initializes the waiting dialog.
     */
    private void init() {

        // preferences
        setLayout(new BorderLayout());
        setModal(true);
        setResizable(false);

        // create and add label with waiting message
        JLabel lblWaiting = new JLabel("Bitte warten ...");
        lblWaiting.setHorizontalTextPosition(JLabel.CENTER);
        lblWaiting.setVerticalTextPosition(JLabel.CENTER);
        lblWaiting.setBorder(new EmptyBorder(30, 30, 30, 30));

        add(lblWaiting);
    }

    /**
     * Optimizes the waiting dialog and displays it on the center of the screen. To hide the dialog, the method
     * {@link #dispose()} has to be called.
     */
    public void makeVisible() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
