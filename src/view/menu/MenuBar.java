package view.menu;

import model.BoardConstants;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    /* --> Fields <-- */

    // the menus on the bar
    private MnuFile mnuFile;

    /* --> Constructor <-- */

    /**
     * Creates a menu bar with all the menus and sub menus.
     * Does not create the event handling for those menus. This has to be done by {@link eventHandling.MenuHandler}.
     */
    public MenuBar() {
        init();
    }

    /* --> Methods <-- */

    /**
     * Initializes the menu bar and adds all menus to it.
     */
    private void init() {

        // preferences
        setBackground(BoardConstants.BACKGROUND);
        setBorder(null);

        // create the menus
        mnuFile = new MnuFile();

        // add the menus to the menu bar
        add(mnuFile);
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the menu "File" of the menu bar
     */
    public MnuFile getMnuFile() {
        return mnuFile;
    }
}
