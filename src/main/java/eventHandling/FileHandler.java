package eventHandling;

import console.SudokuPrinter;
import model.Cell;
import model.Sudoku;
import model.xml.XmlCell;
import model.xml.XmlSudoku;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class FileHandler {

    /* --> Fields <-- */

    private static File currentFile;
    private static Sudoku lastSavedSudoku;

    /* --> Methods <-- */

    /*
     * Save and Save As
     */

    /**
     * Exports the given Sudoku into a XML under a path the user has to select. This method could be used for both the
     * options "save as ..." and "save" by setting the given boolean parameter. When true and a XML is already given
     * (e.g. when an previous opened XML should be saved again), the Sudoku will be directly saved into this current
     * file.
     *
     * @param toExport
     *      the Sudoku to be exported
     * @param saveIntoCurrentFile
     *      true when directly saving (if possible), else false to explicitely ask for a saving path
     */
    public static void exportSudokuIntoXml(Sudoku toExport, boolean saveIntoCurrentFile) {

        // choose the file in which the Sudoku should be exported
        // --> may be the current file if wished so and valid
        File destination = currentFile;
        if (!saveIntoCurrentFile || currentFile == null) {
            destination = chooseFile("Exportieren");
        }

        // if no file is selected, stop the method
        if (destination == null) {
            return;
        }

        // add the ending ".suk" if needed
        if (!destination.getName().endsWith(".suk")) {
            destination = new File(destination + ".suk");
        }

        // convert the internal Sudoku to a XmlSudoku
        XmlSudoku xmlSudoku = new XmlSudoku(toExport);

        // export into the choosen file
        try {
            // helping classes
            JAXBContext jc = JAXBContext.newInstance(XmlSudoku.class, XmlCell.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // write in XML
            m.marshal(xmlSudoku, destination);

            // set the destination file as current file
            currentFile = destination;

            // set a copy of the saved Sudoku as the last saved Sudoku
            // --> can be compared with later states for checking if the current state is saved or not when closing
            lastSavedSudoku = toExport.copy();
            SudokuPrinter.showOnConsole(lastSavedSudoku, "lastSavedSudoku");

        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Die Datei konnte nicht exportiert werden.",
                                            "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Load
     */

    /**
     * Imports a Sudoku from a XML selected by the user and returns this Sudoku. If there is a wrong file selected
     * or the importing dialog is cancelled, there will be returned null.
     *
     * @return
     *      the imported Sudoku or null when cancelled or at errors
     */
    public static Sudoku importSudokuFromXml() {

        // choose the file from which the Sudoku should be imported
        File source = chooseFile("Importieren");

        // import from the choosen file and return the result (Sudoku or null)
        return importSudokuFromXml(source);
    }

    /**
     * Directly imports a Sudoku from the given file. It is assumed, that the file is correctly and existing.
     *
     * @param source
     *      the file from which the Sudoku is imported
     * @return
     *      the imported Sudoku or null at errors
     */
    public static Sudoku importSudokuFromXml(File source) {

        // import from the choosen file
        XmlSudoku xmlSudoku = null;
        try {
            // helping classes
            JAXBContext jc = JAXBContext.newInstance(XmlSudoku.class, XmlCell.class);
            Unmarshaller um = jc.createUnmarshaller();

            // read XML
            xmlSudoku = (XmlSudoku) um.unmarshal(source);

            // set the destination file as current file
            currentFile = source;

        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Die Datei konnte nicht importiert werden.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // convert the imported XmlSudoku to an internal Sudoku
        Cell[][] internalBoard = new Cell[9][9];
        xmlSudoku.getBoard().forEach(xmlCell -> {
            internalBoard[xmlCell.getRow()][xmlCell.getColumn()] =
                    new Cell(xmlCell.getRow(), xmlCell.getColumn(), xmlCell.getValue(), xmlCell.isEditable(),
                            xmlCell.isValid(), xmlCell.isAutomaticallySolved(), xmlCell.isSameAsSolution());
        });

        Sudoku sudoku = new Sudoku(internalBoard);

        // update the Sudoku's Cell state
        // --> sameAsSolution is no must-have attribute
        sudoku.checkAndMarkCellsNotSameAsSolution();

        // set a copy of the imported Sudoku as the last saved Sudoku
        // --> can be compared with later states for checking if the current state is saved or not when closing
        lastSavedSudoku = sudoku.copy();
        SudokuPrinter.showOnConsole(lastSavedSudoku, "lastSavedSudoku");

        // return the converted Sudoku
        return sudoku;
    }

    /*
     * Helping method for file choosing
     */

    /**
     * Opens a file choosing dialog with the given text for the approve button (e.g. "Save" or "Export") and only
     * ".suk"-files and directories showed.
     * Returns the selected file or null when the dialog is cancelled or an error occurs.
     *
     * @param approveButtonText
     *      the button for the approve button
     * @return
     *      the selected File or null when cancelled or error
     */
    private static File chooseFile(String approveButtonText) {

        // create a file chooser for ".suk"-files
        JFileChooser fc = new JFileChooser(new File("."));
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".suk") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Sudoku-Dateien";
            }
        });

        // show the file chooser
        // --> if cancelled, the method returns
        int option = fc.showDialog(null, approveButtonText);
        File source = null;

        if (option == JFileChooser.APPROVE_OPTION) {
            source = fc.getSelectedFile();
        }

        // return the choosed file, may be null
        return source;
    }

    /*
     * Compare a given Sudoku with the last saved Sudoku
     * --> e.g. used when closing the Window
     */

    /**
     * Returns true when the given Sudoku equals the last saved Sudoku, else false.
     *
     * @param toCompare
     *      the Sudoku to compare with the last saved Sudoku
     * @return
     *      true when equals, else false
     */
    public static boolean equalsLastSavedSudoku(Sudoku toCompare) {
        return toCompare.equals(lastSavedSudoku);
    }

    /* --> Getters and Setters <-- */

    public static void setLastSavedSudoku(Sudoku lastSavedSudoku) {
        FileHandler.lastSavedSudoku = lastSavedSudoku;
    }
}
