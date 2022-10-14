package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents a string constant, inheriting Element and overriding.
 */
public class ElementString extends Element {
    /**
     * Value of the string.
     */
    private String value;

    /**
     * Constructor that sets the value of the string.
     * 
     * @param value - value of the string
     */
    public ElementString(String value) {
        this.value = value;
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

}
