package srg.ports;

import java.util.ArrayList;
import java.util.List;

import srg.ship.Room;

/**
 * A child class of SpacePort which is capable of upgrading link Rooms in Ships.
 */
public class ShipYard extends SpacePort {

    /**
     * A List of Room classes as class names that can be upgraded by this ShipYard.
     */
    private List<String> canUpgrade;

    /**
     * Constructs a ShipYard
     * Precondition: Each ShipYard must have a unique name. 
     * @param name The name of the SpacePort. Each SpacePort must have a unique name.
     * @param position The physical Position of the SpacePort.
     * @param canUpgrade A List of Rooms that can be upgraded by this ShipYard.
     */
    public ShipYard(String name, Position position, List<String> canUpgrade) {
        super(name, position);
        this.canUpgrade = canUpgrade;
    }

    /**
     * Upgrades a Room.
     * @param room The Room to Upgrade. Rooms are upgraded according to the tiers in the RoomTier enum.
     * @throws IllegalArgumentException if the room is not on the list of Rooms that this ShipYard can upgrade.
     */
    public void upgrade(Room room) {
        if (!canUpgrade.contains(room.getClass().getSimpleName())) {
            throw new IllegalArgumentException("This ShipYard cannot upgrade this room.");
        }
        room.upgrade();
    }

    /**
     * Get the list of actions that it is possible to perform at this SpacePort.
     * ShipYards are able to upgrade Rooms that appear in their canUpgrade List.
     * Action Strings must be formatted as "upgrade Room name".
     * @return A List of actions that are unique to this SpacePort as Strings.
     */
    @Override
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();
        for (String roomName : canUpgrade) {
            actions.add("upgrade " + roomName);
        }
        return actions;
    }
}
