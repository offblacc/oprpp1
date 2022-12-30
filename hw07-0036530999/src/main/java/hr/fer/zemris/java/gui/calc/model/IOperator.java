package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;

public interface IOperator {
    public static final DoubleBinaryOperator ADD = Double::sum;
    public static final DoubleBinaryOperator SUB = (a, b) -> a - b;
    public static final DoubleBinaryOperator MUL = (a, b) -> a * b;
    public static final DoubleBinaryOperator DIV = (a, b) -> a / b;
    public static final DoubleBinaryOperator POW = Math::pow;
    public static final DoubleBinaryOperator LOG = (a, b) -> Math.log(a) / Math.log(b);
    public static final DoubleBinaryOperator ROOT = (a, b) -> Math.pow(a, 1 / b);
}
