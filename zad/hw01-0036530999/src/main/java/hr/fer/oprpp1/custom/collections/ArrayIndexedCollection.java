package hr.fer.oprpp1.custom.collections;

// not implementing addAll here because it has a defined behaviour in Collection class
// and here we were to implement methods with undefined behaviour # TODO implement addAll ?

// TODO check do constructors set size correctly
// TODO check that you use .equals(), not ==, where necessary
public class ArrayIndexedCollection extends Collection {
    private int size;
    private Object[] elements;

    public ArrayIndexedCollection() {
        this(16);
    }

    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Array size should be at least 1!");
        }

        size = 0;
        elements = new Object[initialCapacity];
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

        size = other.size();
        elements = new Object[initialCapacity];
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

        int i = 0;
        if (size == elements.length) {
            Object[] newElements = new Object[2 * elements.length];
            for (Object v : elements) {
                newElements[i++] = v;
            }
            elements = newElements;
        } else {
            while (elements[i++] != null)
                ;
            i--; // because postfix++ set i to one too many
        }
        elements[i] = value;
    }

    @Override
    public boolean contains(Object value) { // it is OK to ask if collection contains null
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    @Override // TODO fix this, size lets you know the numNotNull xd stupidass
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

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }

    public Object get(int index) {
        if (index < 0 || index > elements.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        return elements[index];
    }

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }
        shiftLeft(index);
        return true;
    }

    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        shiftLeft(index);
    }

    private void shiftLeft(int index) {
        index++;
        for (; index < size; index++) {
            elements[index - 1] = elements[index];
        }
        elements[size - 1] = null;
    }

    public void insert(Object value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }

        if (elements[elements.length - 1] != null) {
            Object[] newElements = new Object[2 * elements.length];
            int i = 0;
            for (Object v : elements) {
                newElements[i++] = v;
            }
            elements = newElements;
        }

        for (int i = size - 1; i > position; i--) {
            elements[i] = elements[i - 1];
        }
        
        elements[position] = null;
        add(value); // add finds first empty space - will find the newly created null
    }

    @Override
    public void forEach(Processor processor) {
        for (Object o : elements) {
            processor.process(o);
        }
    }

    @Override
    public void addAll(Collection other) {
        class LocalProcessor extends Processor {
            public void process(Object value) {
                add(value);
                size++;
            }
        }
        ((ArrayIndexedCollection) other).forEach(new LocalProcessor());
    }

}
