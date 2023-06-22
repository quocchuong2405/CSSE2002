package lms.logistics;

import java.util.function.Consumer;

/**
 * Maintains a doubly linked list to maintain the links for each node. Has previous and next item.
 * The path can't have an empty node, as it will throw an illegal argument exception.
 *
 * @version 1.0
 * @ass2
 */
public class Path extends Object {

    /**
     * current node in path
     */
    private final Transport node;
    /**
     * previous path
     */
    private Path previous;
    /**
     * next path
     */
    private Path next;

    /**
     * Constructs a new Path object with the same Transport node, previous Path, and next Path as the
     * specified Path object.
     *
     * @param path the Path object to copy.
     * @throws IllegalArgumentException if path is null
     */
    public Path(Path path) throws IllegalArgumentException {
        if (path == null) {
            throw new IllegalArgumentException();
        }

        this.node = path.node;
        this.previous = path.previous;
        this.next = path.next;
    }

    /**
     * Constructs a new Path object with the specified Transport node, and sets the previous and next
     * Path objects in the path to null. Throws an IllegalArgumentException if the node argument is
     * null.
     *
     * @param node the Transport node for this Path.
     * @throws IllegalArgumentException if the node argument is null.
     */
    public Path(Transport node)
            throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        this.node = node;
        this.previous = null;
        this.next = null;
    }

    /**
     * Constructs a new Path object with the specified Transport node, and the previous and next Path
     * objects in the path. Throws an IllegalArgumentException if the node argument is null.
     *
     * @param node     the Transport node for this Path.
     * @param previous the previous Path object in the path.
     * @param next     the next Path object in the path.
     * @throws IllegalArgumentException if the node argument is null.
     */
    public Path(Transport node, Path previous, Path next)
            throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        this.node = node;
        this.previous = previous;
        this.next = next;
    }

    /**
     * Returns the head of this Path, which is the first element in the path. If this Path is the
     * first element, it is returned as is.
     *
     * @return the head of this Path.
     */
    public Path head() {
        Path current = this;
        while (current.previous != null) {
            current = current.previous;
        }
        return current;
    }

    /**
     * Accessor method for the transport node associated with this path.
     *
     * @return the transport node associated with this path
     */
    public Transport getNode() {
        return node;
    }

    /**
     * Returns the tail of this Path, which is the last element in the path. If this Path is the last
     * element, it is returned as is.
     *
     * @return the tail of this Path.
     */
    public Path tail() {
        Path current = this;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    /**
     * Returns the previous Path object in the chain.
     *
     * @return the previous Path object in the chain, or null if this is the first Path object
     */
    public Path getPrevious() {
        return previous;
    }

    /**
     * Sets the previous path for this path.
     *
     * @param path the previous path to be set for this path.
     */
    public void setPrevious(Path path) {
        this.previous = path;
    }

    /**
     * Returns the next Path object in the chain.
     *
     * @return the next Path object in the chain, or null if this is the last Path object.
     */
    public Path getNext() {
        return next;
    }

    /**
     * Sets the next path for this path.
     *
     * @param path the next path to be set for this path
     */
    public void setNext(Path path) {
        this.next = path;
    }

    /**
     * This method takes a Transport Consumer, using the Consumer&lt;T&gt; functional interface from
     * java.util. It finds the tail of the path and calls Consumer&lt;T&gt;'s accept() method with the
     * tail node as an argument. Then it traverses the Path until the head is reached, calling
     * accept() on all nodes.
     * <p>
     * This is how we call the tick method for all the different transport items.
     *
     * @param consumer Consumer&lt;Transport&gt;
     * @provided
     * @see java.util.function.Consumer
     */
    public void applyAll(Consumer<Transport> consumer) {
        Path path = tail(); // IMPORTANT: go backwards to aid tick
        do {
            consumer.accept(path.node);
            path = path.previous;
        } while (path != null);
    }

    /**
     * Compares this Path object to the specified object for equality. Returns true if and only if the
     * specified object is also a Path object and has the same Transport node as this Path object.
     *
     * @param o the object to compare this Path against
     * @return true if the specified object is equal to this Path object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path = (Path) o;
        return node.equals(path.node);
    }

    /**
     * toString that provides a list of Path nodes from a Producer, along the belt to a Receiver.
     *
     * @return String representing the entirety of the best path links in the format:
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("START -> ");
        toStringHelper(sb, this);
        sb.append(" -> END");
        return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, Path current) {
        sb.append("<").append(current.getNode().getClass()
                .getSimpleName()).append("-").append(current.getNode()
                .getId()).append(">");

        if (current.next != null) {
            sb.append(" -> ");
            toStringHelper(sb, current.next);
        }
    }
}
