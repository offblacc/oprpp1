package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enum representing token types lexer can produce.
 */
public enum SmartScriptTokenType { // TODO not sure if all are needed
    TEXT, TAG, VARIABLE, FUNCTION, OPERATOR, STRING, INTEGER, DOUBLE, EOF
}
