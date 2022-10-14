package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * States for the SmartScriptLexer. Basic state is reating text, while TAG
 * treats all text as variables.
 */
public enum SmartScriptLexerStates {
    BASIC,
    TAG
}
