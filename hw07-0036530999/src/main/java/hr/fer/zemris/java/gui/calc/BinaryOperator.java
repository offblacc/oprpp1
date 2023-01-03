package hr.fer.zemris.java.gui.calc;

import java.util.HashMap;
import java.util.function.DoubleBinaryOperator;

public interface BinaryOperator {
    DoubleBinaryOperator ADD = Double::sum;
    DoubleBinaryOperator SUB = (a, b) -> a - b;
    DoubleBinaryOperator MUL = (a, b) -> a * b;
    DoubleBinaryOperator DIV = (a, b) -> a / b;
//    public static final DoubleBinaryOperator POW = Math::pow;
//    public static final DoubleBinaryOperator LOG = (a, b) -> Math.log(a) / Math.log(b);
//    public static final DoubleBinaryOperator ROOT = (a, b) -> Math.pow(a, 1 / b);


    /**
     * Returns a map of all available binary operators.
     * @return a map of all available binary operators
     */
    static DoubleBinaryOperator[] getOperators() {
        return new DoubleBinaryOperator[] {ADD, SUB, MUL, DIV};
    }

    /**
     * Returns a map of available operators and their names.
     * @return a map of available operators and their names
     */
    static HashMap<String, DoubleBinaryOperator> getOperatorsMap() {
        HashMap<String, DoubleBinaryOperator> map = new HashMap<>();
        map.put("+", ADD);
        map.put("-", SUB);
        map.put("*", MUL);
        map.put("/", DIV);
        return map;
    }
}
