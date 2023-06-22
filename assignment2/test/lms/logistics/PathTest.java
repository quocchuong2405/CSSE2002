package lms.logistics;

import lms.logistics.belts.Belt;
import lms.logistics.container.Producer;
import lms.logistics.container.Receiver;
import org.junit.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Test Path class
 */
public class PathTest {

    private Transport transport;
    private Path path;
    private Path path2;
    private Belt belt;
    private Producer producer;
    private Receiver receiver;

    private static class TransportTest extends Transport {

        public TransportTest(int id) {
            super(id);
        }

        public String getEncoding() {
            return Integer.toString(getId());
        }
    }


    @Before
    public void setUp() {
        transport = new TransportTest(1);
        path = new Path(transport);
        belt = new Belt(1);
        producer = new Producer(2, new Item("producer1"));
        receiver = new Receiver(3, new Item("producer1"));

    }

    /**
     * Test Null Transport
     */
    @Test
    public void pathConstructorNullTransportParameterTest() {
        try {
            path2 = new Path((Transport) null);
            assertFalse("Assertion failed", 0 == 0);
        } catch (IllegalArgumentException ex) {
            assertTrue("Assertion thrown", 0 == 0);
        }
    }

    /**
     * Test Null Path
     */
    @Test
    public void pathConstructorNullPathParameterTest() {
        try {
            path2 = new Path((Path) null);
            assertFalse("Assertion failed", 0 == 0);
        } catch (IllegalArgumentException ex) {
            assertTrue("Assertion thrown", 0 == 0);
        }
    }

    /**
     * Test null node, null previous, null next
     */
    @Test
    public void pathConstructorNullThreePathParametersTest() {
        try {
            path2 = new Path((Transport) null, (Path) null, (Path) null);
            assertFalse("Assertion failed", 0 == 0);
        } catch (IllegalArgumentException ex) {
            assertTrue("Assertion thrown", 0 == 0);
        }
    }

    /**
     * Test getNode()
     */
    @Test
    public void getNodeTest() {

        path2 = new Path(belt);
        assertEquals(belt, path2.getNode());

        assertEquals(transport, path.getNode());

    }

    /**
     * Test head()
     */
    @Test
    public void headTest() {
        assertNull(path.head().getNext());

        path2 = new Path(belt);
        Path path3 = new Path(receiver);
        path2.setNext(path3);
        path3.setPrevious(path2);
        assertEquals(belt, path3.head().getNode());

    }

    /**
     * Test tail()
     */
    @Test
    public void tailTest() {
        assertNull(path.tail().getPrevious());

        path2 = new Path(belt);
        Path path3 = new Path(receiver);
        path2.setNext(path3);
        path3.setPrevious(path2);
        assertEquals(receiver, path2.tail().getNode());
    }

    /**
     * Test toString
     */
    @Test
    public void toStringTest() {
        String expected = "START -> <TransportTest-1> -> END";
        assertEquals(expected, path.toString());

        String expected2 = "START -> <Belt-1> -> <Receiver-3> -> END";
        path2 = new Path(belt);
        Path path3 = new Path(receiver);
        path2.setNext(path3);
        path3.setPrevious(path2);
        assertEquals(expected2, path2.toString());

        path3.setNext(new Path(producer));
        String expected3 = "START -> <Belt-1> -> <Receiver-3> -> <Producer-2> -> END";
        assertEquals(expected3, path2.toString());
    }

    /**
     * Test applyAll
     */
    @Test
    public void applyAllTest() {
        Path newPath = new Path(new TransportTest(2));
        path.setNext(newPath);
        path.applyAll(Transport::tick);
        assertNull(transport.getInventory());
    }

    /**
     * test getPrevious
     */
    @Test
    public void setAndGetPreviousTest() {
        Path path3 = new Path(new TransportTest(2));
        path.setPrevious(path3);

        assertEquals(path3, path.getPrevious());
    }

    /**
     * test setter getter
     */
    @Test
    public void setAndGetNextTest() {
        Path path3 = new Path(new TransportTest(2));
        path.setNext(path3);

        assertEquals(path3, path.getNext());
    }

    /**
     * Test equals()
     */
    @Test
    public void equalsTest() {
        Belt belt1 = new Belt(2);
        Path path2 = new Path(belt1);
        assertEquals(false, path.equals(path2));

        Path path3 = new Path(belt1);
        assertEquals(true, path2.equals(path3));
    }

}


