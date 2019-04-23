package eventHandling.printing;

import view.Board;

import javax.swing.*;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintHandler {

    /* --> Methods <-- */

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
}
