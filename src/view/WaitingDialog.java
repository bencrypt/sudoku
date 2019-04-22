package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WaitingDialog extends JDialog {

    public WaitingDialog() {
        init();
    }

    private void init() {

        // preferences
        setLayout(new BorderLayout());
        setModal(true);
        setResizable(false);

        // add label with waiting message
        JLabel lblWaiting = new JLabel("Bitte warten ...");
        lblWaiting.setHorizontalTextPosition(JLabel.CENTER);
        lblWaiting.setVerticalTextPosition(JLabel.CENTER);
        lblWaiting.setBorder(new EmptyBorder(30, 30, 30, 30));

        add(lblWaiting);
    }

    public void makeVisible() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
