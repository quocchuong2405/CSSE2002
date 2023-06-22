package lms.grid;

import java.util.HashMap;
import java.util.Map;

/**
 * The GameGrid is responsible for managing the state and initialisation of the game's grid. It
 * provides the Map structure to hold the coordinates of each node in the grid. It also maintains
 * the size of the grid using a range variable. The range value donates how many nodes each
 * hexagonal grid node extends to.
 *
 * @version 1.1
 * <p>
 * Summary: Initializes a grid of the game.
 * @ass2
 */
public class GameGrid {

    /**
     * range for grid
     */
    private final int range;
    /**
     * Map grid
     */
    private final Map<Coordinate, GridComponent> grid;

    /**
     * Creates a new GameGrid with the given range, stored in a Map.
     *
     * @param range The range of the grid.
     * @throws IllegalArgumentException if range <= 0
     */
    public GameGrid(int range) {
        if (range <= 0) {
            throw new IllegalArgumentException();
        }
        this.range = range;
        this.grid = generate(range);
    }

    /**
     * Helper method: Generates a grid with the given range, starting from the origin (the centre) and
     * maintaining a balanced shape for the entire mapping structure. This has been provided to
     * support you with the hexagonal coordinate logic.
     *
     * @param range The range of the map.
     * @provided
     */
    private Map<Coordinate, GridComponent> generate(int range) {
        Map<Coordinate, GridComponent> tempGrid = new HashMap<>();
        for (int q = -range; q <= range; q++) { // From negative to positive (inclusive)
            for (int r = -range; r <= range; r++) { // From negative to positive (inclusive)
                for (int s = -range; s <= range; s++) { // From negative to positive (inclusive)
                    if (q + r + s == 0) {
                        // Useful to default to error
                        tempGrid.put(new Coordinate(q, r, s), () -> "ERROR");
                    }
                }
            }
        }
        return tempGrid;
    }

    /**
     * Get a copy of the grid of the game.
     * @return Map Coordinate, GridComponent A copy of the grid of the game.
     */
    public Map<Coordinate, GridComponent> getGrid() {
        return new HashMap<>(grid);
    }

    /**
     * Get the range of the grid.
     *
     * @return int The range of the grid.
     */
    public int getRange() {
        return range;
    }

    /**
     * Set the GridComponent at the given coordinate.
     *
     * @param coordinate The coordinate of the GridComponent.
     * @param component  The GridComponent to be set.
     */
    public void setCoordinate(Coordinate coordinate, GridComponent component) {
        grid.put(coordinate, component);
    }
}
