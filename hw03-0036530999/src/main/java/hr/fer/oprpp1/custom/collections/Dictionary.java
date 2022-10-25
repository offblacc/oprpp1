package hr.fer.oprpp1.custom.collections;

public class Dictionary<K, V> {
    ArrayIndexedCollection<Entry<K, V>> col;

    public Dictionary() {
        col = new ArrayIndexedCollection<>();
    }
    // TODO key can't be null
    // only value is nullable

    public boolean isEmpty() {
        return col.isEmpty();
    }

    public int size() {
        return col.size();
    }

    public void clear() {
        col.clear();
    }

    public V put(K key, V value) {
        for (int i = 0; i < col.size(); i++) {
            if (col.get(i).getKey().equals(key)) {
                col.get(i).setValue(value);
                return value;
            }
        }
        col.add(new Entry<K, V>(key, value));
        return value;
    }

    public V get(K key) {
        for (int i = 0; i < col.size(); i++) {
            if (col.get(i).getKey().equals(key))
                return col.get(i).getValue();
        }
        return null;
    }

    public V remove(K key) {
        for (int i = 0; i < col.size(); i++) {
            if (col.get(i).getKey().equals(key)) {
                V value = col.get(i).getValue();
                col.remove(i);
                return value;
            }
        }
        return null;
    }

    private static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
