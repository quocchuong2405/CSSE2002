package srg.ship;

import srg.cli.given.IO;
import srg.cli.given.PurchaseCommand;
import srg.cli.given.ShipCommand;
import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ports.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spaceship, which has a unique name, a unique ID, a registered owner, a CargoHold and a NavigationRoom.
 * @version 1.0
 * @ass1
 */
public class Ship extends Object {
    /** The ship's name. */
    private String name;
    /** The ship's owner */
    private String owner;
    /** The ship's id */
    private String id;
    /** The Ship's CargoHold */
    private CargoHold cargoHold;
    /** The Galaxy map kept by NavigationRoom */
    private NavigationRoom navigationRoom;

    /**
     * Constructs a Ship with a default complement of resources
     * (5x REPAIR_KIT, 100x TRITIUM, 5x HYPERDRIVE_CORE)
     * and specified CargoHold and NavigationRoom quality.
     * @param name The ship's name.
     * @param owner The ship's owner
     * @param id The ship's id
     * @param cargoHoldTier The tier of the CargoHold
     * @param navigationRoomTier The tier of the NavigationRoom
     * @param galaxyMap The galaxy map to be kept by the NavigationRoom
     */
    public Ship(String name, String owner, String id, RoomTier cargoHoldTier,
                RoomTier navigationRoomTier, List<SpacePort> galaxyMap) {
        this.name = name;
        this.owner = owner;
        this.id = id;
        this.cargoHold = new CargoHold(cargoHoldTier);
        this.navigationRoom = new NavigationRoom(navigationRoomTier, galaxyMap);

        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, 5));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM, 100));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 5));
        } catch (InsufficientCapcaityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the specified Room.
     * @param name The type of Room to return.
     * @return The specified Room.
     * @throws IllegalArgumentException if the specified Room does not exist.
     */
    public Room getRoomByName(String name) throws IllegalArgumentException {
        if (name.equals("NavigationRoom")) {
            return this.navigationRoom;
        }
        if (name.equals("CargoHold")) {
            return this.cargoHold;
        }
        throw new IllegalArgumentException("The specified Room does not exist.");
    }

    /**
     * Returns a String representation of a Ship. Identifies the name, id, owner, CargoHold and NavigationRoom.
     *
     * @return A String containing details of the Ship. Format must be: "SHIP: name (id) owned by owner\n----\nCargoHold details\nNavigationRoom details".
     * e.g.
     * SHIP: Leaden Matter (SCS1249) owned by Jesko Thoch
     * ----
     * ROOM: CargoHold(BASIC) health: 100%, needs repair: false, capacity: 5, items: 1
     *     REPAIR_KIT: 5
     * ROOM: NavigationRoom(BASIC) health: 100%, needs repair: false
     */
    @Override
    public String toString() {
        return "SHIP: " + this.name + " (" + this.id + ") owned by " + this.owner + "\n----\n"
                        + cargoHold.toString() + "\n" + navigationRoom.toString();
    }

    /**
     * Get the list of actions that it is possible to perform from this Ship. A Ship is able to perform the actions of its CargoHold and NavigationRoom.
     * @return A List of actions as a string
     */
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();
        for (String cargoHoldAction : cargoHold.getActions()) {
            actions.add(cargoHoldAction);
        }
        /*  It may be incorrect
        for (String spacePortAction : navigationRoom.getCurrentPort().getActions()) {
            actions.add(spacePortAction);
        } */
        for (String navigationRoomAction : navigationRoom.getActions()) {
            actions.add(navigationRoomAction);
        }
        return actions;
    }

    /**
     * This method is provided as it interfaces with the command line interface.
     *
     * @param ioHandler Handles IO
     * @param command   A command to the ship
     */
    public void performCommand(IO ioHandler, ShipCommand command) {
        try {
            processCommand(ioHandler, command);
        } catch (InsufficientResourcesException error) {
            ioHandler.writeLn("Unable to perform action due to broken component or " +
                    "insufficient resources."
                    + System.lineSeparator() + error.getMessage());
        } catch (IllegalArgumentException | NoPathException | InsufficientCapcaityException error) {
            ioHandler.writeLn(error.getMessage());
        }
    }

    /**
     * This method is provided as it interfaces with the command line interface.
     * @param ioHandler Handles IO
     * @param command   A command to the ship
     * @throws InsufficientResourcesException
     *      If an action cannot be performed due to a lack or resources or a broken Room.
     * @throws NoPathException
     *      If a specified SpacePort cannot be found, or cannot be reached.
     * @throws InsufficientCapcaityException
     *      If resources cannot be added because there is not enough capacity in the CargoHold.
     */
    public void processCommand(IO ioHandler, ShipCommand command)
            throws InsufficientResourcesException, NoPathException, InsufficientCapcaityException {
        switch (command.type) {
            case SHOW_ROOM -> {
                ioHandler.writeLn(getRoomByName(command.value).toString());
            }
            case FLY_TO -> {
                navigationRoom.flyTo(command.value, cargoHold);
            }
            case JUMP_TO -> {
                navigationRoom.jumpTo(command.value, cargoHold);
            }
            case REPAIR_ROOM -> {
                // Ignore whether CargoHold may be broken
                cargoHold.consumeResource(ResourceType.REPAIR_KIT, 1);
                getRoomByName(command.value).resetHealth();

            }
            case UPGRADE_ROOM -> {
                ShipYard shipYard = navigationRoom.getShipYard();
                if (shipYard == null) {
                    ioHandler.writeLn("Can only upgrade when docked at a ShipYard.");
                    return;
                }

                shipYard.upgrade(getRoomByName(command.value));
            }
            case PURCHASE_ITEM -> {
                PurchaseCommand purchaseCommand = (PurchaseCommand) command;
                Store store = navigationRoom.getStore();
                if (store == null) {
                    ioHandler.writeLn("Can only purchase items at a Store.");
                    return;
                }
                ResourceContainer resource = store.purchase(purchaseCommand.item,
                        purchaseCommand.amount);
                cargoHold.storeResource(resource);
            }
            case SHOW_PORT -> {
                ioHandler.writeLn(navigationRoom.getCurrentPort().toString());
                ioHandler.writeLn(String.join(System.lineSeparator(),
                        navigationRoom.getCurrentPort().getActions()));
            }
            case SHOW_ACTIONS -> {
                ioHandler.writeLn(String.join(System.lineSeparator(), getActions()));
            }

        }

    }
}
