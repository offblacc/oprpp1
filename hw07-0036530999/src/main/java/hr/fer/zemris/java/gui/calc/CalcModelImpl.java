package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
    private boolean isEditable = true;
    private String currentNumber = "";
    private double currentNumberValue = 0;
    private String frozenDisplayValue = null;
    private DoubleBinaryOperator binaryOperator = null;
    private OptionalDouble activeOperand = OptionalDouble.empty();
    private boolean inverted = false;
    private boolean hasDecimalPoint = false;
    private boolean enteredDigit = false;

    List<CalcValueListener> listeners = new ArrayList<>();


    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return currentNumberValue;
    }

    // TODO check this is ok Primijetite da broj koji se predaje u
    // setValue može biti i pozitivno ili negativno beskonačno te NaN, u kojem slučaju se od metode toString
    // očekuje Infinity, -Infinity odnosno NaN
    @Override
    public void setValue(double value) {
        currentNumberValue = value;
        currentNumber = Double.toString(value);
        isEditable = false;
        listeners.forEach(l -> l.valueChanged(this));
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void clear() { // TODO not sure this is right
        currentNumber = "";
        currentNumberValue = 0;
        isEditable = true;
        listeners.forEach(l -> l.valueChanged(this));
    }

    @Override
    public void clearAll() {
        clear();
        activeOperand = OptionalDouble.empty();
        binaryOperator = null;
        frozenDisplayValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable) throw new CalculatorInputException("Calculator is not editable");
        currentNumberValue *= -1;
        if (currentNumber.isEmpty()) currentNumber = "0";
        currentNumber = currentNumber.startsWith("-") ? currentNumber.substring(1) : "-" + currentNumber;
        frozenDisplayValue = null;
        listeners.forEach(l -> l.valueChanged(this));
    }

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
        this.activeOperand = OptionalDouble.of(activeOperand); // TODO no listener here, right?
        frozenDisplayValue = toString();
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
    public void setPendingBinaryOperation(DoubleBinaryOperator op) { // TODO no listener here, right?
        binaryOperator = op;
    }

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
