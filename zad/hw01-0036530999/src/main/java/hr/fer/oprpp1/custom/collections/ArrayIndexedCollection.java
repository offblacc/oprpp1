package hr.fer.oprpp1.custom.collections;

// not implementing addAll here because it has a defined behaviour in Collection class
// and here we were to implement methods with undefined behaviour

public class ArrayIndexedCollection extends Collection {
    private int size;
    private Object[] elements; // TODO gonna need to check if actual len > size var

    public ArrayIndexedCollection() {
        this(16);
    }

    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Array size should be at least 1!");
        }

        size = initialCapacity;
        elements = new Object[size];
    }

    public ArrayIndexedCollection(Collection other) {
        this(other, other.size());
    }

    public ArrayIndexedCollection(Collection other, int initialCapacity) { // double check this
        if (other == null) {
            throw new NullPointerException();
        }

        if (initialCapacity < other.size()) {
            initialCapacity = other.size();
        }

        size = initialCapacity;
        elements = new Object[size];
        addAll(other);
    }
    // s novim definiranim ponašanjem get, insert

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }

        boolean fullArray = true;
        for (int i = 0; i < size(); i++) {
            if (elements[i] == null) {
                fullArray = false;
                elements[i] = value;
                return;
            }
        }

        if (fullArray) {
            Object[] newElements = new Object[2 * size()];
            int i = 0;
            for (Object v : elements) {
                newElements[i++] = v;
            }
            elements = newElements;
        }
    }

    @Override
    public boolean contains(Object value) { // it is OK to ask if collection contains null
        for (Object o : elements) {
            if (o.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }

        for (int i = 0; i < size(); i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }
        elements[index] = null;
        return true;
    }

    @Override // TODO should skip null values, right ?
    Object[] toArray() {
        int numNotNull = 0;
        for (Object o : elements) {
            if (o == null) {
                continue;
            }
            numNotNull++;
        }

        Object[] arr = new Object[numNotNull];
        int i = 0;
        for (Object o : elements) {
            if (o == null) {
                continue;
            }
            arr[i++] = o;
        }
        return arr;
    }

    public void forEach(Processor processor) {
        for (Object o : elements) {
            processor.process(o);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            elements[i] = null;
        }
    }

    public Object get(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }

        return elements[index];
    }

    public void remove(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }
        index++;
        for (; index < size(); index++) {
            elements[index - 1] = elements[index];
        }
    }

    public void insert(Object value, int position) {
        if (position < 0 || position > size()) {
            throw new IndexOutOfBoundsException();
        }

        if (!(elements[size() - 1] == null)) {
            Object[] newElements = new Object[2 * size()];
            int i = 0;
            for (Object v : elements) {
                newElements[i++] = v;
            }
            elements = newElements;
        }

        for (int i = size() - 1; i > position; i--) {
            elements[i] = elements[i - 1];
        }

        add(value);
    }

}
