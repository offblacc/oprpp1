package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents a variable, inheriting Element an overriding.
 */
public class ElementVariable extends Element {
    /**
     * Name of the variable.
     */
    private String name;

    /**
     * Constructor that takes a name of the variable.
     * @param name - name of the variable.
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the name variable
     * 
     * @return name of the variable
     */
    @Override
    public String asText() {
        return name;
    }
}
