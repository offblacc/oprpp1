package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {
    public IFieldValueGetter fieldGetter;
    public String literal;
    public IComparisonOperator comparisonOperator;

    public ConditionalExpression(IFieldValueGetter fieldGetter, String literal, IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.literal = literal;
        this.comparisonOperator = comparisonOperator;
    }

    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    public String getLiteral() {
        return literal;
    }

    public String getStringLiteral() {
        return getLiteral();
    }

    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    
}
