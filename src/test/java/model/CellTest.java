package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    /*==================================================*
     *==                   equals()                   ==*
     *==================================================*/

    @Test
    public void equals_TwoDifferentTypes_ShouldReturnFalse() {

        // create a cell and some other object
        Cell cell = new Cell(2, 2, 5, true, true, false, true);
        Object o = new Object();

        // check for equality
        assertFalse(cell.equals(o));
    }

    @Test
    public void equals_TwoEqualCells_ShouldReturnTrue() {

        // create two cells with same parameters
        Cell cellA = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB = new Cell(2, 2, 5, true, true, false, true);

        // check for equality
        assertTrue(cellA.equals(cellB));

    }

    @Test
    public void equals_TwoDifferentCells_ShouldReturnFalse() {

        // create two different cells for each parameter with different values for this parameter
        Cell cellA_param1 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param1 = new Cell(1, 2, 5, true, true, false, true);

        Cell cellA_param2 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param2 = new Cell(2, 1, 5, true, true, false, true);

        Cell cellA_param3 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param3 = new Cell(2, 2, 3, true, true, false, true);

        Cell cellA_param4 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param4 = new Cell(2, 2, 5, false, true, false, true);

        Cell cellA_param5 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param5 = new Cell(2, 2, 5, true, false, false, true);

        Cell cellA_param6 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param6 = new Cell(2, 2, 5, true, true, true, true);

        Cell cellA_param7 = new Cell(2, 2, 5, true, true, false, true);
        Cell cellB_param7 = new Cell(2, 2, 5, true, true, false, false);

        // check each cell A and B changed in only one parameter for equality
        assertFalse(cellA_param1.equals(cellB_param1));
        assertFalse(cellA_param2.equals(cellB_param2));
        assertFalse(cellA_param3.equals(cellB_param3));
        assertFalse(cellA_param4.equals(cellB_param4));
        assertFalse(cellA_param5.equals(cellB_param5));
        assertFalse(cellA_param6.equals(cellB_param6));
        assertFalse(cellA_param7.equals(cellB_param7));
    }

}
