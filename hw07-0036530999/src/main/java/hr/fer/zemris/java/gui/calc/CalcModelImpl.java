package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of the {@link CalcModel} interface.
 *
 * @author offblacc
 */
public class CalcModelImpl implements CalcModel {
    /**
     * Boolean value that indicates if the calculator is editable.
     */
    private boolean isEditable = true;

    /**
     * String representing the current number, or the number that is currently
     * being entered
     */
    private String currentNumber = "";

    /**
     * The double value of the current number.
     */
    private double currentNumberValue = 0;

    /**
     * Frozen display value. Can be null if the display is not frozen.
     */
    private String frozenDisplayValue = null;

    /**
     * The operator that is currently active. Null if no operator is active.
     */
    private DoubleBinaryOperator binaryOperator = null;

    /**
     * The first operand of the binary operation. OptionalDouble.empty() if no
     * operand is active.
     */
    private OptionalDouble activeOperand = OptionalDouble.empty();

    /**
     * True if the user has clicked the decimal point button.
     */
    private boolean hasDecimalPoint = false;

    /**
     * True if the user has clicked any number button. Resets on clear.
     */
    private boolean enteredDigit = false;

    /**
     * List of listeners.
     */
    List<CalcValueListener> listeners = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return currentNumberValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        currentNumberValue = value;
        currentNumber = Double.toString(value);
        isEditable = false;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEditable() {
        return isEditable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() { // TODO not sure this is right
        currentNumber = "";
        currentNumberValue = 0;
        isEditable = true;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAll() {
        clear();
        activeOperand = OptionalDouble.empty();
        binaryOperator = null;
        frozenDisplayValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        currentNumberValue *= -1;
        if (currentNumber.isEmpty()) currentNumber = "0";
        currentNumber = currentNumber.startsWith("-") ? currentNumber.substring(1) : "-" + currentNumber;
        frozenDisplayValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException { // TODO is this ok? -> myb without ? fr idk
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        if (hasDecimalPoint) throw new CalculatorInputException("Number already contains decimal point");
        if (!this.enteredDigit) throw new CalculatorInputException();
        currentNumber += ".";
        hasDecimalPoint = true;
        frozenDisplayValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        if (digit == 0 && (currentNumber.equals("0")) && !hasDecimalPoint) return;

        double num;
        try {
            num = Double.parseDouble(currentNumber + digit);
            if (num > Double.MAX_VALUE) throw new CalculatorInputException("Number is too big");
        } catch (NumberFormatException e) {
            throw new CalculatorInputException("Invalid number");
        }

        if (frozenDisplayValue != null) {
            currentNumber = "";
            frozenDisplayValue = null;
        }
        enteredDigit = true;
        currentNumberValue = num;
        currentNumber += digit;
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActiveOperandSet() {
        return activeOperand.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet()) throw new IllegalStateException("Active operand is not set");
        if (activeOperand.isPresent()) return activeOperand.getAsDouble();
        throw new IllegalStateException("Active operand is not set");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = OptionalDouble.of(activeOperand); // TODO no listener here, right?
        frozenDisplayValue = toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearActiveOperand() {
        this.activeOperand = OptionalDouble.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return binaryOperator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) { // TODO no listener here, right?
        binaryOperator = op;
    }

    /**
     * {@inheritDoc}
     *
     * Returns frozen display value if it is not null. Otherwise, it checks if the current
     * number has a decimal point or any non-zero digits after the decimal point. If it
     * doesn't, it removes any leading zeros from the current number. Finally, it returns
     * the current number, or "0" if the current number is an empty string.
     *
     * @return frozen display value if it is not null. Otherwise, it checks if the current
     * number has a decimal point or any non-zero digits after the decimal point. If it
     * doesn't, it removes any leading zeros from the current number. Finally, it returns
     * the current number, or "0" if the current number is an empty string.
     */
    @Override
    public String toString() {
        if (frozenDisplayValue != null) return frozenDisplayValue;
        // using a regex removes zeros after the decimal point if there are only zeros
        boolean hasRelevantDecimal = currentNumber.matches(".*\\.[1-9]+");
        if (!hasDecimalPoint && !hasRelevantDecimal) {
            int i = 0;
            while (i < currentNumber.length() && currentNumber.charAt(i) == '0') i++;
            currentNumber = currentNumber.substring(i);
        }
        if (currentNumber.equals("")) return "0";
        return currentNumber;
    }
}
