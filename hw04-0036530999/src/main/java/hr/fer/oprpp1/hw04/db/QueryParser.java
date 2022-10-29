package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
    private QueryLexer lexer;
    private QueryToken token;
    private boolean isDirectQuery;
    List<ConditionalExpression> expressions;
    private boolean isExit;

    public QueryParser(String query) {
        lexer = new QueryLexer(query);
        parseQuery();
    }

    public void parseQuery() {// TODO db ignores query kw
        token = lexer.nextToken();
        expressions = new ArrayList<>();
        if (token.getType() == QueryTokenType.EOF) {
            throw new QueryParserException("Query is empty.");
        }

        isExit = false;
        if (token.getType() == QueryTokenType.EXIT) {
            isExit = true;
        }
        var fieldValueGetter = resolveFieldValueGetter(token.getType());
        var comparisonOperator = resolveComparisonOperator((token = lexer.nextToken()));
        var literal = (token = lexer.nextToken()).getValue();
        expressions.add(new ConditionalExpression(fieldValueGetter, literal, comparisonOperator));

        token = lexer.nextToken();

        while (token.getType() != QueryTokenType.EOF) {
            if (token.getType() != QueryTokenType.AND) {
                throw new QueryParserException("Expected AND, got " + token.getValue());
            }
            QueryToken[] condList = new QueryToken[3];
            for (int i = 0; i < 3; i++)
                condList[i] = lexer.nextToken();

            expressions.add(new ConditionalExpression(resolveFieldValueGetter(condList[0].getType()),
                    condList[2].getValue(), resolveComparisonOperator(condList[1])));
            token = lexer.nextToken();
        }
        isDirectQuery = resolveIsDirectQuery();
    }

    /**
     * Determines if the query is direct query.
     * 
     * @return - true if the query is direct query, false otherwise.
     */
    private boolean resolveIsDirectQuery() {
        if (expressions.size() != 1)
            return false;

        ConditionalExpression expression = expressions.get(0);
        if (expression.getFieldGetter() != FieldValueGetters.JMBAG)
            return false;

        if (expression.getComparisonOperator() != ComparisonOperators.EQUALS)
            return false;

        return true;
    }

    /**
     * Getter for isDirectQuery. For actual calculation of isDirectQuery's value,
     * call resolveIsDirectQuery().
     * 
     * @return - true if query is direct query, false otherwise
     */
    public boolean isDirectQuery() {
        return isDirectQuery;
    }

    /**
     * Returns the jmbag for comparison if the query is direct query. Throws
     * IllegalStateException if the query is not direct query.
     * 
     * @return - jmbag for comparison
     * @throws IllegalStateException - if the query is not direct query
     */
    String getQueriedJMBAG() {
        if (!isDirectQuery())
            throw new IllegalStateException("Query is not direct query.");

        return (String) expressions.get(0).getStringLiteral();
    }

    /**
     * Returns the list of conditional expressions.
     * 
     * @return - list of conditional expressions
     */
    public List<ConditionalExpression> getQuery() {
        return expressions;
    }

    private IFieldValueGetter resolveFieldValueGetter(QueryTokenType typ) {
        switch (typ) {
            case LASTNAME:
                return FieldValueGetters.LAST_NAME;
            case FIRSTNAME:
                return FieldValueGetters.FIRST_NAME;
            case JMBAG:
                return FieldValueGetters.JMBAG;
            default:
                throw new UnsupportedOperationException("Could not resolve FieldValueGetter");
        }
    }

    private IComparisonOperator resolveComparisonOperator(QueryToken token) {
        if (token.getType() == QueryTokenType.LIKE) {
            return ComparisonOperators.LIKE;
        }
        switch (token.getValue()) {
            case "<":
                return ComparisonOperators.LESS;
            case "<=":
                return ComparisonOperators.LESS_OR_EQUALS;
            case ">":
                return ComparisonOperators.GREATER;
            case ">=":
                return ComparisonOperators.GREATER_OR_EQUALS;
            case "=":
                return ComparisonOperators.EQUALS;
            case "!=":
                return ComparisonOperators.NOT_EQUALS;
            default:
                throw new UnsupportedOperationException("Could not resolve ComparisonOperator");

        }
    }
}
