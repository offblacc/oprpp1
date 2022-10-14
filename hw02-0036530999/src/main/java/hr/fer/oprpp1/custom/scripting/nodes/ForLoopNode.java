package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class that represents a for loop node, inheriting Node.
 */
public class ForLoopNode extends Node {
    ElementVariable variable;
    Element startExpression;
    Element endExpression;
    Element stepExpression; // only one here that can be null
    
    /** // TODO are these getters needed?
     * Returns ElementVariable variable.
     * @return - ElementVariable variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Returns Element startExpression.
     * @return - Element startExpression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Returns Element endExpression.
     * @return - Element endExpression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Returns Element stepExpression.
     * @return - Element stepExpression
     */
    public Element getStepExpression() {
        return stepExpression;
    }

}
