package eventHandling.printing;

import view.Board;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class BoardPrinter implements Printable {

    private Board toPrint;

    public BoardPrinter(Board toPrint) {
        this.toPrint = toPrint;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        // only 1 page should be printed
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        // get the original size
        double originalWidth = toPrint.getPreferredSize().getWidth();
        double originalHeight = toPrint.getPreferredSize().getHeight();

        // get the size of the printable area
        double printWidth = pf.getImageableWidth();
        double printHeight = pf.getImageableHeight();

        // scale down the component (same X as Y scaling) if needed
        double scale = Math.min(printWidth / originalWidth, printHeight / originalHeight);
        if (scale > 1) {
            scale = 1;
        }

        // control output
//        System.out.println("Original: " + originalWidth + "|" + originalHeight);
//        System.out.println("Drucken: " + printWidth + "|" + printHeight);
//        System.out.println("Skalierung: " + scale);

        // place the component in the center of the page
        double printStartX = pf.getImageableX() + Math.abs(printWidth - originalWidth*scale)/2;
        double printStartY = pf.getImageableY() + Math.abs(printHeight - originalHeight*scale)/2;

        // create the Graphics2D object in which the content to be printed is drawn
        Graphics2D g2d = (Graphics2D) g.create();

        // set up the new Graphics
        g2d.translate(printStartX, printStartY);
        g2d.scale(scale, scale);

        // print the component into the Graphics
        toPrint.printAll(g2d);

        // dispose the Graphics2D object to return memory
        g2d.dispose();

        // return that the page should be printed
        return Printable.PAGE_EXISTS;
    }
}
