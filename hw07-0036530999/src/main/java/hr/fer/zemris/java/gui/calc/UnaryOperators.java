package hr.fer.zemris.java.gui.calc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public class UnaryOperators {
    public static final DoubleUnaryOperator RECIPROCAL = x -> (1 / x);
    public static final DoubleUnaryOperator LOG = Math::log10;
    public static final DoubleUnaryOperator LN = Math::log;
    // TODO x^n, doesn't go here, but keep in mind you need to implement it, and it's not a unary operator
    public static final DoubleUnaryOperator SIN = Math::sin;
    public static final DoubleUnaryOperator COS = Math::cos;
    public static final DoubleUnaryOperator TAN = Math::tan;
    public static final DoubleUnaryOperator CTG = x -> (1 / Math.tan(x));

    // -- inverse trigonometric functions --

    public static final DoubleUnaryOperator ASIN = Math::asin;
    public static final DoubleUnaryOperator ACOS = Math::acos;


    /**
     * Returns an array of all available unary operators.
     *
     * @return an array of all available unary operators
     */
    public static DoubleUnaryOperator[] getOperators() {
        return new DoubleUnaryOperator[]{RECIPROCAL, LOG, LN, SIN, COS, TAN, CTG};
    }

    /**
     * Returns a map of available operators and their names.
     *
     * @return a map of available operators and their names
     */
    public static Map<String, DoubleUnaryOperator> getOperatorsMap() {
        HashMap<String, DoubleUnaryOperator> map = new HashMap<>();
        map.put("1/x", RECIPROCAL);
        map.put("log", LOG);
        map.put("ln", LN);
        map.put("sin", SIN);
        map.put("cos", COS);
        map.put("tan", TAN);
        map.put("ctg", CTG);
        return map;
    }
}
