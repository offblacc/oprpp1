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
     * Return the value of the string as a string.
     * 
     * @return value of the string
     */
    @Override
    public String asText() {
        return value;
    }

}
