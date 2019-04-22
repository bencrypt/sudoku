package eventHandling;

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

    /* --> Methods <-- */

    /*
     * Save and Save As
     */

    public static void exportSudokuIntoXml(Sudoku toExport, boolean saveIntoCurrentFile) {

        // choose the file in which the Sudoku should be exported
        // --> may be the current file if wished so and valid
        File destination = currentFile;
        if (!saveIntoCurrentFile || currentFile == null) {
            destination = chooseFile("Exportieren");
        }

        // add the ending ".suk" if needed
        if (destination != null && !destination.getName().endsWith(".suk")) {
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

        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Die Datei konnte nicht exportiert werden.",
                                            "Fehler", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /*
     * Load
     */

    public static Sudoku importSudokuFromXml() {

        // choose the file from which the Sudoku should be imported
        File source = chooseFile("Importieren");

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
            e.printStackTrace();
        }

        // convert the imported XmlSudoku to an internal Sudoku
        Cell[][] internalBoard = new Cell[9][9];
        xmlSudoku.getBoard().forEach(xmlCell -> {
            internalBoard[xmlCell.getGridX()][xmlCell.getGridY()] =
                    new Cell(xmlCell.getGridX(), xmlCell.getGridY(), xmlCell.getValue(), xmlCell.isEditable(),
                             xmlCell.isValid(), xmlCell.isAutomaticallySolved());
        });

        Sudoku sudoku = new Sudoku(internalBoard);

        // return the converted Sudoku
        return sudoku;
    }

    /*
     * Helping method for file choosing
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
}
