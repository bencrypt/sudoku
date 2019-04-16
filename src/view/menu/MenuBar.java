package view.menu;

import model.BoardConstants;
import view.menu.MnuFile;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {

    /* --> Fields <-- */

    private JMenu mnuFile;

    /* --> Constructor <-- */

    public MenuBar() {
        init();
    }

    /* --> Methods <-- */

    private void init() {

        // preferences
        setBackground(BoardConstants.BACKGROUND);

        // create the menus
        mnuFile = new MnuFile();

        // add the menus to the menu bar
        add(mnuFile);
    }

    /* --> Getters and Setters <-- */

    public JMenu getMnuFile() {
        return mnuFile;
    }
}
