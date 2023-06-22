package srg.test.srg.ports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import srg.ports.Position;

public class PositionTest {
   
    @Test
    public void constructorTest() {
        Position position = new Position(3, 6, 8);
        assertEquals(3, position.x);
        assertEquals(6, position.y);
        assertEquals(8, position.z);

        position = new Position(-8, 9, 0);
        assertEquals(-8, position.x);
        assertEquals(9, position.y);
        assertEquals(0, position.z);

        position = new Position(0, 0, 0);
        assertEquals(0, position.x);
        assertEquals(0, position.y);
        assertEquals(0, position.z);
    }

    @Test
    public void distanceToTest() {
        int expectedDistance;
        int actualDistance;
        Position position1 = new Position(1, 2, 3);
        Position position2 = new Position(4, 5, 6);
        // distance between (1,2,3) and (4,5,6) is 5.196 -> floor = 5
        assertEquals(5, position1.distanceTo(position2));

        Position position3 = new Position(8, 9, 10);
        assertEquals(12, position1.distanceTo(position3));
        assertEquals(12, position3.distanceTo(position1));

        Position position4 = new Position(-8, 0, 3);
        assertEquals(13, position2.distanceTo(position4));
        assertEquals(13, position4.distanceTo(position2));
    }

    @Test
    public void toStringTest() {
        Position position = new Position(1, 2, 3);
        assertEquals("(1, 2, 3)", position.toString());

        position = new Position(-7, 0, 11);
        assertEquals("(-7, 0, 11)", position.toString());

        position = new Position(0, 0, 0);
        assertEquals("(0, 0, 0)", position.toString());
    }
}
