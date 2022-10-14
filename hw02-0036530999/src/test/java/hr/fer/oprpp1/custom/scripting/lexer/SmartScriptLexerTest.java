package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {

    @Test
    public void testEmpty() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testSimpleText() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text");
        assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
        assertEquals("This", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
        assertEquals("is", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
        assertEquals("sample", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
        assertEquals("text", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testForLoop() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $}");
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("FOR", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("i", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("1", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("10", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("1", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }
}
