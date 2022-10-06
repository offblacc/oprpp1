package hr.fer.oprpp1.custom.collections;

public class Collection {
    public Collection() {}

    public boolean isEmpty(){
        return this.size() == 0;
    }

    public int size() {
        return 0;
    }

    public void add(Object value) { 
       // "Adds the given object into this collection. Implement it here to do nothing."
    }

    public boolean contains(Object value) {
        return false; // Implement it here to always return false. It is OK to ask if collection contains null.
    }

    public boolean remove(Object value) {
        return false;
    }

    Object[] toArray(){
        throw new UnsupportedOperationException();
    }

    void forEach(Processor processor){
        
    }

    void addAll(Collection other) {
        class LocalProcessor extends Processor {
            public void process(Object value) {
                add(value);
            }
        }
        other.forEach(new LocalProcessor());
    }

    void clear(){

    }
}
