package hr.fer.oprpp1.custom.collections;

import java.lang.Math;

/**
 * A simple hashtable implementation.
 */
public class SimpleHashtable<K, V> {
    /**
     * Slots in the hashtable.
     */
    TableEntry<K, V>[] table;
    /**
     * Number of elements in the hashtable.
     */
    int size;

    /**
     * The default capacity of the hashtable.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Constructs a new hashtable with the default capacity.
     */
    public SimpleHashtable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a new hashtable with the given capacity.
     * 
     * @param capacity the capacity of the hashtable
     * @throws IllegalArgumentException if the capacity is less than 1
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) { // TODO check supressed warning
        size = 0;
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }

        table = (TableEntry<K, V>[]) new Object[((int) Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2))))];
    }

    /**
     * Adds the given key-value pair to the hashtable. If the given key already
     * exists replace the value. If the slot is full add the new entry to the end of
     * the linked list in the slot. Returns the old value if exists, otherwise null.
     * The key can't be null, otherwise NullPointerException will be thrown.
     * 
     * @param key   - the key of the new entry
     * @param value - the value of the new entry
     * @return - the old value if exists, otherwise null
     * @throws NullPointerException if the key is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("The key can't be null!");
        }

        int pos = Math.abs(key.hashCode()) % table.length;

        if (table[pos] == null) {
            table[pos] = new TableEntry<K, V>(key, value);
            size++;
            return null;
        }

        TableEntry<K, V> entry = table[pos];
        while (entry.next != null) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            entry = entry.next;
        }
        entry.next = new TableEntry<K, V>(key, value);
        size++;
        return null;
    }

    /**
     * Returns the value of the entry with the given key. If the key doesn't exist
     * returns null. The given key can't be null, otherwise NullPointerException
     * will be thrown. Be careful, if the value is null this will also returns null,
     * as it is the value.
     * 
     * @param key - the key of the entry
     * @return - the value of the entry with the given key, otherwise null
     * @throws NullPointerException if the key is null
     */
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException("The key can't be null!");
        }

        int pos = Math.abs(key.hashCode()) % table.length;

        if (table[pos] == null) {
            return null;
        }

        TableEntry<K, V> entry = table[pos];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns the number of elements in the hashtable.
     * 
     * @return - the number of elements in the hashtable
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the hashtable contains the given key, false otherwise.
     * Throws NullPointerException if the given key is null.
     * 
     * @param key - the key whose presence in the hashtable is to be determined.
     * @return - true if the key is in the hashtable, false otherwise
     * @throws NullPointerException if the given key is null
     */
    public boolean containsKey(Object key) {
        if (key == null)
            throw new NullPointerException("The key can't be null!");
        for (int i = 0; i < table.length; i++) {
            var entry = table[i];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * Returns true if the hashtable contains the given value, false otherwise.
     * 
     * @param value - the value whose presence in the hashtable is to be determined.
     * @return - true if the value is in the hashtable, false otherwise
     */
    public boolean containsValue(Object value) {
        boolean searchNull = (value == null);
        TableEntry<K, V> entry;
        if (searchNull) {
            for (int i = 0; i < table.length; i++) {
                entry = table[i];
                while (entry != null) {
                    if (entry.getValue() == value) {
                        return true;
                    }
                    entry = entry.next;
                }
            }
        } else {
            for (int i = 0; i < table.length; i++) {
                entry = table[i];
                while (entry != null) {
                    if (entry.getValue().equals(value)) {
                        return true;
                    }
                    entry = entry.next;
                }
            }
        }
        return false;
    }

    /**
     * Removes the entry with the given key from the hashtable, returns the value
     * just before removing the entry. If the key doesn't exist null is returned.
     * Throws NullPointerException if the given key is null.
     * 
     * @param key - the key of the entry to be removed
     * @return - the value of the removed entry, null if the key doesn't exist
     */
    public V remove(Object key) {
        if (key == null) {
            throw new NullPointerException("The key can't be null!");
        }

        int pos = Math.abs(key.hashCode()) % table.length;

        if (table[pos] == null) {
            return null;
        }

        TableEntry<K, V> entry = table[pos];
        while (entry.next != null) {
            if (entry.next.key == key) {
                V oldValue = entry.next.value;
                entry.next = entry.next.next;
                size--;
                return oldValue;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns true if the hashtable is empty, false otherwise.
     * @return - true if the hashtable is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // TODO nema clear?

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < table.length; i++) {
            TableEntry<K, V> entry = table[i];
            while (entry != null) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
                entry = entry.next;
            }
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public TableEntry<K, V>[] toArray() {
        TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[size];
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            TableEntry<K, V> entry = table[i];
            while (entry != null) {
                array[index++] = entry;
                entry = entry.next;
            }
        }
        return array;
    }


    /**
     * Slot in the hashtable. Each slot can contain multiple elements - a linked
     * list.
     */
    public static class TableEntry<K, V> {
        private K key;
        private V value;
        private TableEntry<K, V> next;

        /**
         * Constructs a new table entry with the given key and value.
         * 
         * @param key   the key of the entry
         * @param value the value of the entry
         */
        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key of the entry.
         * 
         * @return Key of the entry.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value of the entry.
         * 
         * @return Value of the entry.
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value of the entry.
         * 
         * @param value New value of the entry.
         */
        public void setValue(V value) {
            this.value = value;
        }
    }
}
