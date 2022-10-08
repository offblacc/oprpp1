package hr.fer.oprpp1.custom.collections;

import java.util.EmptyStackException;

public class ObjectStack {
    ArrayIndexedCollection array;

    public ObjectStack() {
        array = new ArrayIndexedCollection();
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int size() {
        return array.size();
    }

    public void push(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        array.add(value);
    }

    public Object pop() {
        if (array.size() == 0) {
            throw new EmptyStackException();
        }
        
        Object object = array.get(array.size() - 1);
        array.remove(array.size() - 1);
        return object;
    }

    public Object peek() {
        if (array.size() == 0) {
            throw new EmptyStackException();
        }

        return array.get(array.size() - 1);
    }

    public void clear() {
        array.clear();
    }
}
