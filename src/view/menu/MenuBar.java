package view.menu;

import model.BoardConstants;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    /* --> Fields <-- */

    private MnuFile mnuFile;

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

    public MnuFile getMnuFile() {
        return mnuFile;
    }
}
