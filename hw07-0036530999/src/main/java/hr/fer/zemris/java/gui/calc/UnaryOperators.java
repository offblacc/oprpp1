package hr.fer.zemris.java.gui.calc;

import java.util.LinkedHashMap;
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


    public static final DoubleUnaryOperator TENEXN = x -> Math.pow(10, x);
    public static final DoubleUnaryOperator EXP = Math::exp;
    public static final DoubleUnaryOperator ASIN = Math::asin;
    public static final DoubleUnaryOperator ACOS = Math::acos;
    public static final DoubleUnaryOperator ATAN = Math::atan;
    public static final DoubleUnaryOperator ACTG = x -> (Math.PI / 2 - Math.atan(x));


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
    @SuppressWarnings("Duplicates")
    public static Map<String, DoubleUnaryOperator> getOperatorsMap() {
        LinkedHashMap<String, DoubleUnaryOperator> map = new LinkedHashMap<>();
        map.put("1/x", RECIPROCAL);
        map.put("log", LOG);
        map.put("ln", LN);
        map.put("sin", SIN);
        map.put("cos", COS);
        map.put("tan", TAN);
        map.put("ctg", CTG);
        return map;
    }

    /**
     * Returns a map of available inverse operators and their uninverted names.
     *
     * @return a map of available inverse operators and their uninverted names
     */
    @SuppressWarnings("Duplicates")
    public static Map<String, DoubleUnaryOperator> getInvOperatorsMap() {
        LinkedHashMap<String, DoubleUnaryOperator> map = new LinkedHashMap<>();
        map.put("log", TENEXN);
        map.put("ln", EXP);
        map.put("sin", ASIN);
        map.put("cos", ACOS);
        map.put("tan", ATAN);
        map.put("ctg", ACTG);
        return map;
    }
}
