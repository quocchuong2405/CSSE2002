package srg.resources;

/**
 * A child of ResourceContainer which stores fuel type resources.
 */
public class FuelContainer extends ResourceContainer {

    /** Maximum capacity for a FuelContainer. Contains 1000 units. */
    public static final int MAXIMUM_CAPACITY = 1000;

    /** The grade of the fuel*/
    private FuelGrade grade;

    /**
     * Constructs a FuelContainer holding a specified amount of Fuel of a specified Grade.
     * Precondition: The amount must be at least 1 and less than or equal to MAXIMUM_CAPACITY.
     * @param grade The grade of the Fuel to be stored, based on FuelGrade enum.
     * @param amount The amount of Fuel to be stored.
     */
    public FuelContainer(FuelGrade grade, int amount) {
        super(ResourceType.FUEL, amount);
        this.grade = grade;
    }

    /**
     * Returns the grade of fuel being stored.
     *
     * @return The grade of the fuel, based on the FuelGrade enum.
     */
    public FuelGrade getFuelGrade() {
        return this.grade;
    }

    /**
     * Returns the short name of the type of fuel stored in this FuelContainer
     * 
     * @return A String containing the type of this resource, based on the FuelGrade enum
     */
    @Override
    public String getShortName() {
        return grade.name();
    }

    /**
     * Checks if resources of the specified ResourceType can be stored in this FuelContainer.
     * 
     * @param type The ResourceType to check.
     * @return true if the ResourceType is FUEL. false if the ResourceType is not FUEL;
     */
    @Override
    public boolean canStore(ResourceType type) {
        return type == ResourceType.FUEL;
    }

    /**
     * Returns a String representation of this FuelContainer.
     * 
     * @return A string of format: "ResourceType: amount - grade"
     */
    @Override
    public String toString() {
        return super.toString() + " - " + this.grade;
    }

}
