package model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "cell", propOrder = {"row", "column", "value", "editable", "valid", "automaticallySolved"})
public class XmlCell {

    /* --> Fields <-- */

    // position of the cell in a Sudoku board
    private int row;
    private int column;

    // value of the cell (1 to 9, 0 if empty)
    private int value;

    // status of the cell
    private boolean editable;
    private boolean valid;
    private boolean automaticallySolved;

    /* --> Constructor <-- */

    /**
     * Empty constructor for easy XML-converting.
     */
    public XmlCell() { }

    /**
     * Constructor to create a XmlCell from the fields of an internal Cell. Needed to import and export a Sudoku
     * from a XML-file.
     *
     * @param row
     *      the row of the Cell on the Sudoku board
     * @param column
     *      the column of the Cell on the Sudoku board
     * @param value
     *      the value of the Cell
     * @param editable
     *      true when the Cell is editable, else false (when predefined)
     * @param valid
     *      true when the Cell is valid, else false (e.g. when duplicate)
     * @param automaticallySolved
     *      true when the Cell was automatically solved, else false
     */
    public XmlCell(int row, int column, int value, boolean editable, boolean valid, boolean automaticallySolved) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.editable = editable;
        this.valid = valid;
        this.automaticallySolved = automaticallySolved;
    }

    /* --> Getters and Setters <-- */

    /**
     * @return the row of the Cell
     */
    @XmlElement(required = true)
    public int getRow() {
        return row;
    }

    /**
     * @param row the row of the Cell
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the column of the Cell
     */
    @XmlElement(required = true)
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column of the Cell
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the value of the Cell
     */
    @XmlElement(required = true)
    public int getValue() {
        return value;
    }

    /**
     * @param value the value of the Cell
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return true when the Cell is editable, else false
     */
    @XmlElement(required = true)
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable true for an editable Cell, else false
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return true when the Cell is valid, else false
     */
    @XmlElement(required = true)
    public boolean isValid() {
        return valid;
    }

    /**
     * @param valid true for a valid Cell, else false
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return true when the Cell is automatically solved, else false
     */
    @XmlElement(required = true)
    public boolean isAutomaticallySolved() {
        return automaticallySolved;
    }

    /**
     * @param automaticallySolved true for an automatically solved Cell, else false
     */
    public void setAutomaticallySolved(boolean automaticallySolved) {
        this.automaticallySolved = automaticallySolved;
    }
}
