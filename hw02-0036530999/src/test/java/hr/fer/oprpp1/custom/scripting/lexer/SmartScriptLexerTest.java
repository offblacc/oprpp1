package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;

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
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("is", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("sample", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("text.", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    // TODO READING DOUBLES NEEDS TO BE MORE ADVANCED TO READ DOT AT THE END OF THE
    // DOUBLE AS A STRING - EXIT READING DOUBLE WHEN SECOND DOT IS ENCOUNTERED
    @Test
    public void testBasicTestWithNumbers() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text 123 1.23 .");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("is", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("sample", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("text", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("123", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementConstantInteger);
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("1.23.", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());

    }
}