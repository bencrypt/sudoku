package model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "cell", propOrder = {"gridX", "gridY", "value", "editable", "valid", "automaticallySolved"})
public class XmlCell {

    /* --> Fields <-- */

    // position of the cell in a Sudoku board
    private int gridX;
    private int gridY;

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
     * Constructor to create a XmlCell from the fields of an internal Cell.
     *
     * @param gridX
     * @param gridY
     * @param value
     * @param editable
     * @param valid
     * @param automaticallySolved
     */
    public XmlCell(int gridX, int gridY, int value, boolean editable, boolean valid, boolean automaticallySolved) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.value = value;
        this.editable = editable;
        this.valid = valid;
        this.automaticallySolved = automaticallySolved;
    }

    /* --> Getters and Setters <-- */

    @XmlElement(required = true)
    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    @XmlElement(required = true)
    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    @XmlElement(required = true)
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @XmlElement(required = true)
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @XmlElement(required = true)
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @XmlElement(required = true)
    public boolean isAutomaticallySolved() {
        return automaticallySolved;
    }

    public void setAutomaticallySolved(boolean automaticallySolved) {
        this.automaticallySolved = automaticallySolved;
    }
}
