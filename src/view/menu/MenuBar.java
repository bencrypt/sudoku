package view.menu;

import javafx.scene.control.ComboBox;
import model.BoardConstants;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {

    /* --> Fields <-- */

    // the menus on the bar
    private MnuFile mnuFile;

    // the combobox for the font size
    private JComboBox<Integer> cbxFontSize;

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

        // create and add input for font size
        createAndAddComboBoxForFontSize();
    }

    /**
     * Creates and adds the combo box and label for choosing a font size.
     */
    private void createAndAddComboBoxForFontSize() {

        // create label
        JLabel lblFontSize = new JLabel("Schriftgröße:  ");

        // create combo box with default values
        cbxFontSize = new JComboBox<>();
        cbxFontSize.setMaximumSize(new Dimension(50, 30));

        cbxFontSize.addItem(14);
        cbxFontSize.addItem(16);
        cbxFontSize.addItem(18);
        cbxFontSize.addItem(20);
        cbxFontSize.addItem(24);

        // choose initial size
        cbxFontSize.setSelectedItem(14);

        // create transparent separator to show the combo box and the label on the right side
        // of the menu bar
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(0,0,0,0));
        separator.setOrientation(JSeparator.VERTICAL);

        // add the elements
        add(separator);
        add(lblFontSize);
        add(cbxFontSize);
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the menu "File" of the menu bar
     */
    public MnuFile getMnuFile() {
        return mnuFile;
    }

    /**
     * @return the combo box for choosing the font size
     */
    public JComboBox<Integer> getCbxFontSize() {
        return cbxFontSize;
    }
}
