package lms.grid;

import java.util.Map;

import lms.logistics.Path;
import lms.logistics.Transport;
import lms.logistics.belts.Belt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameGridTest {

    private GameGrid gameGrid;
    private int initRange = 3;

    @Before
    public void setUp() {
        gameGrid = new GameGrid(initRange);
    }

    /**
     * Test constructor with invalid range
     */
    @Test
    public void constructorInvalidRangeTest() {
        GameGrid gameGrid2;
        try {
            gameGrid2 = new GameGrid(0);
            assertFalse("Assertion failed", 0 == 0);
        } catch (IllegalArgumentException ex) {
            assertTrue("Assertion thrown", 0 == 0);
        }
    }

    /**
     * Test getRange
     */
    @Test
    public void getRangeTest() {
        assertEquals(3, gameGrid.getRange());
    }

    /**
     * Test gridSize
     */
    @Test
    public void gridSizeTest() {
        assertEquals(37, gameGrid.getGrid().size());
    }

    /**
     * Test setCoordinate
     */
    @Test
    public void setCoordinateTest() {
        //Belt belt = new Belt(3);
        GridComponent component = () -> "Test Component";
        Coordinate coordinate = new Coordinate(0, 0, 0);
        gameGrid.setCoordinate(coordinate, component);

        assertEquals(component, gameGrid.getGrid().get(coordinate));
    }

    /**
     * Test getGrid
     */
    @Test
    public void getGridTest() {
        Map<Coordinate, GridComponent> grid2;
        GameGrid gameGrid2 = new GameGrid(3);
        assertEquals(gameGrid2.getGrid(), gameGrid.getGrid());
    }

    /**
     * Test get Non Existent Coordinate
     */
    @Test
    public void getNonExistentCoordinateTest() {
        Coordinate coordinate = new Coordinate(500, 200, -300);
        assertNull(gameGrid.getGrid().get(coordinate));
    }

}
