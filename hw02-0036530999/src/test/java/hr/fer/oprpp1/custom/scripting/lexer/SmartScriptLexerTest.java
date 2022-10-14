package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {
    
    // assert eof
    @Test
    public void testEmptyText() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testBasicText() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("is", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("sample", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("text.", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }
}