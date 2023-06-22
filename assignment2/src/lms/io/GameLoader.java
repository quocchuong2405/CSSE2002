package lms.io;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

import lms.exceptions.FileFormatException;
import lms.grid.Coordinate;
import lms.grid.GameGrid;
import lms.grid.GridComponent;
import lms.logistics.Item;
import lms.logistics.Path;
import lms.logistics.belts.Belt;
import lms.logistics.container.Producer;
import lms.logistics.container.Receiver;

/**
 * This class is responsible for loading (reading and parsing) a text file containing details
 * required for the creation of a simulated factory represented in the form of a graphical hexagonal
 * grid. The factory consists of hexagonal nodes (as seen in a beehive) which are linked together to
 * form a complete and symmetrical grid. Each node within this grid provides a depiction of one or
 * more simulated production line(s) nodes. A production line consists of one Producer, one or more
 * Receiver(s) and numerous connected nodes, called Belts. The Producer nodes produce Items while
 * the Receiver Nodes consume them. In between each pair (or more) of a Producer and Receiver, are
 * conveyor belt nodes. Each belt node transports the Items produced by the Producer towards the
 * direction of the connected Receiver(s). Each production line, can have one or more Producer, and
 * one or more Receiver.
 * <p>
 * For example (Where p is a Producer, c is a Receiver, --- are belts and * represents a splitter):
 * p----*-----c \____c
 * <p>
 * In the text file, each hexagonal grid node is encoded to represent a specific type of node.
 */
public class GameLoader {

    /**
     * Map stores nodes
     */
    private static Map<Integer, GridComponent> nodeMap = new HashMap<>();

    /**
     * default constructor
     */
    public GameLoader() {
    }

    /**
     * "o" Empty white hexagon, without any connections "w"
     * A black unusable hexagon (a wall) "r" A
     * Receiver node where belts lead towards it from a Producer "p" A Producer node,
     * which produce
     * Items that lead down the belts to the Receivers "b" A Belt node,
     * which connects Producers to Receivers and moves items
     * <p>
     * The load method provides an access point to load
     * and parse the grid map text file. When reading
     * the input grid: p = insert a Producer
     * at this position of the grid b = insert a Belt node at
     * this position of the grid r = insert a Receiver
     * at this position of the grid s = insert a
     * Splitter at this position of the grid * (CSSE7023 students only) o
     * or w = insert a lambda into
     * the grid that returns the appropriate character code
     *
     * @param reader the reader to read from
     * @return the game grid loaded from the reader file
     * @throws IOException if there is an error reading from the reader
     * @throws FileFormatException if the file is not in the correct format
     * @throws NullPointerException if reader is null
     */
    public static GameGrid load(Reader reader)
            throws IOException, FileFormatException {
        // current line in input.txt
        String line;

        // to determine size of grid. The size of a grid will be equal to range * 2 + 1.
        int range;

        // number of producers
        int numOfProducer;

        // number of receivers
        int numOfReceiver;

        // size of grid
        int gridSize;

        // current order of item
        int itemId = 0;

        if (reader == null) {
            throw new NullPointerException();
        }
        BufferedReader bfreader = new BufferedReader(reader);

        // Section 1
        line = bfreader.readLine();

        try {
            range = Integer.parseInt(line);
            gridSize = range * 2 + 1;
        } catch (Exception e) {
            throw new FileFormatException();
        }

        splitLineExceptionCheck(bfreader.readLine());

        // Section 2
        // get number of producers && number of receivers
        line = bfreader.readLine();
        try {
            numOfProducer = Integer.parseInt(line);
        } catch (Exception e) {
            throw new FileFormatException();
        }

        line = bfreader.readLine();
        try {
            numOfReceiver = Integer.parseInt(line);
        } catch (Exception e) {
            throw new FileFormatException();
        }

        splitLineExceptionCheck(bfreader.readLine());

        // Section 3,4   producer/receiver keys
        Queue<String> producerItemKeys = new LinkedList<>();
        Queue<String> receiverItemKeys = new LinkedList<>();

        for (int i = 0; i < numOfProducer; i++) {
            try {
                producerItemKeys.add(bfreader.readLine());
            } catch (Exception e) {
                throw new FileFormatException();
            }
        }

        splitLineExceptionCheck(bfreader.readLine());

        for (int i = 0; i < numOfReceiver; i++) {
            try {
                receiverItemKeys.add(bfreader.readLine());
            } catch (Exception e) {
                throw new FileFormatException();
            }

        }

        splitLineExceptionCheck(bfreader.readLine());

        // Section 5
        GameGrid gameGrid = new GameGrid(range);

        Coordinate topLeftCoord = new Coordinate();
        for (int i = 0; i < range; i++) {
            topLeftCoord = topLeftCoord.getTopLeft();
        }

        //System.out.println("range: " + range);
        Coordinate firstCoordOfRow = topLeftCoord;
        Coordinate currentCoord = firstCoordOfRow;

        for (int i = 0; i < range; i++) {
            //int numNodes = range + (range + 1) * (Math.abs(i));
            //System.out.println("Number of nodes in row " + i + ": " + numNodes);
            line = bfreader.readLine().trim();
            String[] nodeTypes = line.split("\\s+");
            int spaceCount = 0;
            // traverse columns
            for (int j = 0; j < nodeTypes.length; j++) {
                String nodeType = nodeTypes[j];

                if (!nodeType.equals(" ")) {
                    spaceCount = 0;
                    if (nodeType.equals("p") || nodeType.equals("r")
                            || nodeType.equals("b")) {
                        itemId++;
                    }
                    createNodeHelper(itemId, gameGrid, currentCoord,
                            nodeType, producerItemKeys, receiverItemKeys);
                    currentCoord = currentCoord.getRight();
                }

            }
            firstCoordOfRow = firstCoordOfRow.getBottomLeft();
            currentCoord = firstCoordOfRow;
        }

        for (int i = 0; i <= range; i++) {
            line = bfreader.readLine().trim();
            String[] nodeTypes = line.split("\\s+");
            // traverse columns
            for (String nodeType : nodeTypes) {
                if (nodeType.equals("p") || nodeType.equals("r")
                        || nodeType.equals("b")) {
                    itemId++;
                }
                createNodeHelper(itemId, gameGrid, currentCoord,
                        nodeType, producerItemKeys, receiverItemKeys);
                currentCoord = currentCoord.getRight();
            }
            firstCoordOfRow = firstCoordOfRow.getBottomRight();
            currentCoord = firstCoordOfRow;

        }

        splitLineExceptionCheck(bfreader.readLine());

        // SECTION 6
        String beltPattern = "^\\d+-(\\d*)?(,-?\\d*)?$";
        String producerPattern = "^\\d+-\\d+$";
        String receiverPattern = "^\\d+-\\d+$";
        //line = bfreader.readLine();

        while ((line = bfreader.readLine()) != null) {
            int currentNodeId = -1;
            int prevNodeId = -1;
            int nextNodeId = -1;

            if (Pattern.matches(producerPattern, line)) {
                String[] parts = line.split("-");
                currentNodeId = Integer.parseInt(parts[0]);
                nextNodeId = parts.length > 1 && !parts[1].isEmpty()
                        ? Integer.parseInt(parts[1]) : -1;



            } else if (Pattern.matches(beltPattern, line)) {
                String[] parts = line.split("-|,");
                currentNodeId = Integer.parseInt(parts[0]);
                prevNodeId = parts.length > 1 && !parts[1].isEmpty()
                        ? Integer.parseInt(parts[1]) : -1;
                nextNodeId = parts.length > 2 && !parts[2].isEmpty()
                        ? Integer.parseInt(parts[2]) : -1;

                if (prevNodeId == -1 && nextNodeId == -1) {
                    throw new FileFormatException();
                }
                for ( Map.Entry<Integer, GridComponent> entry : nodeMap.entrySet()) {
                    int key = entry.getKey();
                    GridComponent component = entry.getValue();
                    if (key == currentNodeId) {
                        if (component.getClass().getSimpleName().equals("Producer") || component.getClass().getSimpleName().equals("Receiver")) {
                            throw new FileFormatException();
                        }
                    }
                }

            }
        }

        return gameGrid;
    }

    private static void createNodeHelper(int itemId, GameGrid gameGrid,
                                         Coordinate currentCoord, String nodeType,
                                         Queue<String> producerKeys,
                                         Queue<String> receiverKeys)
            throws FileFormatException {

        if (nodeType.equals("p")) {
            Producer producer = new Producer(itemId, new Item(producerKeys.remove()));
            gameGrid.setCoordinate(currentCoord, producer);
            nodeMap.put(itemId, producer);

        } else if (nodeType.equals("r")) {
            Receiver receiver = new Receiver(itemId, new Item(receiverKeys.remove()));
            gameGrid.setCoordinate(currentCoord, receiver);
            nodeMap.put(itemId, receiver);
        } else if (nodeType.equals("b")) {
            Belt belt = new Belt(itemId);
            gameGrid.setCoordinate(currentCoord, belt);
            nodeMap.put(itemId, belt);

        } else if (nodeType.equals("w")) {
            GridComponent gridComponent = () -> "w";
            gameGrid.setCoordinate(currentCoord, gridComponent);
        } else if (nodeType.equals("o")) {
            GridComponent gridComponent = () -> "o";
            gameGrid.setCoordinate(currentCoord, gridComponent);
        } else {
            throw new FileFormatException();
        }
    }

    private static void splitLineExceptionCheck(String line)
            throws FileFormatException {
        if (!line.startsWith("_____")) {
            throw new FileFormatException();
        }
    }


}
