package srg.ship;

/**
 * Classes which implement Damageable are capable of experiencing damage. Damageable objects have a health stat which can take damage and be repaired.
 */
public interface Damageable {

    /**
     * The rate at which damage occurs to a Damageable object is determined by the DAMAGE_RATE 
     * and some feature of the object's class.
     */
    public static final int DAMAGE_RATE = 5;
    
    /**
     * The maximum health of a Damageable object is determined by the HEALTH_MULTIPLIER 
     * and some feature of the object's class.
     */
    public static final int HEALTH_MULTIPLIER = 5;

    /**
     * The threshold at which a Damageable object must be repaired.
     */
    public static final int REPAIR_THRESHOLD = 30;

    
    /**
     * Applies damage to the Damageable object at the appropriate rate.
     */
    public void damage();

    /**
     * Returns the health as an integer percentage of the maximum. 
     * Health may become negative, in which case the percentage should also be negative.
     * @return An integer representing the current health as a percentage of the maximum, rounded down.
     */
    public int getHealth();

    /**
     * Recalculates maximum health and resets health to maximum.
     */
    public void resetHealth();

    
    /**
     * Sets the damage rate of a Damageable object to newDamageRate
     * Precondition: the newDamageRate must be greater than or equal to zero.
     * @param newDamageRate The new damage rate.
     */
    public void setDamageRate(int newDamageRate);

    /**
     * Returns whether the current object needs repair. 
     * If the percentage health (rounded-down) is less than or equal to 30% then repair is needed, 
     * or further use of the item will lead to damage.
     * @return True if getHealth() is less than or equal to 30%.
     */
    default boolean needsRepair() {
        return getHealth() <= REPAIR_THRESHOLD;
    }

    /**
     * Return whether the current object is non-functional.
     * @return true if health is less than or equal to 0, or false otherwise.
     */
    default boolean isBroken() {
        return getHealth() <= 0;
    }
    
}
