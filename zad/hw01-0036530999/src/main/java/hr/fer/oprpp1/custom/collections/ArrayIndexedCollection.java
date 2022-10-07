package hr.fer.oprpp1.custom.collections;

// TODO General contract of this collection is: duplicate elements are allowed; storage of null references is not allowed.
// add private constructor to try improving the code (just a possibility perhaps)
public class ArrayIndexedCollection extends Collection {
    private int size;
    private Object[] elements; // TODO gonna need to check if actual len > size var

    public ArrayIndexedCollection() {
        size = 16;
        elements = new Object[size];
    }

    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Array size should be at least 1!");
        }

        size = initialCapacity;
        elements = new Object[size];
    }

    public ArrayIndexedCollection(Collection other) {

    }

}
