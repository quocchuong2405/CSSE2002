package srg.ports;

import java.util.ArrayList;
import java.util.List;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ship.CargoHold;
import srg.ship.RoomTier;

/**
 * A child class of SpacePort with a store, where Ships can restock on resources.
 */
public class Store extends SpacePort {

    /** A store contains a CargoHold*/
    private CargoHold cargoHold;

    /**
     * Construct a {@link Store} containing a {@link CargoHold}, which can sell items to ships. 
     * By default, one container with the maximum store-able amount is added to the cargo hold,
     * for each {@link ResourceType} and each {@link FuelGrade}.
     * The cargo hold has an AVERAGE RoomTier.
     * Precondition: Each Store must have a unique name. 
     * @param name The name of the {@link SpacePort}. Each {@link SpacePort} must have a unique name.
     * @param position The physical {@link Position} of the {@link SpacePort}.
     */
    public Store(String name, Position position) {
        super(name, position);
        cargoHold = new CargoHold(RoomTier.AVERAGE);

        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, 10));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 1000));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM, 1000));
        } catch (InsufficientCapcaityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove an item from the store, and return a {@link resourceContainer}
     * containing the removed amount of the same item.
     * If there is not enough resource available, {@link InsufficientResourcesException} is propagated up.
     * Preconditions:
     *      The amount is greater than zero.
     *      The amount is less than the MAXIMUM_CAPACITY of the corresponding {@link ResourceContainer}.
     * @param item The short string representation of the item name. 
     * E.g. The value returned by FuelGrade.TRITIUM.name() or ResourceType.REPAIR_KIT.name().
     *             ResourceType.FUEL is NOT a valid item name.
     * @param amount The amount of the resource to purchase.
     * @return A ResourceContainer containing an ``amount`` of resource corresponding to the item name.
     * @throws InsufficientResourcesException If there is not enough available in the inventory (or the Store's cargoHold).
     * The exception message is unspecified.
     * OR If item does not correspond to a valid {@link FuelGrade}, {@link ResourceType}.
     * The exception message must be "The specified resource does not exist.".
     * OR If item is ResourceType.FUEL.
     * The exception message must also be "The specified resource does not exist.".
     */
    public ResourceContainer purchase(String item, int amount)
        throws InsufficientResourcesException {
        
        if ((!item.equals(ResourceType.REPAIR_KIT.name()))
            && (!item.equals(FuelGrade.HYPERDRIVE_CORE.name()))
            && (!item.equals(FuelGrade.TRITIUM.name()))) {
            throw new InsufficientResourcesException("The specified resource does not exist.");
        }

        // returnedResource with amount of resource removed.
        ResourceContainer returnedResource = null;

        System.out.println("item: " + item + "   amount: " + amount);
        // Precondition: 0 < amount < ResourceContainer.MAXIMUM_CAPACITY
        if (amount > 0) {
            // 1 Store -> 1 CargoHold -> List of ResourceContainers / FuelContainers
            // item is either REPAIR_KIT / Fuel ------> FuelGrade: HYPERDRIVE / TRITIUM
            if (item.equals(ResourceType.REPAIR_KIT.name())) {
                cargoHold.consumeResource(ResourceType.REPAIR_KIT, amount);
                returnedResource = new ResourceContainer(ResourceType.REPAIR_KIT, amount);
            }

            if (item.equals(FuelGrade.HYPERDRIVE_CORE.toString())) {
                cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, amount);
                returnedResource = new FuelContainer(FuelGrade.HYPERDRIVE_CORE, amount);
            }

            if (item.equals(FuelGrade.TRITIUM.toString())) {
                cargoHold.consumeResource(FuelGrade.TRITIUM, amount);
                returnedResource = new FuelContainer(FuelGrade.TRITIUM, amount);
            }
        }

        return returnedResource;
    }

    /**
     * Get the list of actions that it is possible to perform at this {@link SpacePort}. 
     * Stores sell items that appear in their {@link CargoHold}. 
     * Action Strings must be formatted as "buy item name 1..maximum number available".
     * There must be only one action per item type. For example, if the CargoHold contains:
     *      [FuelContainer(FuelGrade.TRITIUM, 10), FuelContainer(FuelGrade.TRITIUM, 20)]
     * then the list of actions must contain only one action for "buy TRITIUM 1..30".
     * @return A List of actions that are unique to this SpacePort as Strings.
     * note: What happen if the resourceContainers contain only 1 or 2 types? that mean another type has 0 amount of resource
     */
    @Override
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();

        int sumAmountOfTritium = cargoHold.getTotalAmountByType(FuelGrade.TRITIUM);
        int sumAmountOfHyperDriveCore = cargoHold.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE);
        int sumAmountOfRepairKit = cargoHold.getTotalAmountByType(ResourceType.REPAIR_KIT);

        /*
        for (ResourceContainer resourceContainer : cargoHold.getResource()) {
            if (resourceContainer.getType() == ResourceType.FUEL) {
                FuelContainer fuelContainer = (FuelContainer) resourceContainer;
                if (fuelContainer.getFuelGrade() == FuelGrade.TRITIUM) {
                    sumAmountOfTritium += resourceContainer.getAmount();
                } else {
                    sumAmountOfHyperDriveCore += resourceContainer.getAmount();
                }
            }
            else {
                sumAmountOfRepairKit += resourceContainer.getAmount();
            };
        }*/

        if (sumAmountOfRepairKit > 0) {
            actions.add("buy REPAIR_KIT 1.." + sumAmountOfRepairKit);
        }
        if (sumAmountOfHyperDriveCore > 0) {
            actions.add("buy HYPERDRIVE_CORE 1.." + sumAmountOfHyperDriveCore);
        }
        if (sumAmountOfTritium > 0) {
            actions.add("buy TRITIUM 1.." + sumAmountOfTritium);
        }

        return actions;
    }
}
