package hr.fer.oprpp1.custom.collections;

/**
 * A more detailed definition of methods that any list should implement.
 * Extends class Collection.
 */
public interface List<T> extends Collection<T> {

    /**
     * Returns the object at the given index in the collection.
     * 
     * @param index - index of the object to be returned
     * @return object at the requested index
     */
    public T get(int index);

    /**
     * Inserts value at the given position in the collection. Element that was
     * previously at that index and every next one are moved right one place,
     * increasing their indexes by one to make space for the element we're
     * inserting. The value cannot be null, and position must be between 0 and size.
     * 
     * @param value    - value to be inserted in the collection
     * @param position - index to which to insert that value
     * @throws NullPointerException      if the passed value is null
     * @throws IndexOutOfBoundsException if position is not between 0 and size
     */
    public void insert(T value, int position);

    /**
     * Searches the collection and returns the index of the first occurrence of the
     * given value or -1 if the value is not found.
     * 
     * @param value - value to be searched for
     * @return index of the first occurrence of the value or -1 if the value is not
     *         found
     */
    public int indexOf(T value);

    /**
     * Removes element at specified index from collection. Element that was
     * previously at the index after the removed one and every next one are moved
     * left one place, decreasing their indexes by one to fill the gap. The value
     * cannot be null, and position must be between 0 and size - 1.
     * 
     * @param index - index of the element to be removed
     * @throws IndexOutOfBoundsException if index is not between 0 and size - 1
     */
    public void remove(int index);
}
