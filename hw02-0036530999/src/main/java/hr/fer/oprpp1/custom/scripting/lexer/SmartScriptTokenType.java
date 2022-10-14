package hr.fer.oprpp1.custom.scripting.lexer;

public enum SmartScriptTokenType {
    BASIC, // - covers all text outside of tags
    FOR,
    ECHO,
    BOUND,
    EOF
}
