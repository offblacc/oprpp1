package hr.fer.zemris.java.gui.calc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

public class BinaryOperators {
    public static final DoubleBinaryOperator ADD = Double::sum;
    public static final DoubleBinaryOperator SUB = (a, b) -> a - b;
    public static final DoubleBinaryOperator MUL = (a, b) -> a * b;
    public static final DoubleBinaryOperator DIV = (a, b) -> a / b;
//    public static final DoubleBinaryOperator POW = Math::pow;
//    public static final DoubleBinaryOperator LOG = (a, b) -> Math.log(a) / Math.log(b);
//    public static final DoubleBinaryOperator ROOT = (a, b) -> Math.pow(a, 1 / b);


    /**
     * Returns an array of all available binary operators.
     *
     * @return an array of all available binary operators
     */
    public static DoubleBinaryOperator[] getOperators() {
        return new DoubleBinaryOperator[]{ADD, SUB, MUL, DIV};
    }

    /**
     * Returns a map of available operators and their names.
     *
     * @return a map of available operators and their names
     */
    public static Map<String, DoubleBinaryOperator> getOperatorsMap() {
        HashMap<String, DoubleBinaryOperator> map = new HashMap<>();
        map.put("+", ADD);
        map.put("-", SUB);
        map.put("*", MUL);
        map.put("/", DIV);
        return map;
    }
}
