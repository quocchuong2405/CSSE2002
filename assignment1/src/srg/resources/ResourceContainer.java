package srg.resources;

/**
 * A container which can store a certain type of resource.
 * @version 1.4
 */
public class ResourceContainer extends Object {
    /** Maximum capacity for a ResourceContainer. */
    public static final int MAXIMUM_CAPACITY = 10;

    /** Type of resource. */
    private ResourceType type;

    /** Amount of resource. */
    private int amount;

    /**
     * Constructor for a container filled with a consumable resource.
     * Precondition: The amount must be at least 1 and less than or equal to MAXIMUM_CAPACITY.
     * @param type Resource type.
     * @param amount The amount of this resource in the container
     * @throws IllegalArgumentException Throw exception if type == FUEL and
     * this constructor was not called from a constructor of the FuelContainer class.
     */
    public ResourceContainer(ResourceType type, int amount) throws IllegalArgumentException {
        if (type == ResourceType.FUEL && !(this instanceof FuelContainer)) {
            throw new IllegalArgumentException("Not called from FuelContainer.");
        }
        this.type = type;
        this.amount = amount;
    }

    /**
     * Checks if this ResourceContainer can store resources of a certain type.
     * 
     * @param type The ResourceType to check.
     * @return True if the ResourceType is not FUEL; false if the ResourceType is FUEL.
     */
    public boolean canStore(ResourceType type) {
        return (type != ResourceType.FUEL);
    }

    /**
     * Gets the amount of resource stored in this ResourceContainer.
     * 
     * @return The amount of resource stored in this ResourceContainer.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of resource stored in this ResourceContainer.
     * Precondition: The amount must be at least 1 and less than or equal to MAXIMUM_CAPACITY.
     * @param amount The new amount.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the type of resource stored in this ResourceContainer.
     * 
     * @return The type of resource stored in this ResourceContainer.
     */
    public ResourceType getType() {
        return this.type;
    }

    /**
     * Gets the short name of the resource stored in this ResourceContainer.
     * 
     * @return a string containing the type of this resource, based on the ResourceType enum
     */
    public String getShortName() {
        return this.type.name();
    }

    /**
     * Returns a string representation of this ResourceContainer.
     * 
     * @return a string of format: "ResourceType: amount"
     */
    @Override
    public String toString() {
        return this.type + ": " + this.amount;
    }
}