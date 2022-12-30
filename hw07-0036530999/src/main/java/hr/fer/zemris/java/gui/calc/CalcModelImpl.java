package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
    boolean isEditable = true;
    boolean isNegative = false;
    String currentNumber = "";
    OptionalDouble currentNumberValue = OptionalDouble.empty();
    String frozenDisplayValue = null;
    DoubleBinaryOperator binaryOperator = null;
    OptionalDouble activeOperand = OptionalDouble.empty();

    @Override
    public void addCalcValueListener(CalcValueListener l) {

    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {

    }

    @Override
    public double getValue() {
        if (currentNumberValue.isPresent()) return currentNumberValue.getAsDouble();
        return 0;
    }

    // TODO check this is ok Primijetite da broj koji se predaje u
    // setValue može biti i pozitivno ili negativno beskonačno te NaN, u kojem slučaju se od metode toString
    // očekuje Infinity, -Infinity odnosno NaN
    @Override
    public void setValue(double value) {
        currentNumberValue = OptionalDouble.of(value);
        currentNumber = Double.toString(value);
        isEditable = false;
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void clear() { // TODO not sure this is right
        currentNumber = "";
        currentNumberValue = OptionalDouble.empty();
        isEditable = true;
        isNegative = false;
        frozenDisplayValue = null;
    }

    @Override
    public void clearAll() {
        clear();
        activeOperand = OptionalDouble.empty();
        binaryOperator = null;
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        isNegative = !isNegative;
        frozenDisplayValue = null;
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        if (currentNumber.contains(".")) throw new CalculatorInputException("Number already contains decimal point");
        currentNumber += ".";
        frozenDisplayValue = null;
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        double num;
        try {
            num = Double.parseDouble(currentNumber + digit);
            if (num < 0 || num > 9) throw new IllegalArgumentException("Digit must be between 0 and 9");
        } catch (NumberFormatException e) {
            throw new CalculatorInputException("Invalid number");
        }
        if (currentNumberValue.isPresent() && currentNumberValue.getAsDouble() == 0 && num == 0 && !currentNumber.contains("."))
            return;
        currentNumber += digit;
        currentNumberValue = OptionalDouble.of(num);
        frozenDisplayValue = null;
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperand.isPresent();
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet()) throw new IllegalStateException("Active operand is not set");
        if (activeOperand.isPresent()) return activeOperand.getAsDouble();
        throw new IllegalStateException("Active operand is not set");
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = OptionalDouble.of(activeOperand);
    }

    @Override
    public void clearActiveOperand() {
        this.activeOperand = OptionalDouble.empty();
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return binaryOperator;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        binaryOperator = op;
    }

    @Override
    public String toString() {
        if (frozenDisplayValue != null) return frozenDisplayValue;
        if (currentNumber.equals("")) return isNegative ? "-0" : "0";
        return (isNegative ? "-" : "") + currentNumber;
    }
}
