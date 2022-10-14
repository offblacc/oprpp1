package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents a function, inheriting Element and overriding.
 */
public class ElementFunction extends Element {
    /**
     * Name of the function.
     */
    private String name;

    /**
     * Returns the name of the function.
     * 
     * @return name of the function
     */
    @Override
    public String asText() {
        return name;
    }

}
