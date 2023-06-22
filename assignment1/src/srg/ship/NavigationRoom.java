package srg.ship;

import java.util.ArrayList;
import java.util.List;

import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.ports.ShipYard;
import srg.ports.SpacePort;
import srg.ports.Store;
import srg.resources.FuelGrade;

/**
 * Child class of Room which represents a NavigationRoom in a {@link Ship}.
 * NavigationRooms track the Ship's current Port and the galaxy map which lists all {@link SpacePort}s in the galaxy.
 * NavigationRooms also track which {@link SpacePort}s the {@link Ship} could fly to (shorter distance) or jump to (longer distance).
 */
public class NavigationRoom extends Room {

    /** The galaxy map which lists all SpacePorts in the galaxy */
    public List<SpacePort> galaxyMap;

    /** Current SpaceSport*/
    private SpacePort currentPort;

    /** Maximum flyable distance */
    private int maximumFlyDistance;

    /** Maximum jumpable distance */
    private int maximumJumpDistance;

    /**
     * Constructor which creates a NavigationRoom at a specified RoomTier.
     * The NavigationRoom's current port should be set to the 0th SpacePort in the galaxyMap.
     * @param roomTier The quality of the NavigationRoom's tier.
     * @param galaxyMap A List of all SpacePorts in the galaxy.
     */
    public NavigationRoom(RoomTier roomTier, List<SpacePort> galaxyMap) {
        super(roomTier);
        this.galaxyMap = new ArrayList<>(galaxyMap);

        if (roomTier == RoomTier.BASIC) {
            maximumFlyDistance = 200;
            maximumJumpDistance = 500;
        } else if (roomTier == RoomTier.AVERAGE) {
            maximumFlyDistance = 400;
            maximumJumpDistance = 750;
        } else if (roomTier == RoomTier.PRIME) {
            maximumFlyDistance = 600;
            maximumJumpDistance = 1000;
        }
        currentPort = galaxyMap.get(0);
    }

    /**
     * Returns the current port.
     *
     * @return The current SpacePort.
     */
    public SpacePort getCurrentPort() {
        return this.currentPort;
    }

    /**
     * Returns a List of SpacePorts the Ship could fly to.
     * This List should not include the current SpacePort.
     *
     * @return A List of Spaceports whose distance is less than or equal to the maximum flying distance.
     */
    public List<SpacePort> getPortsInFlyRange() {
        List<SpacePort> spacePorts = new ArrayList<>();
        for (SpacePort spacePort : galaxyMap) {
            long distance = currentPort.getPosition().distanceTo(spacePort.getPosition());
            if (distance <= maximumFlyDistance) {
                if (!currentPort.getName().equals(spacePort.getName())) {
                    spacePorts.add(spacePort);
                }
            }
        }
        return spacePorts;
    }

    /**
     * Returns the Ship's maximum flying distance.
     *
     * @return Maximum flying distance as an int. This is determined by the quality of the NavigationRoom, as defined by
     *         the RoomTier enum. BASIC NavigationRooms can fly 200 units. AVERAGE NavigationRooms can fly 400 units. PRIME
     *         NavigationRooms can fly 600 units.
     */
    public int getMaximumFlyDistance() {
        return this.maximumFlyDistance;
    }

    /**
     * Returns a List of SpacePorts the Ship could jump to.
     *
     * @return A List of Spaceports whose distance is greater than the maximum flying distance, but less than or equal
     *         to the maximum jumping distance.
     */
    public List<SpacePort> getPortsInJumpRange() {
        List<SpacePort> spacePorts = new ArrayList<>();
        for (SpacePort spacePort : galaxyMap) {
            long distance = currentPort.getPosition().distanceTo(spacePort.getPosition());
            if (distance <= maximumJumpDistance && distance > maximumFlyDistance) {
                if (!currentPort.getName().equals(spacePort.getName())) {
                    spacePorts.add(spacePort);
                }
            }
        }
        return spacePorts;
    }

    /**
     * Returns the Ship's maximum jump distance.
     *
     * @return Maximum jump distance as an int. This is determined by the quality of the NavigationRoom, as defined by
     *         the RoomTier enum. BASIC NavigationRooms can jump 500 units. AVERAGE NavigationRooms can jump 750 units.
     *         PRIME NavigationRooms can jump 1000 units.
     */
    public int getMaximumJumpDistance() {
        return this.maximumJumpDistance;
    }

    /**
     * Returns the amount of fuel required to travel to another SpacePort.
     * @param port The SpacePort to travel to
     * @return The amount of fuel required to travel to \port.
     * This is equal to the distance between current Port and \port.
     */
    public int getFuelNeeded(SpacePort port) {
        return this.currentPort.getPosition().distanceTo(port.getPosition());
    }

    /**
     * Checks if the current port is a ShipYard.
     * @return The current port as a ShipYard, if the current port is a ShipYard; null otherwise.
     */
    public ShipYard getShipYard() {
        if (currentPort instanceof ShipYard) {
            return (ShipYard) currentPort;
        }
        return null;
    }

    /**
     * Checks if the current port is a Store
     * @return The current port as a Store, if the current port is a Store; null otherwise.
     */
    public Store getStore() {
        //if (currentPort.getClass().getName().equals("Store")) {
        //    return (Store) currentPort;
        //}
        if (currentPort instanceof Store) {
            return (Store) currentPort;
        }
        return null;
    }

    /**
     * Flies the Ship to the specified SpacePort if it is in range, and the ship is able to support the journey.
     * Ship's Rooms take damage at their damage rate when flying.
     * Flying requires TRITIUM fuel. TRITIUM is used up at a rate of one unit per unit of distance travelled.
     * @param portName A String representation of the target SpacePort's unique name.
     * @param cargoHold The Ship's CargoHold
     * @throws InsufficientResourcesException if the CargoHold is broken;
     * if the NavigationRoom is broken;
     * or if there is insufficient fuel of the relevant type in the CargoHold.
     * @throws NoPathException  If the named SpacePort cannot be found or is out of range.
     */
    public void flyTo(String portName, CargoHold cargoHold)
        throws InsufficientResourcesException, NoPathException {
        // handled the NoPathException if the portName cannot be found inside the function.
        SpacePort portDestination = getSpacePortFromName(portName);

        // need to check insufficient fuel
        if (cargoHold.isBroken()) {
            throw new InsufficientResourcesException("CargoHold is broken");
        }

        if (this.isBroken()) {
            throw new InsufficientResourcesException("NavigationRoom is broken");
        }

        long distance = currentPort.getPosition().distanceTo(portDestination.getPosition());
        // unable to fly because of out of range
        if (distance > maximumFlyDistance) {
            throw new NoPathException("Out of range to fly");
        }

        // TRITIUM is used up at a rate of one unit per unit of distance travelled.
        int amountTri = currentPort.getPosition().distanceTo(portDestination.getPosition());

        // do not have enough resource to fly
        if (amountTri > cargoHold.getTotalAmountByType(FuelGrade.TRITIUM)) {
            throw new InsufficientResourcesException("Do not have enough TRITIUM resource to fly");
        }

        cargoHold.consumeResource(FuelGrade.TRITIUM, amountTri);
        this.damage();
        cargoHold.damage();

        // update current port
        this.currentPort = portDestination;
    }

    /**
     * Jumps the Ship to the specified SpacePort if it is in range, and the ship is able to support the journey.
     * Ship's Rooms take damage at their damage rate when jumping.
     * Making a jump requires 1 HYPERDRIVE_CORE.
     * @param portName A String representation of the target SpacePort's unique name.
     * @param cargoHold The Ship's CargoHold
     * @throws InsufficientResourcesException if the CargoHold is broken; if the NavigationRoom is broken;
     * or if there is insufficient fuel of the relevant type in the CargoHold.
     * @throws NoPathException If the named SpacePort cannot be found or is out of range.
     */
    public void jumpTo(String portName, CargoHold cargoHold)
        throws InsufficientResourcesException, NoPathException {
        // handled the NoPathException if the portName cannot be found inside the function.
        SpacePort portDestination = getSpacePortFromName(portName);

        if (cargoHold.isBroken() || this.isBroken()) {
            throw new InsufficientResourcesException();
        }

        long distance = currentPort.getPosition().distanceTo(portDestination.getPosition());
        // unable to jump because of out of range
        if (distance > maximumJumpDistance) {
            throw new NoPathException("Out of range to fly");
        }

        if (cargoHold.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE) < 1) {
            throw new InsufficientResourcesException();
        }

        cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, 1);
        this.damage();
        cargoHold.damage();

        // update current port
        this.currentPort = portDestination;
    }

    /**
     * Returns a SpacePort based on a specified name.
     * @param name The unique name of the SpacePort.
     * @return The specified SpacePort if it can be found.
     * @throws NoPathException If the named SpacePort cannot be found.
     */
    public SpacePort getSpacePortFromName(String name) throws NoPathException {
        for (SpacePort spacePort : galaxyMap) {
            if (spacePort.getName().equals(name)) {
                return spacePort;
            }
        }
        throw new NoPathException("The named SpacePort cannot be found.");
    }

    /**
     * Get the list of actions that it is possible to perform from this NavigationRoom.
     * A NavigationRoom is able to fly to SpacePorts in fly range, and jump to SpacePorts in jump range.
     * @return A List of actions that this NavigationRoom can perform as Strings.
     * Format must be: For flyable SpacePorts: "fly to \"SpacePort name\": SpacePort details [COST: amount of fuel needed TRITIUM FUEL"].
     * For jumpable SpacePorts: "jump to \" SpacePort name\" [COST: 1 HYPERDRIVE CORE]
     * note: - Do we add the fly action to the action list first or the jump action first? or following the order of the SpacePorts in the Galaxy?
     *       - do we just add the ports in fly and jump range to the action list or need to check all other conditions mentioned in the jumpTo() and flyTo() methods?
     */
    @Override
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();
        List<SpacePort> flyablePorts = this.getPortsInFlyRange();
        List<SpacePort> jumpablePort = this.getPortsInJumpRange();

        for (SpacePort port : flyablePorts) {
            actions.add("fly to " + "\"" + port.getName() + "\"" + ": " + port.toString()
                + " [COST: " + this.currentPort.getPosition().distanceTo(port.getPosition())
                + " TRITIUM FUEL]");
        }

        for (SpacePort port : jumpablePort) {
            actions.add("jump to " + "\"" + port.getName() + "\""
                + " [COST: " + 1 + " HYPERDRIVE CORE]");
        }
        return actions;
    }
}
