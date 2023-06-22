package srg.ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@link Room} in a {@link Ship}. Rooms are Damageable objects.
 * Rooms also have a tier which contributes to determining starting health and damage rate.
 */
public class Room extends Object implements Damageable {

    /** The quality tier of the Room. */
    private RoomTier tier;

    /** The current health of the Room. */
    private int health;

    /** The maximum health of the Room. */
    private int maxHealth;

    /** The damage rate of the Room. */
    private int damageRate;

    /**
     * Constructs a {@link Room} and automatically assigns it a BASIC tier.
     */
    public Room() {
        this.tier = RoomTier.BASIC;
        this.damageRate = this.tier.damageMultiplier * Damageable.DAMAGE_RATE;
        this.maxHealth = this.tier.healthMultiplier * Damageable.HEALTH_MULTIPLIER;
        this.health = this.maxHealth;
    }

    /**
     * Constructs a {@link Room} and assigns it a tier. 
     * damage rate is determined by the damage multiplier of the Room's tier multiplied by Damageable.DAMAGE_RATE.
     * maximum health is determined by the health multiplier of the Room's tier multiplied by Damageable.HEALTH_MULTIPLIER.
     * health is initially the maximum health.
     * @param tier The quality tier of the Room.
     */
    public Room(RoomTier tier) {
        this.tier = tier;
        this.damageRate = this.tier.damageMultiplier * Damageable.DAMAGE_RATE;
        this.maxHealth = this.tier.healthMultiplier * Damageable.HEALTH_MULTIPLIER;
        this.health = this.maxHealth;
    }

    /**
     * Upgrades a {@link Room} based on its current tier, 
     * resets its health to the new maximum, and sets the new damage rate.
     * If the room is already PRIME, this should reset health to maximum.
     */
    public void upgrade() {
        if (this.tier == RoomTier.AVERAGE) {
            this.tier = RoomTier.PRIME;
        } else if (this.tier == RoomTier.BASIC) {
            this.tier = RoomTier.AVERAGE;
        }
 
        this.maxHealth = this.tier.healthMultiplier * Damageable.HEALTH_MULTIPLIER;
        this.damageRate = this.tier.damageMultiplier * Damageable.DAMAGE_RATE;
        this.health = maxHealth;
    }

    /**
     * Applies a damage rate's worth of damage to the Damageable object.
     */
    @Override
    public void damage() {
        health = this.health - this.damageRate;
    }

    /**
     * Gets the status of the Room's tier.
     * @return The value of the Room's tier.
     */
    public RoomTier getTier() {
        return this.tier;
    }

    /**
     * Get the health as an integer percentage of the maximum.
     * Health may become negative, in which case the percentage should also be negative.
     * @return An integer representing the current health as a percentage of the maximum, rounded down.
     */
    @Override
    public int getHealth() {
        return (int) Math.floor((this.health * 100) / (this.maxHealth));
    }

    /**
     * Recalculates maximum health and resets health to maximum.
     */
    @Override
    public void resetHealth() {
        this.maxHealth = tier.healthMultiplier * Damageable.HEALTH_MULTIPLIER;
        this.health = this.maxHealth;
    }

    /**
     * Sets the damage rate of a Damageable object to newDamageRate.
     * Precondition: the newDamageRate must be greater than or equal to zero.
     */
    @Override
    public void setDamageRate(int newDamageRate) {
        if (newDamageRate >= 0) {
            this.damageRate = newDamageRate;
        }
    }

    /**
     * Returns a String representation of a {@link Room}.
     * @return A String of the format "ROOM: room name (room tier) health: health%, needs repair: boolean"
     * e.g ROOM: Room(BASIC) heath: 100%, needs repair: false
     */
    @Override
    public String toString() {
        return "ROOM: " + this.getClass().getSimpleName() 
                + "(" + tier + ") health: "
                + this.getHealth() + "%, needs repair: " 
                + needsRepair();
    }

    /**
     * Get the list of actions that it is possible to perform from this Room.
     * Generic Rooms have no actions.
     * @return A List of actions that are unique to this {@link SpacePort} as Strings.
    */
    public List<String> getActions() {
        return new ArrayList<>();
    }
    
}