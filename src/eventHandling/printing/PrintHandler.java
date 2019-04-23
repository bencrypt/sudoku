package eventHandling.printing;

import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import model.Sudoku;
import view.Board;
import view.Window;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.print.*;

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
