package view.menu;

import javax.swing.*;

public class MnuPrint extends JMenu {

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
        setText("Drucken");

        // create the menu-items
        mniPrintCurrent = new JMenuItem("Drucke aktuelles Sudoku");
        mniPrintMultiple = new JMenuItem("Drucke mehrere Sudokus");

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
