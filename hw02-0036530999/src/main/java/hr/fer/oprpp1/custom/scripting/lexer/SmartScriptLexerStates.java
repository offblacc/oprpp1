package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * States for the SmartScriptLexer. Basic state is reating text, while special
 * means we just opened a tag
 * TODO probably gonna need new states for different tags
 */
public enum SmartScriptLexerStates {
    BASIC, TAG, ID_TAG, FOR_LOOP, FOR_LOOP_VAR, FOR_LOOP_BEG, FOR_LOOP_END, FOR_LOOP_STEP, ECHO
}
