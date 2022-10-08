package hr.fer.oprpp1.custom.collections;

/**
 * (Effectively abstract) class that serves as a base class for any collection
 * of objects.
 */
public class Collection {
    /**
     * Default constructor.
     */
    public Collection() {
    }

    /**
     * Returns true if the collection has no objects stored, false otherwise.
     *
     * @return true if the collection has no objects stored, false otherwise.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Returns the number of currently stored objects.
     *
     * @return the number of currently stored objects.
     */
    public int size() {
        return 0;
    }


    /**
     * An abstract method. All subclases that implement this method add the parameter value to the collection.
     * @param value - value to be added to the collection.
     */
    public void add(Object value) {}


    /**
     * An abstract method. All subclases that implement this method check if the collection contains the parameter value.
     * @param value - value to be checked if it is in the collection.
     * @return true if the collection contains the given value, false otherwise.
     */
    public boolean contains(Object value) {
        return false;
    }

    public boolean remove(Object value) {
        return false;
    }

    Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    void forEach(Processor processor) {

    }

    void addAll(Collection other) {
        class LocalProcessor extends Processor {
            public void process(Object value) {
                add(value);
            }
        }
        other.forEach(new LocalProcessor());
    }

    void clear() {

    }
}
