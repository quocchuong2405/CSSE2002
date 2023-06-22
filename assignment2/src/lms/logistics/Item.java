package lms.logistics;

import java.util.Objects;

/**
 * Class to manage the name of an Item object. Provides implementaions for equals, hashcode, and
 * toString.
 */
public class Item extends Object {

    /**
     * The name of item
     */
    private String name;

    /**
     * The constructor to instantiate an Item
     *
     * @param name String
     * @throws IllegalArgumentException if the item name is null or empty string
     */
    public Item(String name) throws IllegalArgumentException {
        this.name = name;
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this one. This implementation of equals
     * compares the given object to the current object for equivalence based on the values of their
     * respective properties. If the two objects have the same class, and their properties have the
     * same values (as determined by the equals method of each property), then they are considered
     * equal. Note that the comparison is symmetric, meaning that a.equals(b) will return true if and
     * only if b.equals(a) returns true for any non-null object reference b.
     *
     * @param o the object to compare for equality
     * @return true if the given object is equal to this object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return this.name == item.name;
    }

    /**
     * Hashcode implementation, where hashcode is calculated based on the item's name
     *
     * @return A hashcode calculated based on the item's name.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * A String representation of the Item.
     *
     * @return the Item name.
     */
    @Override
    public String toString() {
        return name;
    }
}
