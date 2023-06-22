package srg.ship;

import java.util.ArrayList;
import java.util.List;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;

/**
 * A child class of {@link Room} which is able to store {@link ResourceContainer}s.
 */
public class CargoHold extends Room {

    /** A list of ResourceContainers in this CargoHold.*/
    private List<ResourceContainer> resourceContainers;

    /** The maximum number of ResourceContainers this CargoHold can contain.*/
    private int capacity;

    /**
     * Constructor which creates a CargoHold at a specified RoomTier.
     *
     * @param roomTier The quality of the CargoHold's tier.
     */
    public CargoHold(RoomTier roomTier) {
        super(roomTier);

        if (roomTier == RoomTier.BASIC) {
            this.capacity = 5;
        } else if (roomTier == RoomTier.AVERAGE) {
            this.capacity = 10;
        } else if (roomTier == RoomTier.PRIME) {
            this.capacity = 15;
        } else {
            throw new IllegalArgumentException();
        }

        resourceContainers = new ArrayList<>();
    }

    /**
     * An int representing the CargoHold's maximum capacity.  
     * A BASIC CargoHold has a capacity of 5. 
     * An AVERAGE CargoHold has a capacity of 10. 
     * A PRIME CargoHold has a capacity of 15.
     * @return Returns the maximum capacity of the CargoHold based on its tier.
     */
    public int getMaximumCapacity() {
        return this.capacity;
    }

    /**
     * Returns the remaining capacity of the CargoHold for new ResourceContainers.
     * 
     * @return The remaining capacity of the CargoHold for new ResourceContainers 
     * The maximum capacity of the CargoHold less any ResourceContainers currently stored.
     */
    public int getRemainingCapacity() {
        return this.capacity - this.resourceContainers.size();
    }
    
    /**
     * Gets the list of ResourceContainers stored by this CargoHold.
     * @return A list of ResourceContainers stored by this CargoHold.
     */
    public List<ResourceContainer> getResources() {
        return this.resourceContainers;
    }

    /**
     * Attempts to add a new ResourceContainer to this CargoHold.
     * The added ResourceContainer should not be merged into existing containers.
     * @param resource The ResourceContainer to add to this CargoHold.
     * @throws InsufficientCapacityException  If there is not enough capacity to add resource, 
     * i.e. CargoHold already stores the maximum capacity or higher.
     */
    public void storeResource(ResourceContainer resource) throws InsufficientCapcaityException {
        if (this.getRemainingCapacity() == 0) {
            throw new InsufficientCapcaityException("Out of capacity");
        }

        this.resourceContainers.add(resource);
    }

    /**
     * Get a List of ResourceContainers holding a given ResourceType.
     *
     * @param type The ResourceType.
     * @return A List of ResourceContainers holding resources of a particular type.
     */
    public List<ResourceContainer> getResourceByType(ResourceType type) {
        List<ResourceContainer> resources = new ArrayList<>();
        for (ResourceContainer resourceContainer : resourceContainers) {
            if (resourceContainer.getType() == type) {
                resources.add(resourceContainer);
            }
        }
        return resources;
    }

    /**
     * Return a list of ResourceContainers holding fuel of a particular FuelGrade.
     * @param grade The FuelGrade.
     * @return A list of ResourceContainers holding fuel of a particular FuelGrade.
     */
    public List<ResourceContainer> getResourceByType(FuelGrade grade) {
        List<ResourceContainer> resources = new ArrayList<>();
        for (ResourceContainer resourceContainer : resourceContainers) {
            if (resourceContainer.getType() == ResourceType.FUEL) {
                FuelContainer fuelContainer = (FuelContainer) resourceContainer;
                if (fuelContainer.getFuelGrade() == grade) {
                    resources.add(fuelContainer);
                }
            }
        }
        return resources;
    }

    /**
     * Sums the quantity of a given resource in the ResourceContainers.
     * @param type The ResourceType.
     * @return The quantity of the resource.
     */
    public int getTotalAmountByType(ResourceType type) {
        int sum = 0;
        for (ResourceContainer resourceContainer : resourceContainers) {
            if (resourceContainer.getType() == type) {
                sum += resourceContainer.getAmount();
            }
        }
        return sum;
    }

    /**
     * Sums the quantity of a given fuel in the ResourceContainer.
     * @param grade The FuelGrade.
     * @return The quantity of the fuel.
     */
    public int getTotalAmountByType(FuelGrade grade) {
        int sum = 0;
        for (ResourceContainer resourceContainer : resourceContainers) {
            if (resourceContainer.getType() == ResourceType.FUEL) {
                FuelContainer fuelContainer = (FuelContainer) resourceContainer;
                if (fuelContainer.getFuelGrade() == grade) {
                    sum += resourceContainer.getAmount();
                }
            }
        }
        return sum;
    }

    /**
     * Consumes the specified amount of non-fuel resources.
     * 
     * Resources must be consumed from ResourceContainers in the order that the container was stored.
     * Containers that were added first will be removed from first.
     * Once a container is emptied of content, it must be removed from the CargoHold.
     * 
     * Precondition: The amount is greater than zero.
     * @param type The resource type to be consumed.
     * @param amount The amount to be consumed.
     * @throws InsufficientResourcesException  if amount is greater than the total of resource.amount for resource in CargoHold
     * @throws IllegalArgumentException if type equals FUEL
     */
    public void consumeResource(ResourceType type, int amount)
                                throws InsufficientResourcesException {
        if (type == ResourceType.FUEL) {
            throw new IllegalArgumentException();
        }

        if (this.getTotalAmountByType(type) < amount) {
            throw new InsufficientResourcesException("unspecified.");
        }

        // to count the number of resources that are not FUEL in the resourceContainers.
        int numberOfResources = 0;

        for (ResourceContainer resourceContainer : resourceContainers) {
            if (resourceContainer.getType() != ResourceType.FUEL) {
                
                numberOfResources++;

                if (resourceContainer.getAmount() >= amount) {
                    resourceContainer.setAmount(resourceContainer.getAmount() - amount);
                    amount = 0;
                } else if (resourceContainer.getAmount() < amount) {
                    amount -= resourceContainer.getAmount();
                    resourceContainer.setAmount(0);
                }
                
                // check the empty resource, then remove it 
                if (resourceContainer.getAmount() <= 0) {
                    resourceContainers.remove(resourceContainer);
                    numberOfResources--;
                }

                if (amount == 0) {
                    break;
                }
            }
        }
        
        // The amount is greater than the total of resource.amount for resource in CargoHold
        if (numberOfResources == 0 && amount > 0) {
            throw new InsufficientResourcesException("unspecified.");
        }
    }

    /**
     * Consumes the specified amount of fuel resources. 
     * Fuel resources must be consumed from ResourceContainers
     * in the order that the container was stored.
     * Containers that were added first will be removed from first. 
     * Once a container is emptied of content, it must be removed from the CargoHold. 
     * Precondition: The amount is greater than zero.
     * @param grade The fuel grade to be consumed.
     * @param amount The amount to be consumed
     * @throws InsufficientResourcesException - if amount is greater than the total of resource.amount for resource in CargoHold
     */
    public void consumeResource(FuelGrade grade, int amount)
                                throws InsufficientResourcesException {
        
        // The number of ResourceContainers that are FuelContainers having the same provided grade
        int numberOfFuelContainers = 0;

        if (this.getTotalAmountByType(grade) < amount) {
            throw new InsufficientResourcesException("unspecified.");
        }

        for (ResourceContainer resourceContainer : resourceContainers) {
            if (resourceContainer.getType() == ResourceType.FUEL) {
                // down cast to get the grade
                FuelContainer fuelContainer = (FuelContainer) resourceContainer;

                if (fuelContainer.getFuelGrade() == grade) {
                    numberOfFuelContainers++;

                    if (resourceContainer.getAmount() >= amount) {
                        resourceContainer.setAmount(resourceContainer.getAmount() - amount);
                        amount = 0;
                    } else if (resourceContainer.getAmount() < amount) {
                        amount -= resourceContainer.getAmount();
                        resourceContainer.setAmount(0);
                    }

                    if (resourceContainer.getAmount() <= 0) {
                        resourceContainers.remove(resourceContainer);
                        numberOfFuelContainers--;
                    }

                    if (amount == 0) {
                        break;
                    }
                }  
            }
        }

        // The amount is greater than the total of resource.amount for resource in CargoHold
        if (numberOfFuelContainers == 0 && amount > 0) {
            throw new InsufficientResourcesException("unspecified.");
        }
    }

    /**
     * Returns a String representation of a CargoHold
     * @return A String of the format "ROOM: room name (room tier) health: health%, 
     * needs repair: boolean, capacity: maximum capacity, 
     * items: number of ResourceContainers in List\n [4 space indent] ResourceContainer details for all ResourceContainers"
     */
    @Override
    public String toString() {
        String itemsDetail = "";

        // bug here: Why the resourceContainer is null after "buy TRITIUM 500" commands?
        // So resourceContainer.toString() is not executable.
        for (ResourceContainer resourceContainer : resourceContainers) {
            itemsDetail += "\n    " + resourceContainer.toString();
        }

        return "ROOM: " + this.getClass().getSimpleName() + "(" + this.getTier() + ") health: "
                + this.getHealth() + "%, needs repair: " + this.needsRepair() 
                + ", capacity: " + this.capacity + ", items: " + resourceContainers.size()
                + itemsDetail;
    }

    /**
     * Get the list of actions that it is possible to perform from this CargoHold. 
     * A CargoHold is able to repair Rooms in the Ship if it has any REPAIR_KITs available. 
     * If the CargoHold can repair, there must be an action for both types of rooms, namely: CargoHold and NavigationRoom.
     * @return A List of actions that this CargoHold can perform as Strings. 
     * Format must be: "repair Room [COST: 1 REPAIR_KIT]"
     */
    @Override
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();
        int totalAmount = this.getTotalAmountByType(ResourceType.REPAIR_KIT);

        if (totalAmount >= 1) {
            actions.add("repair CargoHold [COST: 1 REPAIR_KIT]");
            actions.add("repair NavigationRoom [COST: 1 REPAIR_KIT]");
        }
        return actions;
    }
}
