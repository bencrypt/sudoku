package view.menu;

import javax.swing.*;

public class MnuFile extends JMenu {

    /* --> Fields <-- */

    private JMenuItem mniNew;
    private JMenuItem mniRestart;
    private JMenuItem mniLoad;
    private JMenuItem mniSave;
    private JMenuItem mniSaveAs;
    private JMenuItem mniPrint;
    private JMenuItem mniExit;

    /* --> Constructor <-- */

    public MnuFile() {
        init();
    }

    /* --> Methods <-- */

    private void init() {

        // preferences
        setText("Datei");

        // create the menu-items
        mniNew = new JMenuItem("Neues Sudoku erstellen");
        mniRestart = new JMenuItem("Sudoku neustarten");
        mniLoad = new JMenuItem("Sudoku laden");
        mniSave = new JMenuItem("Sudoku speichern");
        mniSaveAs = new JMenuItem("Sudoku speichern unter ...");
        mniPrint = new JMenuItem("Sudoku ausdrucken");
        mniExit = new JMenuItem("Programm beenden");

        // add the menu-items to the menu
        add(mniNew);
        add(mniRestart);
        addSeparator();
        add(mniLoad);
        add(mniSave);
        add(mniSaveAs);
        addSeparator();
        add(mniPrint);
        addSeparator();
        add(mniExit);
    }

    /* --> Getters and Setters <-- */

    public JMenuItem getMniNew() {
        return mniNew;
    }

    public JMenuItem getMniRestart() {
        return mniRestart;
    }

    public JMenuItem getMniLoad() {
        return mniLoad;
    }

    public JMenuItem getMniSave() {
        return mniSave;
    }

    public JMenuItem getMniSaveAs() {
        return mniSaveAs;
    }

    public JMenuItem getMniPrint() {
        return mniPrint;
    }

    public JMenuItem getMniExit() {
        return mniExit;
    }
}
