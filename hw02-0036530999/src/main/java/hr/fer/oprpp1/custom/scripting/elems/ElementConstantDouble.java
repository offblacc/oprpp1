package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents a double constant, inheriting Element and overriding.
 */
public class ElementConstantDouble extends Element {
    /**
     * Value of the double.
     */
    private double value;

    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Return the value of the double as a string.
     * 
     * @return value of the double
     */
    @Override
    public String asText() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value == ((ElementConstantDouble) o).value;
    }
}
