package view.menu;

import javax.swing.*;

public class MnuFile extends JMenu {

    /* --> Fields <-- */

    private JMenuItem mniGenerate;
    private JMenuItem mniEnter;
    private JMenuItem mniRestart;
    private JMenuItem mniLoad;
    private JMenuItem mniSave;
    private JMenuItem mniSaveAs;
    private JMenuItem mniExit;

    /* --> Constructor <-- */

    /**
     * Creates the menu "File" with its submenus.
     * Does not create the event handling for those menus. This has to be done by {@link eventHandling.MenuHandler}.
     */
    public MnuFile() {
        init();
    }

    /* --> Methods <-- */

    private void init() {

        // preferences
        setText("Datei");

        // create the menu-items
        mniGenerate = new JMenuItem("Neues Sudoku generieren");
        mniEnter = new JMenuItem("Vorgegebenes Sudoku eingeben");
        mniRestart = new JMenuItem("Sudoku neustarten");
        mniLoad = new JMenuItem("Sudoku laden");
        mniSave = new JMenuItem("Sudoku speichern");
        mniSaveAs = new JMenuItem("Sudoku speichern unter ...");
        mniExit = new JMenuItem("Programm beenden");

        // add the menu-items to the menu
        add(mniGenerate);
        add(mniEnter);
        add(mniRestart);
        addSeparator();
        add(mniLoad);
        add(mniSave);
        add(mniSaveAs);
        addSeparator();
        add(mniExit);
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the menu item "Generate Sudoku"
     */
    public JMenuItem getMniGenerate() {
        return mniGenerate;
    }

    /**
     * @return the menu item "Enter given Sudoku"
     */
    public JMenuItem getMniEnter() {
        return mniEnter;
    }

    /**
     * @return the menu item "Restart Sudoku"
     */
    public JMenuItem getMniRestart() {
        return mniRestart;
    }

    /**
     * @return the menu item "Load Sudoku"
     */
    public JMenuItem getMniLoad() {
        return mniLoad;
    }

    /**
     * @return the menu item "Save Sudoku"
     */
    public JMenuItem getMniSave() {
        return mniSave;
    }

    /**
     * @return the menu item "Save Sudoku As ..."
     */
    public JMenuItem getMniSaveAs() {
        return mniSaveAs;
    }

    /**
     * @return the menu item "Exit"
     */
    public JMenuItem getMniExit() {
        return mniExit;
    }
}
