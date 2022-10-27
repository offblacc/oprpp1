package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
    @Test
    public void testConstructor() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertEquals(0, col.size());
    }

    @Test
    public void testConstructorWithCollectionInitialSizeZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayIndexedCollection<Integer>(0);
        });
    }

    @Test
    public void testConstructorWithCollectionContainingValuesAndInitialCapacity() {
        var col1 = new ArrayIndexedCollection<Integer>(2);
        col1.add(2);
        var col = new ArrayIndexedCollection<>(col1);
        assertAll(
                () -> assertEquals(1, col.size()),
                () -> assertEquals(2, col.get(0)));

    }

    @Test
    public void testConstructorWithLinkedListContainingValues() {
        var col1 = new LinkedListIndexedCollection<Integer>();
        col1.add(2);
        var col = new ArrayIndexedCollection<>(col1);
        assertAll(
                () -> assertEquals(1, col.size()),
                () -> assertEquals(2, col.get(0)));

    }
    
    @Test
    public void testConstructorWithEmptyLinkedList() {
        var col1 = new LinkedListIndexedCollection<Integer>();
        var col2 = new ArrayIndexedCollection<>(col1);
        assertEquals(0, col2.size());
    }

    @Test
    public void testSize() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertEquals(0, col.size());
        col.add(1);
        assertEquals(1, col.size());
        col.add(2);
        assertEquals(2, col.size());
        col.add(3);
        assertEquals(3, col.size());
        col.remove(0);
        assertEquals(2, col.size());
    }

    @Test
    public void testIsEmpty() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertTrue(col.isEmpty());
        col.add(1);
        assertFalse(col.isEmpty());
    }

    @Test
    public void testAdd() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        assertEquals(1, col.get(0));
        col.add(2);
        assertEquals(2, col.get(1));
        col.add(3);
        assertEquals(3, col.get(2));
        assertEquals(1, col.get(0));
        assertEquals(2, col.get(1));
    }

    @Test
    public void testAddNull() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertThrows(NullPointerException.class, () -> {
            col.add(null);
        });
    }

    @Test
    public void testContains() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        assertTrue(col.contains(1));
        assertTrue(col.contains(2));
        assertTrue(col.contains(3));
        assertFalse(col.contains(4));
    }

    @Test
    public void testContainsNull() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertFalse(col.contains(null));
    }

    @Test
    public void testIndexOf() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        assertEquals(0, col.indexOf(1));
        assertEquals(1, col.indexOf(2));
        assertEquals(2, col.indexOf(3));
        assertEquals(-1, col.indexOf(4));
    }

    @Test
    public void testIndexOfNull() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertEquals(-1, col.indexOf(null));
    }

    @Test
    public void testToArray() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        Object[] array = col.toArray();
        assertAll(
                () -> assertEquals(1, array[0]),
                () -> assertEquals(2, array[1]),
                () -> assertEquals(3, array[2]));
    }

    @Test
    public void testClear() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        col.clear();
        assertEquals(0, col.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.get(0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.get(2);
        });
    }

    @Test
    public void testGet() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals(2, col.get(1)),
                () -> assertEquals(3, col.get(2)));
    }

    @Test
    public void testGetOutOfBounds() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.get(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.get(3);
        });
    }

    @Test
    public void testRemoveByIndex() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        col.remove(1);
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals(3, col.get(1)),
                () -> assertEquals(2, col.size()));
    }

    @Test
    public void testRemoveByValue() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        col.remove(Integer.valueOf(2));
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals(3, col.get(1)),
                () -> assertEquals(2, col.size()));
    }
 

    @Test
    public void testRemoveByIndexOutOfBounds() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.remove(3);
        });
    }

    @Test
    public void testRemoveByValueNull() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertFalse(col.remove(null));
    }

    @Test
    public void testInsert() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        col.insert(4, 1);
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals(4, col.get(1)),
                () -> assertEquals(2, col.get(2)),
                () -> assertEquals(3, col.get(3)),
                () -> assertEquals(4, col.size()));
    }

    @Test
    public void testInsertOutOfBounds() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.insert(4, -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.insert(4, 4);
        });
    }

    @Test
    public void testInsertNull() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertThrows(NullPointerException.class, () -> {
            col.insert(null, 0);
        });
    }

    // if addall works, foreach works
    @Test
    public void testAddAll() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add(2);
        col.add(3);
        ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>();
        col2.add(4);
        col2.add(5);
        col2.add(6);
        col.addAll(col2);
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals(2, col.get(1)),
                () -> assertEquals(3, col.get(2)),
                () -> assertEquals(4, col.get(3)),
                () -> assertEquals(5, col.get(4)),
                () -> assertEquals(6, col.get(5)),
                () -> assertEquals(6, col.size()));
    }

    @Test
    public void testInsertIntoEmpty() {
        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        col.insert(1, 0);
        assertEquals(1, col.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            col.get(1);
        });
    }

    @Test
    public void testGenerics() {
        ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add("2");
        col.add(3.0);
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals("2", col.get(1)),
                () -> assertEquals(3.0, col.get(2)));
    }

    @Test
    public void testGenerics2() {
        ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
        col.add(1);
        col.add("2");
        col.add(3.0);
        ArrayIndexedCollection<Object> col2 = new ArrayIndexedCollection<>();
        col2.add(4);
        col2.add("5");
        col2.add(6.0);
        col.addAll(col2);
        assertAll(
                () -> assertEquals(1, col.get(0)),
                () -> assertEquals("2", col.get(1)),
                () -> assertEquals(3.0, col.get(2)),
                () -> assertEquals(4, col.get(3)),
                () -> assertEquals("5", col.get(4)),
                () -> assertEquals(6.0, col.get(5)));
    }
}
