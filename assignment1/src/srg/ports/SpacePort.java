package srg.ports;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic SpacePort.
 */
public class SpacePort extends Object {
    /**
     * Name of SpacePort. Each SpacePort must have a unique name.
     */
    private String name;

    /**
     * Physical position of SpacePort (3D coordinate)
     */
    private Position position;

    /**
     * Constructor for SpacePort
     * @param name The name of the SpacePort. Each SpacePort must have a unique name.
     * @param position The physical Position of the SpacePort.
     */
    public SpacePort(String name, Position position) {
        super();
        this.name = name;
        this.position = position;
    }
    
    /**
     * Returns the position of the SpacePort.
     * @return The position of the SpacePort as a Position.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a String representation of a SpacePort. Identifies the name, type of SpacePort and position.
     * @return A String containing the name, type and position of the SpacePort. 
     * Format must be "PORT: \"SpacePort name\" SpacePort type at POSITION".
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns a String representation of a SpacePort.
     * Identifies the name, type of SpacePort and position.
     * @return Returns a String representation of a SpacePort.
     * Identifies the name, type of SpacePort and position.
     */
    @Override
    public String toString() {
        return "PORT: \"" + this.name + "\" " + this.getClass().getSimpleName()
            + " at " + position.toString();
    }

    /**
     * Get the list of actions that it is possible to perform at this SpacePort. Generic SpacePorts have no actions.
     * @return A List of actions that are unique to this SpacePort as Strings. (This should be an empty List.)
     * @version 1.3 Update
     */
    public List<String> getActions() {
        return new ArrayList<String>();
    }
}
