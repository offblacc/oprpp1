package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents an integer constant, inheriting Element and overriding.
 */
public class ElementConstantInteger extends Element {
    /**
     * Value of the integer.
     */
    private int value;

    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Return the value of the integer as a string.
     * 
     * @return value of the integer
     */
    @Override
    public String asText() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value == ((ElementConstantInteger) o).value;
    }

}
