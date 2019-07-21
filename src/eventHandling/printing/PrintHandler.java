package eventHandling.printing;

import model.Sudoku;
import view.Board;

import javax.swing.*;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

public class PrintHandler {

    /* --> Methods <-- */

    /**
     * Prints the given Board if the printing procedure is not cancelled. Therefore shows a print dialog and
     * creates an instance of {@link BoardPrinter}.
     *
     * @param toPrint
     *      the Board to be printed
     */
    public static void printBoard(Board toPrint) {

        // create a printer and a printer job
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Sudoku");

        // create the Board to be printed
        BoardPrinter boardPrinter = new BoardPrinter(toPrint);
        printerJob.setPrintable(boardPrinter);

        // set page range from 1 to 1
 //       Book book = new Book();
 //       book.append(boardPrinter, printerJob.defaultPage());
 //       printerJob.setPageable(book);

        // execute the printer job
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {

                // ignore aborting the printer job
                if (e instanceof PrinterAbortException) {
                    return;
                }

                // show message when error
                JOptionPane.showMessageDialog(null, "Das Sudoku konnte nicht gedruckt werden.",
                                              "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Prints the multiple Boards if the printing procedure is not cancelled. Therefore shows a print dialog and
     * creates an instance of {@link BoardPrinter}.
     *
     */
    public static void printMultiple(List<Board> toPrint) {

        // create a printer and a printer job
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Sudoku");

        // create the Board to be printed
        BoardPrinter boardPrinter = new BoardPrinter(toPrint);
        printerJob.setPrintable(boardPrinter);

        // execute the printer job
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {

                // ignore aborting the printer job
                if (e instanceof PrinterAbortException) {
                    return;
                }

                // show message when error
                JOptionPane.showMessageDialog(null, "Die Sudokus konnte nicht gedruckt werden.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
