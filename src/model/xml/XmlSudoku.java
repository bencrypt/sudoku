package model.xml;

import model.Cell;
import model.Sudoku;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="Sudoku")
@XmlType(propOrder = {"board"})
public class XmlSudoku {

    /* --> Fields <-- */

    private List<XmlCell> board;

    /* --> Constructor <-- */

    /**
     * Empty constructor for easy XML-converting.
     */
    public XmlSudoku() { }

    /**
     * Constructor to create a XmlSudoku from an internal Sudoku.
     * @param sudoku
     */
    public XmlSudoku(Sudoku sudoku) {

        // add the Cells from the Sudoku as XmlCells to the board list
        board = new ArrayList<>(81);
        for (int row = 0; row < sudoku.getBoard().length; row++) {
            for (int col = 0; col < sudoku.getBoard().length; col++) {
                Cell c = sudoku.getBoard()[row][col];
                board.add(new XmlCell(c.getGridX(), c.getGridY(), c.getValue(), c.isEditable(), c.isValid(),
                                      c.isAutomaticallySolved()));
            }
        }
    }

    /* --> Getters and Setters <-- */

    @XmlElementWrapper(name = "board", required = true)
    @XmlElement(name="cell")
    public List<XmlCell> getBoard() {
        return board;
    }

    public void setBoard(List<XmlCell> board) {
        this.board = board;
    }
}
