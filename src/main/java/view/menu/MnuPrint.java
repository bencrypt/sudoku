package view.menu;

import utils.LanguageBundle;

import javax.swing.*;
import java.util.ResourceBundle;

public class MnuPrint extends JMenu {

    /* --> Internationalization <-- */

    private ResourceBundle bundle = LanguageBundle.getBundle();

    /* --> Fields <-- */

    private JMenuItem mniPrintCurrent;
    private JMenuItem mniPrintMultiple;

    /* --> Constructor <-- */

    /**
     * Creates the menu "Print" with its submenus.
     * Does not create the event handling for those menus. This has to be done by {@link eventHandling.MenuHandler}.
     */
    public MnuPrint() {
        init();
    }

    /* --> Methods <-- */

    private void init() {

        // preferences
        setText(bundle.getString("Print"));

        // create the menu-items
        mniPrintCurrent = new JMenuItem(bundle.getString("PrintCurrentSudoku"));
        mniPrintMultiple = new JMenuItem(bundle.getString("PrintMultipleSudokus"));

        // add the menu-items to the menu
        add(mniPrintCurrent);
        add(mniPrintMultiple);
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the menu item "Print current Sudoku"
     */
    public JMenuItem getMniPrintCurrent() {
        return mniPrintCurrent;
    }

    /**
     * @return the menu item "Print multiple Sudokus"
     */
    public JMenuItem getMniPrintMultiple() {
        return mniPrintMultiple;
    }
}
