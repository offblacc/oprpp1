package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents a string constant, inheriting Element and overriding.
 */
public class ElementString extends Element {
    /**
     * Value of the string.
     */
    private String value;

    private boolean isTagString;

    /**
     * Constructor that sets the value of the string.
     * 
     * @param value - value of the string
     */
    public ElementString(String value) {
        this(value, false);
    }

    public ElementString(String value, boolean isTagString) {
        this.value = value;
        this.isTagString = isTagString;
    }

    

    /**
     * Return the value of the string as a string.
     * 
     * @return value of the string
     */
    @Override
    public String asText() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public boolean isTagString() {
        return isTagString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value.equals(((ElementString) o).value);
    }

}
