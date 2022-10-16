package hr.fer.oprpp1.custom.scripting.lexer;

public enum SmartScriptTokenType {
    BASIC, // - covers all text outside of tags
    TAG, // for, = or any other tag name
    BOUND,
    EOF, END
}