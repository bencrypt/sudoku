package view.menu;

import utils.LanguageBundle;

import javax.swing.*;
import java.util.ResourceBundle;

public class MnuFile extends JMenu {

    /* --> Internationalization <-- */

    ResourceBundle bundle = LanguageBundle.getBundle();

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
        setText(bundle.getString("File"));

        // create the menu-items
        mniGenerate = new JMenuItem(bundle.getString("CreateNewSudoku"));
        mniEnter = new JMenuItem(bundle.getString("EnterExistingSudoku"));
        mniRestart = new JMenuItem(bundle.getString("Restart"));
        mniLoad = new JMenuItem(bundle.getString("Load"));
        mniSave = new JMenuItem(bundle.getString("Save"));
        mniSaveAs = new JMenuItem(bundle.getString("SaveAs"));
        mniExit = new JMenuItem(bundle.getString("Exit"));

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
