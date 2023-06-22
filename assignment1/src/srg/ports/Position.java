package srg.ports;

/**
 * Represents a three-dimensional position in space.
 */
public class Position extends Object {
    /** x-coordinate */
    public final int x;
    /** y-coordinate */
    public final int y;
    /** z-coordinate */
    public final int z;

    /**
     * Constructor for Position class.
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     */
    public Position(int x, int y, int z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculate distance between 2 3D points.
     * @param other other point.
     * @return An integer representation of the distance between the two points, rounded down. 
     * The distance between two 3d points is: Distance = sqrt( (x2-x1)^2 + (y2-y1)^2 + (z2-z1)^2 )  
     */
    public int distanceTo(Position other) {
        return (int) Math.floor(Math.sqrt(Math.pow(this.x - other.x, 2)
            + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2)));
    }
    
    /**
     * Returns a formatted string representation of the Position.
     * @return A formatted string representation of the Position. Format must be "(x-coordinate, y-coordinate, z-coordinate)".
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
