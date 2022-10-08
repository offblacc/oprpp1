package hr.fer.oprpp1.custom.collections;

public class LinkedListIndexedCollection extends Collection {
    private int size;
    private ListNode first;
    private ListNode last;

    public LinkedListIndexedCollection() {
        first = last = null;
    }

    public LinkedListIndexedCollection(Collection other) {
        addAll(other);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(Object value) {
        if (value == null)
            throw new NullPointerException();

        ListNode newNode = new ListNode(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    // TODO unsure about this one
    public void insert(Object value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }

        if (position == size) {
            add(value);
            return;
        }

        ListNode newNode = new ListNode(value);
        ListNode node = first;
        for (int i = 0; i < position - 1; i++) {
            node = node.next;
        }
        ListNode prev = new ListNode(node);
        node = node.next;

        if (position > 0) {
            prev.next = newNode;
            newNode.prev = prev;
        } else {
            first = newNode;
        }

        if (position < size) {
            node.prev = newNode;
            newNode.next = node;
        }
        size++;
    }

    public Object get(int index) { // TODO complexity never more than n/2 + 1
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }

        ListNode node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.value;
    }

    public int indexOf(Object value) {
        ListNode node = first;
        for (int i = 0; node != null; i++) {
            if (node.value.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object value) {
        ListNode node = first;
        while (node != null) {
            if (node.value == value) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean remove(Object value) {
        ListNode node = first;
        while (node != null) {
            if (node.value == value) {
                (node.prev).next = node.next;
                (node.next).prev = node.prev;
                size--;
                return true;
            }

            node = node.next;
        }
        return false;
    }

    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }

        ListNode node = first;
        for (int i = 0; i <= index; i++) {
            node = node.next;
        }

        if (node.prev != null) {
            (node.prev).next = node.next;
        }
        if (node.next != null) {
            (node.next).prev = node.prev;
        }
        size--;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        ListNode node = first;
        int i = 0;
        while (node != null) {
            arr[i++] = node.value;
            node = node.next;
        }
        return arr;
    }

    @Override
    public void forEach(Processor processor) {
        ListNode node = first;
        while (node != null) {
            processor.process(node.value);
            node = node.next;
        }
    }

    @Override
    public void addAll(Collection other) {
        class LocalProcessor extends Processor {
            public void process(Object value) {
                add(value);
            }
        }
        // seems redundant and bad TODO try improving this
        if (other instanceof LinkedListIndexedCollection) {
            other = (LinkedListIndexedCollection) other;
        } else if (other instanceof ArrayIndexedCollection) {
            other = (ArrayIndexedCollection) other;
        }
        other.forEach(new LocalProcessor());
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    private static class ListNode {
        private ListNode prev;
        private ListNode next;
        private Object value;

        private ListNode() {
            this(null, null, null);
        }

        private ListNode(Object value) {
            this(null, null, value);
        }

        private ListNode(ListNode prev, ListNode next, Object value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }

        private ListNode(ListNode node) {
            this(node.prev, node.next, node.value);
        }
    }
}