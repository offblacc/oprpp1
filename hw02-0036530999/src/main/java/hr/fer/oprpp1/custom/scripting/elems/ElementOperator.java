package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents an operator, inheriting Element and overriding.
 */
public class ElementOperator extends Element {
    /**
     * Operator.
     */
    private String symbol;

    /**
     * Returns the symbol of the operator.
     * 
     * @return symbol of the operator
     */
    @Override
    public String asText() {
        return symbol;
    }
    
}